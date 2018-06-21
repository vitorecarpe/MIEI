
/**
 * Write a description of class ex8 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Scanner;

public class ex8
{
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        int dia, mes, ano;
        System.out.println("Dia:");
        dia = input.nextInt();
        System.out.println("Mês:");
        mes = input.nextInt();
        System.out.println("Ano:");
        ano = input.nextInt();
        
        int e=(ano-1900)*365;
        e+=(ano-1900)/4;
    }
}
