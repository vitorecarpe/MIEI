/**
 ** Escrever um programa que leia sucessivas vezes a base e altura de um triângulo retângulo (valores
 ** reais) e calcule a área e o perímetro respectivos. Usar printf() para apresentar os resultados
 ** com uma precisão de 5 casas decimais. O programa apenas deverá terminar com a leitura de uma
 ** base = 0.0.
 */

import java.util.Scanner;
import java.lang.Math;

public class ex12 {
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in);
        double base, altura;
        do {
            System.out.println("Base:");
            base = input.nextDouble();
            System.out.println("Altura:");
            altura = input.nextDouble();
            System.out.printf("ÁREA: %.5f%n", (altura*base)/2);
            System.out.printf("PERÍMETRO: %.5f%n", altura+base+Math.sqrt(Math.pow(altura,2)+Math.pow(base,2)));
        } while (base!=0);
    }
}
