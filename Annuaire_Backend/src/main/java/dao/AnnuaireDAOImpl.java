package dao;

import Persistence.numTel;
import Persistence.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AnnuaireDAOImpl implements AnnuaireDAO, AutoCloseable {
    private Connection connexion;

    public AnnuaireDAOImpl(Connection connexion) {
        this.connexion = connexion;
    }





    private void gestionErreurSQLException(SQLException e) {
        e.printStackTrace();

    }


    private void validateCin(int numCin) {
        String numCinStr = String.valueOf(numCin);
        if (numCinStr.length() != 8 || !numCinStr.matches("\\d{8}")) {
            throw new RuntimeException("numCin must be exactly 8 digits.");
        }
    }



    @Override
    public void ajouterNumTel(numTel numTel) {
        validateCin(numTel.getNumcin());

        if (numTel.getValeur().length() != 8 || !numTel.getValeur().matches("\\d{8}")) {
            throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
        }
        String requete = "INSERT INTO numTel (numcin, valeur, type) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
            if (personExists(numTel.getNumcin())) {
                if (!phoneNumberExists(numTel.getNumcin(), numTel.getValeur())) {
                    preparedStatement.setInt(1, numTel.getNumcin());
                    preparedStatement.setString(2, numTel.getValeur());
                    preparedStatement.setString(3, numTel.getType());
                    preparedStatement.executeUpdate();
                    System.out.println("Phone number added successfully!");
                } else {
                    System.out.println("Phone number already exists!");
                }
            } else {
                System.out.println("Person does not exist!");
            }
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
    }


    @Override
    public void modifierNumTel(numTel numTel) {
        validateCin(numTel.getNumcin());

        if (numTel.getValeur().length() != 8 || !numTel.getValeur().matches("\\d{8}")) {
            throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
        }
        String requete = "UPDATE numTel SET type = ? WHERE numcin = ? AND valeur = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
            preparedStatement.setString(1, numTel.getType());
            preparedStatement.setInt(2, numTel.getNumcin());
            preparedStatement.setString(3, numTel.getValeur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
    }


    // Add a method to check if the phone number already exists
    public boolean phoneNumberExists(int numCin, String phoneNumber) {
        String requete = "SELECT COUNT(*) FROM numTel WHERE numcin = ? AND valeur = ?";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, numCin);
            statement.setString(2, phoneNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
        return false;
    }



    @Override
    public void supprimerNumTel(int numCin, String valeur) {
        String requete = "DELETE FROM numTel WHERE numcin = ? AND valeur = ?";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, numCin);
            statement.setString(2, valeur);

            statement.executeUpdate();
            System.out.println("Numéro de téléphone supprimé avec succès !");
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
    }

    @Override
    public numTel obtenirNumTel(int numCin, String valeur) {
        numTel numTel = null;
        String requete = "SELECT * FROM numTel WHERE numcin = ? AND valeur = ?";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, numCin);
            statement.setString(2, valeur);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    numTel = new numTel(numCin, resultSet.getString("valeur"), resultSet.getString("type"));
                }
            }

        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
        return null;
    }

//lambda
    @Override
    public List<numTel> listerTousLesNumTels() {
        List<numTel> numTels = new ArrayList<>();
        String requete = "SELECT * FROM numTel";
        try (PreparedStatement statement = connexion.prepareStatement(requete);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                numTels.add(new numTel(
                        resultSet.getInt("numcin"),
                        resultSet.getString("valeur"),
                        resultSet.getString("type")
                ));
            }

        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }

        // hethi lambda
        numTels.forEach(num -> System.out.println(num));

        return numTels;
    }


    @Override
    public List<numTel> listerTousLesNumTels(int numCin) {
        List<numTel> numTels = new ArrayList<>();
        String requete = "SELECT * FROM numTel WHERE numcin = ?";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, numCin);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    numTels.add(new numTel(
                            numCin,
                            resultSet.getString("valeur"),
                            resultSet.getString("type")
                    ));
                }
            }

        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }

        // Using lambda expression to print the list
        numTels.forEach(System.out::println);

        return numTels;
    }

    @Override
    public boolean personExists(int numCin) {
        String query = "SELECT COUNT(*) FROM Personne WHERE numCin = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, numCin);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }

        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
        return false;
    }

    @Override
    public void close() throws Exception {

    }
    @Override
    public void updateNumTel(numTel numTel, String oldPhoneNumber) {
        validateCin(numTel.getNumcin());
        // Validate phone number
        if (numTel.getValeur().length() != 8 || !numTel.getValeur().matches("\\d{8}")) {
            throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
        }
        String query = "UPDATE numTel SET valeur = ?, type = ? WHERE numcin = ? AND valeur = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setString(1, numTel.getValeur());
            preparedStatement.setString(2, numTel.getType());
            preparedStatement.setInt(3, numTel.getNumcin());
            preparedStatement.setString(4, oldPhoneNumber); // Use the old phone number value
            preparedStatement.executeUpdate();
            System.out.println("Phone number updated successfully!");
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
    }
}
