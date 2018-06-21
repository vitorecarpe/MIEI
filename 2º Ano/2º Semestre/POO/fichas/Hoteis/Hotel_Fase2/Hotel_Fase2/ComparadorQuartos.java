import java.util.Comparator;

/**
 * Write a description of class ComparadorQuartos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComparadorQuartos implements Comparator<Hotel>
{
    public int compare(Hotel h1, Hotel h2) {
        if(h1.getNumeroQuartos() < h2.getNumeroQuartos()) {
            return 1;
        }
        if(h1.getNumeroQuartos() > h2.getNumeroQuartos()) {
            return -1;
        }
        return 0;
    }
}