package horarios;

import horarios.business.Facade;
import horarios.presentation.FormLogin;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Horarios {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Facade facade = new Facade();
        FormLogin form = new FormLogin(facade);
        
        form.setVisible(true);
    }
    
}
