package TP3;

public class TitreDejaUtiliseException extends Exception {
    public TitreDejaUtiliseException(String titre) {
        super("Un jeu nomme \"" + titre + "\" existe deja dans le catalogue.");
    }
}