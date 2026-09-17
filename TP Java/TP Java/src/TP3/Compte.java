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

    public void suprimer(){
        actif = false;
    }

    public int getSolde(){

    }
}
