
/**
 * Escreva a descrição da classe HotelPremium aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class HotelPremium extends Hotel
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private boolean epocaAlta;

    /**
     * COnstrutor para objetos da classe HotelPremium
     */
    public HotelPremium()
    {
        // inicializa variáveis de instância
        x = 0;
    }
   
    public boolean existeHotel(String cod){
        for(Hotel h:this.hoteis.values()){
            if(h.getCodigo() == cod){
                return true;
            }
        }
        return false;
    }
    
}
