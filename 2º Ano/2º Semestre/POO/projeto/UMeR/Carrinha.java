import java.io.Serializable;

public class Carrinha extends Viatura implements Serializable {
    /** Construtor padrao.*/
    public Carrinha(){
        super();
    }
    /** Construtor parameterizado que usa super() para chamar o contrutor da superclasse Viatura.*/
    public Carrinha(Carrinha c){
        super(c);
    }
 
    /** Construtor parameterizado com as variaveis de instancia da classe Viatura para 
     * as obter usa-se  super() que recebe como parametros as variaveis. */
    public Carrinha(String matricula, int velocidade, double preco, int fiabilidade, 
                    Motorista motorista, double x1, double y1,
                    Cliente cliente, double x2, double y2, double faturacao) {
        super(matricula, velocidade, preco, fiabilidade, 
                motorista, x1, y1, cliente, x2, y2, faturacao);
    }
    /** Faz o clone da Carrinha **/
    public Carrinha clone() {
        return new Carrinha(this);
    }
    /** Faz o toString da classe utilizando um string builder.*/
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        return sb.toString();
    }
    /** Funçao equals que verifica se as Matriculas sao iguais, se forem retorna true senao false. */
    public boolean equals(Viatura v) {
        if(v.getMatricula().equals(super.getMatricula())) {
            return true;
        }
        return false;
    }
}
