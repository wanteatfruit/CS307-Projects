package com.example.backend_demo.dao;

import com.example.backend_demo.pojo.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SelectApiIDAOImpl {

    private String url = "jdbc:postgresql://localhost:5432/contract_project2";
    private static Connection con = null;

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


    public List<Staff> getAllStaffCount() {
        Connection connection = null;
        List<Staff> staffList = new ArrayList<>();
        String sql = "select type, count(*) as cnt from staff group by type";

        try {
            String url = "jdbc:postgresql://localhost:5432/contract_project2";
            connection = DriverManager.getConnection(url, "checker", "123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Staff s = new Staff();
                s.setType(rs.getString("type"));
                s.setCount(rs.getInt("cnt"));
                staffList.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public List<Contract> getContractCount() {
        openDB();
        List<Contract> contracts = new ArrayList<>();

        String sql = "select count(*) as cnt " +
                "from contract_record";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contract contract = new Contract();
                contract.setCount(resultSet.getInt("cnt"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return contracts;
    }

    public List<Contract> getOrderCount() {
        openDB();
        List<Contract> contracts = new ArrayList<>();

        String sql = "select count(*) as cnt from contract";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contract contract = new Contract();
                contract.setCount(resultSet.getInt("cnt"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return contracts;
    }

    public List<Storage> getNeverSoldProductCount() {
        openDB();
        List<Storage> list = new ArrayList<>();
        String sql = "select count(distinct product_model) as cnt from storage " +
                "where product_model not in (select distinct product_model from contract);";
        try {
            ResultSet resultSet;
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Storage storage = new Storage();
                storage.setCount(resultSet.getInt("cnt"));
                list.add(storage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    public List<Model> getFavoriteProductModel() {

        openDB();
        List<Model> list = new ArrayList<>();

        String sql = "with q as " +
                "(select product_model, sum(quantity) as cnt " +
                "from contract group by product_model) " +
                "select * from q where cnt = (select max(cnt) from q);";
        try {
            ResultSet resultSet;
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Model model = new Model();
                model.setSold_sum(resultSet.getInt("cnt"));
                model.setProduct_model(resultSet.getString("product_model"));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    public List<Center> getAvgCenter() {

        openDB();
        List<Center> list = new ArrayList<>();

        String sql = "select supply_center, round(((100.0*sum)/(100*cnt)),1) as avg " +
                "from (select distinct supply_center, " +
                "                      sum(quantity) over (partition by supply_center) as sum, " +
                "                      count(*) over (partition by supply_center) as cnt " +
                "      from storage) s order by supply_center;";
        try {
            ResultSet resultSet;
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Center center = new Center();
                center.setName(resultSet.getString("supply_center"));
                center.setAvg(resultSet.getDouble("avg"));
                list.add(center);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    public List<Generic> getProductByNumber(String product_number) { //only for convenience
        openDB();
        List<Generic> list = new ArrayList<>();
        String sql = "select storage.supply_center,m.product_model,sr.purchase_price,storage.quantity from storage join model m on storage.product_model = m.product_model join store_record sr on m.product_model = sr.product_model where product_number=? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, product_number);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Generic generic = new Generic();
                generic.setSupply_center(resultSet.getString("supply_center"));
                generic.setProduct_model(resultSet.getString("product_model"));
                generic.setQuantity(resultSet.getInt("quantity"));
                list.add(generic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    //step 0 in q13
    public List<Contract> getContractNum(String con_num) {
        String sql = "select supply_center,contract_num from enterprise join contract_record c on enterprise.enterprise_name = c.enterprise where contract_num=?";
        openDB();
        List<Contract> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, con_num);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contract contract = new Contract();
                contract.setContract_number(resultSet.getString("contract_num"));
                list.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }


    //step 1 in q13
    public List<Enterprise> getEnterpriseByContractNum(String con_num) {
        openDB();
        String sql = "select * from contract_record where contract_num = ?";
        List<Enterprise> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, con_num);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Enterprise enterprise = new Enterprise();
                enterprise.setEnterprise_name(resultSet.getString("enterprise"));
                list.add(enterprise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    //step2 in q13
    public List<Center> getCenterByContractNum(String con_num) {
        String sql = "select supply_center,contract_num from enterprise join contract_record c on enterprise.enterprise_name = c.enterprise where contract_num=?";
        openDB();
        List<Center> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, con_num);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Center center = new Center();
                center.setName(resultSet.getString("supply_center"));
                list.add(center);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    //step3 in q13
    public List<Staff> getManagerByContractNum(String con_num) {
        String sql = "select * from staff join contract_record c on c.manager=staff_number where contract_num=?";
        openDB();
        List<Staff> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, con_num);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaff_name(resultSet.getString("staff_name"));
                list.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    //step4 in q13
    public List<Generic> getOrderInfoByContractNum(String con_num) {
        String sql = "select contract_num, m.product_model, quantity, unit_price, est_date,staff_name,lod_date " +
                " from contract" +
                "         join model m on contract.product_model = m.product_model " +
                "        join staff s on s.staff_number=salesman_num " +
                "where contract_num = ? order by m.product_model";
        openDB();
        List<Generic> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, con_num);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Generic generic = new Generic();
                generic.setProduct_model(resultSet.getString("product_model"));
                generic.setStaff_name(resultSet.getString("staff_name"));
                generic.setQuantity(resultSet.getInt("quantity"));
                generic.setUnit_price(resultSet.getInt("unit_price"));
                generic.setEst_date(String.valueOf(resultSet.getDate("est_date")));
                generic.setLod_date(String.valueOf(resultSet.getDate("lod_date")));
                list.add(generic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    public List<Price> getOrderCostGroupByEnterprise() {
        String sql = "select * from orderCostGroupByEnterprise";
        openDB();
        List<Price> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Price price = new Price();
                price.setText(resultSet.getString("enterprise"));
                price.setSum(resultSet.getInt("sum"));
                list.add(price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
//        System.out.println(1);
        return list;
    }

    public List<Price> getOrderCostGroupByModel() {
        String sql = "select * from orderCostGroupByModel";
        openDB();
        List<Price> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Price price = new Price();
                price.setText(resultSet.getString("product_model"));
                price.setSum(resultSet.getInt("sum"));
                list.add(price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    public List<Price> getStockCostByCenter() {
        String sql = "select * from storageCostGroupByCenter";
        openDB();
        List<Price> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Price price = new Price();
                price.setText(resultSet.getString("supply_center"));
                price.setSum(resultSet.getInt("total_price"));
                list.add(price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    public List<Price> getStockCostByModel() {
        String sql = "select * from storageCostGroupByModel";
        openDB();
        List<Price> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Price price = new Price();
                price.setText(resultSet.getString("product_model"));
                price.setSum(resultSet.getInt("total_price"));
                list.add(price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }

    public List<Contract> queryOrder(String contract_num, String model, String enterprise) {
        String sql0 = "select * from contract where enterprise=? and product_model=? and contract_num = ?;";
        String sql1 = "select * from contract where enterprise=? and contract_num = ?;";
        String sql2 = "select * from contract where enterprise=? and product_model = ?;";
        String sql3 = "select * from contract where product_model= ? and contract_num = ?;";
        String sql4 = "select * from contract where enterprise= ?;";
        String sql5 = "select * from contract where product_model= ?;";
        String sql6 = "select * from contract where contract_num= ?;";
        String sql7 = "select * from contract;";
//        String sqlFinal;
        openDB();
        List<Contract> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement;
            if (contract_num.equals("") && model.equals("") && enterprise.equals("")) {
//                sqlFinal = sql7;
                preparedStatement = con.prepareStatement(sql7);
            } else if (contract_num.equals("") && model.equals("")) {
//                sqlFinal = sql4;
                preparedStatement = con.prepareStatement(sql4);
                preparedStatement.setString(1,enterprise);
            } else if (contract_num.equals("") && enterprise.equals("")) {
//                sqlFinal = sql5;
                preparedStatement = con.prepareStatement(sql5);
                preparedStatement.setString(1,model);
            } else if (model.equals("") && enterprise.equals("")) {
//                sqlFinal = sql6;
                preparedStatement =con.prepareStatement(sql6);
                preparedStatement.setString(1,contract_num);
            } else if (contract_num.equals("")) {
//                sqlFinal = sql2;
                preparedStatement = con.prepareStatement(sql2);
                preparedStatement.setString(1,enterprise);
                preparedStatement.setString(2,model);
            } else if (enterprise.equals("")) {
//                sqlFinal = sql3;
                preparedStatement = con.prepareStatement(sql3);
                preparedStatement.setString(1,model);
                preparedStatement.setString(2,contract_num);
            } else if (model.equals("")) {
//                sqlFinal = sql1;
                preparedStatement = con.prepareStatement(sql1);
                preparedStatement.setString(1,enterprise);
                preparedStatement.setString(2,contract_num);
            } else{
                //sql0
                preparedStatement = con.prepareStatement(sql0);
                preparedStatement.setString(1,enterprise);
                preparedStatement.setString(2,model);
                preparedStatement.setString(3,contract_num);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contract contract = new Contract();
                contract.setContract_number(resultSet.getString("contract_num"));
                contract.setEnterprise(resultSet.getString("enterprise"));
                contract.setProduct_model(resultSet.getString("product_model"));
                contract.setQuantity(resultSet.getInt("quantity"));
                contract.setContract_manager(resultSet.getString("contract_manager"));
                contract.setContract_date(resultSet.getString("contract_date"));
                contract.setEst_date(String.valueOf(resultSet.getDate("est_date")));
                contract.setLod_date(String.valueOf(resultSet.getDate("lod_date")));
                contract.setSalesman_num(resultSet.getString("salesman_num"));
                contract.setContract_type(resultSet.getString("contract_type"));
                list.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB();
        return list;
    }
}
