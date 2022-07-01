package com.example.backend_demo.dao;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@Repository
public class UpdateApiDAOImpl {

    private String url = "jdbc:postgresql://localhost:5432/contract_project2";
    private static Connection con = null;

    private ResultSet resultSet;

    private PreparedStatement statement = null;

    public void openDB() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            con = DriverManager.getConnection(url, "checker", "123456");
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadData(String a, String b, PreparedStatement statement)
            throws SQLException {
        if (con != null) {
            statement.setString(1, a);
            statement.setString(2, b);
            statement.execute();
        }
    }

    public static void loadData(String a, String b, String c, String d, String e, String f, PreparedStatement statement)
            throws SQLException {
        if (con != null) {
            statement.setString(1, a);
            statement.setString(2, b);
            statement.setString(3, c);
            statement.setString(4, d);
            statement.setString(5, e);
            statement.setString(6, f);
            statement.execute();
        }
    }

    public static void loadData(String a, String b, String c, String d, String e, PreparedStatement statement)
            throws SQLException {
        if (con != null) {
            statement.setString(1, a);
            statement.setString(2, b);
            statement.setString(3, c);
            statement.setString(4, d);
            statement.setString(5, e);
            statement.execute();
        }
    }

    public static void loadData(String a, String b, String c, String d, String e, String f, String g, PreparedStatement statement)
            throws SQLException {
        if (con != null) {
            statement.setString(1, a);
            statement.setString(2, b);
            statement.setString(3, c);
            statement.setString(4, d);
            statement.setString(5, e);
            statement.setString(6, f);
            statement.setString(7, g);
            statement.execute();
        }
    }


    public static void loadData(String a, String b, String c, String d, String e, String f, String g, String h, PreparedStatement statement)
            throws SQLException {
        if (con != null) {
            statement.setString(1, a);
            statement.setString(2, b);
            statement.setString(3, c);
            statement.setString(4, d);
            statement.setString(5, e);
            statement.setString(6, f);
            statement.setString(7, g);
            statement.setString(8, h);
            statement.execute();
        }
    }

    public static void loadData(int index, String a, String b, String c, String d, String e, String f, String g, String h, String i, String j, PreparedStatement statement)
            throws SQLException {
        if (con != null) {
            statement.setInt(1, index);
            statement.setString(2, a);
            statement.setString(3, b);
            statement.setString(4, c);
            statement.setString(5, d);
            statement.setString(6, e);
            statement.setString(7, f);
            statement.setString(8, g);
            statement.setString(9, h);
            statement.setString(10, i);
            statement.setString(11, j);
            statement.execute();
        }
    }

