
/**
 * Write a description of class ContaTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.GregorianCalendar;

public class ContaTest {
    public static void main (String[] args) {
        GregorianCalendar d1 = new GregorianCalendar(2017,3,0);
        GregorianCalendar d2 = new GregorianCalendar(2017,12,20);
        Conta c = new Conta("hele","4t42wfr",d1,1000,0.05,d2);
        
        System.out.println(c.diasPassados());
    }
}
