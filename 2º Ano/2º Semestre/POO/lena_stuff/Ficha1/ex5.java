/**
 ** Ler uma sequência de inteiros (terminada pelo valor 0) e determinar a sua soma. Imprimir esse valor,
 ** bem como o maior e o menor valor introduzidos.
 */

import java.util.Scanner;
import java.lang.Integer;

public class ex5 {
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        int soma=0, a, min=Integer.MAX_VALUE, max=Integer.MIN_VALUE;
        System.out.println("Sequência terminada com 0:");
        do {
            a = input.nextInt();
            soma+=a;
            if (a>max && a!=0) max=a;
            if (a<min && a!=0) min=a;
        } while (a!=0);
       System.out.println("O maior valor é "+max+" e o menor é "+min+". A soma é "+soma+".");
       input.close();
    }
}
