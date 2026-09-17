package TP3;

public class Compte {

    private String identifiant;
    private String dateDeCreation;
    private boolean actif;
    private int solde;
    private boolean connected;

    public void Compte(){
        identifiant = "anonyme";
        dateDeCreation = "17/09/2026";
        actif = true;
    }
    public void Compte(String identifiant, String dateDeCreation){
        this.identifiant = identifiant;
        this.dateDeCreation = dateDeCreation;
        this.actif = true;
    }
    public int getSolde(){ return solde;}

    public String getIdentifiant(){ return identifiant;}

    public String getDateDeCreation(){ return dateDeCreation;}

    public boolean getActif(){ return actif;}

    public boolean getConnected(){ return connected;}

    public void setIdentifiant(String identifiant){ this.identifiant = identifiant;}

    public void setDateDeCreation(String dateDeCreation){ this.dateDeCreation = dateDeCreation;}

    public void suprimer(){actif = false;}

    public void setConnected(boolean connected) { this.connected = connected; }

}
