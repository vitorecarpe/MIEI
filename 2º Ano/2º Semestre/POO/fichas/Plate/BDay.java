import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Write a description of class LPlate here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BDay
{

    public static void main(String[] args) {
        int tries=3;
        System.out.println("Bom dia, por favor insira a sua data de nascimento.");
        do{
            Scanner skanni = new Scanner(System.in);
            System.out.print("Data nascimento (AAAA/MM/DD): ");
            String bilnumer = skanni.nextLine();

            int lengd = bilnumer.length();
    
            if (lengd > 10)
            {
                System.out.println("Datas de nascimento nao podem ter mais de 10 carateres");
                tries--;
                System.out.println(tries + " tentativas restantes.");
            }

    
            Matcher m = Pattern.compile("[1-2]{1,1}[0-9]{3,3}/[0-9]{2,2}/[0-9]{2,2}").matcher(bilnumer);
            if (m.find()) {
                System.out.println(bilnumer + " registado!");
                tries=0;
                break;
            } else {
                System.out.println(bilnumer + " nao e uma data de nascimento valida!");
                tries--;
                System.out.println(tries + " tentativas restantes.");
            }

        } while(tries>0);
       
    
    }
}