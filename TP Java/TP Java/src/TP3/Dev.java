package TP3;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void publierJeu(Jeu jeu) throws TitreDejaUtiliseException {
        for (Jeu j : jeuxPublies)
            if (j.getTitre().equalsIgnoreCase(jeu.getTitre()))
                throw new TitreDejaUtiliseException(jeu.getTitre());
        jeu.setIndex(jeuxPublies.size());
        jeuxPublies.add(jeu);
        nbJeuPublie++;
    }

    public void rechercherJeu() {
        String str;
        do {
            System.out.print("Saisir recherche : ");
            str = scanner.nextLine();
            if (Objects.equals(str, "")) System.out.println("Veuillez saisir une recherche.");
        } while (Objects.equals(str, ""));
        for (int i = 0; i < Dev.getJeuxPublies().size(); i++) {
            Jeu j = Dev.getJeuxPublies().get(i);
            if (j.getTitre().toLowerCase().contains(str.toLowerCase()) && (
                    j.getAuteur().equals(this.nomStudio) || j.getVerif()))
                System.out.println(j);
        }
    }

    public void publierAnnonce(String annonce){ System.out.println(annonce); }
    public int getNbJeuPublie(){ return nbJeuPublie; }
    public String getNomStudio(){ return nomStudio; }
    public String getSpecialite(){ return specialite; }
    public static List<Jeu> getJeuxPublies() { return jeuxPublies; }
    public static Jeu getJeu(int i) { return jeuxPublies.get(i); }
    public static void removeJeuxPublies(int i) { jeuxPublies.remove(i); }
    public static void setJeuxPublies(List<Jeu> jp){jeuxPublies=jp;}

}

