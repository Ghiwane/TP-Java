package TP3;

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

    public void accepterJeu(Jeu jeu) {
        char conf;
        if (jeu.getVerif()) {
            System.out.println("Le jeu " + jeu.getTitre() + " est déjà vérifié.");
            return;
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
}
