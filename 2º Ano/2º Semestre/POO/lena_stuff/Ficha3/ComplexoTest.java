
/**
 * Write a description of class ComplexoTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComplexoTest {
    public static void main (String[] args) {
       Complexo c1 = new Complexo(10.3,20.4);
       Complexo c2 = new Complexo(15.2,15.1);
        
       Complexo a = c1.conjugado();
        
       System.out.println("Conjugado c1: "+a.getA() +" "+a.getB());
        
       Complexo c3 = c1.soma(c2);
       // Complexo c3 = c1.produto(c2);
       System.out.println("Soma:"+c3.getA()+" "+c3.getB());
    }
}
