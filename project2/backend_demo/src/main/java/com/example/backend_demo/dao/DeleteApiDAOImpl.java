package com.example.backend_demo.dao;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@Repository
public class DeleteApiDAOImpl {
    private String url="jdbc:postgresql://localhost:5432/contract_project2";
    private static Connection con =null;

    private ResultSet resultSet;

    private PreparedStatement statement=null;

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

    public void deleteOrder(String filename) {
        try (BufferedReader infile
                     = new BufferedReader(new FileReader(filename))) {
            openDB();
            String line;
            String[] parts;
            String a, b, c;
            infile.readLine();
            String select = "select * from contract where contract_num=? and salesman_num=? order by est_date,product_model";
            PreparedStatement selectstmt = con.prepareStatement(select);
            String sql = "delete from contract where contract_num=? and salesman_num=? and est_date=? and product_model=? ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            while ((line = infile.readLine()) != null) {

                int rowCnt = 0;
                String quantity = "";
                parts = line.split("\\s+");
                a = parts[0]; //contract_num
                b = parts[1]; //sales_num
                c = parts[2]; //seq`
                int rowToDelete = Integer.parseInt(c);
                selectstmt.setString(1, a);
                selectstmt.setString(2, b);
                ResultSet r = selectstmt.executeQuery();
                while (r.next()) { //update storage and check seq
                    if (rowCnt == rowToDelete) {;
                        break;
                    }
                    rowCnt++;
                    quantity = r.getString("quantity");
                    String model = r.getString("product_model");
                    String est = r.getString("est_date");
                    String center = orderFindCenter(r.getString("enterprise"));
                    if (rowCnt == rowToDelete) {
                        updateStorageInDeleteOrder(quantity, center, model);
                    }
                    preparedStatement.setString(1, a);
                    preparedStatement.setString(2, b);
                    preparedStatement.setDate(3, Date.valueOf(est));
                    preparedStatement.setString(4, model);
                    if (rowCnt == rowToDelete) {
                        preparedStatement.execute();
                    }
                }
            }
            con.commit();
            System.out.println("Finish");
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


    public void updateStorageInDeleteOrder(String quantity, String center, String model) {
        String update = "update storage set quantity = quantity + ?::integer where supply_center = ? and product_model = ?";
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



}
