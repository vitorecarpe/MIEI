import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.*;
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
        Map<String, List<String>> novo = new HashMap<>();
        Iterator<Entrada> it = this.entradas.values().iterator();
        while (it.hasNext()) {
            Entrada e = it.next();
            String key = e.getDefinicao();
            List<String> value;
            if (novo.containsKey(key)) value = novo.get(key);
            else value = new ArrayList<>();
            value.add(e.getTermo());
            novo.put(key, value);
        }
        return novo;
    }
    
    //usando iteradores internos -> stream
    Map<String, List<String>> getSinonimosInt() {
        return this.entradas.values()
                            .stream()
                            .collect(Collectors.groupingBy(Entrada::getDefinicao,
                                                           Collectors.mapping(Entrada::getTermo,Collectors.toList())));
    }
}
