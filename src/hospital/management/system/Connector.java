package hospital.management.system;

import java.sql.*;

public class Connector {

    public Connection connection;
    public Statement statement;

    // Constructor to connect to Oracle
    public Connector() {
        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to the correct PDB (XEPDB1) in Oracle XE
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521/XEPDB1", // Use /XEPDB1 for PDB
                    "hr",  // Oracle username
                    "hr"   // Oracle password
            );

            // Create statement
            statement = connection.createStatement();
            System.out.println("Database connected successfully!");

        } catch (SQLException se) {
            System.out.println("Database connection failed! Check username/password/PDB.");
            se.printStackTrace();
            statement = null;
            connection = null;

        } catch (ClassNotFoundException ce) {
            System.out.println("Oracle JDBC Driver not found!");
            ce.printStackTrace();
            statement = null;
            connection = null;
        }
    }

    // Optional: main method to test connection
    public static void main(String[] args) {
        Connector c = new Connector();
        if (c.connection != null) {
            try {
                ResultSet rs = c.statement.executeQuery("SELECT * FROM ROOM");
                while (rs.next()) {
                    System.out.println(
                            rs.getString("Room_Number") + " | " +
                                    rs.getString("Availability") + " | " +
                                    rs.getDouble("Price") + " | " +
                                    rs.getString("Bed_Type")
                    );
                }
                rs.close();
                c.statement.close();
                c.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cannot run queries: No database connection.");
        }
    }
}
