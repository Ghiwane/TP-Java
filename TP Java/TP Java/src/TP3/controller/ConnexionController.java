package TP3.controller;

import TP3.model.*;
import TP3.view.FenetreCatalogue;
import TP3.view.Fenetreconnexion;

/** Contrôleur de l'écran de connexion : vérifie les identifiants via le modèle, puis ouvre le catalogue. */
public class ConnexionController {
    private final Fenetreconnexion vue;
    private final Dev dev;
    private final Compte compteDev;
    private final Admin admin;
    private final Compte compteAdmin;
    private final CatalogueModel catalogue;

    public ConnexionController(Fenetreconnexion vue, Dev dev, Compte compteDev,
                               Admin admin, Compte compteAdmin, CatalogueModel catalogue) {
        this.vue = vue;
        this.dev = dev;
        this.compteDev = compteDev;
        this.admin = admin;
        this.compteAdmin = compteAdmin;
        this.catalogue = catalogue;

        vue.onConnexion(e -> tenterConnexion());
    }

    public void afficher() { vue.setVisible(true); }

    private void tenterConnexion() {
        boolean estDev = vue.estDeveloppeurSelectionne();
        Utilisateur utilisateur = estDev ? dev : admin;
        Compte compte = estDev ? compteDev : compteAdmin;

        if (!utilisateur.identifiantValide(vue.getIdentifiant())) {
            vue.afficherErreur("Le pseudo ou l'email est incorrect.");
            return;
        }
        if (!utilisateur.mdpValide(vue.getMotDePasse())) {
            vue.afficherErreur("Le mot de passe est incorrect.");
            return;
        }
        if (!compte.getActif()) {
            vue.afficherErreur("Ce compte est restreint.");
            return;
        }

        utilisateur.seConnecter(compte);
        vue.dispose();
        ouvrirCatalogue(utilisateur, compte, estDev);
    }

    private void ouvrirCatalogue(Utilisateur utilisateur, Compte compte, boolean estDev) {
        FenetreCatalogue vueCatalogue = new FenetreCatalogue(utilisateur.getPseudo(), estDev);
        new CatalogueController(utilisateur, compte, catalogue, vueCatalogue, this::rouvrirConnexion);
        vueCatalogue.setVisible(true);
    }

    /** Appelé après déconnexion : on recrée un couple vue + contrôleur de connexion. */
    private void rouvrirConnexion() {
        new ConnexionController(new Fenetreconnexion(), dev, compteDev, admin, compteAdmin, catalogue).afficher();
    }
}
