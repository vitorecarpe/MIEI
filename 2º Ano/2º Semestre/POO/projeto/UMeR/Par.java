import java.awt.geom.Point2D.Double;
import java.io.Serializable;

public class Par implements Serializable {
    /** Variaveis de instancia. */
    private double x;
    private double y;
    /** Setter da coordenada X. */
    public void setX(double x) {
        this.x = x;
    }
    /** Getter da coordenada Y. */
    public void setY(double y) {
        this.y = y;
    }
    /** Getter da coordenada X.*/
    public double getX() {
        return x;
    }
    /** Setter da coordenada Y. */
    public double getY() {
        return y;
    }
    /** Construtor que inicializa as variaveis. */
    public Par() {
        this.x=0;
        this.y=0;
    }
    /** Recebe a propria classe iguala as coordenadas x e y ao seus getters.*/
    public Par(Par p) {
        this.x=p.getX();
        this.y=p.getY();
    }
    /** Recebe como parametro as coordenadas altera o valor do this.x e o this.y para esse x e y. */
    public Par(double x, double y) {
        this.x=x;
        this.y=y;
    }
    /** Faz o clone das Coordenadas. */
    public Par clone(){
        return new Par(this.x,this.y);
    }
    /** Faz os equals, isto e verifica se as coordenadas sao iguais. */
    public boolean equals(Par p){
        if((p.getX()==this.x) && (p.getY()==this.y)){
            return true;
        }
        return false;
    }
    /** toString da classe */
    public String toString(){
        return "Coordenadas: ("+x+","+y+")";
    }
    /** Funçao que calcula a distancia entre um certa posiçao e o destino, para isso
     * utiliza-se a formula da distancia entre dois pontos.
     */
    public double distance(Par destino){
        double distX = Math.abs(destino.getX()-x);
        double distY = Math.abs(destino.getY()-y);
        double distDiag = Math.sqrt(distX*distX + distY*distY);
        return distDiag;
    }
    
}