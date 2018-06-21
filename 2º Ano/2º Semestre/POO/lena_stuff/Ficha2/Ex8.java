/**
 ** Escrever um programa que simule o jogo do Euromilhões. O programa gera aleatoriamente uma chave
 ** contendo 5 números (de 1 a 50) e duas estrelas (1 a 9). Em seguida são pedidos ao utilizador
 ** 5 números e duas estrelas (a aposta).
 ** O programa deverá em seguida apresentar a chave gerada e o número de números e estrelas certos
 ** da aposta do utilizador. Naturalmente devem ser usados arrays para guardar os dados. 
 */

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Ex8 {
    private static int[] ch (int[] arr, int n) {
        Random r = new Random();
        int elem;
        for (elem=0; elem<arr.length;elem++) {
            int a = r.nextInt(n+1)+1;
            if (!Arrays.asList(arr).contains(a)) {
                arr[elem] = a;
            }
        }
        return arr;
    }
    private static int[] escreveArray (int[] arr) {
        Scanner input = new Scanner (System.in);
        int i;
        for (i=0; i<arr.length;i++) arr[i] = input.nextInt();
        input.close();
        return arr;
    }
    private static int ciguais (int[] arrp, int[] arrc) {
        int q=0, i, j;
        for (i=0; i<arrp.length; i++) {
            for (j=0; j<arrc.length; j++) {
                if (arrp[i] == arrc[j]) q++;
            }
        }
        return q;
    }
    public static void main (String[] args) {
        int[] chnum = new int[5];
        int[] chest = new int[2];
        int[] anum = new int[5];
        int[] aest = new int[2];
        
        chnum = ch(chnum, 49);
        chest = ch(chest,9);
        
        System.out.println("Quais os números da sua aposta?");
        anum = escreveArray(anum);
        System.out.println("E as estrelas?\n");
        aest = escreveArray(aest);
        System.out.println("A calcular...");
        int num = ciguais(anum, chnum);
        int est = ciguais(aest, chest);
        System.out.println("Os números são: "+Arrays.toString(chnum));
        System.out.println("As estrelas são: "+Arrays.toString(chest));
        System.out.println("Acertou em "+num+" números e "+est+" estrelas.");
        
    }
}
