package TP3.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/** Vue : écran de connexion. Aucune logique d'authentification ici. */
public class Fenetreconnexion extends JFrame {

    private final JComboBox<String> choixRole = new JComboBox<>(new String[]{"Développeur", "Administrateur"});
    private final JTextField champId = new JTextField(18);
    private final JPasswordField champMdp = new JPasswordField(18);
    private final JLabel messageErreur = new JLabel(" ");
    private final JButton boutonConnexion = new JButton("Se connecter");

    public Fenetreconnexion() {
        super("Plateforme de jeux - Connexion");

        JPanel formulaire = new JPanel(new GridLayout(3, 2, 8, 8));
        formulaire.add(new JLabel("Rôle"));
        formulaire.add(choixRole);
        formulaire.add(new JLabel("Pseudo ou email"));
        formulaire.add(champId);
        formulaire.add(new JLabel("Mot de passe"));
        formulaire.add(champMdp);

        JLabel titre = new JLabel("Connexion", SwingConstants.CENTER);
        titre.setFont(titre.getFont().deriveFont(Font.BOLD, 20f));

        messageErreur.setForeground(Color.RED);
        messageErreur.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel bas = new JPanel(new BorderLayout(0, 8));
        bas.add(messageErreur, BorderLayout.NORTH);
        bas.add(boutonConnexion, BorderLayout.SOUTH);

        JPanel contenu = new JPanel(new BorderLayout(0, 15));
        contenu.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        contenu.add(titre, BorderLayout.NORTH);
        contenu.add(formulaire, BorderLayout.CENTER);
        contenu.add(bas, BorderLayout.SOUTH);
        setContentPane(contenu);

        getRootPane().setDefaultButton(boutonConnexion);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    // --- Pour le contrôleur : branchement des événements ---
    public void onConnexion(ActionListener l) { boutonConnexion.addActionListener(l); }

    // --- Pour le contrôleur : lecture de la saisie ---
    public boolean estDeveloppeurSelectionne() { return choixRole.getSelectedIndex() == 0; }
    public String getIdentifiant() { return champId.getText().trim(); }
    public String getMotDePasse() { return new String(champMdp.getPassword()); }

    // --- Pour le contrôleur : affichage ---
    public void afficherErreur(String message) { messageErreur.setText(message); }
}
