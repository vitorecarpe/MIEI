package servidor;

import java.util.Comparator;

/**
 *
 * @author gcama
 */
public class ServerComparator 
{
   public static Comparator<Server> priceComparator = new Comparator<Server>() {         
    
    @Override
    public int compare(Server jc1, Server jc2) {             
      return (jc2.getLastBid() < jc1.getLastBid() ? 1 :                     
              (jc2.getLastBid() == jc1.getLastBid() ? 0 : -1));           
    }     
  };  
}
