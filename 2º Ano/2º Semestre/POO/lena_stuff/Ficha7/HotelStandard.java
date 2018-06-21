
/**
 * Write a description of class HotelStandard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Map;

public class HotelStandard extends Hotel {
    private boolean epocaAlta;
    
    /* CONSTRUTORES */
    public HotelStandard() {
        super();
        this.epocaAlta = false;
    }
    
    public HotelStandard(String codigo, String nome, String localidade, double precoBaseQuarto, int numQuartos, int estrelas, boolean epoca) {
        super(codigo, nome, localidade, precoBaseQuarto, numQuartos, estrelas);
        this.epocaAlta = epoca;
    }
    
    public HotelStandard(HotelStandard h) {
        super(h);
        this.epocaAlta = h.getEpoca();
    }
    
    /* GETTERS E SETTERS */
    public boolean getEpoca() {
        return this.epocaAlta;
    }
    
    public void setEpoca(boolean epoca) {
        this.epocaAlta = epoca;
    }
    
    /* OUTROS MÉTODOS */
    public double getPrecoBaseQuarto() {
        return super.getPrecoBaseQuarto() + (this.epocaAlta ? 20 : 0);
    }
    
    public boolean equals(Object obj) {
        boolean a = super.equals(obj);
        HotelStandard novo = (HotelStandard) obj;
        return a && novo.getEpoca() == this.epocaAlta;
    }
    
    public HotelStandard clone() {
        return new HotelStandard(this);
    }
}
