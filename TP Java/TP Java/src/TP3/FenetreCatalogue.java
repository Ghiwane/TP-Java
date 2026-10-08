package TP3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Fenêtre 2 : catalogue des jeux.
 * - Développeur : recherche + publication d'un jeu
 * - Administrateur : recherche + validation / suppression d'un jeu
 */
public class FenetreCatalogue extends JFrame {

    private final Utilisateur utilisateur;
    private final Compte compte;

    // Modèle de données du tableau + liste des jeux actuellement affichés (même ordre que les lignes)
    private final DefaultTableModel modele = new DefaultTableModel(
            new String[]{"Titre", "Auteur", "Genre", "Prix (€)", "Vérifié"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; } // tableau en lecture seule
    };
    private final JTable table = new JTable(modele);
    private final List<Jeu> jeuxAffiches = new ArrayList<>();
    private final JTextField champRecherche = new JTextField(20);

    public FenetreCatalogue(Utilisateur utilisateur, Compte compte, Runnable apresDeconnexion) {
        super("Catalogue - " + utilisateur.getPseudo());
        this.utilisateur = utilisateur;
        this.compte = compte;

        // --- Haut : barre de recherche + déconnexion ---
        JButton boutonRecherche = new JButton("Rechercher");
        JButton boutonDeconnexion = new JButton("Se déconnecter");
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        haut.add(new JLabel("Recherche :"));
        haut.add(champRecherche);
        haut.add(boutonRecherche);
        haut.add(boutonDeconnexion);

        // --- Centre : tableau des jeux dans un JScrollPane ---
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // --- Bas : boutons selon le rôle (instanceof reprend le polymorphisme de Utilisateur) ---
        JPanel bas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        if (utilisateur instanceof Dev) {
            JButton boutonPublier = new JButton("Publier un jeu");
            boutonPublier.addActionListener(e -> publierJeu());
            bas.add(boutonPublier);
        } else {
            JButton boutonValider = new JButton("Valider le jeu");
            JButton boutonSupprimer = new JButton("Supprimer le jeu");
            boutonValider.addActionListener(e -> validerJeu());
            JButton boutonSerialiser = new JButton("Sérialiser");
            JButton boutonDeserialiser = new JButton("Désérialiser");
            boutonSupprimer.addActionListener(e -> supprimerJeu());
            boutonSerialiser.addActionListener(e -> serialiser());
            boutonDeserialiser.addActionListener(e -> deserialiser());
            bas.add(boutonValider);
            bas.add(boutonSupprimer);
            bas.add(boutonSerialiser);
            bas.add(boutonDeserialiser);
        }

        // --- Assemblage ---
        setLayout(new BorderLayout());
        add(haut, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        // --- Écouteurs ---
        boutonRecherche.addActionListener(e -> rafraichir());
        champRecherche.addActionListener(e -> rafraichir()); // touche Entrée dans le champ
        boutonDeconnexion.addActionListener(e -> {
            compte.setConnected(false);
            dispose();
            apresDeconnexion.run(); // retour à la fenêtre de connexion
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);
        rafraichir();
        setVisible(true);
    }

    /** Recharge le tableau depuis Dev.getJeuxPublies() en appliquant le filtre de recherche. */
    private void rafraichir() {
        String filtre = champRecherche.getText().trim().toLowerCase();
        modele.setRowCount(0);
        jeuxAffiches.clear();

        for (Jeu j : Dev.getJeuxPublies()) {
            boolean correspond = j.getTitre().toLowerCase().contains(filtre);
            // Même règle que Dev.rechercherJeu() : un dev voit ses jeux et les jeux vérifiés
            boolean visible = true;
            if (utilisateur instanceof Dev) {
                visible = j.getAuteur().equals(((Dev) utilisateur).getNomStudio()) || j.getVerif();
            }
            if (correspond && visible) {
                jeuxAffiches.add(j);
                modele.addRow(new Object[]{
                        j.getTitre(), j.getAuteur(), j.getGenre(), j.getPrix(), j.getVerif() ? "Oui" : "Non"});
            }
        }
    }

    /** Ouvre un formulaire de saisie, puis publie le jeu (gère les exceptions du TP). */
    private void publierJeu() {
        Dev dev = (Dev) utilisateur;
        JTextField titre = new JTextField();
        JTextField genre = new JTextField();
        JTextField prix = new JTextField();
        JPanel formulaire = new JPanel(new GridLayout(3, 2, 6, 6));
        formulaire.add(new JLabel("Titre"));    formulaire.add(titre);
        formulaire.add(new JLabel("Genre"));    formulaire.add(genre);
        formulaire.add(new JLabel("Prix (€)")); formulaire.add(prix);

        int reponse = JOptionPane.showConfirmDialog(this, formulaire, "Publier un jeu",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (reponse != JOptionPane.OK_OPTION) return;

        try {
            double p = Double.parseDouble(prix.getText().trim().replace(',', '.'));
            dev.publierJeu(new Jeu(titre.getText().trim(), dev.getNomStudio(), p, genre.getText().trim()));
            rafraichir();
        } catch (NumberFormatException e) {
            erreur("Le prix doit être un nombre.");
        } catch (IllegalArgumentException | TitreDejaUtiliseException e) {
            erreur(e.getMessage()); // prix négatif ou titre déjà pris
        }
    }

    /** Valide le jeu sélectionné (JeuDejaVerifieException si déjà vérifié). */
    private void validerJeu() {
        Jeu j = jeuSelectionne();
        if (j == null) return;
        try {
            if (j.getVerif()) throw new JeuDejaVerifieException(j.getTitre());
            j.setVerif(true);
            rafraichir();
        } catch (JeuDejaVerifieException e) {
            erreur(e.getMessage());
        }
    }

    /** Supprime le jeu sélectionné après confirmation, puis réindexe la liste. */
    private void supprimerJeu() {
        Jeu j = jeuSelectionne();
        if (j == null) return;
        int rep = JOptionPane.showConfirmDialog(this,
                "Supprimer le jeu " + j.getTitre() + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (rep != JOptionPane.YES_OPTION) return;

        Dev.getJeuxPublies().remove(j);
        for (int i = 0; i < Dev.getJeuxPublies().size(); i++) {
            Dev.getJeuxPublies().get(i).setIndex(i);
        }
        rafraichir();
    }

    /** Sérialise la liste des jeux dans jp.ser (le même fichier que Admin.serialiserListJeu). */
    private void serialiser() {
        if (Dev.getJeuxPublies().isEmpty()) {
            erreur("Le catalogue est vide.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("jp.ser"))) {
            oos.writeObject(Dev.getJeuxPublies());
            JOptionPane.showMessageDialog(this,
                    Dev.getJeuxPublies().size() + " jeu(x) sérialisé(s) dans jp.ser.",
                    "Sérialisation", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            erreur("Échec de la sérialisation : " + e.getMessage());
        }
    }

    /** Désérialise jp.ser, remplace le catalogue puis met à jour le tableau. */
    @SuppressWarnings("unchecked")
    private void deserialiser() {
        int rep = JOptionPane.showConfirmDialog(this,
                "Remplacer le catalogue actuel par le contenu de jp.ser ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (rep != JOptionPane.YES_OPTION) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("jp.ser"))) {
            List<Jeu> jeux = (List<Jeu>) ois.readObject();
            Dev.setJeuxPublies(jeux);
            for (int i = 0; i < jeux.size(); i++) {
                jeux.get(i).setIndex(i);
            }
            rafraichir();
            JOptionPane.showMessageDialog(this,
                    jeux.size() + " jeu(x) désérialisé(s).",
                    "Désérialisation", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.io.FileNotFoundException e) {
            erreur("Fichier jp.ser introuvable : sérialisez d'abord le catalogue.");
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            erreur("Échec de la désérialisation : " + e.getMessage());
        }
    }

    /** Retourne le jeu de la ligne sélectionnée, ou null (avec message) si aucune ligne n'est choisie. */
    private Jeu jeuSelectionne() {
        int ligne = table.getSelectedRow();
        if (ligne < 0) {
            erreur("Sélectionnez d'abord un jeu dans le tableau.");
            return null;
        }
        return jeuxAffiches.get(ligne);
    }

    private void erreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}