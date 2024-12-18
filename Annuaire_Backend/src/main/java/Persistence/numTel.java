package Persistence;
public class numTel {
    private int numcin;
    private String valeur;
    private String type;

    public numTel(int numcin, String valeur, String type) {
        this.numcin = numcin;
        this.valeur = valeur;
        this.type = type;
    }

    // Getter and Setter methods for numcin, valeur, and type
    public int getNumcin() {
        return numcin;
    }

    public void setNumcin(int numcin) {
        this.numcin = numcin;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        if (valeur == null || valeur.length() != 8 || !valeur.matches("\\d{8}")) {
            throw new IllegalArgumentException("Phone number must be exactly 8 digits.");
        }
        this.valeur = valeur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NumTel{" +
                "numcin=" + numcin +
                ", valeur='" + valeur + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
