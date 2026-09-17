package TP3;

public class Dev extends Utilisateur {
    private String nomStudio;
    private String specialite;
    private int nbJeuPublie;

    public Dev(String pseudo, String email, String mdp, String nomStudio, String specialite, int nbJeuPublie)
    {
        super(pseudo, email, mdp);
        this.nomStudio = nomStudio;
        this.specialite = specialite;
        this.nbJeuPublie = nbJeuPublie;
    }
}
