import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class BadLoader {
    private static URL        propertyURL = BadLoader.class
            .getResource("/loader.cnf");

    private static Connection         con = null;
    private static PreparedStatement  stmt = null;
    private static boolean            verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch(Exception e) {
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
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        try {
            stmt = con.prepareStatement("insert into order_sale (contract_number, product_model, est_date, lodge_date, quantity,salesman_id)" //fk
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
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
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

    public static void main(String[] args) {
        String  fileName = null;
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
                        System.err.println("Usage: java [-v] BadLoader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] BadLoader filename");
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
        defprop.put("database", "contracts_project");
        Properties prop = new Properties(defprop);

        try (BufferedReader infile
                     = new BufferedReader(new FileReader(fileName))) {
            long     start;
            long     end;
            String   line;
            String[] parts;
            String   a,b,c,d,e,f;
            int      cnt = 0;
            // Empty target table
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            Statement stmt0;
            if (con != null) {
                stmt0 = con.createStatement();
//                stmt0.execute("truncate table students");
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
                    a = parts[1];
                    b = parts[2];
                    c = parts[3];
//                    d = parts[3];
                    if (Objects.equals(parts[4], "")) {
                        d = null;
                    } else {
                        d = parts[4];
                    }
                    e = parts[5];
                    f = parts[6];
                    loadData(a, b, c, d, e, f, stmt);
                    cnt++;
                }
            }
            closeDB();
            end = System.currentTimeMillis();
            System.out.println(cnt + " records successfully loaded");
            System.out.println("Loading speed : "
                    + (cnt * 1000)/(end - start)
                    + " records/s");
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            closeDB();
            System.exit(1);
        }
        closeDB();
    }
}

