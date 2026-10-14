package TP3.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/** Modèle : données du catalogue + règles qui les modifient. Aucune dépendance à Swing ni à la console. */
public class CatalogueModel {
    private List<Jeu> jeux = new ArrayList<>();
    private final List<Runnable> observateurs = new ArrayList<>();

    // --- Observateurs (la vue est prévenue via le contrôleur) ---
    public void ajouterObservateur(Runnable o) { observateurs.add(o); }

    private void notifierObservateurs() {
        for (Runnable o : observateurs) o.run();
    }

    public List<Jeu> getJeux() { return Collections.unmodifiableList(jeux); }

    // --- Règles métier (reprises de Dev, Admin et FenetreCatalogue) ---
    public void ajouter(Jeu jeu) throws TitreDejaUtiliseException {
        for (Jeu j : jeux)
            if (j.getTitre().equalsIgnoreCase(jeu.getTitre()))
                throw new TitreDejaUtiliseException(jeu.getTitre());
        jeu.setIndex(jeux.size());
        jeux.add(jeu);
        notifierObservateurs();
    }

    public void valider(Jeu jeu) throws JeuDejaVerifieException {
        if (jeu.getVerif()) throw new JeuDejaVerifieException(jeu.getTitre());
        jeu.setVerif(true);
        notifierObservateurs();
    }

    public void supprimer(Jeu jeu) {
        jeux.remove(jeu);
        reindexer();
        notifierObservateurs();
    }

    private void remplacer(List<Jeu> nouveaux) {
        this.jeux = nouveaux;
        reindexer();
        notifierObservateurs();
    }

    private void reindexer() {
        for (int i = 0; i < jeux.size(); i++) jeux.get(i).setIndex(i);
    }

    // --- Persistance (reprise de Admin, sans console) ---
    public void serialiser(String fichier) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(jeux);
        }
    }

    @SuppressWarnings("unchecked")
    public int deserialiser(String fichier) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            List<Jeu> lus = (List<Jeu>) ois.readObject();
            remplacer(lus);
            return lus.size();
        }
    }

    public void sauvegarderTxt(String fichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (int i = 0; i < jeux.size(); i++) {
                Jeu j = jeux.get(i);
                writer.write(i + ";");
                writer.write(j.getTitre() + ";");
                writer.write(j.getAuteur() + ";");
                writer.write(j.getGenre() + ";");
                writer.write(j.getPrix() + ";");
                writer.write(j.getVerif() + ";\n");
            }
        }
    }

    public int lireTxt(String fichier) throws FileNotFoundException {
        List<Jeu> lus = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(fichier))) {
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine().trim();
                if (ligne.isEmpty()) continue;
                String[] champs = ligne.split(";");
                if (champs.length < 6) continue; // ligne ignorée (format invalide)
                try {
                    lus.add(new Jeu(champs[1], champs[2], Double.parseDouble(champs[4]),
                            champs[3], Boolean.parseBoolean(champs[5])));
                } catch (NumberFormatException e) {
                    // ligne ignorée (prix invalide)
                }
            }
        }
        remplacer(lus);
        return lus.size();
    }
}
