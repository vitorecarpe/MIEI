/**
 ** Escrever um programa que determine a data e hora do sistema, calcule o factorial de 1000000, determine a
 ** hora após tal ciclo, e calcule o total de milissegundos que tal ciclo demorou a executar.
 */

import java.math.BigInteger;

public class ex7 {
    public static String factorial(int n) {
        BigInteger fact = new BigInteger("1");
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(new BigInteger(i + ""));
        }
        return fact.toString();
    }
    
    public static void main (String[] args) {
        long antes = System.currentTimeMillis();
        String f = factorial(10000000);
        long depois = System.currentTimeMillis();
        long dif;
        dif = depois-antes;
        System.out.println("Factorial:"+f);
        System.out.println("Diferença: "+dif);
    }
}
