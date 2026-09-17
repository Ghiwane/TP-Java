package TP3;

public class Jeu {
    private String titre;
    double prix;
    private String genre;

    public void Jeu(String titre, double prix, String genre)
    {
        this.titre = titre;
        this.prix = prix;
        this.genre = genre;
    }

    public void affiche()
    {
        System.out.println("Titre : " + titre);
        System.out.println("Prix : " + prix);
        System.out.println("Genre : " + genre);
    }

    public void modifierPrix(double prix)
    {
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
    public String getTitre()
    {
        return titre;
    }

    public String getGenre()
    {
        return genre;
    }

    public double getPrix()
    {
        return prix;
    }

}
