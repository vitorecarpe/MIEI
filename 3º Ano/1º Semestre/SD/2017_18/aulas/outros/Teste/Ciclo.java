package Teste;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 * Date: Dec 26, 2008
 * Time: 1:06:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ciclo {
    public static void main(String args[]) throws InterruptedException {
        int i=0;
        while(true){
            System.out.println(i);
            Thread.sleep(1500);
            i++;
        }
    }
}
