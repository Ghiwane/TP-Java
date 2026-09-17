package TP3;

public class Jeu {
    private String titre;
    private double prix;
    private String genre;
    private boolean verif;
    private int index;

    public Jeu(String titre, double prix, String genre)
    {
        this.titre = titre;
        this.prix = prix;
        this.genre = genre;
        verif = false;
        index = 0;
    }

    @Override
    public String toString() { return "Titre : " + titre + "\nPrix : " + prix + "\nGenre : " + genre; }

    public void modifierPrix(double prix) { this.prix = prix; }

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

    public String getGenre()  {return genre; }

    public double getPrix() { return prix; }
    public boolean getVerif() { return verif; }
    public int getIndex() { return index; }

    public void setVerif(boolean verif) { this.verif = verif; }
    public void setIndex(int index) { this.index = index; }
}
