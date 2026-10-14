package TP3.model;

public class Admin extends Utilisateur {
    private String niveauAcces, service;
    private int nombreComptesGeres;

    public Admin() {
        super();
        this.niveauAcces = "standard";
        this.service = "support";
        this.nombreComptesGeres = 0;
    }

    public Admin(String pseudo, String email, String mdp, String niveauAcces, String service, int nombreComptesGeres) {
        super(pseudo, email, mdp);
        this.niveauAcces = niveauAcces; this.service = service; this.nombreComptesGeres = nombreComptesGeres;
    }

    public String getNiveauAcces() { return niveauAcces; }
    public String getService() { return service; }
    public int getNombreComptesGeres() { return nombreComptesGeres; }

    public void setNiveauAcces(String niveauAcces) { this.niveauAcces = niveauAcces; }
    public void setService(String service) { this.service = service; }
    public void setNombreComptesGeres(int nombreComptesGeres) { this.nombreComptesGeres = nombreComptesGeres; }

    /** @return false si le compte était déjà inactif. La confirmation Y/N relève de la vue. */
    public boolean restreindreCompte(Compte compte) {
        if (!compte.getActif()) return false;
        compte.setActif(false);
        return true;
    }

    /** @return false si le compte était déjà actif. */
    public boolean restituerCompte(Compte compte) {
        if (compte.getActif()) return false;
        compte.setActif(true);
        return true;
    }

    public void accepterJeu(Jeu jeu, CatalogueModel catalogue) throws JeuDejaVerifieException {
        catalogue.valider(jeu);
    }

    public void supprimerJeu(Jeu jeu, CatalogueModel catalogue) {
        catalogue.supprimer(jeu);
    }
}
