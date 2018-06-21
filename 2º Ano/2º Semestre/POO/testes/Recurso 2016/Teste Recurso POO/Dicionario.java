import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Write a description of class Dicionario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dicionario
{
    private String nomeDic;
    private Map<String, Entrada> entradas;
    
    public Dicionario(String nomeDic){
        this.nomeDic = nomeDic;
        this.entradas = new HashMap<String, Entrada>();
    }
        
    public void add(Entrada ed){
        if(!this.entradas.containsKey(ed.getTermo())){
            this.entradas.put(ed.getTermo(),ed);
        }
    }
    
    public boolean exists(String termo){
        return this.entradas.containsKey(termo);
    }
    
    public Entrada get(String termo){
        return this.entradas.get(termo);
    }
    
    public Collection<Entrada> getAll(){
        return this.entradas.values();
    }
    
    public boolean sinonimos(String termo1, String termo2){
        return this.get(termo1).getDefinicao() == this.get(termo2).getDefinicao();
    }
    
    Map<String, List<String>> getSinonimosExterno(){
        Map<String, List<String>> sinonimos = new HashMap<String, List<String>>();
        Iterator iter = sinonimos.values().iterator();
        while(iter.hasNext()){
            Entrada e = (Entrada) iter.next();
            if(!sinonimos.containsKey(e.getDefinicao())){
                List<String> termos = new ArrayList<String>();
                termos.add(e.getTermo());
                sinonimos.put(e.getDefinicao(),termos);
            }
            else{
                sinonimos.get(e.getDefinicao()).add(e.getTermo());
            }
        }
        
        return sinonimos;
    }
    
    
    /*
    Map<String, List<String>> getSinonimosInterno(){      
        Map<String, List<String>> sinonimos = this.values().toMap(
    }
    */
}
