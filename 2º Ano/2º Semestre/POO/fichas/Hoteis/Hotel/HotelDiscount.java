
/**
 * Escreva a descrição da classe HotelDiscount aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class HotelDiscount extends Hotel
{

    private boolean epocaAlta;
    
    public HotelDiscount(){
        super();
        this.epocaAlta=false;
    }
    
    public HotelDiscount(String cod, String nome, String localidade, int precoQuarto, int estrelas, int numeroQuartos, boolean epocaAlta){
        super(cod, nome, localidade, precoQuarto, numeroQuartos, estrelas);
        this.epocaAlta = epocaAlta;
    }
    
    public HotelDiscount(HotelDiscount hs){
        super(hs);
        this.epocaAlta= hs.getEpocaAlta();
    }
    
    public double getPrecoQuarto(){
        if(this.epocaAlta){
            return super.getPrecoQuarto() + 20;
        }
        else {return super.getPrecoQuarto();} 
    }
    
    public double precoNoite(){
        return getPrecoBaseQuarto() + (this.epocaAlta?20:0);
    }
    
    public Hotel clone(){
        return new HotelDiscount(this);
    }
    
}
