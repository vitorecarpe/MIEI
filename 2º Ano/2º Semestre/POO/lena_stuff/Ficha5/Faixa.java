
/**
 * Write a description of class Ponto2D here.
 * 
 * @author Rui  Couto
 * @version 1.0 2016
 */
public class Faixa
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
        this.nome = "n/a";
        this.autor = "n/a";
        this.duracao = 0;
        this.classificacao = 0;
    }

    public Faixa(Faixa f) {
        this.nome = f.getNome();
        this.autor = f.getAutor();
        this.duracao = f.getDuracao();
        this.classificacao = f.getClassificacao();
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
        return nome+" ("+autor+"): "+ classificacao + "* ["+duracao+"m]";
    }
}
