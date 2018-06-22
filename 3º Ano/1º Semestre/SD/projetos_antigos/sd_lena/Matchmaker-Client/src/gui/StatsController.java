package gui;

import business.ClientWrite;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Menu de Estatísticas.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class StatsController extends Controller {
	@FXML private Label ranking;
	@FXML private Label nrPlayed;
	@FXML private Label nrWon;	
	
	/**
	 * Constrói um Menu de Estatísticas.
	 * @param stage		Stage da aplicação
	 * @param cw		ClientWrite da aplicação
	 */
	public StatsController(Stage stage, ClientWrite cw) {
		super(stage,"MenuStats.fxml", cw);
	}
	
	/**
	 * Informa o servidor que o jogador quer jogar e carrega o Menu de Espera.
	 */
	@FXML
	public void buttonJogarOnAction() {
		ClientWrite cw = getCW();
		cw.write("jogar");
		new LoadingController(getStage(), cw);
	}
	
	/**
	 * Faz logout do utilizador.
	 */
	@FXML
	public void buttonLogoutOnAction() {
		getCW().shutdown();
		Platform.exit();
	}
	
	
	/** 
	 * Preenche as stats do jogador no ecrã.
	 * @param ranking  ranking do jogador.
	 * @param nrPlayed número de jogos jogados no total.
	 * @param nrWon    número de jogos ganhos.
	 */
	public void preencheStats(String ranking, String nrPlayed, String nrWon) {
		this.ranking.setText(ranking);
		this.nrPlayed.setText(nrPlayed);
		this.nrWon.setText(nrWon);
	}
}