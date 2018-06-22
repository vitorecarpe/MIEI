package cesium;

import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Associacao extends Observable {
    private HashMap<Integer,Socio> associacao;
    
    public Associacao() {
        this.associacao = new HashMap<>();
    }
    
    public Socio getSocio(Integer num) {
        Socio socio = associacao.get(num);
        return socio;
    }
    public Set<Integer> getKeySet() {
        return associacao.keySet();
    }
    public void addSocio(Socio s) {
        associacao.put(s.getNumero(), s);
        setChanged();
        notifyObservers(s);
    }
    public void delSocio(Integer num) {
        associacao.remove(num);
        setChanged();
        notifyObservers(num);
    }
}
