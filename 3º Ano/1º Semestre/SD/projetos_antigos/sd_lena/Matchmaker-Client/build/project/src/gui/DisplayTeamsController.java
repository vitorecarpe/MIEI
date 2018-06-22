package gui;

import business.ClientWrite;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Menu de Equipas.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class DisplayTeamsController extends Controller {
	@FXML private Label teamAplayer1;
	@FXML private Label teamAplayer2;
	@FXML private Label teamAplayer3;
	@FXML private Label teamAplayer4;
	@FXML private Label teamAplayer5;
	@FXML private Label teamBplayer1;
	@FXML private Label teamBplayer2;
	@FXML private Label teamBplayer3;
	@FXML private Label teamBplayer4;
	@FXML private Label teamBplayer5;
	
	/**
	 * Constrói um Menu de Equipas.
	 * @param stage		Stage da aplicação
	 * @param cw		ClientWrite da aplicação
	 */
	public DisplayTeamsController(Stage stage, ClientWrite cw) {
		super(stage,"DisplayTeams.fxml", cw);
	}
	
	/**
	 * Preenche na GUI a equipaA.
	 * @param equipaA EquipaA
	 */
	public void setEquipaA(String[] equipaA) {
		this.teamAplayer1.setText(equipaA[0]);
		this.teamAplayer2.setText(equipaA[1]);
		this.teamAplayer3.setText(equipaA[2]);
		this.teamAplayer4.setText(equipaA[3]);
		this.teamAplayer5.setText(equipaA[4]);
	}
	
	/**
	 * Preenche na GUI a equipaB.
	 * @param equipaB	EquipaB
	 */
	public void setEquipaB(String[] equipaB) {
		this.teamBplayer1.setText(equipaB[0]);
		this.teamBplayer2.setText(equipaB[1]);
		this.teamBplayer3.setText(equipaB[2]);
		this.teamBplayer4.setText(equipaB[3]);
		this.teamBplayer5.setText(equipaB[4]);
	}
	

	/**
	 * Começa um jogo.
	 */
	public static void startGame() {
		new GameController(getStage(), getCW());
	}
}