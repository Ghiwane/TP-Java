package TP3.view;

import TP3.model.Jeu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/** Vue : catalogue des jeux. Elle affiche et relaie les clics, elle ne décide de rien. */
public class FenetreCatalogue extends JFrame {

    private final DefaultTableModel modele = new DefaultTableModel(
            new String[]{"Titre", "Auteur", "Genre", "Prix (€)", "Vérifié"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(modele);
    private final JTextField champRecherche = new JTextField(20);

    private final JButton boutonRecherche = new JButton("Rechercher");
    private final JButton boutonDeconnexion = new JButton("Se déconnecter");
    private final JButton boutonPublier = new JButton("Publier un jeu");
    private final JButton boutonValider = new JButton("Valider le jeu");
    private final JButton boutonSupprimer = new JButton("Supprimer le jeu");
    private final JButton boutonSerialiser = new JButton("Sérialiser");
    private final JButton boutonDeserialiser = new JButton("Désérialiser");

    /** @param estDev true : boutons du développeur ; false : boutons de l'administrateur */
    public FenetreCatalogue(String pseudo, boolean estDev) {
        super("Catalogue - " + pseudo);

        JPanel haut = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        haut.add(new JLabel("Recherche :"));
        haut.add(champRecherche);
        haut.add(boutonRecherche);
        haut.add(boutonDeconnexion);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel bas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        if (estDev) {
            bas.add(boutonPublier);
        } else {
            bas.add(boutonValider);
            bas.add(boutonSupprimer);
            bas.add(boutonSerialiser);
            bas.add(boutonDeserialiser);
        }

        setLayout(new BorderLayout());
        add(haut, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);
    }

    // --- Branchement des événements (appelé par le contrôleur) ---
    public void onRecherche(ActionListener l) {
        boutonRecherche.addActionListener(l);
        champRecherche.addActionListener(l); // touche Entrée dans le champ
    }
    public void onDeconnexion(ActionListener l) { boutonDeconnexion.addActionListener(l); }
    public void onPublier(ActionListener l) { boutonPublier.addActionListener(l); }
    public void onValider(ActionListener l) { boutonValider.addActionListener(l); }
    public void onSupprimer(ActionListener l) { boutonSupprimer.addActionListener(l); }
    public void onSerialiser(ActionListener l) { boutonSerialiser.addActionListener(l); }
    public void onDeserialiser(ActionListener l) { boutonDeserialiser.addActionListener(l); }

    // --- Lecture de l'état de l'interface ---
    public String getFiltre() { return champRecherche.getText(); }
    public int getLigneSelectionnee() { return table.getSelectedRow(); }

    // --- Affichage ---
    public void afficherJeux(List<Jeu> jeux) {
        modele.setRowCount(0);
        for (Jeu j : jeux) {
            modele.addRow(new Object[]{
                    j.getTitre(), j.getAuteur(), j.getGenre(), j.getPrix(), j.getVerif() ? "Oui" : "Non"});
        }
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherInfo(String message, String titre) {
        JOptionPane.showMessageDialog(this, message, titre, JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean demanderConfirmation(String message, String titre) {
        return JOptionPane.showConfirmDialog(this, message, titre, JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    /** Formulaire de publication. @return {titre, genre, prix} (texte brut) ou null si annulé. */
    public String[] demanderNouveauJeu() {
        JTextField titre = new JTextField();
        JTextField genre = new JTextField();
        JTextField prix = new JTextField();
        JPanel formulaire = new JPanel(new GridLayout(3, 2, 6, 6));
        formulaire.add(new JLabel("Titre"));    formulaire.add(titre);
        formulaire.add(new JLabel("Genre"));    formulaire.add(genre);
        formulaire.add(new JLabel("Prix (€)")); formulaire.add(prix);

        int reponse = JOptionPane.showConfirmDialog(this, formulaire, "Publier un jeu",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (reponse != JOptionPane.OK_OPTION) return null;
        return new String[]{titre.getText(), genre.getText(), prix.getText()};
    }
}
