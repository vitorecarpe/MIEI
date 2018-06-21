
/**
 * Write a description of class ComparadorNome here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Comparator;

public class ComparadorNome implements Comparator<Lugar> {
   public int compare (Lugar l1, Lugar l2) {
       return l1.getNome().compareTo(l2.getNome());
    }
}
