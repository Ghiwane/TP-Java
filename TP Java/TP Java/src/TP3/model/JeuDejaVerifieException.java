package TP3.model;

public class JeuDejaVerifieException extends Exception {
    public JeuDejaVerifieException(String titre) {
        super("Le jeu " + titre + " est deja verifie.");
    }
}
