/**
 ** Sendo n dado pelo utilizador, ler n reais e calcular as suas raízes quadradas.
 */

import java.lang.Math;
import java.util.Scanner;

public class ex4 {
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        System.out.println("Quantos números?");
        int n=input.nextInt();
        while (n>0) {
            System.out.println("Número:");
            double a=input.nextDouble();
            double b=Math.sqrt(a);
            System.out.println("Raíz:"+b);
            n--;
        }
        input.close();
    }
}
