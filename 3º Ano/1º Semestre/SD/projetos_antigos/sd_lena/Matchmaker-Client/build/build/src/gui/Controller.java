package gui;

import java.io.IOException;

import business.ClientWrite;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Carrega Menus.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public abstract class Controller {
	private static Stage stage;
	private static ClientWrite cw;
	
	/**
	 * Constrói um Menu.
	 * @param stage	Stage da aplicação.
	 * @param path	Localização do ficheiro fxml.
	 * @param cw	ClientWrite da aplicação. 
	 */
	public Controller(Stage stage, String path, ClientWrite cw) {
		Controller.stage = stage;
		Controller.cw = cw;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		loader.setController(this);

		try {
			stage.setScene(new Scene (loader.load()));
		} catch(IOException e) {
			e.printStackTrace();
		}

		stage.setTitle("Underwatch");
		stage.show();

		Controller.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				cw.shutdown();
			}
		});
	}
	
	/**
	 * Devolve o Stage da aplicação.
	 * @return	Stage da aplicação.
	 */
	public static Stage getStage() {
		return stage;
	}
	
	/**
	 * Devolve o ClientWrite da aplicação.
	 * @return	ClientWrite da aplicação.
	 */
	public static ClientWrite getCW() {
		return cw;
	}
}