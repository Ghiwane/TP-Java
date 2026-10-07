package TP3;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre 1 : écran de connexion.
 * Remplace la saisie console de Utilisateur.seConnecter() par un formulaire graphique.
 */
public class Fenetreconnexion extends JFrame {

    // Les profils et comptes sont fournis par le programme principal
    private final Dev dev;
    private final Compte compteDev;
    private final Admin admin;
    private final Compte compteAdmin;

    // Composants du formulaire
    private final JComboBox<String> choixRole = new JComboBox<>(new String[]{"Développeur", "Administrateur"});
    private final JTextField champId = new JTextField(18);
    private final JPasswordField champMdp = new JPasswordField(18);
    private final JLabel messageErreur = new JLabel(" ");

    public Fenetreconnexion(Dev dev, Compte compteDev, Admin admin, Compte compteAdmin) {
        super("Plateforme de jeux - Connexion");
        this.dev = dev;
        this.compteDev = compteDev;
        this.admin = admin;
        this.compteAdmin = compteAdmin;

        // Panneau central : formulaire en grille (étiquette | champ)
        JPanel formulaire = new JPanel(new GridLayout(3, 2, 8, 8));
        formulaire.add(new JLabel("Rôle"));
        formulaire.add(choixRole);
        formulaire.add(new JLabel("Pseudo ou email"));
        formulaire.add(champId);
        formulaire.add(new JLabel("Mot de passe"));
        formulaire.add(champMdp);

        // Titre en haut
        JLabel titre = new JLabel("Connexion", SwingConstants.CENTER);
        titre.setFont(titre.getFont().deriveFont(Font.BOLD, 20f));

        // Bas : bouton + message d'erreur
        JButton boutonConnexion = new JButton("Se connecter");
        messageErreur.setForeground(Color.RED);
        messageErreur.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel bas = new JPanel(new BorderLayout(0, 8));
        bas.add(messageErreur, BorderLayout.NORTH);
        bas.add(boutonConnexion, BorderLayout.SOUTH);

        // Assemblage dans la fenêtre (BorderLayout)
        JPanel contenu = new JPanel(new BorderLayout(0, 15));
        contenu.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        contenu.add(titre, BorderLayout.NORTH);
        contenu.add(formulaire, BorderLayout.CENTER);
        contenu.add(bas, BorderLayout.SOUTH);
        setContentPane(contenu);

        // Clic sur le bouton (ou touche Entrée) : on tente la connexion
        boutonConnexion.addActionListener(e -> tenterConnexion());
        getRootPane().setDefaultButton(boutonConnexion);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // centre la fenêtre à l'écran
        setVisible(true);
    }

    /** Vérifie les identifiants, puis ouvre la fenêtre du catalogue si tout est correct. */
    private void tenterConnexion() {
        boolean estDev = choixRole.getSelectedIndex() == 0;
        Utilisateur utilisateur = estDev ? dev : admin;
        Compte compte = estDev ? compteDev : compteAdmin;

        String id = champId.getText().trim();
        String mdp = new String(champMdp.getPassword());

        // Même logique que Utilisateur.seConnecter(), sans passer par la console
        boolean idOk = id.equals(utilisateur.getPseudo()) || id.equals(utilisateur.getEmail());
        if (!idOk) {
            messageErreur.setText("Le pseudo ou l'email est incorrect.");
            return;
        }
        if (!mdp.equals(utilisateur.getMdp())) {
            messageErreur.setText("Le mot de passe est incorrect.");
            return;
        }
        if (!compte.getActif()) {
            messageErreur.setText("Ce compte est restreint.");
            return;
        }

        compte.setConnected(true);
        dispose(); // ferme la fenêtre de connexion
        // Ouvre la fenêtre 2 ; au retour (déconnexion), on rouvre cette fenêtre
        new FenetreCatalogue(utilisateur, compte,
                () -> new Fenetreconnexion(dev, compteDev, admin, compteAdmin));
    }
}