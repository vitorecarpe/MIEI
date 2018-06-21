import java.util.ArrayList;
import java.time.LocalDateTime;

public class Faixa implements Playable {
    private String nome;
    private String autor;
    private double duracao;
    private int classificacao;
    private ArrayList<String> letra;
    private int numeroVezesTocada;
    private LocalDateTime ultimaVez;
    
    /* 1 a) CONSTRUTOR POR CÓPIA */
    public Faixa(Faixa f) {
        this.nome = f.getNome();
        this.autor = f.getAutor();
        this.duracao = f.getDuracao();
        this.classificacao = f.getClassificacao();
        this.letra = f.getLetra();
        this.numeroVezesTocada = f.getNumeroVezesTocada();
        this.ultimaVez = this.getUltimaVez();
    }
    
    public Faixa(String nome, String autor, double duracao, int classificacao, ArrayList<String> letra, int numeroVezesTocada, LocalDateTime ultimaVez) {
        this.nome = nome;
        this.autor = autor;
        this.duracao = duracao;
        this.classificacao = classificacao;
        this.letra = letra;
        this.numeroVezesTocada = numeroVezesTocada;
        this.ultimaVez = ultimaVez;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getDuracao() {
        return this.duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public int getClassificacao() {
        return this.classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    /* NÃO FAZER CLONE DE STRINGS, QUE ISSO É IDIOTA */
    public ArrayList<String> getLetra() {
        ArrayList<String> novo = new ArrayList<String>();
        for (String str: this.letra)
            novo.add(str);
        return novo;
    }
    
    public void setLetra(ArrayList<String> letra) {
        this.letra = new ArrayList<String>();
        for (String str: letra)
            this.letra.add(str);
    }

    public int getNumeroVezesTocada() {
        return this.numeroVezesTocada;
    }

    public void setNumeroVezesTocada(int numeroVezesTocada) {
        this.numeroVezesTocada = numeroVezesTocada;
    }

    public LocalDateTime getUltimaVez() {
        return this.ultimaVez;
    }

    public void setUltimaVez(LocalDateTime ultimaVez) {
        this.ultimaVez = ultimaVez;
    }
    
    
    /* 3 c) */
    @Override
    public void play() {
        /*for (String str : this.letra) {
            System.audio.println(str);
        }*/
        this.numeroVezesTocada++;
        this.ultimaVez = LocalDateTime.now();
    }

    /* 1 b) MÉTODO EQUALS */
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || (obj.getClass() != this.getClass())) return false;
        Faixa f = (Faixa) obj;
        return this.nome.equals(f.getNome())
            && this.autor.equals(f.getAutor())
            && this.duracao == f.getDuracao()
            && this.classificacao == f.getClassificacao()
            && this.letra.equals(f.getLetra())
            && this.numeroVezesTocada == f.getNumeroVezesTocada()
            && this.ultimaVez.equals(f.getUltimaVez());
    }
    
    
    /* 1 c) ordem NATURAL (compareTo) por ordem crescente de número de vezes tocada */
    public int compareTo(Faixa f) {
        int numThis = this.numeroVezesTocada;
        int numThat = f.getNumeroVezesTocada();
        if (numThis > numThat) return 1;
        else if (numThis < numThat) return -1;
        else return 0;
    }
    
    public Faixa clone() {
        return new Faixa(this);
    }
}
