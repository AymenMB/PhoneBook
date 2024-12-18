package Test;

import Persistence.numTel;
import Persistence.Personne;
import dao.AnnuaireDAOImpl;
import dao.PersonneDAOImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.List;

public class TestDAO {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3307/annuaire_telephonique";
        String utilisateur = "root";
        String motDePasse = "";

        try (Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            testPersonneDAO(connexion);
            testAnnuaireDAO(connexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testPersonneDAO(Connection connexion) {
        PersonneDAOImpl personneDAO = new PersonneDAOImpl(connexion);

        // Ajouter une personne
        Personne nouvellePersonne = new Personne("123456", "hatheway", "anna", "Madame");
        personneDAO.ajouterPersonne(nouvellePersonne);

        // Modifier la personne ajoutée
        nouvellePersonne.setNom("Smith");
        personneDAO.modifierPersonne(nouvellePersonne);

        // Récupérer la personne par son numéro de CIN
        Personne personneRecuperee = personneDAO.chercherPersonneParCin("123456");
        System.out.println("Personne récupérée : " + personneRecuperee);

        // Supprimer la personne
      //  personneDAO.supprimerPersonne("123456");
    }

    private static void testAnnuaireDAO(Connection connexion) {
        AnnuaireDAOImpl annuaireDAO = new AnnuaireDAOImpl(connexion);

        // Ajouter un numéro de téléphone à une personne
        numTel nouveauNumTel = new numTel(123456, "0123456789", "Mobile");
        annuaireDAO.ajouterNumTel(nouveauNumTel);

        // Modifier le numéro de téléphone ajouté
        nouveauNumTel.setType("Domicile");
        annuaireDAO.modifierNumTel(nouveauNumTel);

        // Récupérer le numéro de téléphone par son numéro de CIN et sa valeur
        numTel numTelRecupere = annuaireDAO.obtenirNumTel(123456, "0123456789");
        System.out.println("Numéro de téléphone récupéré : " + numTelRecupere);

        // Lister tous les numéros de téléphone d'une personne
        List<numTel> numTels = annuaireDAO.listerTousLesNumTels();
        System.out.println("Numéros de téléphone  existant dans l'annuaire : " + numTels);

        // Supprimer le numéro de téléphone
      //  annuaireDAO.supprimerNumTel(123456, "0123456789");
        List<numTel> numTels1 = annuaireDAO.listerTousLesNumTels(125209);
        System.out.println("Numéros de téléphone de la personne : " + numTels1);
    }
}
