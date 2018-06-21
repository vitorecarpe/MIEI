/**
 ** Ler um nome (String) e um saldo (decimal) e imprimir um texto com os resultados.
 */

import java.util.Scanner;

public class ex1 {
   public static void main (String[] args) {
       float s;
       Scanner input = new Scanner (System.in);
       String nome;
       System.out.println("Nome:");
       nome=input.nextLine();
       System.out.println("Saldo:");
       s=input.nextFloat();
       System.out.println("O nome é "+nome+" e o saldo é "+s+".");
       input.close();
   }
}
