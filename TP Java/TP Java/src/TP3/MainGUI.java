package TP3;

import TP3.controller.ConnexionController;
import TP3.model.*;
import TP3.view.Fenetreconnexion;

import javax.swing.SwingUtilities;

public class MainGUI {
    public static void main(String[] args) {
        // --- Modèle ---
        CatalogueModel catalogue = new CatalogueModel();

        Dev dev = new Dev("DevIndie", "dev@studio.com", "pass123", "PixelForge", "Plateforme / RPG", 0);
        Compte compteDev = new Compte(dev.getPseudo(), "22/09/2026");

        Admin admin = new Admin("SuperAdmin", "admin@support.com", "root123", "SuperAdmin", "Modération", 12);
        Compte compteAdmin = new Compte(admin.getPseudo(), "22/09/2026");

        try {
            dev.publierJeu(new Jeu("Cyber Run", "Microsoft", 14.99, "Action"), catalogue);
            dev.publierJeu(new Jeu("Fantasy Quest", "PixelForge", 29.99, "RPG"), catalogue);
        } catch (TitreDejaUtiliseException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // --- Vue + Contrôleur (dans le thread graphique) ---
        SwingUtilities.invokeLater(() ->
                new ConnexionController(new Fenetreconnexion(), dev, compteDev, admin, compteAdmin, catalogue)
                        .afficher());
    }
}
