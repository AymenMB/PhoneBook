package Persistence;

import java.util.ArrayList;
import java.util.List;

public class Personne {
    private String numCin;
    private String nom;
    private String prenom;
    private String civilite;

    private List<numTel> numerosTelephone = new ArrayList<>();

    // Constructeur avec quatre arguments
    public Personne(String numCin, String nom, String prenom, String civilite) {
        this.numCin = numCin;
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
    }

    // Getters
    public String getNumCin() {
        return numCin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getCivilite() {
        return civilite;
    }

    public List<numTel> getNumerosTelephone() {
        return numerosTelephone;
    }

    // Setters
    public void setNumCin(String numCin) {
        this.numCin = numCin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public void setNumerosTelephone(List<numTel> numerosTelephone) {
        this.numerosTelephone = numerosTelephone;
    }

    // Dans la classe Personne
    @Override
    public String toString() {
        return "Personne{numCin='" + numCin + "', nom='" + nom + "', prenom='" + prenom + "', civilite='" + civilite + "'}";
    }


}
