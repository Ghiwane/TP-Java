package TP3;

import javax.swing.SwingUtilities;

/** Point d'entrée de la version graphique : mêmes données initiales que TP3.Main. */
public class MainGUI {
    public static void main(String[] args) {
        Dev dev = new Dev("DevIndie", "dev@studio.com", "pass123", "PixelForge", "Plateforme / RPG", 0);
        Compte compteDev = new Compte(dev.getPseudo(), "22/09/2026");

        Admin admin = new Admin("SuperAdmin", "admin@support.com", "root123", "SuperAdmin", "Modération", 12);
        Compte compteAdmin = new Compte(admin.getPseudo(), "22/09/2026");

        try {
            dev.publierJeu(new Jeu("Cyber Run", "Microsoft", 14.99, "Action"));
            dev.publierJeu(new Jeu("Fantasy Quest", "PixelForge", 29.99, "RPG"));
        } catch (TitreDejaUtiliseException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // Toute création de fenêtre Swing doit se faire dans le thread graphique (EDT)
        SwingUtilities.invokeLater(() -> new Fenetreconnexion(dev, compteDev, admin, compteAdmin));
    }
}