
/**
 * Write a description of class ComparadorMinutos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Comparator;


public class ComparadorMinutos implements Comparator<Lugar> {
    public int compare (Lugar l1, Lugar l2) {
        if (l1.getMinutos() == l2.getMinutos()) return l1.getMatricula().compareTo(l2.getMatricula());
        else if (l1.getMinutos() > l2.getMinutos()) return 1;
        else return -1;
    }
}
