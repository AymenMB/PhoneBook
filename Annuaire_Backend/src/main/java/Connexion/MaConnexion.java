package Connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/annuaire_telephonique";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Load the JDBC driver class
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading MySQL JDBC Driver");
        }
    }

    // Create and return the connection object
    public static Connection obtenirConnexion() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        }
    }

    // Close the connection
    public static void fermerConnexion(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error closing the connection");
            }
        }
    }
}
