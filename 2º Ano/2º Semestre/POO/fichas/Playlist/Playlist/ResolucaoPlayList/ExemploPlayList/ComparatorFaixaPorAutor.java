
/**
 * Comparador de Faixa, tomando em consideração o campo do
 * autor.
 * 
 * @author anr 
 * @version 2017.03.30
 */
import java.util.Comparator;

public class ComparatorFaixaPorAutor implements Comparator<Faixa> {
  
  public int compare(Faixa f1, Faixa f2) {
    return f1.getAutor().compareTo(f2.getAutor());
  }    
}
