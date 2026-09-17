package TP3;

public class Dev extends Utilisateur {
    private String nomStudio;
    private String specialite;
    private int nbJeuPublie;
    private static Jeu[] jeuxPublies;

    public Dev(String pseudo, String email, String mdp, String nomStudio, String specialite, int nbJeuPublie)
    {
        super(pseudo, email, mdp);
        this.nomStudio = nomStudio;
        this.specialite = specialite;
        this.nbJeuPublie = nbJeuPublie;
    }

    public void publierJeu(Jeu jeu)
    {
        jeuxPublies[nbJeuPublie] = jeu;
        nbJeuPublie++;
    }

    public static Jeu[] getJeuxPublies()
    {
        return jeuxPublies;
    }
}

