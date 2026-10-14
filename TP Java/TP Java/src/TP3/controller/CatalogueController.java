package TP3.controller;

import TP3.model.*;
import TP3.view.FenetreCatalogue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Contrôleur du catalogue : traduit les actions de la vue en appels au modèle et met la vue à jour. */
public class CatalogueController {
    private static final String FICHIER_SER = "jp.ser";

    private final Utilisateur utilisateur;
    private final Compte compte;
    private final CatalogueModel catalogue;
    private final FenetreCatalogue vue;
    private final Runnable apresDeconnexion;

    // Jeux actuellement affichés (même ordre que les lignes du tableau)
    private List<Jeu> jeuxAffiches = new ArrayList<>();

    public CatalogueController(Utilisateur utilisateur, Compte compte, CatalogueModel catalogue,
                               FenetreCatalogue vue, Runnable apresDeconnexion) {
        this.utilisateur = utilisateur;
        this.compte = compte;
        this.catalogue = catalogue;
        this.vue = vue;
        this.apresDeconnexion = apresDeconnexion;

        // Modèle -> Vue : à chaque changement du catalogue, on rafraîchit l'affichage
        catalogue.ajouterObservateur(this::rafraichir);

        // Vue -> Contrôleur : branchement des événements
        vue.onRecherche(e -> rafraichir());
        vue.onDeconnexion(e -> deconnecter());
        if (utilisateur instanceof Dev) {
            vue.onPublier(e -> publierJeu());
        } else {
            vue.onValider(e -> validerJeu());
            vue.onSupprimer(e -> supprimerJeu());
            vue.onSerialiser(e -> serialiser());
            vue.onDeserialiser(e -> deserialiser());
        }

        rafraichir();
    }

    private void rafraichir() {
        jeuxAffiches = utilisateur.rechercherJeu(catalogue, vue.getFiltre());
        vue.afficherJeux(jeuxAffiches);
    }

    private void deconnecter() {
        utilisateur.seDeconnecter(compte);
        vue.dispose();
        apresDeconnexion.run();
    }

    private void publierJeu() {
        Dev dev = (Dev) utilisateur;
        String[] saisie = vue.demanderNouveauJeu();
        if (saisie == null) return;

        try {
            double prix = Double.parseDouble(saisie[2].trim().replace(',', '.'));
            dev.publierJeu(new Jeu(saisie[0].trim(), dev.getNomStudio(), prix, saisie[1].trim()), catalogue);
        } catch (NumberFormatException e) {
            vue.afficherErreur("Le prix doit être un nombre.");
        } catch (IllegalArgumentException | TitreDejaUtiliseException e) {
            vue.afficherErreur(e.getMessage()); // prix négatif ou titre déjà pris
        }
    }

    private void validerJeu() {
        Jeu j = jeuSelectionne();
        if (j == null) return;
        try {
            ((Admin) utilisateur).accepterJeu(j, catalogue);
        } catch (JeuDejaVerifieException e) {
            vue.afficherErreur(e.getMessage());
        }
    }

    private void supprimerJeu() {
        Jeu j = jeuSelectionne();
        if (j == null) return;
        if (!vue.demanderConfirmation("Supprimer le jeu " + j.getTitre() + " ?", "Confirmation")) return;
        ((Admin) utilisateur).supprimerJeu(j, catalogue);
    }

    private void serialiser() {
        if (catalogue.getJeux().isEmpty()) {
            vue.afficherErreur("Le catalogue est vide.");
            return;
        }
        try {
            catalogue.serialiser(FICHIER_SER);
            vue.afficherInfo(catalogue.getJeux().size() + " jeu(x) sérialisé(s) dans " + FICHIER_SER + ".",
                    "Sérialisation");
        } catch (IOException e) {
            vue.afficherErreur("Échec de la sérialisation : " + e.getMessage());
        }
    }

    private void deserialiser() {
        if (!vue.demanderConfirmation("Remplacer le catalogue actuel par le contenu de " + FICHIER_SER + " ?",
                "Confirmation")) return;
        try {
            int n = catalogue.deserialiser(FICHIER_SER);
            vue.afficherInfo(n + " jeu(x) désérialisé(s).", "Désérialisation");
        } catch (FileNotFoundException e) {
            vue.afficherErreur("Fichier " + FICHIER_SER + " introuvable : sérialisez d'abord le catalogue.");
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            vue.afficherErreur("Échec de la désérialisation : " + e.getMessage());
        }
    }

    /** Jeu de la ligne sélectionnée, ou null (avec message d'erreur) si aucune ligne n'est choisie. */
    private Jeu jeuSelectionne() {
        int ligne = vue.getLigneSelectionnee();
        if (ligne < 0) {
            vue.afficherErreur("Sélectionnez d'abord un jeu dans le tableau.");
            return null;
        }
        return jeuxAffiches.get(ligne);
    }
}
