package cesium;

import java.util.*;
import java.lang.String;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Socio{
    /** Variaveis de instancia */
    private int numero;
    private String nome;
    private String curso;
    private int ano;
    private String morada;
    private int inscricao;
    private HashMap<Integer,Boolean> quotas;
    
    /** Getter do numero.*/
    public int getNumero(){
        return this.numero;
    }
    /** Getter do nome.*/
    public String getNome(){
        return this.nome;
    }
    /** Getter do curso.*/
    public String getCurso(){
        return this.curso;
    }
    /** Getter do ano.*/
    public int getAno(){
        return this.ano;
    }
    /** Getter da morada.*/
    public String getMorada(){
        return this.morada;
    }
    /** Getter do ano de inscrição.*/
    public int getInscricao(){
        return this.inscricao;
    }
    /** Getter das quotas.*/
    public HashMap<Integer,Boolean> getQuotas(){
        return this.quotas;
    }
    /** Setter do numero.*/
    public void setNumero(int numero){
        this.numero=numero;
    }
    /** Setter do nome .*/
    public void setNome(String nome){
        this.nome=nome;
    }
    /** Setter da curso.*/
    public void setCurso(String curso){
        this.curso=curso;
    }
    /** Setter do ano.*/
    public void setAno(int ano){
        this.ano=ano;
    }
    /** Setter da morada.*/
    public void setMorada(String morada){
        this.morada=morada;
    }
    /** Setter do ano de inscrição.*/
    public void setInscricao(int inscricao){
        this.inscricao=inscricao;
    }
    /** Setter das quotas.*/
    public void setQuotas(HashMap<Integer,Boolean> quotas){
        this.quotas=quotas;
    }
    
    /** Construtor que inicializa as variaveis. */
    public Socio(){
        this.numero=00000;
        this.nome="n/a";
        this.curso="n/a";
        this.ano=0;
        this.morada="n/a";
        this.inscricao=0;
        this.quotas=new HashMap<Integer,Boolean>();
    }
    /** Contrutor parameterizado que recebe a classe. */
    public Socio(Socio a){
        this.numero=a.getNumero();
        this.nome=a.getNome();
        this.curso=a.getCurso();
        this.ano=a.getAno();
        this.morada=a.getMorada();
        this.inscricao=a.getInscricao();
        this.quotas=new HashMap<Integer,Boolean>(a.getQuotas());
    }
    /** Construtor que recebe as variaveis. */
    public Socio(int numero, String nome, String curso, 
                int ano, String morada, int inscricao,
                HashMap<Integer,Boolean> quotas){
        this.numero=numero;
        this.nome=nome;
        this.curso=curso;
        this.ano=ano;
        this.morada=morada;
        this.inscricao=inscricao;
        this.quotas=quotas;
    }

    /** Como a classe é abstrata nao se faz o clone. */
    //public abstract Socio clone();
    
    /** Funcao toString. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numero: "+this.numero+"\n");
        sb.append("Nome: "+this.nome+"\n");
        sb.append("Curso: "+this.curso+"\n");
        sb.append("Ano: "+this.ano+"\n");
        sb.append("Morada: "+this.morada+"\n");
        sb.append("Ano de Inscrição: "+this.inscricao+"\n");
        sb.append("Quotas: "+this.quotas.toString());
        return sb.toString();
    }
    /** Funçao equals que verifica o ID. */
    public boolean equals(Socio a) {
        if(a.getNumero() == this.numero) {
            return true;
        }
        return false;
    }
    public Object clone() {
        Socio s = new Socio(this.numero,this.nome,
                            this.curso,this.ano,
                            this.morada,this.inscricao,
                            this.quotas);
        return s;
    }
}
