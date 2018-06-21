/**
 ** Escrever um programa que apresente ao utilizador um menu com as opções:
 ** 1 - Login
 ** 2 - Registo
 ** 3 - Informações
 ** 0 - Sair
 ** 
 ** Em seguida, o programa deverá ler um inteiro, que apenas será válido se entre 0 e 3. Deverá haver
 ** também um método auxiliar para cada opção (excepto 0), que será invocado de acordo com a opção
 ** correspondente. Cada método deverá apresentar ao utilizador, textualmente, o nome da operação a
 ** corresponde (Login, Registo, etc), ou a mensagem Opção Inválida, O programa deverá repetir a
 ** apresentação do menu até que o utilizador seleccione a opção 0.
 */

import java.util.Scanner;

public class ex15 {
    public static void op1 () {
        System.out.println("=== LOGIN ===");
    }
    public static void op2 () {
        System.out.println("=== REGISTO ===");
    }
    public static void op3 () {
        System.out.println("=== INFORMAÇÕES ===");
    }
    public static void opo () {
        System.out.println("Opção Inválida!");
    }
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        int o;
        do {
            System.out.println("1 - Login");
            System.out.println("2 - Registo");
            System.out.println("3 - Informações");
            System.out.println("0 - Sair");
            o = input.nextInt();
            if (o>4 || o<0) opo();
            if (o==1) op1();
            if (o==2) op2();
            if (o==3) op3();
        } while (o!=0);
        input.close();
    }
}
