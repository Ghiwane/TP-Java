package TP3.model;

public class Dev extends Utilisateur {
    private String nomStudio;
    private String specialite;
    private int nbJeuPublie;

    public Dev(String pseudo, String email, String mdp, String nomStudio, String specialite, int nbJeuPublie) {
        super(pseudo, email, mdp);
        this.nomStudio = nomStudio;
        this.specialite = specialite;
        this.nbJeuPublie = nbJeuPublie;
    }

    public void publierJeu(Jeu jeu, CatalogueModel catalogue) throws TitreDejaUtiliseException {
        catalogue.ajouter(jeu);
        nbJeuPublie++;
    }

    /** Un développeur voit ses propres jeux et les jeux vérifiés. */
    @Override
    public boolean peutVoir(Jeu j) {
        return j.getAuteur().equals(nomStudio) || j.getVerif();
    }

    public String publierAnnonce(String annonce) { return annonce; } // l'affichage est fait par la vue

    public int getNbJeuPublie() { return nbJeuPublie; }
    public String getNomStudio() { return nomStudio; }
    public String getSpecialite() { return specialite; }
}
