import java.util.ArrayList;
import java.io.Serializable;

public class Motorista extends Atores implements Serializable {
    /** grau cumprimento do horario (0-100) */
    private int grau;
    /** classificaçao do motorista dado pela cliente */
    private int classificacao;
    /** numero de kms realizados pelo motorista */
    private double nKms; 
    /** True se esta disponivel, False se nao */
    private boolean disponivel; 
    /** Quantidade de desvios no valor faturado */
    private int desvios;
    
    /** Getter do Grau. */
    public int getGrau() {
        return this.grau;
    }
    /** Getter da classificacao. */
    public int getClassificacao() {
        return this.classificacao;
    }
    /** Getter do numero de kms realizados pelo motorista. */
    public double getNKMS() {
        return this.nKms;
    }
    /** Getter da disponibilidade do motorista. */
    public boolean getDisponivel() {
        return this.disponivel;
    }
    /** Getter dos desvios de preço. */
    public int getDesvios(){
        return this.desvios;
    }
    /** Setter doGrau. */
    public void setGrau(int grau) {
        this.grau=grau;
    }
    /** Setter da classificacao. */
    public void setClassificacao(int classificacao) {
        this.classificacao=classificacao;
    }
    /** Setter do numero de kms realizados pelo motorista. */
    public void setNKMS(double nKms) {
        this.nKms=nKms;
    }
    /** Setter da disponibilidade do motorista. */
    public void setDisponivel(boolean disponivel) {
        this.disponivel=disponivel;
    }
    /** Setter dos desvios de preço. */
    public void setDesvios(int desvios) {
        this.desvios=desvios;
    }
    /** Construtor padrao que inicializa as variaveis */
    public Motorista() {
        super();
        this.grau=0;
        this.classificacao=0;
        this.nKms=0;
        this.disponivel=false;
        this.desvios=0;
    }
    /** Construtor que recebe a sua propra classe e iguala as variaveis aos seus respetivos getters. */
    public Motorista(Motorista m) {
        super(m);
        this.grau=m.getGrau();
        this.classificacao=m.getClassificacao();
        this.nKms=m.getNKMS();
        this.disponivel=m.getDisponivel();
        this.desvios=m.getDesvios();
    }
    /** Construtor parameterizado que recebe as variaveis da superclasse e da propria. */
    public Motorista(String email, String nome, String password,
                     String morada, int nascimento, 
                     int grau, int classificacao, double nKms, boolean disponivel, int desvios) {
        super(email,nome,password,morada,nascimento);
        this.grau=grau;
        this.classificacao=classificacao;
        this.nKms=nKms;
        this.disponivel=disponivel;
        this.desvios=desvios;
    }
    /** Clone do motorista. */
    public Motorista clone() {
        return new Motorista(this);
    }
    /** Funcao toString. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grau: "+this.grau+" \n");
        sb.append("Classificaçao: "+this.classificacao+" \n");
        sb.append("Numero de kms: "+this.nKms+" \n");
        sb.append("Disponivel: "+this.disponivel+" \n");
        sb.append("Desvios: "+this.desvios+" \n");
        sb.append(super.toString());
        return sb.toString();
    }
    /** Equals que testa se e o mesmo motorista com base no email. */
    public boolean equals(Motorista m) {
        if(m.getEmail().equals(super.getEmail())) {
            return true;
        }
        return false;
    }
}
