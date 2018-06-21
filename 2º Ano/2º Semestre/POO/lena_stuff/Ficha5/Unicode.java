
/**
 * Write a description of class Unicode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Scanner;

public class Unicode {
   public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       System.out.print("Carater? ");
       char c, o;
       int code;
       
       if (input.hasNextInt()) code = input.nextInt();
       else code = (int) input.nextLine().charAt(0);
       c = (char) code;
       
       System.out.print("'"+c+"' = "+ (int) c);
       
       if (c >=48 && c <=57) System.out.println(" [dígito]");
       
       else if (c >= 65 && c <= 90) {
           System.out.println(" [maiúscula]");
           o = (char) (((int) c) + 32);
           System.out.println("minúscula: '"+o+"' = "+ (int) o);
       }
       else if (c >= 97 && c<= 122) {
           System.out.println(" [mínuscula]");
           o = (char) (((int) c) - 32);
           System.out.println("maiúscula: '"+o+"' = "+ (int) o);
       };
   }
}
