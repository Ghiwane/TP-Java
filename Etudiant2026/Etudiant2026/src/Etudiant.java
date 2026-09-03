import java.util.Scanner;

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
            Scanner sc = new Scanner(System.in) ;
            System.out.print("Entrez le nom de l'étudiant :") ;
            this.nom = sc.nextLine();
            return this.nom;
    }

    /*
     A COMPLETER : Méthode qui saisit la valeur de l'attribut age, en verifiant dans une boucle qu'elle est comprise
                   entre 0 et 99, puis retourne cet attribut.
     */
    public int getAge() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Entrez l'âge de l'étudiant : ");
            this.age = sc.nextInt();
        } while (this.age < 0 || this.age > 25);
        return this.age;
    }

    /*
     A COMPLETER : Méthode qui saisit la valeur de l'attribut nbnotes (nombre de notes du tableau en attribut), en
                   verifiant dans une boucle qu'elle est comprise entre 1 et 4. Cette méthode retourne à la fin cet
                   attribut.
     */
    public int getNbnotes() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Entrez le nombre de notes de l'étudiant : ");
            this.nbnotes = sc.nextInt();
        } while (this.nbnotes < 1 || this.nbnotes > 4);
        return this.nbnotes;
    }

    /*
     A COMPLETER : Méthode qui instancie le tableau notes en attribut, en fonction du paramètre nbnotes (nombre de notes
                   du tableau). Puis elle saisit les valeurs de ce tableau,en verifiant dans une boucle que chaque
                   valeur du tableau est comprise entre 0 et 20. Puis elle retourne à la fin ce tableau en attribut.
     */
    public double[] getNotes(int nbnotes) {
        Scanner sc = new Scanner(System.in);
        this.notes = new double[nbnotes];
            for (int i = 0; i < nbnotes; i++) {
                do {
                    System.out.print("Entrez la note " + (i + 1) + " de l'étudiant : ");
                    this.notes[i] = sc.nextDouble();
                } while (this.notes[i] < 0 || this.notes[i] > 20);
            }
        return this.notes;
    }

    /*
         A COMPLETER : Méthode qui affiche les informations ci-dessous, en respect des instructions suivantes
     */
    public void afficherInfos() {
        /*
         Afficher le nom en attribut dans le message suivant
         */
            System.out.println("Nom : " + this.nom);
        /*
         Afficher l'age en attribut dans le message suivant
         */
            System.out.println("Age : " + this.age);

        /*
         Afficher les valeurs du tableau notes en attribut dans le message suivant
         */
            System.out.print("Notes : ");
            for (int i = 0; i < this.nbnotes; i++) {
                System.out.print(this.notes[i] + " ");
            }
            System.out.println();
    }

    /*
         A COMPLETER : Méthode qui calcule et retourne la moyenne du tableau notes en attribut
     */
    public double calculerMoyenne() {
        /*
          Définir et initialiser la somme du tableau notes en attribut a 0
         */
        double somme = 0;

        /*
          Calculer la somme du tableau notes en attribut
         */
        for (int i = 0; i < this.nbnotes; i++) {
            somme += this.notes[i];
        }

        return somme / this.nbnotes;

        /*
          Retouner la moyenne du tableau notes en attribut
         */

    }
}