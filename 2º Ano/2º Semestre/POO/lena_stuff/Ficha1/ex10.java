/**
 ** Escrever um programa que aceite n classificações (números reais) de uma UC, e indique o número de
 ** classificações em cada um dos intervalos [0,5[,[5,10[,[10,15[,[15,20].
 */

import java.util.Scanner;

public class ex10 {
    public static void main (String[] args) {
        int a, n, c1=0, c2=0, c3=0, c4=0;
        Scanner input = new Scanner (System.in);
        System.out.println("Quantas classificações?");
        n = input.nextInt();
        while (n>0) {
            a=input.nextInt();
            if (a>=0 && a<5) c1++;
            if (a>=5 && a<10) c2++;
            if (a>=10 && a<15) c3++;
            if (a>=15 && a<=20) c4++;
            n--;
        }
        System.out.println("Há "+c1+" classificações entre 0 e 5, "+c2+" classificações entre 5 e 10, "+c3+" classificações entre 10 e 15 e "+c4+" classificações entre 15 e 20.");
        input.close();
    }
}
