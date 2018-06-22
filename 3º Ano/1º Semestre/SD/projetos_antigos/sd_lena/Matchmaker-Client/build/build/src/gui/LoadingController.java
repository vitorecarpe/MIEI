package gui;


import business.ClientWrite;
import javafx.stage.Stage;

/**
 * Menu de Espera.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class LoadingController extends Controller {
	
	/**
	 * Constrói um Menu de Espera.
	 * @param stage		Stage da aplicação
	 * @param cw		ClientWrite da aplicação
	 */	
	public LoadingController(Stage stage, ClientWrite cw) {
		super(stage,"LoadingWindow.fxml", cw);
	}
	

	/**
	 * Abre a janela de escolha de personagens quando uma partida é encontrada.
	 */
	public static ChooseCharacterController partidaEncontrada() {
		ChooseCharacterController CC = new ChooseCharacterController(getStage(), getCW());
		CC.startTime();
		return CC;
	}	
}