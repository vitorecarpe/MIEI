/**
 ** Escrever um programa que leia uma série de palavras para um array até ser introduzida a palavra
 ** "fim". De seguida, deve ser efectuada a leitura de um caracter. Se o caracter lido for `s', então
 ** ler duas palavras, e substituir a primeira pela segunda no array. Se o caracter lido for 'r', ler
 ** uma palavra e remover essa palavra do array. Utilizar métodos auxiliares.
 */

import java.util.Scanner;
import java.util.Arrays;
import java.lang.*;

public class ex6 {
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        String[] txt = new String[100];
        int i=0;
        char c;
        System.out.println("Qual o texto?\n");
        input.findInLine("fim");
        System.out.println("s/r");
        c = input.next().charAt(0);
    }
}
