/**
 ** Declarar, inicializar e imprimir os elementos de um array de inteiros.
 */

public class ex1 {
    public static void main (String[] args) {
        int [] lista = {12 , 2 , 45 , 66 , 7 , 23 , 99};
        System.out.println ( " --- ELEMENTOS DO ARRAY ---" ) ;
        for (int i = 0; i < lista . length ; i ++) {
            System.out.println ("Elemento " + i + " = " + lista [i]) ;
        }
        System.out.println ( " --------------------------" ) ;
    }
}