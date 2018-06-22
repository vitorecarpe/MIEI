package Teste;

import java.util.Vector;
import java.io.File;
import java.applet.Applet;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 */
public class aaaa {
    public static void main(String args[]){
        int a,b;
        a=2;
        b=0;

        System.out.println(g(a,new int[]{b}));

    }

    public static int g(int a, int b[]){
        b[0]= 2*a;
        return b[0];
    }

    int zz[] = new int[200];
    int a = zz.length;
   // double.ceil();

}
