import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.stream.Collectors;
/**
 * Write a description of class Dicionario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dicionario {
    private String nomeDic;
    private Map<String, Entrada> entradas;

    /**
     * Constructor for objects of class Dicionario
     */
    public Dicionario(String nomeDic) {
        this.nomeDic = nomeDic;
        this.entradas = new HashMap<>();
    }
    
    void add(Entrada ed) throws ExistingEntryException {
        if (this.entradas.containsKey(ed.getTermo())) throw new ExistingEntryException();
        this.entradas.put(ed.getTermo(), ed);
    }
    
    boolean exists(String termo) {
        return this.entradas.containsKey(termo);
    }
    
    Entrada get(String termo) throws EntryDoesNotExistException {
        Entrada e = this.entradas.get(termo);
        if (e == null) throw new EntryDoesNotExistException();
        else return e;
    }
    
    Collection<Entrada> getAll() {
        Collection<Entrada> novo = new TreeSet<>();
        for (Entrada e : this.entradas.values()) 
            novo.add(e);
        return novo;
    }
    
    boolean sinonimos(String termo1, String termo2) {
        try {
            Entrada e1 = get(termo1);
            Entrada e2 = get(termo2);
            return e1.getDefinicao().equals(e2.getDefinicao());
        }
        catch(EntryDoesNotExistException e) {}
        return false;
    }
    
    // usando iteradores externos -> iterator
    Map<String, List<String>> getSinonimosExt() {
        Map<String, List<String>> ret = new HashMap<>();
        for (Entrada e: entradas.values()){
            List<String> aux = new ArrayList<>();
            if(ret.containsKey(e.getDefinicao())){
                aux = ret.get(e.getDefinicao());
                aux.add(e.getTermo());
                ret.replace(e.getDefinicao(),aux);
            }
            else{
                aux.add(e.getTermo());
                ret.put(e.getDefinicao(),aux);
            }
        }
        return ret;
    }
    
    //usando iteradores internos -> stream
    Map<String, List<String>> getSinonimosInt() {
        Map<String, List<String>> ret = new HashMap<>();

        this.entradas.values().stream()
            .forEach(e -> {
                List<String> aux = new ArrayList<>();
                if(ret.containsKey(e.getDefinicao())){
                    aux.clear();
                    aux = ret.get(e.getDefinicao());
                    aux.add(e.getTermo());
                    ret.replace(e.getDefinicao(),aux);
                }
                else{
                    aux.clear();
                    aux.add(e.getTermo());
                    ret.put(e.getDefinicao(),aux);
                }
            }

        );
        return ret;
    }
}
