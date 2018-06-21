/**
 ** Escrever um programa que leia um inteiro n e imprima todos os números primos inferiores a n. Utilize
 ** um método auxiliar para determinar se um número é ou não primo.
 */

import java.lang.Math;
import java.util.Scanner;

public class ex14 {
    public static boolean isPrime (int n) {
        double sqrt = Math.sqrt(n) + 1;
        int i;
        for (i=2; i<sqrt; i++) {
            if (n%i ==0) return false;
        }
        return true;
    }
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        int numero, i;
        System.out.println("Número:");
        numero = input.nextInt();
        System.out.println("Números primos menores que "+numero+":");
        for (i=0;i<numero;i++) {
            if (isPrime(i)==true) System.out.println(i);
        }
    }
}
