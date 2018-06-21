import java.io.*;
/**
 * Write a description of class HotelDiscount here.
 * 
 * @author Rui Couto
 * @version 1.0
 * 
 * @author anr
 * @version 2.0
 * 
 */
public class HotelDiscount extends Hotel implements Serializable {
    
    private double ocupacao;
    
    public HotelDiscount() {
        super();
        this.ocupacao = 0;
    }
    
    public HotelDiscount(HotelDiscount hotel) {
        super(hotel);
        this.ocupacao = hotel.getOcupacao();
    }
    
    public HotelDiscount(String codigo, String nome, String localidade, double precoBaseQuarto, int numQuartos, int estrelas, double ocupacao) {
        super(codigo, nome, localidade, precoBaseQuarto, numQuartos, estrelas);
        this.ocupacao = ocupacao;
    }
    
    public void setOcupacao(double ocupacao) {
        this.ocupacao = ocupacao;
    }
    
    
    public double getOcupacao() {
        return this.ocupacao;
    }
    
    
    
    /**
     * Calcula o preço de uma noite no hotel
     * @return valor do preço base afectado pela ocupação.
     */
    
    public double precoNoite() {
        return getPrecoBaseQuarto() * 0.5 + getPrecoBaseQuarto() * 0.4 * ocupacao;
    }
    
    
    
    public HotelDiscount clone() {
        return new HotelDiscount(this);
    }
    
    // equals e toString
    /**
     * Compara a igualdade com outro objecto
     * @param obj
     * @return 
     */
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        HotelDiscount o = (HotelDiscount) obj;
        return super.equals(o) && o.getOcupacao() == this.ocupacao;
    }

    /**
     * Devolve uma representação textual do hotel
     * @return 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Ocupação: ").append(this.ocupacao);
        sb.append("Preço final: ").append(precoNoite()).append("€"); 
        return sb.toString();
    }
    
    
}
