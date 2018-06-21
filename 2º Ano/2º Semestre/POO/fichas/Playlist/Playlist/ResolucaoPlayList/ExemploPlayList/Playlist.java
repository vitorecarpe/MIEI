import java.util.*;
import java.util.stream.Collectors;

/**
 * Playlist. Classe que representa uma playlist, 
 * contendo uma lista de Faixas.
 * 
 * @author Rui  Couto
 * @version 1.0 2016
 * 
 * @author anr
 * @version 2017.03.28 (revisões)
 * @version 2017.03.30 (revisões jfc)
 * @version 2017.03.31 (revisões anr)
 */
public class Playlist
{
    /** Lista de faixas da playlist */
    private List<Faixa> faixas;
    
    //construtores
    
    /**
     * Construtor vazio. Inicializa a playlist vazia.
     */
    public Playlist() {
        this.faixas = new ArrayList<Faixa>();
    }
    
    /**
     * Construtor por parametro. Cria uma playlist, dando uma lista 
     * de faixas.
     */
    public Playlist(ArrayList<Faixa> faixas) {
        this();
        this.setFaixas(faixas);
    }
    
    /**
     * Construtor por cópia. Cria uma playlist, a partir de outra 
     * instância de playlist.
     */
    public Playlist(Playlist faixas) {
        this.faixas = faixas.getFaixas();
    }
    
    //getter, setter
    
