import java.io.Serializable;

public class Moto extends Viatura implements Serializable {
    /** Construtor padrao.*/
    public Moto(){
        super();
    }
    /** Construtor parameterizado que usa super() para chamar o contrutor da superclasse Viatura.*/
    public Moto(Moto c){
        super(c);
    }
    /** Construtor parameterizado com as variaveis de instancia da classe Viatura para 
     * as obter usa-se  super() que recebe como parametros as variaveis. */
    public Moto(String matricula, int velocidade, double preco, int fiabilidade, 
                Motorista motorista, double x1, double y1,
                Cliente cliente, double x2, double y2, double faturacao) {
        super(matricula, velocidade, preco, fiabilidade, 
                motorista, x1, y1, cliente, x2 ,y2, faturacao);
    }
    /** Faz o clone da Moto. */
    public Moto clone() {
        return new Moto(this);
    }
    /** Faz o toString da classe. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        return sb.toString();
    }
    /** Funçao equals, verifica se a matricula da viatura e igual a matricula da superclasse. */
    public boolean equals(Viatura v) {
        if(v.getMatricula().equals(super.getMatricula())) {
            return true;
        }
        return false;
    }
}