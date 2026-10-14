package TP3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Utilisateur implements IConnection {
    private String pseudo, email, mdp;

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

    // Règles d'authentification (avant : boucles Scanner dans seConnecter)
    public boolean identifiantValide(String id) {
        return Objects.equals(id, getPseudo()) || Objects.equals(id, getEmail());
    }

    public boolean mdpValide(String mdpSaisi) {
        return Objects.equals(mdpSaisi, getMdp());
    }

    @Override
    public void seConnecter(Compte c) { c.setConnected(true); }

    @Override
    public void seDeconnecter(Compte c) { c.setConnected(false); }

    /** Règle de visibilité d'un jeu : par défaut (admin) tout est visible. */
    public boolean peutVoir(Jeu j) { return true; }

    /** Recherche par titre (insensible à la casse), limitée aux jeux que l'utilisateur peut voir. */
    public List<Jeu> rechercherJeu(CatalogueModel catalogue, String filtre) {
        String f = filtre.trim().toLowerCase();
        List<Jeu> resultat = new ArrayList<>();
        for (Jeu j : catalogue.getJeux())
            if (j.getTitre().toLowerCase().contains(f) && peutVoir(j))
                resultat.add(j);
        return resultat;
    }
}
