package TP3;

public class Admin extends Utilisateur {
    private String niveauAcces, service;
    private int nombreComptesGeres;

    public Admin() {
        super();
        this.niveauAcces = "standard";
        this.service = "support";
        this.nombreComptesGeres = 0;
    }

    public Admin(String pseudo, String email, String mdp, String niveauAcces, String service, int nombreComptesGeres) {
        super(pseudo, email, mdp);
        this.niveauAcces = niveauAcces; this.service = service; this.nombreComptesGeres = nombreComptesGeres;
    }

    public String getNiveauAcces() { return niveauAcces; }
    public String getService() { return service; }
    public int getNombreComptesGeres() { return nombreComptesGeres; }

    public void setNiveauAcces(String niveauAcces) { this.niveauAcces = niveauAcces; }
    public void setService(String service) { this.service = service; }
    public void setNombreComptesGeres(int nombreComptesGeres) { this.nombreComptesGeres = nombreComptesGeres; }
}
