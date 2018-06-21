import java.util.*;

/**
 * Escreva a descrição da classe HoteisInc aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class HoteisInc
{
    private String nome;
    private String codigo;
    private String localidade;
    private double precoQuarto;
    private int numeroQuartos;
    private int estrelas;
    
    private Map<String,Hotel> hoteis;
    
    public HoteisInc(){
        this.hoteis=new HashMap<>();
    }
    
    public HoteisInc(HoteisInc h){
        this.nome = h.getNome();
        this.hoteis = h.getHoteis();
    }
    
    public HoteisInc(Map<String,Hotel> hoteis){
        this.hoteis = new HashMap<>();
        for(Hotel h: hoteis.values()){
            this.hoteis.put(h.getCodigo(), h.clone());
        }
    }
    
    public Map<String,Hotel> getHoteis(){
        Map<String,Hotel> res = new HashMap<>();
        for(Hotel h: this.hoteis.values()){
            res.put(h.getCodigo(), h.clone());
        }
        return res;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public Hotel getHotel(String cod){
        for(Hotel h: this.hoteis.values()){
            if(h.getCodigo() == cod){
                return h.getCodigo();
            }
        }
    }
    
    public interface CartaoPontos{
        public void setPontos(int pontos);
        public int getPontos();
    }
    
    public List<CartaoPontos> CartaoPontos(){
        return this.hoteis.values().stream();
            .filter(h->h instanceof CartaoPontos)
            .map(Hotel::clone)
            .map(h->(CartaoPontos)h)
            .collect(Collectors.toList());
    }
    
}

  
  //fazer com iteradores internos...
  
  /*public Map<String, Lugar> getLugares(){
      HashMap<String,Lugar> res = new HashMap<>();
      for(Lugar l:this.lugares.values)){
          res.put(l.getMatricula(), l.clone());
      }
      return res;
  }*/
  