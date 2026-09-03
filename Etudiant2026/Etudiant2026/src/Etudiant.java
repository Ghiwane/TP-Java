
// Classe Etudiant
class Etudiant {
    // Tous les attributs ci-dessous sont private et donc pas accessibles directement dans une autre classe
    private String nom; // nom de l'etudiant
    private int age; // age de l'etudiant
    private double[] notes; // tableau des notes de l'etudiant
    private int nbnotes; // nombre de notes du tableau ci-dessus

    /*
     A COMPLETER : Méthode qui saisit la valeur de l'attribut nom et retourne cet attribut.
     */
    public String getNom() {

    }

    /*
     A COMPLETER : Méthode qui saisit la valeur de l'attribut age, en verifiant dans une boucle qu'elle est comprise
                   entre 0 et 99, puis retourne cet attribut.
     */
    public int getAge() {

    }

    /*
     A COMPLETER : Méthode qui saisit la valeur de l'attribut nbnotes (nombre de notes du tableau en attribut), en
                   verifiant dans une boucle qu'elle est comprise entre 1 et 4. Cette méthode retourne à la fin cet
                   attribut.
     */
    public int getNbnotes() {

    }

    /*
     A COMPLETER : Méthode qui instancie le tableau notes en attribut, en fonction du paramètre nbnotes (nombre de notes
                   du tableau). Puis elle saisit les valeurs de ce tableau,en verifiant dans une boucle que chaque
                   valeur du tableau est comprise entre 0 et 20. Puis elle retourne à la fin ce tableau en attribut.
     */
    public double[] getNotes(int nbnotes) {

    }

    /*
         A COMPLETER : Méthode qui affiche les informations ci-dessous, en respect des instructions suivantes
     */
    public void afficherInfos() {
        /*
         Afficher le nom en attribut dans le message suivant
         */

        /*
         Afficher l'age en attribut dans le message suivant
         */

        /*
         Afficher les valeurs du tableau notes en attribut dans le message suivant
         */

    }

    /*
         A COMPLETER : Méthode qui calcule et retourne la moyenne du tableau notes en attribut
     */
    public double calculerMoyenne() {
        /*
          Définir et initialiser la somme du tableau notes en attribut a 0
         */

        /*
          Calculer la somme du tableau notes en attribut
         */

        /*
          Retouner la moyenne du tableau notes en attribut
         */

    }
}