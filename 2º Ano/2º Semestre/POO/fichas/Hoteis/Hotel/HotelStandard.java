
/**
 * Escreva a descrição da classe HotelStandard aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class HotelStandard extends Hotel implements CartaoPontos
{
    private boolean epocaAlta;
    
    public HotelStandard(){
        super();
        this.epocaAlta=false;
    }
    
    public HotelStandard(String cod, String nome, String localidade, int precoQuarto, int estrelas, int numeroQuartos, boolean epocaAlta){
        super(cod, nome, localidade, precoQuarto, numeroQuartos, estrelas);
        this.epocaAlta = epocaAlta;
    }
    
    public HotelStandard(HotelStandard hs){
        super(hs);
        this.epocaAlta= hs.getEpocaAlta();
    }
    
    public boolean getEpocaAlta(){
        return this.epocaAlta;
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
        return new HotelStandard(this);
    }
    
    
    
    
    
    
    
    
}
