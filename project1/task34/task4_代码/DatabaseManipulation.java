
import java.sql.*;

public class DatabaseManipulation implements DataManipulation {
    private Connection con = null;
    private ResultSet resultSet;

    private String host = "localhost";
    private String dbname = "contract_project";
    private String user = "checker";
    private String pwd = "123456";
    private String port = "5432";


    @Override
    public void openDatasource() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            con = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void closeDatasource() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String allOrders() {
        StringBuilder sb = new StringBuilder();
        String sql = "select * from order_sale";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("order_id")).append("\t");
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append(resultSet.getString("product_model")).append("\t");
                sb.append(resultSet.getString("est_date")).append("\t");
                sb.append(resultSet.getString("lodge_date")).append("\t");
                sb.append(resultSet.getString("quantity")).append("\t");
                sb.append(resultSet.getString("salesman_id")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String findOrdersByModel(String model) {
        StringBuilder sb = new StringBuilder();
        String sql = "select contract_number,product_model from order_sale where product_model like ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, model);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append(resultSet.getString("product_model")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }





    @Override
    public String findOrdersByQuantity(int min, int max) {
        StringBuilder sb = new StringBuilder();
        String sql = "select contract_number, product_model, quantity " +
                "from order_sale " +
                "where quantity between ? and ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append(resultSet.getString("product_model")).append("\t");
                sb.append(resultSet.getString("quantity")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public int countAllContracts() {
        StringBuilder sb = new StringBuilder();
        String sql = "select count(distinct contract_number) cnt from order_sale";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("cnt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(sb.toString());
    }

    @Override
    public String maxQuantityContract() {
        StringBuilder sb = new StringBuilder();
        String sql = "select max(quantity), contract_number " +
                "from order_sale " +
                "group by contract_number";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("max")).append("\t");
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String countOrdersByModel(String model) {
        StringBuilder sb = new StringBuilder();
        String sql = "select count(*) " +
                "from order_sale " +
                "where product_model = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, model);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String mostOrdersContract() {
        StringBuilder sb = new StringBuilder();
        String sql = "select max(cnt) m, contract_number " +
                "from (" +
                "select count(*) cnt, contract_number from order_sale group by contract_number " +
                "     ) o_cnt " +
                "group by contract_number " +
                "order by m desc " +
                "limit 1 ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("m")).append("\t");
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String countSalesmanOrders() {
        StringBuilder sb = new StringBuilder();
        String sql="select count(*), o.salesman_id  " +
                "from order_sale o " +
                "         join salesman s on o.salesman_id = s.salesman_id " +
                "group by o.salesman_id order by salesman_id asc";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("count")).append("\t");
                sb.append(resultSet.getString("salesman_id")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String findOrdersByPrice(int min,int max) {
        StringBuilder sb = new StringBuilder();
        String sql="select contract_number, quantity, m.product_model, unit_price " +
                "from order_sale o " +
                "         join model m on m.product_model = o.product_model " +
                "where m.unit_price between ? and ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,min);
            preparedStatement.setInt(2,max);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append(resultSet.getString("quantity")).append("\t");
                sb.append(resultSet.getString("product_model")).append("\t");
                sb.append(resultSet.getString("unit_price")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    @Override
    public String findContractClientIndustry(String industry) {
        StringBuilder sb = new StringBuilder();
        String sql="select o.contract_number, product_model, client_enterprise " +
                "from order_sale o " +
                "         join (select * " +
                "               from contract c1 " +
                "                        join client c2 on c1.client_id = c2.client_id) client " +
                "              on o.contract_number = client.contract_number " +
                "where industry = ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,industry);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append(resultSet.getString("product_model")).append("\t");
                sb.append(resultSet.getString("client_enterprise")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    @Override
    public void addOneOrder(String str){
        //con.setAutoCommit(false);

        String sql = "insert into order_sale (order_id, contract_number, product_model,est_date,lodge_date,quantity,salesman_id) " +
                "values (?,?,?,?,?,?,?)";
        String[] orderInfo = str.split(",");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(orderInfo[0]));
            preparedStatement.setString(2, orderInfo[1]);
            preparedStatement.setString(3, orderInfo[2]);
            preparedStatement.setDate(4,Date.valueOf(orderInfo[3]));
            preparedStatement.setDate(5,Date.valueOf(orderInfo[4]));
            preparedStatement.setInt(6,Integer.parseInt(orderInfo[5]));
            preparedStatement.setInt(7,Integer.parseInt(orderInfo[6]));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderByID(int id){
        int result=0;
        String sql= "delete from order_sale where order_id=?";
        try{
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            System.out.println(preparedStatement.toString());

            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateQuantityByID(int quantity, int id) {
        int result=0;
        String sql= "update order_sale set quantity=? where order_id=?";
        try{
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setInt(2,id);
            System.out.println(preparedStatement.toString());

            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


    public String findOrderByID(int id){
        StringBuilder sb = new StringBuilder();
        String sql = "select * from order_sale where order_id=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("order_id")).append("\t");
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append(resultSet.getString("product_model")).append("\t");
                sb.append(resultSet.getString("est_date")).append("\t");
                sb.append(resultSet.getString("lodge_date")).append("\t");
                sb.append(resultSet.getString("quantity")).append("\t");
                sb.append(resultSet.getString("salesman_id")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String findRangeOrderByID(int min,int max){
        StringBuilder sb = new StringBuilder();
        String sql = "select * from order_sale where order_id between ? and ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2,max);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("order_id")).append("\t");
                sb.append(resultSet.getString("contract_number")).append("\t");
                sb.append(resultSet.getString("product_model")).append("\t");
                sb.append(resultSet.getString("est_date")).append("\t");
                sb.append(resultSet.getString("lodge_date")).append("\t");
                sb.append(resultSet.getString("quantity")).append("\t");
                sb.append(resultSet.getString("salesman_id")).append("\t");
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }




}