    public void updateUnfinished(String file){
        try (BufferedReader infile
                     = new BufferedReader(new FileReader(file))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c, d, e, f;
            infile.readLine();
            String update = "update contract set quantity = ?::integer, est_date = ?::date, " +
                    "lod_date = ?::date,contract_type = 'Finished' where " +
                    "contract_num = ? and product_model = ? and salesman_num = ?";

            try {
                statement = con.prepareStatement(update);
            } catch (SQLException err) {
                System.err.println("Insert statement failed");
                System.err.println(err.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split("\t");
                a = parts[0];//contract_num
                b = parts[1];//product_model
                c = parts[2];//salesman
                d = parts[3];//quantity
                e = parts[4];//estimated
                f = parts[5];//lod_date
                //是否有订单
//                boolean flag = checkOrderValid(a, b, c);
//                if (!flag) {
//                    continue;
//                }
                int preQuantity = getPreQuantity(a, b, c);

                //获取enterprise
                String enterprise = getEnterprise(a, b, c);
                //更新库存
                updateOrderUpdateStorage(preQuantity, Integer.parseInt(d), enterprise, b);
                //下面出了错
                if (Integer.parseInt(d) == 0) {
                    //若订单要更新为0，直接删！
                    String delete = "delete from contract " +
                            "where contract_num = ? and product_model = ? and salesman_num = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(delete);
                    preparedStatement.setString(1, a);//contract
                    preparedStatement.setString(2, b);//model
                    preparedStatement.setString(3, c);//salesman
                    preparedStatement.execute();
//                    System.out.println("订单更新为0，删除当前行，合同号：" + a + " model: " + b);
                } else {
                    System.out.println("更新type");
//                    System.out.println("更新当前行。  合同号：" + a + " model: " + b + "salesman : " + c);
                    loadData(d, e, f, a, b, c, statement);
                }
            }
            con.commit();
            System.out.println("unfinished changed Finish");
        } catch (
                SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (
                IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        }

        closeDB();
    }
    public void updateOrder(String file) {
        try (BufferedReader infile
                     = new BufferedReader(new FileReader(file))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c, d, e, f;
            infile.readLine();
            String update = "update contract set quantity = ?::integer, est_date = ?::date, " +
                    "lod_date = ?::date where " +
                    "contract_num = ? and product_model = ? and salesman_num = ?";

            try {
                statement = con.prepareStatement(update);
            } catch (SQLException err) {
                System.err.println("Insert statement failed");
                System.err.println(err.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split("\t");
                a = parts[0];//contract_num
                b = parts[1];//product_model
                c = parts[2];//salesman
                d = parts[3];//quantity
                e = parts[4];//estimated
                f = parts[5];//lod_date
                //是否有订单
                boolean flag = checkOrderValid(a, b, c);
                if (!flag) {
                    continue;
                }
                int preQuantity = getPreQuantity(a, b, c);

                //获取enterprise
                String enterprise = getEnterprise(a, b, c);
                //更新库存
                updateOrderUpdateStorage(preQuantity, Integer.parseInt(d), enterprise, b);
                //下面出了错
                if (Integer.parseInt(d) == 0) {
                    //若订单要更新为0，直接删！
                    String delete = "delete from contract " +
                            "where contract_num = ? and product_model = ? and salesman_num = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(delete);
                    preparedStatement.setString(1, a);//contract
                    preparedStatement.setString(2, b);//model
                    preparedStatement.setString(3, c);//salesman
                    preparedStatement.execute();
//                    System.out.println("订单更新为0，删除当前行，合同号：" + a + " model: " + b);
                } else {
//                    System.out.println("更新当前行。  合同号：" + a + " model: " + b + "salesman : " + c);
                    loadData(d, e, f, a, b, c, statement);
                }
            }
            con.commit();
            System.out.println("Finish");
        } catch (
                SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (
                IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        }

        closeDB();
    }

    public String getEnterprise(String contract, String model, String salesman) {
        StringBuilder sb = new StringBuilder();
        String sql = "select enterprise from contract " +
                "where contract_num = ? and product_model = ? and salesman_num = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, contract);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, salesman);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sb.append(resultSet.getString("enterprise"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void updateOrderUpdateStorage(int preQuantity, int curQuantity, String enterprise, String model) {
        String sql = "update storage set quantity = quantity + ? - ? " +
                "where supply_center = ? and product_model = ?";//加上之前的，减去现在的
        String center = orderFindCenter(enterprise);
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, preQuantity);
            preparedStatement.setInt(2, curQuantity);
            preparedStatement.setString(3, center);
            preparedStatement.setString(4, model);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String orderFindCenter(String name) {
        StringBuilder sb = new StringBuilder();
        String sql = "select supply_center from enterprise where enterprise_name = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("supply_center"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }


    public int getPreQuantity(String contract, String model, String salesman) {
//        StringBuilder sb = new StringBuilder();
        int result = 0; // 无订单
        String sql = "select quantity from contract " +
                "where contract_num = ? and product_model = ? and salesman_num = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, contract);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, salesman);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkOrderValid(String contract, String model, String salesman) {
        boolean flag = false;
        String sql = "select * from contract " +
                "where contract_num = ? and product_model = ? and salesman_num = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, contract);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, salesman);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void updateType() {
        openDB();
        String select = "select * from contract where lod_date is not null and contract_type = 'Unfinished';";
        String sql = "update contract set contract_type = 'Finished' where id = ?;";
        String updateStorage = "update storage set quantity = quantity - ? " +
                "where supply_center = ? and product_model = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String enterprise = resultSet.getString("enterprise");
                int quantity = resultSet.getInt("quantity");
                String model = resultSet.getString("product_model");
                String center = orderFindCenter(enterprise);
                PreparedStatement p0 = con.prepareStatement(sql);
                //合同完成，更改为finish并库存减少
                p0.setInt(1, id);
                p0.execute();
                PreparedStatement p1 = con.prepareStatement(updateStorage);
                p1.setInt(1,quantity);
                p1.setString(2,center);
                p1.setString(3,model);
                p1.execute();
            }
            con.commit();
            System.out.println("Finish");
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        }
        closeDB();
    }
}
