/**
 * This class serves as the main backend class which establishes the database
 * connection
 * 
 * @author 444 Team (Alfredo)
 * @version 1.0
 */

// DB imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sweetShopApp {
    // DB connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/thesweetshopdb";
    private static final String USERNAME = "sweetShopDev";
    private static final String PASSWORD = "SS444"; // abbreviation of sweet shop for simplicity

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("Database connected successfully");
    }
}
