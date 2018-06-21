/**
 * Write a description of class HoteisInc here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HoteisInc {
    private String nome;
    Map <String, Hotel> hoteis;

    /* CONSTRUTORES */
    public HoteisInc() {
        this.nome = "N/A";
        this.hoteis = new HashMap <String, Hotel>();
    }
    
    public HoteisInc(String nome, Map <String, Hotel> hoteis) {
        this.nome = nome;
        this.hoteis = new HashMap <String, Hotel>();
        for (Hotel h: hoteis.values()) {
            this.hoteis.put(h.getCodigo(), h.clone());
        }
        
        /*
         * this.hoteis = hoteis.entrySet()
         *              .stream()
         *              .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone());
         */
    }
    
    public HoteisInc(HoteisInc cadeia) {
        this.nome = cadeia.getNome();
        this.hoteis = cadeia.getHoteis();
    }
    
    /* GETTERS E SETTERS */
    public String getNome() {
        return this.nome;
    }
    
    public Map<String, Hotel> getHoteis() {
        Map<String, Hotel> novo = new HashMap<>();
        for (Hotel h: this.hoteis.values()) {
            novo.put(h.getCodigo(), h.clone());
        }
        return novo;
    }

    /* OUTROS MÉTODOS */
    public boolean existeHotel(String cod) {
        return this.hoteis.containsKey(cod);
    }
    
    public int quantos() {
        return this.hoteis.size();
    }
    
    public int quantos(String loc) {
        return (int) this.hoteis.values()
                                .stream()
                                .filter(h -> h.getLocalidade().equals(loc))
                                .count();
    }
    
    public Hotel getHotel(String cod) {
        return this.hoteis.get(cod).clone();
    }
    
    public void adiciona(Hotel h) {
        this.hoteis.put(h.getCodigo(),h.clone());
    }
    
    public int contHoteisStandard() {
        int t = 0;
        for (Hotel h: this. hoteis.values()) if (h instanceof HotelStandard) t++;
        return t;
    }
    
    public List<Hotel> getHoteisAsList() {
        return this.hoteis.values()
                          .stream()
                          .map(Hotel::clone)
                          .collect(Collectors.toList());
    }
    
    public void adiciona(Set<Hotel> hs) {
        for (Hotel h: hs) this.adiciona(h);
    }
}
