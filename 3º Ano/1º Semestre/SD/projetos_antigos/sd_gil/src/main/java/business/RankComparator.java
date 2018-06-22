package business;

import java.io.Serializable;
import java.util.Comparator;

/**
 * RankComparator
 */
public class RankComparator implements Comparator<Player>, Serializable {

    /**
     * Compares the rank between two players
     * @param p1 player 1
     * @param p2 player 2
     */
    public int compare(Player p1, Player p2){
        if (p1.getRatio() > p2.getRatio()) return -1;
        if (p1.getRatio() < p2.getRatio()) return 1;
        return (p1.getUsername().compareTo(p2.getUsername()));
    }
}
