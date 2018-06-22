package gui;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import business.ClientWrite;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

/**
 * Menu Inicial.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class LoginController extends Controller {
	@FXML private TextField username;
	@FXML private PasswordField password;
	
	/**
	 * Constrói um Menu Inicial.
	 * @param stage		Stage da aplicação
	 * @param cw		ClientWrite da aplicação
	 */
	public LoginController(Stage stage, ClientWrite cw) {
		super(stage, "MenuLogin.fxml", cw);	
	}


	/**
	 * Login do utilizador.
	 */
	@FXML
	public void buttonLoginOnAction() {
		String name = username.getText();
		String pass = password.getText();
		if (name.equals("")) Erro.start("5");
		else if (pass.equals("")) Erro.start("6");
		else getCW().autentica(name, pass, 0);
	}
	
	/**
	 * Regista o utilizador.
	 */
	@FXML
	public void buttonRegistarOnAction() {
		String name = username.getText();
		String pass = password.getText();
		if (name.equals("")) Erro.start("5");
		else if (pass.equals("")) Erro.start("6");
		else getCW().autentica(name, pass, 1);
	}
	
	
	/** 
	 * Abre uma janela com as estatísticas do jogador, preenchendo-as.
	 * @param ranking  ranking do jogador no jogo.
	 * @param nrPlayed número de jogos que o jogador jogou no total.
	 * @param nrWon    número de jogos ganhos pelo jogador.
	 */
	public static void novaJanelaStats(String ranking, String nrPlayed, String nrWon) {
		StatsController SC =  new StatsController(getStage(), getCW());
		SC.preencheStats(ranking, nrPlayed, nrWon);
	}
}