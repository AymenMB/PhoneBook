package dao;

import Persistence.Personne;

import java.sql.SQLException;
import java.util.List;

public interface PersonneDAO {
    void ajouterPersonne(Personne personne);
    void modifierPersonne(Personne personne);
    void supprimerPersonne(String numCin);
    Personne chercherPersonneParCin(String numCin);
    List<Personne> listerToutesLesPersonnes();
    boolean personExists(String numCin) throws SQLException;
}
