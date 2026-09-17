package TP3;

public class Admin extends Utilisateur {
    private String niveauAcces, service;
    private int nombreComptesGeres;

    public String getNiveauAcces() { return niveauAcces; }
    public String getService() { return service; }
    public int getNombreComptesGeres() { return nombreComptesGeres; }

    public void setNiveauAcces(String niveauAcces) { this.niveauAcces = niveauAcces; }
    public void setService(String service) { this.service = service; }
    public void setNombreComptesGeres(int nombreComptesGeres) { this.nombreComptesGeres = nombreComptesGeres; }


}
