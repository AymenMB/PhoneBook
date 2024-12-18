package dao;

import java.util.List;

import Persistence.numTel;
import Persistence.Personne;

public interface AnnuaireDAO {
    void ajouterNumTel(numTel numTel);
    void modifierNumTel(numTel numTel);
    void supprimerNumTel(int numCin, String valeur);
    numTel obtenirNumTel(int numCin, String valeur);
    List<numTel> listerTousLesNumTels();
    List<numTel> listerTousLesNumTels(int numCin);
    boolean personExists(int numCin);
    boolean phoneNumberExists(int numCin, String phoneNumber);
    void updateNumTel(numTel numTel, String oldPhoneNumber);
}
