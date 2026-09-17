package TP3;

public class Main {
    public static void main(String[] args) {
        // 1. Création du développeur, du jeu et publication
        Dev dev = new Dev("DevMaster", "dev@studio.com", "pass123", "SuperStudio", "RPG", 0);
        Jeu monJeu = new Jeu("Mon jeu", 29.99, "Aventure");

        dev.publierJeu(monJeu);
        System.out.println("Jeu publié par " + dev.getPseudo() + " (vérifié : " + monJeu.getVerif() + ").");

        // 2. Création de l'administrateur et validation du jeu
        Admin admin = new Admin("SuperAdmin", "admin@support.com", "root123", "superadmin", "support", 5);
        System.out.println("\n--- Validation par l'administrateur (saisir 'Y') ---");
        admin.accepterJeu(monJeu);

        // 1. Création du développeur, du jeu et publication
        Dev dev2 = new Dev("DevMaster", "dev@studio.com", "pass123", "SuperStudio", "RPG", 0);
        Jeu monJeu2 = new Jeu("Mon jeu 2", 29.99, "Aventure");

        dev2.publierJeu(monJeu2);
        System.out.println("Jeu publié par " + dev2.getPseudo() + " (vérifié : " + monJeu2.getVerif() + ").");

        // 2. Création de l'administrateur et validation du jeu
        Admin admin2 = new Admin("SuperAdmin", "admin@support.com", "root123", "superadmin", "support", 5);
        System.out.println("\n--- Validation par l'administrateur (saisir 'Y') ---");
        admin2.accepterJeu(monJeu2);

        // 1. Création de l'utilisateur Ruben et de son compte associé
        Utilisateur ruben = new Utilisateur("Ruben", "ruben@test.com", "1234");
        Compte compteRuben = new Compte("Ruben", "17/09/2026");

        System.out.println("État de connexion initial : " + compteRuben.getConnected());

        // 2. Connexion (saisir 'Ruben' ou 'ruben@test.com', puis '1234')
        System.out.println("\n--- Connexion ---");
        ruben.seConnecter(compteRuben);
        System.out.println("État connecté : " + compteRuben.getConnected());

        // 3. Déconnexion
        System.out.println("\n--- Déconnexion ---");
        ruben.seDeconnecter(compteRuben);
        System.out.println("État connecté : " + compteRuben.getConnected());

        Utilisateur.scanner.close();
    }
}