import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.TreeSet;

public class Playlist {
    private String nome;
    private Map<String, Set<Faixa>> musicas; // nome de autor -> faixas por ele interpretadas
    
    /* 2 a) */
   /* public List<Faixa> getFaixas(String autor) throws AutorInexistenteException {
        List<Faixa> faixas = this.musicas.get(autor);
        // o stor provavelmente prefere que se use o containsKey aqui antes
        if (faixas == null) throw new AutorInexistenteException("O autor não existe!");
        ArrayList<Faixa> faixasAutor = new ArrayList<Faixa>();
        for(Faixa f : faixas) faixasAutor.add(f.clone());
        return faixasAutor;
    }
    
    /* 2 b) 
    
    public double tempoTotal(String autor) throws AutorInexistenteException {
        List<Faixa> faixas = this.musicas.get(autor);
        if (faixas == null) throw new AutorInexistenteException("O autor não existe!");
        double tempoTotal = 0;
        for (Faixa f: faixas) tempoTotal += f.getDuracao();
        return tempoTotal;
    }
    */
    
    
    public Set<Faixa> todasAsFaixas() {
        Set<Faixa> todas = new TreeSet<>();
        for (Set<Faixa> faixas : this.musicas.values())
            for (Faixa f : faixas)
                todas.add(f.clone());
        return todas;
    }
    
    /* 3 b) NÃO É NECESSÁRIO FAZER CLONE POIS JÁ É FEITO EM TODASASFAIXAS */
   public Map<Integer, List<Faixa>> faixasPorClass() {
       return todasAsFaixas().stream()
                             .collect(Collectors.groupingBy(Faixa::getClassificacao));
   }
}
