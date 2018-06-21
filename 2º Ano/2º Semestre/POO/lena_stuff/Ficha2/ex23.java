/** 
 ** Escrever um programa que faça a leitura de N valores inteiros para um array e determine qual o
 ** menor valor introduzido, e qual as sua posição no array.
 ** O programa deverá estruturado recorrendo a dois métodos auxiliares:
 ** - private static int[] lerArrayInt(int n)
 ** para ler um array de N inteiros;
 ** - private static int minPos(int[] arr)
 ** para determinar a posição do mínimo de um array de inteiros
 ** Altere o método lerArrayInt por forma a que, durante a leitura e inserção, o array se mantenha
 ** sempre ordenado por ordem decrescente.
*/

import java.util.Scanner;
import java.lang.Integer;
import java.util.Arrays;

public class ex23 {
    private static void inserir (int n, int[] arr, int i) {
        for (i--;i>=0 &&arr[i]<n; i--) {
           arr[i+1] = arr[i];
        }
        arr[i+1]=n;
    }
    private static int[] lerArrayInt(int n) {
        Scanner input = new Scanner (System.in);
        int[] a= new int[n];
        int i,p;
        for (i=0; i<n; i++) {
            inserir (input.nextInt(),a,i);
        }
        input.close();
        return a;
    }
    /*
     * Função dispensável, porque o menor elemento estará sempre na última posição.
     */
    private static int minPos(int[] arr) {
        int min=Integer.MAX_VALUE, pos=0,i;
        for (i=0; i< arr.length; i++) {
            if (arr[i]<min) {min=arr[i]; pos=i;}
        }
        return pos;
    }
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        int n, pos;
        System.out.println("Quantos elementos no array?");
        n = input.nextInt();
        System.out.println("Quais são os elementos?");
        int[] a = lerArrayInt(n);
        pos = minPos (a);
        System.out.println (Arrays.toString (a)) ;
        System.out.println("O valor menor é o "+a[pos]+" na posição "+pos+".");
        input.close();
    }
}
