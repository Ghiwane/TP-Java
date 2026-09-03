// Classe Main contenant le programme principal main
public class Main {
    public static void main(String[] args) {
        //Déclaration des 3 variables suivantes : le nom, l'age et le tableau de notes
        String nom; // nom de l'etudiant
        int age; // age de l'etudiant
        double[] notes; // tableau des notes de l'etudiant
        int nbnotes; // nombre de notes du tableau ci-dessus

        /*
          A COMPLETER : définir et instancier un objet de la classe Etudiant sans parametre
         */
        Etudiant etudiant = new Etudiant();

        /*
            A COMPLETER : Appeler la methode getNom() de l'objet instancie ci-dessus de la classe Etudiant, pour
                          recuperer la variable nom saisie dans cette méthode pour cet objet.
         */
        nom = etudiant.getNom();

         /*
            A COMPLETER : Appeler la methode getAge() de l'objet instancie ci-dessus de la classe Etudiant, pour
                          recuperer la variable age saisie dans cette méthode pour cet objet.
         */
        age = etudiant.getAge();

         /*
            A COMPLETER : Appeler la methode getNbnotes() de l'objet instancie ci-dessus de la classe Etudiant, pour
                          recuperer la variable nbnotes saisie dans cette méthode pour cet objet.
         */
        nbnotes = etudiant.getNbnotes();

         /*
            A COMPLETER : Appeler la methode getNotes(int nbnotes) de l'objet instancie ci-dessus de la classe Etudiant,
                          pour recuperer la variable du tableau notes, dont les valeurs sont saisies dans cette méthode
                          pour cet objet, en fonction du nombre de notes nbnotes précédent en paramètre.
         */
        notes = etudiant.getNotes(nbnotes);

        /*
            A COMPLETER : Appeler la methode afficherInfos() de l'objet instancie ci-dessus de la classe Etudiant, pour
                          afficher les informations de cet objet.
         */
        etudiant.afficherInfos();

        /*
            A COMPLETER : Appeler la methode calculerMoyenne() de l'objet instancie ci-dessus de la classe Etudiant,
                          pour calculer la moyenne du tableau de notes de cet objet. Puis afficher cette moyennne.
         */
        System.out.println(etudiant.calculerMoyenne());

    }
}
