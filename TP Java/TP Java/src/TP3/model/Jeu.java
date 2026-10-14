package TP3.model;

import java.io.Serializable;

public class Jeu implements Serializable {
    private String titre, auteur;
    private double prix;
    private String genre;
    private boolean verif;
    private int index;

    public Jeu(String titre, String auteur, double prix, String genre) {
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        if (prix < 0)
            throw new IllegalArgumentException("Le prix ne peut pas être negatif : " + prix);
        this.prix = prix;
        verif = false;
        index = 0;
    }

    public Jeu(String titre, String auteur, double prix, String genre, boolean verif) {
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        if (prix < 0)
            throw new IllegalArgumentException("Le prix ne peut pas être negatif : " + prix);
        this.prix = prix;
        this.verif = verif;
        index = 0;
    }

    @Override
    public String toString() {
        return "Titre : " + titre + "\nAuteur : " + auteur + "\nPrix : " + prix + "\nGenre : " + genre;
    }

    public void modifierPrix(double prix) {
        if (prix < 0)
            throw new IllegalArgumentException("Le prix ne peut pas être negatif : " + prix);
        this.prix = prix;
    }

    public boolean estDisponible(boolean disponible) { return disponible; } // plus d'affichage console

    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getGenre() { return genre; }
    public double getPrix() { return prix; }
    public boolean getVerif() { return verif; }
    public int getIndex() { return index; }

    public void setVerif(boolean verif) { this.verif = verif; }
    public void setIndex(int index) { this.index = index; }
}
