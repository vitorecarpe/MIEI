
/**
 * Write a description of class VeiculoTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VeiculoTest {
    public static void main (String[] args) {
        Veiculo v1 = new Veiculo ("45-34-pl",3000,300,60,3000,1000);
        
        v1.registarViagem(300,100);
       
        System.out.println(v1.getMat());
        System.out.println(v1.getqT());
        System.out.println(v1.getqP());
        System.out.println(v1.getcap());
        System.out.println(v1.getcM());
        System.out.println(v1.getcont());
    }
}
