package com.example.backend_demo.dao;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

@Repository
public class InsertApiDAOImpl {

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


    public void insertCenter() {
        try (BufferedReader infile
                     = new BufferedReader(new FileReader("src/center.csv"))) {
            openDB();
            //PreparedStatement statement = null;
            String line;
            String[] parts;
            String a, b;
            String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
            infile.readLine();
            String sql = "insert into center(id, name) values (?::integer,?) ";
            try {
                statement = con.prepareStatement(sql);
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split(csvSplitBy);
                a = parts[0];
                if (parts[1].charAt(0) == '\"') {
                    b = parts[1].substring(1, parts[1].length() - 1);
                } else b = parts[1];
                loadData(a, b, statement);
            }
            con.commit();
            System.out.println("Finish");
            statement.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();

            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();

            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        }
        closeDB();
    }


    public void insertEnterprise() {

        try (BufferedReader infile
                     = new BufferedReader(new FileReader("src/enterprise.csv"))) {
            openDB();
            PreparedStatement preparedStatement;
            String line;
            String[] parts;
            String a, b, c, d, e, f;
            String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
            infile.readLine();
            String sql = "insert into enterprise(id, enterprise_name,country,city,supply_center,industry) values (?::integer,?,?,?,?,?) ";
            try {
                statement = con.prepareStatement(sql);
            } catch (SQLException ee) {
                System.err.println("Insert statement failed");
                System.err.println(ee.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split(csvSplitBy);
                a = parts[0];
                b = parts[1];
                c = parts[2];
                if (Objects.equals(parts[3], "")) {
                    d = null;
                } else d = parts[3];
                if (parts[4].charAt(0) == '\"') {
                    e = parts[4].substring(1, parts[4].length() - 1);
                } else e = parts[4];
                f = parts[5];
                loadData(a, b, c, d, e, f, statement);
            }

            con.commit();
            System.out.println("Finish");
            statement.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
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


    public void insertModel() {
        try (BufferedReader infile
                     = new BufferedReader(new FileReader("src/model.csv"))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c, d, e;
            String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
            infile.readLine();
            String sql = "insert into model(id, product_number,product_model,product_name,unit_price) values (?::integer,?,?,?,?::integer) ";
            try {
                statement = con.prepareStatement(sql);
            } catch (SQLException ee) {
                System.err.println("Insert statement failed");
                System.err.println(ee.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split(csvSplitBy);
                a = parts[0];
                b = parts[1];
                c = parts[2];
                d = parts[3];
                e = parts[4];
                loadData(a, b, c, d, e, statement);
            }

            con.commit();
            System.out.println("Finish");
            statement.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
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

    public void insertStaff() {
        try (BufferedReader infile
                     = new BufferedReader(new FileReader("src/staff.csv"))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c, d, e, f, g, h;
            String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
            infile.readLine();
            String sql = "insert into staff(id, staff_name,age,gender,staff_number,supply_center,mobile_number,type) values " +
                    "(?::integer,?,?::integer,?,?,?,?,?) ";
            try {
                statement = con.prepareStatement(sql);
            } catch (SQLException ee) {
                System.err.println("Insert statement failed");
                System.err.println(ee.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split(csvSplitBy);
                a = parts[0];
                b = parts[1];
                c = parts[2];
                d = parts[3];
                e = parts[4];
                if (parts[5].charAt(0) == '\"') {
                    f = parts[5].substring(1, parts[5].length() - 1);
                } else f = parts[5];
                g = parts[6];
                h = parts[7];
                loadData(a, b, c, d, e, f, g, h, statement);
            }

            con.commit();
            System.out.println("Finish");
            statement.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
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

    public void stockIn(String file) {
        try (BufferedReader infile
                     = new BufferedReader(new FileReader(file))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c, d, e, f, g;
            String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
            infile.readLine();
            String sql = "insert into store_record(id, supply_center, product_model, supply_staff, storage_date, purchase_price, quantity)" +
                    " values (?::integer,?,?,?,?::date,?::integer,?::integer) ";
            try {
                statement = con.prepareStatement(sql);
            } catch (SQLException err) {
                System.err.println("Insert statement failed");
                System.err.println(err.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split(csvSplitBy);
                a = parts[0];
                //supply center
                if (parts[1].charAt(0) == '\"') {
                    b = parts[1].substring(1, parts[1].length() - 1);
                } else b = parts[1];
                c = parts[2]; //model
                d = parts[3]; //staff
                e = parts[4]; //date
                f = parts[5]; //price
                g = parts[6]; //quantity
                if (stockCheckCenterExist(b) == null) {
                    continue;
                }
                if (stockCheckStaffExist(d) == null) {
                    continue;
                }
                if (stockCheckModelExist(c) == null) {
                    continue;
                }
                if (!stockCheckStaffType(d).equals("Supply Staff")) {
                    continue;
                }
                if (!stockCheckCenterStaff(d).equals(b)) {
                    continue;
                }
                updateStorage(a, b, c, g);
                loadData(a, b, c, d, e, f, g, statement);
            }
            con.commit();
            System.out.println("Finish");
            statement.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
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

    public void updateStorage(String id, String center, String model, String q) {
        String insert = "insert into storage(id,supply_center,product_model,quantity)" +
                "values(?::integer,?,?,?::integer)";
        String select = "select * from storage where supply_center = ? and product_model = ?";
        String update = "update storage set quantity = quantity + ?::integer where supply_center = ? and product_model = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(select);
            preparedStatement.setString(1, center);
            preparedStatement.setString(2, model);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                //执行insert
                preparedStatement = con.prepareStatement(insert);
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, center);
                preparedStatement.setString(3, model);
                preparedStatement.setString(4, q);
                preparedStatement.execute();
            } else {
                //执行update
                preparedStatement = con.prepareStatement(update);
                preparedStatement.setString(1, q);
                preparedStatement.setString(2, center);
                preparedStatement.setString(3, model);
                preparedStatement.execute();
                System.out.println(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String stockCheckCenterStaff(String str) {
        StringBuilder sb = new StringBuilder();
        String sql = "select supply_center from staff where staff_number=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, str);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("supply_center"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    public String stockCheckStaffType(String str) {
        StringBuilder sb = new StringBuilder();
        String sql = "select type from staff where staff_number=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, str);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String stockCheckStaffExist(String str) {
        StringBuilder sb = new StringBuilder();
        String sql = "select * from staff where staff_number=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, str);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else return "ok";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String stockCheckModelExist(String str) {
        StringBuilder sb = new StringBuilder();
        String sql = "select * from model where product_model=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, str);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else return "ok";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String stockCheckCenterExist(String str) {
        StringBuilder sb = new StringBuilder();
        String sql = "select * from center where name=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, str);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else return "ok";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void placeOrder(String file) {
        try (BufferedReader infile
                     = new BufferedReader(new FileReader(file))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c, d, e, f, g, h, i, j;
            infile.readLine();
            String sql = "insert into contract(id,contract_num, enterprise, product_model, quantity, contract_manager," +
                    " contract_date, est_date,lod_date,salesman_num,contract_type)" +
                    " values (?::integer,?,?,?,?::integer,?,?::date,?::date,?::date,?,?) ";

            try {
                statement = con.prepareStatement(sql);
            } catch (SQLException err) {
                System.err.println("Insert statement failed");
                System.err.println(err.getMessage());
                closeDB();
                System.exit(1);
            }
            int index = 1;
            while ((line = infile.readLine()) != null) {
                parts = line.split("\t");
                a = parts[0];//contract_num
                b = parts[1];//enterprise
                c = parts[2]; //model
                d = parts[3]; //quantity
                e = parts[4]; //manager
                f = parts[5]; //date
                g = parts[6]; //est_date
                //lod_date
                if (Objects.equals(parts[7], "")) {
                    h = null;
                } else h = parts[7];
                i = parts[8];//sales_man num
                j = parts[9];//contract_type
                if (!stockCheckStaffType(i).equals("Salesman")) {
                    continue;
                }
                //库存 < 商品
                String center = orderFindCenter(parts[1]);
                int quantity = Integer.parseInt(orderCheckStock(center, c));
//                System.out.println(parts[1] + " " + parts[2] + " 库存：" + quantity + " 需要：" + parts[3]);
                if (quantity == -1) {
                    continue;//不存在商品
                }
                if (quantity < Integer.parseInt(parts[3])) {
                    continue;//库存少
                }
                loadData(index++, a, b, c, d, e, f, g, h, i, j, statement);
//                index++;
                //先查重，再加入contract
                String select = "select * from contract_record where contract_num = ?";
                String insert = "insert into contract_record(contract_num, enterprise, manager) " +
                        "values(?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(select);
                preparedStatement.setString(1, a);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    preparedStatement = con.prepareStatement(insert);
                    preparedStatement.setString(1, a);
                    preparedStatement.setString(2, b);
                    preparedStatement.setString(3, e);
                    preparedStatement.execute();
                }
                updateStorageInPlaceOrder(parts[3], center, c);
            }

            con.commit();
            System.out.println("Finish");
            statement.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
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

    public void updateStorageInPlaceOrder(String quantity, String center, String model) {
        String update = "update storage set quantity = quantity - ?::integer where supply_center = ? and product_model = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(update);
            preparedStatement.setString(1, quantity);
            preparedStatement.setString(2, center);
            preparedStatement.setString(3, model);
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

    public String orderCheckStock(String center, String model) {//-1表示不存在
        StringBuilder sb = new StringBuilder();
        String sql = "select quantity from storage where supply_center = ?" +
                "and product_model = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, center);
            preparedStatement.setString(2, model);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sb.append(resultSet.getString("quantity"));
            } else sb.append("-1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //检测时间更改
    public void insertUnfinishedOrder(String newFile) {
        try (BufferedReader infile = new BufferedReader(new FileReader(newFile))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c, d, e,f,g,h,i,j;
            infile.readLine();
            int index = 1000;
            String sql = "insert into contract(id,contract_num, enterprise, product_model, quantity, contract_manager," +
                    " contract_date, est_date,lod_date,salesman_num,contract_type)" +
                    " values (?::integer,?,?,?,?::integer,?,?::date,?::date,?::date,?,?) ";
            try {
                statement = con.prepareStatement(sql);
            } catch (SQLException ee) {
                System.err.println("Insert statement failed");
                System.err.println(ee.getMessage());
                closeDB();
                System.exit(1);
            }
            while ((line = infile.readLine()) != null) {
                parts = line.split(",");
                System.out.println(line);
                a = parts[0];//contract_num
                b = parts[1];//enterprise
                c = parts[2]; //model
                d = parts[3]; //quantity
                e = parts[4]; //manager
                f = parts[5]; //date
                g = parts[6]; //est_date
                //lod_date
                if (Objects.equals(parts[7], "")) {
                    h = null;
                } else h = parts[7];
                i = parts[8];//sales_man num
                j = parts[9];//contract_type
                System.out.println(stockCheckStaffType(i));
                if (!stockCheckStaffType(i).equals("Salesman")) {
                    System.out.println("人员类型");
                    continue;
                }
                //库存 < 商品
                String center = orderFindCenter(parts[1]);
                int quantity = Integer.parseInt(orderCheckStock(center, c));
                if (quantity == -1) {
                    System.out.println("不存在");
                    continue;//不存在商品
                }
                if (quantity < Integer.parseInt(parts[3])) {
                    System.out.println("库存少");
                    continue;//库存少
                }
                loadData(index++, a, b, c, d, e, f, g, h, i, j, statement);
                //未完成的订单，不更新库存，只加入record中
                //先查重，再加入contract
                String select = "select * from contract_record where contract_num = ?";
                String insert = "insert into contract_record(contract_num, enterprise, manager) " +
                        "values(?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(select);
                preparedStatement.setString(1, a);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    System.out.println("做记录");
                    preparedStatement = con.prepareStatement(insert);
                    preparedStatement.setString(1, a);
                    preparedStatement.setString(2, b);
                    preparedStatement.setString(3, e);
                    preparedStatement.execute();
                }
            }
            con.commit();
            System.out.println("new order imported");
            statement.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                statement.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
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

}


