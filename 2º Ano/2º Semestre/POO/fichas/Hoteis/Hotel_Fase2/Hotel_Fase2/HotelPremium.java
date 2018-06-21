import java.io.*;

/**
 * Hotel Premium, acresce uma taxa ao hotel
 * @author Rui Couto
 * @version 1.0
 * 
 * @author anr
 * @version 2.0
 */
public class HotelPremium extends Hotel implements CartaoPontos, Serializable {

    /** Taxa de luxo */
    private double taxa;
    
    /**
     * Construtor vazio
     */
    public HotelPremium() {
        super();
        taxa = 1;
    }

    /**
     * Construtor por cópia
     * @param c 
     */
    public HotelPremium(HotelPremium c) {
        super(c);
        this.taxa = c.getTaxa();
    }

    /**
     * Construtor por parâmetro
     * @param codigo
     * @param nome
     * @param localidade
     * @param precoBaseQuarto
     * @param nquartos
     * @param estrelas
     * 
     * @param taxa 
     */
    public HotelPremium(String codigo, String nome, String localidade, double precoBaseQuarto, double taxa, int nquartos, int estrelas) {
        super(codigo, nome, localidade, precoBaseQuarto , nquartos, estrelas);
        this.taxa = taxa;
    }

    
    /**
     * Calcula o preço de uma noite no hotel
     * @return valor do preço base multiplicado pela taxa.
     */
    
    public double precoNoite() {
      return getPrecoBaseQuarto()*taxa;
    }
    
    /**
     * Obter a taxa
     * @return 
     */
    public double getTaxa() {
        return taxa;
    }

    /**
     * Definir a taxa
     * @param taxa 
     */
    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    /**
     * Devolve uma cópia da instância
     * @return 
     */
    public HotelPremium clone() {
        return new HotelPremium(this);
    }

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
        HotelPremium o = (HotelPremium) obj;
        return super.equals(o) && o.getTaxa() == taxa;
    }

    /**
     * Devolve uma representação textual do hotel
     * @return 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append("Taxa: ").append(this.taxa).append("%");
        sb.append("Preço final: ").append(precoNoite()).append("€"); 
        return sb.toString();
    }
    
    //interface
    private int pontos;
    
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    
    public int getPontos() {
        return this.pontos;
    }

}