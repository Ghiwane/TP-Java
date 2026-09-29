package TP3;
import java.util.Objects;

public class Jeu {
    private String titre, auteur;
    private double prix;
    private String genre;
    private boolean verif;
    private int index;

    public Jeu(String titre, String auteur, double prix, String genre) {
        this.titre = Objects.requireNonNull(titre, "Le titre ne peut pas etre null");
        this.auteur = Objects.requireNonNull(auteur, "L'auteur ne peut pas etre null");
        this.genre = Objects.requireNonNull(genre, "Le genre ne peut pas etre null");
        if (prix < 0)
            throw new IllegalArgumentException("Le prix ne peut pas être negatif : " + prix);
        this.prix = prix;
        verif = false;
        index = 0;
    }

    @Override
    public String toString() { return "Titre : " + titre + "\nAuteur : " + auteur + "\nPrix : " + prix
            + "\nGenre : " + genre; }

    public void modifierPrix(double prix) {
        if (prix < 0)
            throw new IllegalArgumentException("Le prix ne peut pas être negatif : " + prix);
        this.prix = prix;
    }

    public void estDisponible(boolean disponible)
    {
        if (disponible)
        {
            System.out.println("Le jeu est disponible");
        }
        else
        {
            System.out.println("Le jeu n'est pas disponible");
        }
    }

    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getGenre()  {return genre; }
    public double getPrix() { return prix; }
    public boolean getVerif() { return verif; }
    public int getIndex() { return index; }

    public void setVerif(boolean verif) { this.verif = verif; }
    public void setIndex(int index) { this.index = index; }
}
