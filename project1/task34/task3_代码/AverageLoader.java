import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class AverageLoader {
    private static URL propertyURL = AverageLoader.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static PreparedStatement stmtClient = null;
    private static PreparedStatement stmtContract = null;
    private static PreparedStatement stmtSalesman = null;
    private static PreparedStatement stmtProduct = null;
    private static PreparedStatement stmtModel = null;
    private static PreparedStatement stmtOrder = null;
    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        try {
            stmtClient = con.prepareStatement("insert into client (client_enterprise, country, city, supply_center, industry)"
                    + " values(?,?,?,?,?)");
            stmtSalesman = con.prepareStatement("insert into salesman (salesman_number, salesman_name, gender, age, mobile_phone)"
                    + " values(?,?,?,?::integer ,?)");
            stmtProduct = con.prepareStatement("insert into product (product_code, product_name)"
                    + " values(?,?)");
            stmtModel = con.prepareStatement("insert into model (product_model, unit_price,product_id)" //fk
                    + " values(?,?::integer ,(select product_id from product where product_code = ?))");
            stmtContract = con.prepareStatement("insert into contract(contract_number, contract_date, director, client_id)" //fk
                    + " values(?,?::date ,?,(select id from client where client_enterprise = ?))");
            stmtOrder = con.prepareStatement("insert into order_sale (contract_number, product_model, est_date, lodge_date, quantity,salesman_id)" //fk
                    + " values(?,?,?::date ,?::date ,?::integer ,(select salesman_id from salesman where salesman_number=?))");

        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmtClient != null) {
                    stmtClient.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadData(String a, String b, String c, String d, String e, String f, PreparedStatement stmt)
            throws SQLException {
        if (con != null) {
            stmt.setString(1, a);
            stmt.setString(2, b);
            stmt.setString(3, c);
            stmt.setString(4, d);
            stmt.setString(5, e);
            stmt.setString(6, f);
            stmt.executeUpdate();
        }
    }


    private static void loadData(String a, String b, String c, String d, String e, PreparedStatement stmt)
            throws SQLException {
        if (con != null) {
            stmt.setString(1, a);
            stmt.setString(2, b);
            stmt.setString(3, c);
            stmt.setString(4, d);
            stmt.setString(5, e);
            stmt.executeUpdate();
        }
    }

    private static void loadData(String a, String b, String c, String d, PreparedStatement stm)
            throws SQLException {
        if (con != null) {
            stm.setString(1, a);
            stm.setString(2, b);
            stm.setString(3, c);
            stm.setString(4, d);
            stm.executeUpdate();
        }
    }

    private static void loadData(String a, String b, PreparedStatement stm)
            throws SQLException {
        if (con != null) {
            stm.setString(1, a);
            stm.setString(2, b);
            stm.executeUpdate();
        }
    }

    private static void loadData(String a, String b, String c, PreparedStatement stm)
            throws SQLException {
        if (con != null) {
            stm.setString(1, a);
            stm.setString(2, b);
            stm.setString(3, c);
            stm.executeUpdate();
        }
    }


    public static void main(String[] args) {
        String fileName = null;
        boolean verbose = false;

        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] AverageLoader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] AverageLoader filename");
                System.exit(1);
        }

        if (propertyURL == null) {
            System.err.println("No configuration file (loader.cnf) found");
            System.exit(1);
        }
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "contract_project");
        Properties prop = new Properties(defprop);

        try (BufferedReader infile
                     = new BufferedReader(new FileReader(fileName))) {
            long start;
            long end;

            String line;
            String[] parts;
            String a, b, c, d, e, f; //column names

            int cnt = 0;
            // Empty target table
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            Statement stmt0;
            if (con != null) {
                stmt0 = con.createStatement();
                //stmt0.execute("truncate table client");
                stmt0.close();
            }
            closeDB();
            //
            start = System.currentTimeMillis();
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            while ((line = infile.readLine()) != null) {
                parts = line.split(",");
                if (parts.length > 1) {
                    a = parts[0];
                    b = parts[1];
                    c = parts[2];
                    d = parts[3];
                    if(Objects.equals(parts[4], "")){
                        e=null;
                    }
                    else {
                        e = parts[4];
                    }
                    f = parts[5];

                    loadData(a, b, d, e, c, f, stmtOrder);
                    cnt++;
                }
            }
            con.commit();
            closeDB();
            end = System.currentTimeMillis();
            System.out.println(cnt + " records successfully loaded");
            System.out.println("Loading speed : "
                    + (cnt * 1000) / (end - start)
                    + " records/s");
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
}

