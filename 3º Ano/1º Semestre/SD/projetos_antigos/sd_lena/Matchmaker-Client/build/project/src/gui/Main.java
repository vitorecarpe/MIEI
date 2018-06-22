package gui;

import java.net.Socket;

import business.ClientRead;
import business.ClientWrite;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class Main extends Application {

	/**
	 * Cria o socket e constrói a janela inicial.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Socket cs = new Socket("127.0.0.1", 9999);
			
			ClientWrite cw = new ClientWrite(cs);
			ClientRead cr = new ClientRead(cs);
			Thread tr = new Thread(cr);
			
			tr.start();
	
			new LoginController(primaryStage, cw);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main da aplicação.
	 * @param args	Parametros.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}