
/**
 * Write a description of class Faixa here.
 * 
 * @author Rui  Couto
 * @version 1.0 2016
 * @version 2017.03.30 (revisões jfc)
 * @version 2017.03.31 (revisões anr)
 */
public class Faixa implements Comparable<Faixa>
{
    private String nome;
    private String autor;
    private double duracao;
    private int classificacao;
    
    public Faixa(String nome, String autor, double duracao, int classificacao) {
        this.nome = nome;
        this.autor = autor;
        this.duracao = duracao;
        this.classificacao = classificacao;
    }

    public Faixa() {
        this("n/a", "n/a", 0, 0);
    }

    public Faixa(Faixa f) {
        this(f.getNome(), f.getAutor(), f.getDuracao(), f.getClassificacao());
    }

    public String getNome() {
        return this.nome;
    }
    
    public String getAutor() {
        return this.autor;
    }
    
    public double getDuracao() {
        return duracao;
    }
    
    public int getClassificacao() {
        return classificacao;
    }
        
    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public boolean equals(Object o) {
        if(o==this) {
            return true;
        }
        if(o==null || o.getClass() != this.getClass()) {
            return false;
        }
        Faixa f = (Faixa) o;
        return f.getNome().equals(nome) && f.getAutor().equals(autor) && 
            f.getDuracao() == duracao && f.getClassificacao() == classificacao;
    }

    public Faixa clone() {
        return new Faixa(this);
    }

    public String toString() {
        return nome+" ("+autor+"): "+ classificacao + "* ["+duracao+"seg]";
    }
    
    
    /*
     * Implementação do método de ordem natural de Faixa.
     * Esta definição ordena as faixas por ordem alfabética do seu nome (título).
     */
    
    public int compareTo(Faixa f) {
      return this.nome.compareTo(f.getNome());
        
    }    
}
