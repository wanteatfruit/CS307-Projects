package dao;

import java.util.List;

public interface DataManipulation {

    public void getConnection();
    public void closeConnection();
    public List<String> selectOrder(String contract_num);

}
