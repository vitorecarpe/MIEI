/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternedbetess;

import business.BetESS;
import java.io.IOException;
import javafx.stage.Stage;
import presentation.Login;

/**
 *
 * @author danie
 */
public class PatternedBetESS {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) throws IOException, ClassNotFoundException {
        BetESS betess = BetESS.load();
        //betess = betess.load(); Acho que não é preciso fazer este
        Login form = new Login(betess);
        form.setVisible(true);
        
    }
    
}
