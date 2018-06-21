/**
 ** Escrever um programa que aceite n temperaturas inteiras (pelo menos duas) e determine a média
 ** das temepraturas, o dia (2,3,...) em que se registou a maior variação em valor absoluto
 ** relativamente ao dia anterior e qual o valor efectivo (positivo ou negativo) dessa variação.
 ** Os resultados devem ser apresentados sob a forma:
 ** A média das _n_ temperaturas foi de _ _ _ _ graus.
 ** A maior variação registou-se entre os dias _ _ e _ _, tendo a temperatura subido /descido _ _ _
 ** graus.
 */

import java.util.Scanner;
import java.lang.Math;
import java.lang.Integer;

public class ex11 {
    public static void main (String [] args) {
        int i,t, n,tant, max=Integer.MIN_VALUE, dia=0, sum=0, media;
        Scanner input = new Scanner (System.in);
        System.out.println("Quantas temperaturas para ser lidas? >=2");
        n = input.nextInt();
        System.out.println("Temperaturas:");
        tant = input.nextInt();
        sum=tant;
        for (i=1; i<n; i++) {
            t = input.nextInt();
            sum+=t;
            if (Math.abs(tant-t)>max) {max = Math.abs(tant-t); dia=i;}
            tant = t;
        }
        media=sum/n;
        System.out.println("A média das "+n+" temperaturas foi de "+media+" graus.");
        System.out.println("A maior variação registou-se entre os dias "+(i-1)+" e "+i+", tendo a tempetura subido/descido "+max+" graus.");
    }
}
