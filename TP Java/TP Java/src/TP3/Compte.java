package TP3;

public class Compte {

    private String identifiant;
    private String dateDeCreation;
    private boolean actif;
    private int solde;
    private boolean connected;

    public Compte(){
        identifiant = "anonyme";
        dateDeCreation = "17/09/2026";
        actif = true;
    }
    public Compte(String identifiant, String dateDeCreation){
        this.identifiant = identifiant;
        this.dateDeCreation = dateDeCreation;
        this.actif = true;
    }
    public String getIdentifiant(){ return identifiant;}
    public String getDateDeCreation(){ return dateDeCreation;}
    public boolean getActif(){ return actif;}
    public int getSolde(){ return solde;}
    public boolean getConnected(){ return connected;}

    public void setIdentifiant(String identifiant) { this.identifiant = identifiant; }
    public void setDateDeCreation(String dateDeCreation) { this.dateDeCreation = dateDeCreation; }
    public void setActif(boolean actif) { this.actif = actif; }
    public void setSolde(int solde) { this.solde = solde; }
    public void setConnected(boolean connected) { this.connected = connected; }

}
