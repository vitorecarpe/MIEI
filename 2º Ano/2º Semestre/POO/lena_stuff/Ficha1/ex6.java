/**
 ** Escrever um programa que calcule o factorial de um valor inteiro passado como parâmetro ao programa
 ** (e acessível através do argumento do método main (String[] args)). O factorial do número deverá
 ** ser calculado num método auxiliar.
 */

import java.lang.Integer;
import java.util.Scanner;

public class ex6 {
    public static long factorial (long n){
        if (n==1) return 1;
        else return n*factorial(n-1);
    }
    
    public static void main (String [] args){
        long valor;
        valor=Integer.parseInt(args[0]);
        System.out.println ("O factorial de "+args [0]+" é "+ factorial(valor)+ ".");
    }
}