package TP3;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    // Définition des modes d'utilisation
    private enum Mode {
        DEVELOPPEUR,
        ADMINISTRATEUR
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = Utilisateur.scanner;

        // --- Instanciation des profils et comptes initiaux ---
        Dev dev = new Dev("DevIndie", "dev@studio.com", "pass123", "PixelForge", "Plateforme / RPG", 0);
        Compte compteDev = new Compte(dev.getPseudo(), "22/09/2026");

        Admin admin = new Admin("SuperAdmin", "admin@support.com", "root123", "SuperAdmin", "Modération", 12);
        Compte compteAdmin = new Compte(admin.getPseudo(), "22/09/2026");

        // Jeux de base pour faciliter les tests
        Jeu jeu1 = new Jeu("Cyber Run", "Microsoft", 14.99, "Action");
        Jeu jeu2 = new Jeu("Fantasy Quest", "PixelForge", 29.99, "RPG");
        try {
            dev.publierJeu(jeu1);
            dev.publierJeu(jeu2);
        } catch (TitreDejaUtiliseException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        Mode modeActuel = Mode.DEVELOPPEUR;
        boolean continuer = true;

        System.out.println("==================================================");
        System.out.println(" BIENVENUE SUR LA PLATEFORME DE GESTION DE JEUX   ");
        System.out.println("==================================================");

        while (continuer) {
            System.out.println("\n--------------------------------------------------");
            System.out.println(" RÔLE ACTIF : [" + modeActuel + "]");
            if (modeActuel == Mode.DEVELOPPEUR) {
                System.out.println(" Utilisateur : " + dev.getPseudo() + " | Connecté : " + compteDev.getConnected() + " | Actif : " + compteDev.getActif());
            } else {
                System.out.println(" Utilisateur : " + admin.getPseudo() + " | Connecté : " + compteAdmin.getConnected() + " | Actif : " + compteAdmin.getActif());
            }
            System.out.println("--------------------------------------------------");
            System.out.println("1. Basculer de mode (Développeur <-> Administrateur)");
            System.out.println("2. S'authentifier (Connexion)");
            System.out.println("3. Se déconnecter");

            if (modeActuel == Mode.DEVELOPPEUR) {
                System.out.println("4. Publier un nouveau jeu");
                System.out.println("5. Publier une annonce de studio");
                System.out.println("6. Rechercher un jeu dans le catalogue");
                System.out.println("7. Afficher les informations du studio");
            } else {
                System.out.println("4. Examiner et valider / refuser un jeu");
                System.out.println("5. Restreindre un compte");
                System.out.println("6. Restituer un compte");
                System.out.println("7. Rechercher un jeu dans tout le catalogue");
                System.out.println("8. Lister tous les jeux enregistrés");
                System.out.println("9. Serialiser et sauvegarder la liste des jeux");
                System.out.println("10. Sauvegarder les jeux dans un fichier .txt");
            }
            System.out.println("0. Quitter l'application");
            System.out.print("Votre choix : ");

            String entree = scanner.nextLine().trim();
            int choix;
            try {
                choix = Integer.parseInt(entree);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un numéro valide.");
                continue;
            }

            System.out.println();

            switch (choix) {
                case 1:
                    // Basculer entre Développeur et Administrateur
                    if (modeActuel == Mode.DEVELOPPEUR) {
                        modeActuel = Mode.ADMINISTRATEUR;
                    } else {
                        modeActuel = Mode.DEVELOPPEUR;
                    }
                    System.out.println("Mode basculé vers : [" + modeActuel + "]");
                    break;

                case 2:
                    // Connexion
                    if (modeActuel == Mode.DEVELOPPEUR) {
                        dev.seConnecter(compteDev);
                    } else {
                        admin.seConnecter(compteAdmin);
                    }
                    break;

                case 3:
                    // Déconnexion
                    if (modeActuel == Mode.DEVELOPPEUR) {
                        dev.seDeconnecter(compteDev);
                    } else {
                        admin.seDeconnecter(compteAdmin);
                    }
                    break;

                case 4:
                    if (modeActuel == Mode.DEVELOPPEUR) {
                        // Publier un jeu
                        System.out.print("Titre du jeu : ");
                        String titre = scanner.nextLine();

                        System.out.print("Genre : ");
                        String genre = scanner.nextLine();

                        System.out.print("Prix (€) : ");
                        double prix = 0.0;
                        try {
                            prix = Double.parseDouble(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Prix invalide, défini à 0.0 par défaut.");
                        }

                        try {
                            Jeu nouveauJeu = new Jeu(titre, dev.getNomStudio(), prix, genre);
                            dev.publierJeu(nouveauJeu);
                            System.out.println("Le jeu '" + titre + "' a été soumis pour publication avec succès !");
                        } catch (IllegalArgumentException | TitreDejaUtiliseException e) {
                            System.out.println("Erreur : " + e.getMessage());
                        }
                    } else {
                        // Examiner un jeu
                        if (Dev.getJeuxPublies().isEmpty()) {
                            System.out.println("Aucun jeu n'est actuellement enregistré.");
                        } else {
                            System.out.println("Sélectionnez le jeu à examiner :");
                            for (int i = 0; i < Dev.getJeuxPublies().size(); i++) {
                                Jeu j = Dev.getJeuxPublies().get(i);
                                System.out.println(i + ". " + j.getTitre() + " (Vérifié : " + j.getVerif() + ")");
                            }
                            System.out.print("Numéro du jeu : ");
                            try {
                                int index = Integer.parseInt(scanner.nextLine().trim());
                                if (index >= 0 && index < Dev.getJeuxPublies().size()) {
                                    try {
                                        admin.accepterJeu(Dev.getJeuxPublies().get(index));
                                    } catch (JeuDejaVerifieException e) {
                                        System.out.println("Erreur : " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("Index introuvable.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrée non valide.");
                            }
                        }
                    }
                    break;

                case 5:
                    if (modeActuel == Mode.DEVELOPPEUR) {
                        // Publier une annonce
                        System.out.print("Saisir le texte de votre annonce : ");
                        String annonce = scanner.nextLine();
                        dev.publierAnnonce(annonce);
                    } else {
                        // Restreindre un compte (testé sur le compte développeur)
                        System.out.println("Cible : Compte de " + compteDev.getIdentifiant());
                        admin.restreindreCompte(compteDev);
                    }
                    break;

                case 6:
                    if (modeActuel == Mode.DEVELOPPEUR) {
                        // Recherche de jeu côté Dev
                        dev.rechercherJeu();
                    } else {
                        // Restituer un compte
                        System.out.println("Cible : Compte de " + compteDev.getIdentifiant());
                        admin.restituerCompte(compteDev);
                    }
                    break;

                case 7:
                    if (modeActuel == Mode.DEVELOPPEUR) {
                        // Informations du studio
                        System.out.println("Nom du studio : " + dev.getNomStudio());
                        System.out.println("Spécialité     : " + dev.getSpecialite());
                        System.out.println("Jeux publiés   : " + dev.getNbJeuPublie());
                    } else {
                        // Recherche côté Admin
                        admin.rechercherJeu();
                    }
                    break;

                case 8:
                    if (modeActuel == Mode.ADMINISTRATEUR) {
                        // Liste exhaustive des jeux
                        if (Dev.getJeuxPublies().isEmpty()) {
                            System.out.println("Le catalogue est vide.");
                        } else {
                            System.out.println("--- Catalogue complet des jeux ---");
                            for (Jeu j : Dev.getJeuxPublies()) {
                                System.out.println("-------------------------");
                                System.out.println(j);
                                System.out.println("Vérifié : " + j.getVerif());
                            }
                        }
                    } else {
                        System.out.println("Option inexistante.");
                    }
                    break;

                case 9:
                    if (modeActuel == Mode.ADMINISTRATEUR) {
                        if (Dev.getJeuxPublies().isEmpty()) System.out.println("Le catalogue est vide.");
                        else Admin.serialiserListJeu(Dev.getJeuxPublies());
                    } else System.out.println("Option inexistante.");
                    break;

                case 10:
                    if (modeActuel == Mode.ADMINISTRATEUR) {
                        if (Dev.getJeuxPublies().isEmpty()) {
                            System.out.println("Le catalogue est vide.");
                        } else {
                            Admin.sauvegarderListJeu();
                        }
                    } else {
                        System.out.println("Option inexistante.");
                    }
                    break;

                case 0:
                    continuer = false;
                    System.out.println("Fermeture du programme. À bientôt !");
                    break;

                default:
                    System.out.println("Option non reconnue.");
                    break;
            }
        }
    }
}