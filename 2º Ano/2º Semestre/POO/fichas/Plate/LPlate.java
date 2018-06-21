import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Write a description of class LPlate here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LPlate
{

    public static void main(String[] args) {
        int tries=3;
        System.out.println("Bom dia, por favor insira uma matricula.");
        do{
            Scanner skanni = new Scanner(System.in);

            System.out.print("Matricula: ");
            String bilnumer = skanni.nextLine();

            bilnumer = bilnumer.toUpperCase();

            int lengd = bilnumer.length();
    
            if (lengd > 8)
            {
                System.out.println("Matriculas nao podem ter mais de 8 carateres");
                tries--;
                System.out.println(tries + " tentativas restantes.");
            }

    
            Matcher ma = Pattern.compile("[0-9]{2,2}-[A-Z]{2,2}-[0-9]{2,2}").matcher(bilnumer);
            Matcher mb = Pattern.compile("[0-9]{2,2}-[0-9]{2,2}-[A-Z]{2,2}").matcher(bilnumer);
            Matcher mc = Pattern.compile("[A-Z]{2,2}-[0-9]{2,2}-[0-9]{2,2}").matcher(bilnumer);
            if (ma.find() || mb.find() || mc.find()) {
                System.out.println(bilnumer + " registado!");
                tries=0;
                break;
            } else {
                System.out.println(bilnumer + " nao e uma matricula valida!");
                tries--;
                System.out.println(tries + " tentativas restantes.");
            }

        } while(tries>0);
    
    }
}
