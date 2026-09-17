package TP3;
import java.util.ArrayList;
import java.util.List;

public class Dev extends Utilisateur {
    private String nomStudio;
    private String specialite;
    private int nbJeuPublie;
    private static List<Jeu> jeuxPublies = new ArrayList<>();

    public Dev(String pseudo, String email, String mdp, String nomStudio, String specialite, int nbJeuPublie)
    {
        super(pseudo, email, mdp);
        this.nomStudio = nomStudio;
        this.specialite = specialite;
        this.nbJeuPublie = nbJeuPublie;
    }

    public void publierJeu(Jeu jeu)
    {
        jeu.setIndex(jeuxPublies.size());
        jeuxPublies.add(jeu);
        nbJeuPublie++;
    }

    public void publierAnnonce(String annonce){ System.out.println(annonce); }
    public int getNbJeuPublie(){ return nbJeuPublie; }
    public String getNomStudio(){ return nomStudio; }
    public String getSpecialite(){ return specialite; }
    public static List<Jeu> getJeuxPublies() { return jeuxPublies; }
    public static Jeu getJeu(int i) { return jeuxPublies.get(i); }
    public static void removeJeuxPublies(int i) { jeuxPublies.remove(i); }


}

