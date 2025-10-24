import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database credentials for XAMPP MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/LostandFound_DB";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Default XAMPP password is empty

    private static Connection connection = null;

    // Method to get database connection
    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // First, try to create the database if it doesn't exist
            createDatabaseIfNotExists();

            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

        return connection;
    }

    // Method to create database if it doesn't exist
    private static void createDatabaseIfNotExists() {
        try {
            // Connect to MySQL without specifying database
            Connection tempConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/", USER, PASSWORD);

            java.sql.Statement stmt = tempConn.createStatement();

            // Create database
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS LostandFound_DB");
            System.out.println("Database checked/created successfully!");

            stmt.close();
            tempConn.close();

        } catch (SQLException e) {
            System.out.println("Error creating database!");
            e.printStackTrace();
        }
    }

    // Method to close connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Test the connection
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Test successful!");
            closeConnection();
        } else {
            System.out.println("Test failed!");
        }
    }
}