package dao;

import Persistence.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonneDAOImpl implements PersonneDAO, AutoCloseable{
    private Connection connexion;

    public PersonneDAOImpl(Connection connexion) {
        this.connexion = connexion;
    }
    private void gestionErreurSQLException(SQLException e) {
        e.printStackTrace();
    }
    private void validateCin(String numCin) {
        if (numCin == null || numCin.length() != 8 || !numCin.matches("\\d{8}")) {
            throw new RuntimeException("numCin must be exactly 8 digits.");
        }
    }

    @Override
    public void close() throws SQLException {}

    @Override
    public void ajouterPersonne(Personne personne) {
        validateCin(personne.getNumCin());
        String requete = "INSERT INTO Personne (numCin, nom, prenom, civilite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, personne.getNumCin());
            statement.setString(2, personne.getNom());
            statement.setString(3, personne.getPrenom());
            statement.setString(4, personne.getCivilite());

            statement.executeUpdate();
            System.out.println("Personne ajoutée avec succès !");
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
    }

    @Override
    public void modifierPersonne(Personne personne) {
        validateCin(personne.getNumCin());
        String requete = "UPDATE Personne SET nom = ?, prenom = ?, civilite = ? WHERE numCin = ?";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, personne.getNom());
            statement.setString(2, personne.getPrenom());
            statement.setString(3, personne.getCivilite());
            statement.setString(4, personne.getNumCin());

            statement.executeUpdate();
            System.out.println("Personne modifiée avec succès !");
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
    }

    @Override
    public void supprimerPersonne(String numCin) {
        String deleteNumTelQuery = "DELETE FROM numTel WHERE numcin = ?";
        String deletePersonQuery = "DELETE FROM Personne WHERE numCin = ?";
        try (PreparedStatement deleteNumTelStmt = connexion.prepareStatement(deleteNumTelQuery);
             PreparedStatement deletePersonStmt = connexion.prepareStatement(deletePersonQuery)) {

            // Delete associated phone numbers
            deleteNumTelStmt.setString(1, numCin);
            deleteNumTelStmt.executeUpdate();

            // Delete the person
            deletePersonStmt.setString(1, numCin);
            deletePersonStmt.executeUpdate();

            System.out.println("Person and associated phone numbers deleted successfully!");
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
    }
    @Override
    public boolean personExists(String numCin) throws SQLException {
        String requete = "SELECT COUNT(*) FROM Personne WHERE numCin = ?";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, numCin);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }


    @Override
    public Personne chercherPersonneParCin(String numCin) {
        String requete = "SELECT * FROM Personne WHERE numCin = ?";
        try (PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, numCin);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Personne personne = new Personne(
                            resultSet.getString("numCin"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("civilite")
                    );

                    return personne;
                } else {
                    return null; // Retourne null si la personne n'est pas trouvée
                }
            }
        } catch (SQLException e) {
            gestionErreurSQLException(e);
        }
        return null;
    }

//lambda
    @Override
    public List<Personne> listerToutesLesPersonnes() {
        List<Personne> personnes = new ArrayList<>();
        String requete = "SELECT * FROM Personne";
        try (PreparedStatement statement = connexion.prepareStatement(requete);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                personnes.add(new Personne(
                        resultSet.getString("numCin"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("civilite")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Using lambda expression to print the list
        personnes.forEach(personne -> System.out.println(personne));

        return personnes;
    }


}
