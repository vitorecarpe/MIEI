/**
 ** Ler 10 inteiros e determinar a quantidade de números introduzidos com valor superior a 5.
 */

import java.util.Scanner;

public class ex3 {
    public static void main (String[] args) {
        int ten=10, q=0;
        Scanner input = new Scanner (System.in);
        System.out.println("Introduza 10 números:");
        while (ten>0) {
            int a=input.nextInt();
            if (a>5) q++;
            ten--;
        }
        System.out.println("Há "+q+" números maiores que 5");
        input.close();
    }
}
