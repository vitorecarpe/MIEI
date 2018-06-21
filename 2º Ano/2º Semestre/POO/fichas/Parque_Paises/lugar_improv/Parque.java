import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Parque
{
    /**
     * Variáveis de instancia
     */
    
    private String nome;
    Map<String, Lugar> lugares;
    
    /**
     * Construtores
     */
    public Parque()
    {
        this.nome = "n/a";
        //this.lugares = new hashMap<String, Lugar>();
    }
    
    public Parque(String nome, Map<String, Lugar> lug)
    {
        this.nome = nome;
        this.lugares = new HashMap<String, Lugar>();
        
        for(Lugar l : lug.values())
        {
            lugares.put(l.getMatricula(), l.clone());
        }
    }
    
    public Parque(Parque p)
    {
       
        this.nome = p.getNome();
        this.lugares = p.getLugares();
    }
   
    /**
     * Métodos de instancia Gets e Sets
     */
    
    public String getNome()
    {
        return this.nome;
    }
    
    public Map<String, Lugar> getLugares()
    {
         Map<String, Lugar> res = new HashMap<>();
         
         res = this.lugares.values()
                           .stream()
                           .collect(Collectors.toMap((e) -> e.getMatricula(), (e)-> e.clone()));
         return res;
    }
    
    /**
     * Métodos da classe
     */
    
    public Set<String> getMatriculas()
    {
        return this.lugares.keySet();
    }
    
    public void registaLugar(Lugar l)
    {
        this.lugares.put(l.getMatricula(), l.clone());
    }
    
    public void removeLugarDadaString(String matricula)
    {
        this.lugares.remove(matricula);
    }
    
    public void removeLugarDadoLugar(Lugar l)
    {
        this.lugares.remove(l.getMatricula());
    }
    
    public void alteraTempo(String mat, int tempo)
    {
        this.lugares.get(mat).setMinutos(tempo);
      
    }
    
    /** 
     * Função que retorna o total de minutos atribuído.
     * Iterador externo
     */
    
    public int totalMinutos()
    {
        int contador = 0;
        for(Lugar l : this.lugares.values())
        {
            contador += l.getMinutos();
        }
        
        return contador;
    }
    
    /**
     * iterador interno
     */
    
    public int totalMinutosF()
    {
        return this.lugares.values()
                           .stream()
                           .mapToInt(Lugar :: getMinutos) // para conseguir somar, temos de passar um mapToInt
                           .sum();
    }
    
    public boolean existe(String mat)
    {
        return this.lugares.containsKey(mat);
    }
    
    /**
     * Método para criar uma lista com matrículas com o tempo acima de x
     * Iterador interno
     */
    
    public List<String> getMatriculasTempo(int minutos)
    {
        List<String> lista = new ArrayList<>();
        
        for(Lugar l : this.lugares.values())
        {
            if(l.getMinutos() > minutos)
            {
                lista.add(l.getMatricula());
            }
        }
        
        return lista;
    }
    
    /**
     * Iterador interno
     */
    
    public List<String> getMatriculasTempoF(int minutos)
    {
        return this.lugares.values()
                           .stream()
                           .filter(l -> l.getMinutos() > minutos)
                           .map(Lugar :: getMatricula)
                           .collect(Collectors.toList());
    }
   
    
    /**
     * Clone
     */
    
    public Parque clone()
    {
        return new Parque(this);
    }
    
    /**
     * Equals
     */
    
    public boolean equals(Object o)
    {
        boolean retorno = true;
        
        if(o==this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
            
        Parque par = (Parque) o;
        for(Lugar l : par.lugares.values())
        {
            if (!this.lugares.containsKey(l.getMatricula()) || !this.lugares.containsValue(l))
                retorno = false;
        }
        
        return retorno;
    }
    
    public TreeSet<Lugar> getLugaresPorTempo(){
        
        TreeSet<Lugar> res = new TreeSet<Lugar>(new ComparatorPorTempo());
        
        for(Lugar l: this.lugares.values()){
            res.add(l.clone());
        }
        
        return res;
        
    }
    
    public TreeSet<Lugar> getLugaresOrdenados(Comparator<Lugar> c){
        
        TreeSet<Lugar> res = new TreeSet<Lugar>(c);
        
        for(Lugar l: this.lugares.values()){
            res.add(l.clone());
        }
        
        return res;
        
    }
    
    public boolean equals(Object o){
        if(o == this) return true;
        
        if(o == null || o.getClass() != this.getClass()) return false;
        
        Parque p = (Parque) o;
        if(!this.nome.equals(p.getNome())) return false;
        //boolean r = true;
        /*
        while(it1.hasNext() && it2.hasNext() && r){
            Lugar l1 = it1.next();
            Lugar l2 = it2.next();
            
            r = l1.equals(l2);
        }
        
        return(r && (it1.hasNext() == it2.hasNext()));
        */
       return this.lugares.equals(p.getLugares());
    }
}

