import java.util.Comparator;

/**
 * Write a description of class ComparadorNome here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComparadorNome implements Comparator<Hotel>
{
    public int compare(Hotel h1, Hotel h2) {
        return h1.getNome().compareTo(h2.getNome());
    }
}