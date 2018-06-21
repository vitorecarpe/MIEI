import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;
import java.io.Serializable;

public abstract class Atores implements Serializable {
    /** Variaveis de instancia */
    private String email;
    private String nome;
    private String password;
    private String morada;
    private int nascimento;
    private ArrayList<Viagem> viagens;
    
    /** Getter do email.*/
    public String getEmail(){
        return this.email;
    }
    /** Getter do Nome.*/
    public String getNome(){
        return this.nome;
    }
    /** Getter da password. */
    public String getPassword(){
        return this.password;
    }
    /** Getter da Morada.*/
    public String getMorada(){
        return this.morada;
    }
    /** Getter da data de nascimento.*/
    public int getNascimento(){
        return this.nascimento;
    }
    /** Setter do email.*/
    public void setEmail(String email){
        this.email=email;
    }
    /** Setter do Nome .*/
    public void setNome(String nome){
        this.nome=nome;
    }
    /** Setter da password.*/
    public void setPassword(String password){
        this.password=password;
    }
    /** Setter da morada.*/
    public void setMorada(String morada){
        this.morada=morada;
    }
    /** Setter da data de nascimento.*/
    public void setNascimento(int nascimento){
        this.nascimento=nascimento;
    }
    /** Getter das ciagens.*/
    public ArrayList<Viagem> getViagens() {
        return this.viagens;
    }
    /** Setter das viagens.*/
    public void setViagens(ArrayList<Viagem> viagens) {
        this.viagens=viagens;
    }
    /** Construtor que inicializa as variaveis. */
    public Atores(){
        this.email="n/a";
        this.nome="n/a";
        this.password="n/a";
        this.morada="n/a";
        this.nascimento=00000000;
        this.viagens=new ArrayList<Viagem>();
    }
    /** Contrutor parameterizado que recebe a classe. */
    public Atores(Atores a){
        this.email=a.getEmail();
        this.nome=a.getNome();
        this.password=a.getPassword();
        this.morada=a.getMorada();
        this.nascimento=a.getNascimento();
        ArrayList<Viagem> newl = new ArrayList<Viagem>();
        for(Viagem v : a.getViagens()) newl.add(v);
        this.viagens=newl;
    }
    /** Construtor que recebe as variaveis. */
    public Atores(String email, String nome, String password, 
                String morada, int nascimento){
        this.email=email;
        this.nome=nome;
        this.password=password;
        this.morada=morada;
        this.nascimento=nascimento;
        this.viagens=new ArrayList<Viagem>();
    }
    /** Adiciona uma viagem ao array das viagens. */
    public void addViagem(Viagem v){
        this.viagens.add(v);
    }
    
    /** Como a classe é abstrata nao se faz o clone. */
    public abstract Atores clone();
    
    /** Funcao toString. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email: "+this.email+"\n");
        sb.append("Nome: "+this.nome+"\n");
        sb.append("Password: "+this.password+"\n");
        sb.append("Morada: "+this.morada+"\n");
        sb.append("Nascimento: "+this.nascimento+"\n");
        for(int i=0;i<viagens.size();i++) sb.append(viagens.get(i).toString()+"\n");
        return sb.toString();
    }
    /** Funçao equals que verifica o email. */
    public boolean equals(Atores a) {
        if(a.getEmail().equals(this.email)) {
            return true;
        }
        return false;
    }

}
