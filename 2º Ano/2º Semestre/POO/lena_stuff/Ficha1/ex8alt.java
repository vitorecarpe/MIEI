
/**
 * Write a description of class ex8 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Scanner;
import java.time.LocalDate;

public class ex8alt
{
    public static void main (String[] args) {
        LocalDate data;
        Scanner input = new Scanner (System.in);
        int dia, mes, ano;
        System.out.println("Dia:");
        dia=input.nextInt();
        System.out.println("Mês:");
        mes=input.nextInt();
        System.out.println("Ano:");
        ano=input.nextInt();
        data=LocalDate.of(ano,mes,dia);
        System.out.println("Day: "+ data.getDayOfWeek());
    }
}
