/**
 ** Escrever um programa que gera dois números aleatórios entre 1 e 100. O programa dará 5 tentativas ao
 ** utilizador para acertar em um dos dois números gerados. A cada tentativa do utilizador, o programa
 ** indicará a distância do número mais próximo. No fim do jogo, o utilizador deverá ter a possibilidade
 ** de jogar novamente.
 */

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class ex16 {
    public static void main (String[] args) {
        String c = "y";
        int n1, n2, i=0, t=0;
        Scanner input = new Scanner (System.in);
        Random r = new Random();
        while (c=="y") {
            n1 = r.nextInt(100 + 1) + 1;
            n2 = r.nextInt(100 + 1) + 1;
            System.out.println("5 tentativas para adivinhar o número:");
            for (i=0; i<5; i++) {
                System.out.println((i+1)+"ª tentativa");
                t = input.nextInt();
                if (t==n1 || t==n2) {System.out.println("Parabéns, acertou!"); break;}
                if (Math.abs(n1-t) > Math.abs(n2-t)) System.out.println("Diferença de "+Math.abs(n2-t)+".");
                else System.out.println("Diferença de "+Math.abs(n1-t)+".");
            }
            if (t!=n1 && t!=n2) System.out.println("Esgotaram-se as tentivas!");
            System.out.println("Do you want to keep playing? y/n");
            c=input.next();
        }
    }
}