    /**
     * Devolve uma cópia da lista de músicas da playlist.
     */
    public ArrayList<Faixa> getFaixas() {
        /* Versão com iterador externo
         * 
        List<Faixa> res = new ArrayList<Faixa>();
        for(Faixa m : this.faixas) {
            res.add(m.clone());
        }
        return res;
        */
        /* Versão funcional, com iteradores internos
         * Não se pode usar Collectors.toList() pois o resultado é um ArrayList
         *
         */
        return this.faixas.stream()
                          .map(Faixa::clone)
                          .collect(Collectors.toCollection(ArrayList::new));
    }
    
    
    /**
     * Define um novo valor para a lista de músicas.
     */
    public void setFaixas(ArrayList<Faixa> faixas) {
        /*
        // Opção 0 - não faz clone - não garante encapsulamento
        this.faixas = new ArrayList<Faixa>(faixas);
        */
       
        /*
        // Opção 1 
        this.faixas.clear();
        for(Faixa f : faixas) {
            this.faixas.add(f.clone());
        }
        */
       
        // Opção 2 - versão funcional
        this.faixas = faixas.stream()
                      .map(Faixa::clone)
                      .collect(Collectors.toList());
    }
    
    
    public boolean equals(Object o) {
        if(o==this) {
            return true;
        }
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Playlist p = (Playlist) o;
        
        /*
         * A solução mais confortável é delegar a igualdade dos arraylist, isto é, dos seus elementos, 
         * no método equals da classe ArrayList.
         * Como a classe Faixa tem o equals implementado (todas as classes tem de o ter codificado!)
         * funcionará bem. Aconselha-se a consultar a API de ArrayList para ver a descrição de como 
         * funciona o método equals. Atenção que o método equals é herdado (inherited from) da classe 
         * AbstractList.
         */
        return p.getFaixas().equals(faixas);
    }    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist:\n");
        for(Faixa f : faixas) {
            sb.append(f.toString()).append("\n");
        }
        return sb.toString();
    }
    
    public Playlist clone() {
        return new Playlist(this);
    }
    
    
    //1. Numero de faixas
      
    /**
     * Contagem do numero de faixas na playlist.
     * 
     * Exemplo de implementação incorrecta
     * (por desconhecimento da API!!)
     *
     * public int numFaixasErrado() {
     *     int i = 0;
     *     for(Faixa f : faixas) {
     *         i++;
     *      }
     *      return i;
     *  }
     * 
     */
    
    public int numFaixas() {
        return faixas.size();
    }
    
    //2. Adicionar faixa
    
    /* 
     * Adicionar uma faixa à playlist
     * 
     * Exemplo de implementação incorrecta
     * (por desconhecimento da API, uma vez que não é assim que se deve fazer)
     * public void addFaixaErrado1(Faixa f) {
     *     int i = 0;
     *     Iterator<Faixa> it = faixas.iterator();
     *     while(it.hasNext()) {
     *         it.next();
     *         i++;
     *      }
     *      faixas.add(i,f); //falta clone!
     *  }
     *  
     *  public void addFaixaErrado2(Faixa m) {
     *      int i = numFaixas();
     *      faixas.add(i,m.clone());
     *  }
     */
    
    public void addFaixa(Faixa f) {
        faixas.add(f.clone());
    }
    
    //3. removerFaixa
    
    /*
     * Remover uma faixa da playlist
     * 
     * Versão incorrecta, por desconhecimento da API de ArrayList.
     * 
     * public void removeFaixaErrado(Faixa m) {
     *     if(faixas.contains(m)) {
     *         int index = 0;
     *         Iterator<Faixa> it = faixas.iterator();
     *         while(it.hasNext() && !it.next().equals(m)) {
     *             index++;
     *          }
     *          faixas.remove(index);
     *      }   
     *  }
     */
     
    
    public void removeFaixa(Faixa m) {
        faixas.remove(m);
    }
    
    //4. Adicionar uma lista de faixas
    
    /**
     * Adicionar um conjunto de faixas à playlist
     */
    public void adicionar(List<Faixa> faixas) {
        for(Faixa p : faixas) {
            if(!this.faixas.contains(p)) {
                this.faixas.add(p.clone());
            }
        }
    }
    
    public void adicionarF(List<Faixa> faixas) {
        faixas.forEach(f -> {
            this.faixas.add(f.clone());
        });
    }
    
    //5. Classificação superior à faixa fornecida
    
    public int classificacaoSuperior(Faixa f) {
        int c = 0;
        for(Faixa t : this.faixas) {
            if(t.getClassificacao()>f.getClassificacao()) {
                c++;
            }
        }
        return c;
    }
    
    public int classificacaoSuperiorF(Faixa f) {
        return (int) faixas.stream()
                     .filter(t -> t.getClassificacao() > f.getClassificacao())
                     .count();
    }
    
    //6. Existe faixa com duracao superior ao valor fornecido
    
    public boolean duracaoSuperior(double d) {
        boolean r = false;
        Iterator<Faixa> it = faixas.iterator();
        while(it.hasNext() && !r) {
            Faixa t = it.next();
            if(t.getDuracao()>d) {
                r = true;
            }
        }
        return r;
    }

    public boolean duracaoSuperiorF(double d) {
        return faixas.stream().anyMatch(f -> f.getDuracao()>d);
    }
    
    
    //7. Devolver copia da playlist, mudando a classificação
    
    public List<Faixa> getCopiaFaixas(int n) {
        List<Faixa> l = new ArrayList<Faixa>();
        for(Faixa f : this.faixas) {
            l.add(new Faixa(f.getNome(),f.getAutor(),f.getDuracao(),n));
        }
        return l;
    }
    
    public List<Faixa> getCopiaFaixasF(int n) {
        return faixas.stream()
            .map(f -> new Faixa(f.getNome(),f.getAutor(),f.getDuracao(),n))
            .collect(Collectors.toList());
         
        /* a versão em que teríamos 
         *    ...
         *    .map(f -> f.setClassificacao(n))
         *    ...
         *    
         *    teria como resultado uma stream cheia de "void", uma vez que esse é o tipo de dados 
         *    de retorno do setClassificacao
         */    
            
            
        /*
         * Uma outra solução recorrendo a peek e ao setClassificação.
         * 
         * return faixas.stream()
         *              .map(Faixa::clone)
         *              .peek(f -> f.setClassificacao(n))
         *              .collect(Collectors.toList()); 
         */
            
    }
    
    
    //8. Devolver duracao total

    public double duracaoTotal() {
        double t = 0;
        for(Faixa f : faixas) {
            t += f.getDuracao();
        }
        return t;
    }
    
    
    /*
     * mapToDouble devolve uma stream em que os elementos são do tipo Double.
     * Esse é o tipo de dados devolvido pelo método getDuracao.
     * 
     */
    
    public double duracaoTotalF() {
        return faixas.stream().mapToDouble(Faixa::getDuracao).sum();
    }
    
    
    //9. Remover faixas de determinado autor
    
    public void removeFaixas(String autor) {
        Iterator<Faixa> it = faixas.iterator();
        while(it.hasNext()) {
            Faixa f = it.next();
            if(f.getAutor().equals(autor)) {
                it.remove();
            }
        }
    }
    
    public void removeFaixasF(String autor) {
        this.faixas.removeIf(f-> f.getAutor().equals(autor));
    }
    
    
    
    /*
     * Método que devolve um map que associa a cada valor de classificação a
     * respectiva lista de faixas
     * 
     */
    
    
    // versão com iteradores externos
    
    public Map<Integer,List<Faixa>> faixasPorClass() {
        Map<Integer, List<Faixa>> res = new HashMap<>();
        
        for(Faixa f: this.faixas) {
          int clf = f.getClassificacao();
          if(!res.containsKey(clf))
            //se não existe cria a entrada, inicializando o arraylist
            res.put(clf,new ArrayList<>());
          res.get(clf).add(f.clone());
        }
        return res;          
        
    }    
    // versão com iteradores internos
    
    public Map<Integer,List<Faixa>> faixasPorClass_F() {
      
      return this.faixas.stream()
                        .collect(Collectors.groupingBy(Faixa::getClassificacao,
                                                       Collectors.mapping(Faixa::clone, Collectors.toList())));                                                    
    }
    
    /*
     * Método que devolve as faixas ordenadas por ordem alfabética de nome da faixa.
     * Para tal usará o ordem natural, definida pela implementação de compareTo, e devolverá
     * um Set<Faixa>
     * 
     */
    
    
    public Set<Faixa> faixasPorOrdemTitulo() {
       
      return this.faixas.stream().map(Faixa::clone).collect(Collectors.toSet());
    }    
    
    
    /*
     * Método que devolve as faixas ordenadas por ordem alfabética de nome do autor.
     * Para tal usará o ordem definida pelo comparador ComparatorFaixaPorAutor, e devolverá
     * um Set<Faixa>
     * 
     */
    
    
    public Set<Faixa> faixasPorOrdemAutor() {
       
      return this.faixas.stream().map(Faixa::clone).
             collect(Collectors.toCollection(() -> new TreeSet<Faixa>(new ComparatorFaixaPorAutor())));
    }    
    
    
    
    
}
