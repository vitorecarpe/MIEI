import java.util.ArrayList;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

public class Cliente extends Atores implements Serializable {
    /** Variaveis de instancia. */ 
    private Par coords;    // coordenadas 
    private double gastos; // dinheiro gasto 
    
    /** Getter das coordenadas. */
    public Par getCoords() {
        return coords;
        
    }/** Setter das coordenadas. */
    public void setCoords(Par coords) {
        this.coords=coords;
    }
    /** Getter dos gastos.*/
    public double getGastos(){
        return this.gastos;
    }
    /** Setter dos gastos. */
    public void setGastos(double gastos){
        this.gastos=gastos;
    }
     /** Inicializa as variaveis. */
    public Cliente() {
        super();
        this.coords = new Par();
        this.gastos = 0;
    }
    /** Construtor parameterizado que recebe a propria classe. */
    public Cliente(Cliente c) {
        super(c);
        this.coords = new Par(c.getCoords().getX(),c.getCoords().getY());
        this.gastos = c.getGastos();
    }
    /** Construtor parameterizado que recebe as variaveis de instancia da superclasse Atores com o super(). */
    public Cliente(String email, String nome, String password, 
                    String morada, int nascimento, 
                    double x, double y, double gastos) {
        super(email,nome,password,morada,nascimento);
        this.coords = new Par(x,y);
        this.gastos = gastos;
    }
    /** Clone do Cliente. */
    public Cliente clone() {
        return new Cliente(this);
    }
    /** Funçao toString. */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(coords.toString()+"\n");
        sb.append(gastos+"\n");
        return sb.toString();
    }
    /** Funçao equals que verifica se clientes sao iguais pelos seus emails. */
    public boolean equals(Cliente c) {
        if(c.getEmail().equals(super.getEmail())) {
            return true;
        }
        return false;
    }
}
