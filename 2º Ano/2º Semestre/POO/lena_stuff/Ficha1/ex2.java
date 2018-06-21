/**
 ** Ler dois inteiros e escrevê-los por ordem decrescente, assim como a sua média.
 */

import java.util.Scanner;

public class ex2 {
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Número 1:");
        int a=input.nextInt();
        System.out.println("Número 2:");
        int b=input.nextInt();
        if (a>b) System.out.println(a+" "+b);
        else System.out.println(b+" "+a);
        int media=(a+b)/2;
        System.out.println("Média:" + media);
        input.close();
    }
}
