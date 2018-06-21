import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;

public class Parque {
    private String nome;
    private Map <String, Lugar> lugares;

    public Parque() {
        this. nome = "N/A";
        this.lugares = new TreeMap<>();
    }
    
    public Parque (String n, Map<String, Lugar> p) {
        this.nome = n;
        this.lugares = new TreeMap<>();
        for(Map.Entry<String,Lugar> l: p.entrySet()) {
            this.lugares.put(l.getKey(), l.getValue().clone());
        }
    }
    
    public Parque (Parque p) {
        this.nome = p.getNome();
        this.lugares = p.getLugares();
    }
    
    /* GETTERS */
    
    public String getNome() {
        return this.nome;
    }
    
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Map<String,Lugar> getLugares() {
        Map<String, Lugar> newa = new TreeMap<>();
        for (Map.Entry<String, Lugar> l: this.lugares.entrySet()) {
            newa.put(l.getKey(), l.getValue().clone());
        }
        return newa;
    }
   
   public Map<String,Lugar> getLugares2() {
       Map<String,Lugar> p = new TreeMap<>();
       Iterator<Map.Entry<String,Lugar>> it = this.lugares.entrySet().iterator();
       while (it.hasNext()) {
           Map.Entry<String,Lugar> e = it.next();
           p.put(e.getKey(), e.getValue().clone());
       }
       return p;
    }
    
   public Map<String,Lugar> getLugares3() {
       Map<String,Lugar> p = new TreeMap<>();
       this.lugares.entrySet().forEach(f -> { p.put(f.getKey(), f.getValue().clone());});
       return p;
   }
   
   public Set <String> matLugaresOcupados() {
       return this.lugares.keySet();
   }
   
   public void novoLugar (Lugar l) {
       this.lugares.put(l.getMatricula(), l);
   }
   
   public void removeLugar (String matricula) {
       this.lugares.remove(matricula);
   }
   
   public void alteraTempo (String matricula, int min) {
       Lugar l = this.lugares.get(matricula);
       this.lugares.remove(matricula);
       
       l.setMinutos(min);
       novoLugar(l);
    }
    
   public int quantidadeMinutos() {
       return this.lugares.values()
                          . stream()
                          . mapToInt (l -> l.getMinutos())
                          . sum();
   }
   
   public int quantidadeMinutos2() {
       int tot = 0;
       for (Lugar l: this.lugares.values()) {
           tot += l.getMinutos();
       }
       return tot;
   }
   
   public boolean existeLugar (String matricula) {
       return this.lugares.containsKey(matricula);
   }
   
   public List<String> matriculasTempo (int x) {
       return this.lugares.values().stream()
                   .filter(l -> l.getMinutos() > x  && l.getPermanente() == true)
                   .map(Lugar :: getMatricula)
                   .collect(Collectors.toList());
   }
   
   public List<String> matriculasTempo2 (int x) {
       List<String> mats = new ArrayList<String>();
       for (Lugar l: this.lugares.values()) {
           if (l.getMinutos() > x && l.getPermanente() == true) {
               mats.add(l.getMatricula());
           }
       }
       return mats;
   }
   
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("===== Parque 1: =====\n");
       for (Lugar l: this.lugares.values()) {
           sb.append(l.toString()).append("\n");
        }
        return sb.toString();
   }
   
   public Set<Lugar> lugaresOrdenadosPorTempo()  {
       Set<Lugar> novo = new TreeSet<>(new ComparadorMinutos());
       for (Lugar l: this.lugares.values()) novo.add(l);
       return novo;
    }
       public Set<Lugar> lugaresOrdenadosPorNome()  {
       Set<Lugar> novo = new TreeSet<>(new ComparadorNome());
       for (Lugar l: this.lugares.values()) novo.add(l);
       return novo;
    }
}
