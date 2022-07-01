package dao;

import Util.DBUtil;
import Util.ProxoolUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManipulation implements DataManipulation {
    private Connection con;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private DBUtil util;

    public DatabaseManipulation(DBUtil util) {
        this.util = util;
    }

    public List<String> selectOrder(String contract_num) {
        List<String> stations = new ArrayList<>();
        String sql = "select enterprise, product_model, quantity from contract where contract_num =?";
        try {
            Thread.sleep(2000);
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, contract_num);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stations.add(String.format("%s, %s, %d", resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return stations;
    }

    public void getConnection() {
        con = this.util.getConnection();
        System.out.println("------Thread " + Thread.currentThread().getId() + " visiting DB!------");
        System.out.println(this.util.getConnectState());

    }

    public void closeConnection() {
        this.util.closeConnection(con, preparedStatement, resultSet);
        System.out.println("------Thread " + Thread.currentThread().getId() + " close DB!------");
    }


}
