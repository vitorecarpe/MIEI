/**
 ** Escrever um programa que determine a soma de duas datas em dias, horas, minutos, e segundos, utilizando
 ** um método auxiliar para o efeito. O método deverá aceitar duas datas e devolver uma string no formato
 ** "ddD hhH mmM ssS".
 */

import java.time.LocalDateTime;
import java.util.Scanner;

public class ex9 {
    public static void main (String[] args) {
        int dia, mes, ano, h1, m1, s1;
        Scanner input = new Scanner (System.in);
        System.out.println("Dia/Mês/Ano/Horas/Minutos (Data 1):");
        dia = input.nextInt();
        mes = input.nextInt();
        ano = input.nextInt();
        h1 = input.nextInt();
        m1 = input.nextInt();
        s1 = input.nextInt();
        LocalDateTime data1 = LocalDateTime.of(ano, mes, dia, h1, m1);
        System.out.println("Dia/Mês/Ano/Horas/Minutos (Data 2):");
        dia = input.nextInt();
        mes = input.nextInt();
        ano = input.nextInt();
        h1 = input.nextInt();
        m1 = input.nextInt();
        s1 = input.nextInt();
        LocalDateTime data1 = LocalDateTime.of(ano, mes, dia, h1, m1);
        
        
        
    }
}
