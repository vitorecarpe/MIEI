package gui;

import business.ClientWrite;
import javafx.stage.Stage;

/**
 * Menu de Jogo.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class GameController extends Controller {
	
	/**
	 * Constrói um Menu de Jogo.
	 * @param stage		Stage da aplicação
	 * @param cw		ClientWrite da aplicação
	 */
	public GameController(Stage stage, ClientWrite cw) {
		super (stage,"GameWindow.fxml", cw);
	}

	
	/**
	 * Volta à janela de estatísticas do jogador.
	 * @param ranking  ranking do jogador.
	 * @param nrPlayed número de jogos jogados pelo jogador no total.
	 * @param nrWon    número de jogos ganhos pelo jogador.
	 */
	public static void goBackToStats(String ranking, String nrPlayed, String nrWon) {
		StatsController SC = (StatsController) new StatsController(getStage(), getCW());
		SC.preencheStats(ranking, nrPlayed, nrWon);
	}
}