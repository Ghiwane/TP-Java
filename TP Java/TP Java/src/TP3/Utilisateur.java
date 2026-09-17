package TP3;

import java.util.Objects;
import java.util.Scanner;

public class Utilisateur {
    private String pseudo, email, mdp;

    Scanner scanner = new Scanner(System.in);

    public Utilisateur() {
        this("null", "null", "null");
    }

    public Utilisateur(String pseudo, String email, String mdp) {
        this.pseudo = pseudo; this.email = email; this.mdp = mdp;
    }

    public String getPseudo() { return pseudo; }
    public String getEmail() { return email; }
    public String getMdp() { return mdp; }

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public void setEmail(String email) { this.email = email; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    public void seConnecter(Compte c) {
        String idIn, mdpIn;
        // demande le pseudo ou l'email pour s'identifier
        do {
            System.out.print("Saisir pseudo/email : ");
            idIn = scanner.nextLine();
            if (!Objects.equals(idIn, getPseudo()) && !Objects.equals(idIn, getEmail()))
                System.out.println("Le pseudo ou l'email est incorrect.");
        } while (!Objects.equals(idIn, getPseudo()) && !Objects.equals(idIn, getEmail()));
        // demande le mdp
        do {
            System.out.print("Saisir mot de passe : ");
            mdpIn = scanner.nextLine();
            if (!Objects.equals(mdpIn, getMdp())) System.out.println("Le mot de passe est incorrect.");
        } while (!Objects.equals(mdpIn, getMdp()));
        c.setConnected(true);
        System.out.println("Vous êtes bien connecté au compte " + getPseudo() + ".");
    }

    public void seDeconnecter(Compte c) {
        c.setConnected(false);
        System.out.println("Vous êtes bien déconnecté du compte " + getPseudo() + ".");
    }

    public void rechercherJeu() {
        String str;
        do {
            System.out.print("Saisir recherche : ");
            str = scanner.nextLine();
            if (Objects.equals(str, "")) System.out.println("Veuillez saisir une recherche.");
        } while (Objects.equals(str, ""));
    }


}
