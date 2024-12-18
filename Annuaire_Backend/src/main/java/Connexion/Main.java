package Connexion;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {

            conn = MaConnexion.obtenirConnexion();

            System.out.println("Connection successful");

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();  // Print the exception details for debugging

        }
        /*finally {
            // Close the connection in a finally block to ensure it's always closed
            MaConnexion.fermerConnexion(conn);
        }*/
    }
}
