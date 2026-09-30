package TP3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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

    public void restreindreCompte(Compte compte) {
        char conf;
        if (!compte.getActif()) {
            System.out.println("Le compte " + compte.getIdentifiant() + " est déjà inactif.");
            return;
        }
        do {
            System.out.print("Confirmer restriction de " + compte.getIdentifiant() + " ?\n[Y/N] -> ");
            conf = scanner.next().trim().toUpperCase().charAt(0);
            if (conf != 'Y' && conf != 'N')
                System.out.println("Saisie incorrecte.");
        } while (conf != 'Y' && conf != 'N');
        if (conf == 'Y') {
            compte.setActif(false);
            System.out.println("Le compte " + compte.getIdentifiant() + " a été restreint.");
        } else System.out.println("Annulation de la restriction.");
    }

    public void restituerCompte(Compte compte) {
        char conf;
        if (compte.getActif()) {
            System.out.println("Le compte " + compte.getIdentifiant() + " est déjà actif.");
            return;
        }
        do {
            System.out.print("Confirmer restitution de " + compte.getIdentifiant() + " ?\n[Y/N] -> ");
            conf = scanner.next().trim().toUpperCase().charAt(0);
            if (conf != 'Y' && conf != 'N')
                System.out.println("Saisie incorrecte.");
        } while (conf != 'Y' && conf != 'N');
        if (conf == 'Y') {
            compte.setActif(true);
            System.out.println("Le compte " + compte.getIdentifiant() + " a été restitué.");
        } else System.out.println("Annulation de la restitution.");
    }

    public void accepterJeu(Jeu jeu) throws JeuDejaVerifieException {
        char conf;
        if (jeu.getVerif()) {
            throw new JeuDejaVerifieException(jeu.getTitre());
        }
        do {
            System.out.println(jeu.toString());
            System.out.print("Voulez vous vérifier ce jeu ?\n[Y/N] -> ");
            conf = scanner.next().trim().toUpperCase().charAt(0);
            if (conf != 'Y' && conf != 'N') {
                System.out.println("Saisie incorrecte.");
            }
        } while (conf != 'Y' && conf != 'N');
        if (conf == 'Y') {
            jeu.setVerif(true);
            System.out.println("Le jeu " + jeu.getTitre() + " a été vérifié.");
        }
        else {
            do {
                System.out.print("Voulez vous supprimer le jeu ?\n[Y/N] -> ");
                conf = scanner.next().trim().toUpperCase().charAt(0);
                if (conf != 'Y' && conf != 'N')
                    System.out.print("Saisie incorrecte.");
            } while (conf != 'Y' && conf != 'N');
            if (conf == 'Y') {
                int idx = jeu.getIndex();
                Dev.removeJeuxPublies(idx);
                for (int i = idx; i < Dev.getJeuxPublies().size(); i++) {
                    Dev.getJeuxPublies().get(i).setIndex(i);
                }
                System.out.println("Jeu supprimé avec succès.");
            }
            else System.out.println("Jeu non supprimé.");
        }
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
            if (j.getTitre().toLowerCase().contains(str.toLowerCase()))
                System.out.println(j);
        }
    }

    public static void sauvegarderListJeu() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("jp.txt"));
            List<Jeu> jp = Dev.getJeuxPublies();
            int len = jp.size();

            for (int i = 0; i < len; i++) {
                System.out.println("(" + i + "/" + len + ") Sauvegarde de " + jp.get(i).getTitre() + "...");
                writer.write(i + ";");
                writer.write(jp.get(i).getTitre() + ";");
                writer.write(jp.get(i).getAuteur() + ";");
                writer.write(jp.get(i).getGenre() + ";");
                writer.write(jp.get(i).getPrix() + ";");
                writer.write(jp.get(i).getVerif() + ";\n");
            }
            System.out.println("Sauvegarde terminée (" + len + " jeux)");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serialiserListJeu(List<Jeu> jp) throws FileNotFoundException, IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("jp.ser"))) {
            oos.writeObject(jp);
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        } finally {
            System.out.println("Fin de la sauvegarde.");
        }
    }

    public static List<Jeu> lireFichierJeu(String nomdufichier) {
        List<Jeu> jeuLue = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(nomdufichier))) {
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine().trim();
                if (ligne.isEmpty()) continue;

                // split(";") ignore les champs vides à la fin, donc 6 éléments ici
                String[] champs = ligne.split(";");

                if (champs.length < 6) {
                    System.out.println("Ligne ignorée (format invalide) : " + ligne);
                    continue;
                }

                try {
                    // champs[0] = index (inutile pour reconstruire l'objet)
                    String titre  = champs[1];
                    String auteur = champs[2];
                    String genre  = champs[3];
                    double prix   = Double.parseDouble(champs[4]);
                    boolean verif = Boolean.parseBoolean(champs[5]);

                    // À adapter selon le constructeur de ta classe Jeu
                    jeuLue.add(new Jeu(titre, auteur, prix, genre, verif));
                } catch (NumberFormatException e) {
                    System.out.println("Ligne ignorée (prix invalide) : " + ligne);
                }
            }
            System.out.println("Lecture terminée (" + jeuLue.size() + " jeux)");
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + e.getMessage());
        }

        return jeuLue;
    }
    public static List<Jeu> deserialiserListJeu(String nomdufichier) {
        List<Jeu> jp = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomdufichier))) {
            jp = (List<Jeu>) ois.readObject();
            System.out.println("Désérialisation terminée (" + jp.size() + " jeux)");
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return jp;
    }
}
