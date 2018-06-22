package gui;

import business.ClientWrite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Menu de Escolha de Personagens.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class ChooseCharacterController extends Controller {
	@FXML public ImageView doomfist;
	@FXML public ImageView genji;
	@FXML public ImageView mccree;
	@FXML public ImageView pharah;
	@FXML public ImageView reaper;
	@FXML public ImageView soldier76;
	@FXML public ImageView sombra;
	@FXML public ImageView tracer;
	@FXML public ImageView bastion;
	@FXML public ImageView hanzo;
	@FXML public ImageView junkrat;
	@FXML public ImageView mei;
	@FXML public ImageView torbjorn;
	@FXML public ImageView widowmaker;
	@FXML public ImageView dva;
	@FXML public ImageView orisa;
	@FXML public ImageView reinhardt;
	@FXML public ImageView roadhog;
	@FXML public ImageView winston;
	@FXML public ImageView zarya;
	@FXML public ImageView ana;
	@FXML public ImageView lucio;
	@FXML public ImageView mercy;
	@FXML public ImageView moira;
	@FXML public ImageView symmetra;
	@FXML public ImageView zenyatta;
	@FXML public ImageView daniel;
	@FXML public ImageView helena;
	@FXML public ImageView mariana;
	@FXML public ImageView pedro;
	@FXML public Label time;
	private Timeline timeline = new Timeline();
	private int timeToChoose = 30;
	private static ClientWrite cw;
	private static String[] equipaA;
	private int persA;


	/**
	 * Constrói um Menu de Escolha de Personagens.
	 * @param stage		Stage da aplicação
	 * @param cw		ClientWrite da aplicação
	 */
	public ChooseCharacterController(Stage stage, ClientWrite cw) {
		super(stage, "ChooseCharacter.fxml", cw);
		ChooseCharacterController.cw = cw;
		ChooseCharacterController.equipaA = new String[5];
		this.persA = 0;
	}
	
	/**
	 * Abre a janela que mostra ao utilizador (jogador) a composição da sua equipa
	 * e da equipa adversária.
	 * @param equipaB array de strings com informação sobre a equipa adversária (username dos players
	 *                e personagem escolhida)
	 */
	public static void showTeams(String[] equipaB) {
		cw.write("JogoTerminou");
		DisplayTeamsController DTC = new DisplayTeamsController(getStage(),cw);
		DTC.setEquipaA(equipaA);
		DTC.setEquipaB(equipaB);
	}

	/**
	 * Volta para a janela onde são mostradas ao jogador as suas estatísticas no jogo.
	 * @param ranking  ranking do jogador.
	 * @param nrPlayed número de jogos jogados pelo jogador no total.
	 * @param nrWon    número de jogos ganhos pelo jogador.
	 */
	public static void goBackToStats(String ranking, String nrPlayed, String nrWon) {
		cw.write("JogoTerminou");
		StatsController SC =  new StatsController(getStage(), cw);
		SC.preencheStats(ranking, nrPlayed, nrWon);
	}

	/**
	 * Começa e controla a contagem do tempo de escolha de personagens
	 * que é mostrado no ecrã ao utilizador.
	 */
	public void startTime() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1),
						new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								timeToChoose--;
								String minutes = "0" + Integer.toString(timeToChoose/60);
								String seconds = Integer.toString(timeToChoose%60);
								if (seconds.length() < 2)
									seconds = "0" + seconds;
								if (timeToChoose <= 10) {
									time.setTextFill(Color.web("#FF0000"));
								}
								time.setText(minutes + ":" + seconds);
								if(timeToChoose <= 0) timeline.stop();
							}
						}));
		timeline.playFromStart();
	}
	


	/** 
	 * Determina que uma personagem foi escolhida para a equipa do jogador.
	 * @param hero herói escolhido.
	 * @param user utilizador que escolheu a personagem.
	 */
	public void escolhaHeroi(String hero, String user) {
		try {
			ImageView v = (ImageView) this.getClass().getField(hero).get(this);
			v.setOpacity(0.5);
			v.setDisable(true);
		
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Erro.start("");
		}
	
		ChooseCharacterController.equipaA[persA] = user + " AS " + hero;
		persA++;
	}
	


	/**
	 * Determina que uma personagem foi mudada por um outro jogador da equipa do jogador.
	 * @param heroiO herói prévio do jogador.
	 * @param heroiD novo herói escolhido pelo jogador.
	 * @param user   jogador que alterou o herói com o qual irá jogar.
	 */
	public void  alteraEscolhaHeroi(String heroiO, String heroiD, String user) {
		Class<?> objClass = this.getClass();
			try {
				ImageView vO = (ImageView) objClass.getField(heroiO).get(this);
				ImageView vD = (ImageView) objClass.getField(heroiD).get(this);
				
				vO.setOpacity(1);
				vO.setDisable(false);
						
				vD.setOpacity(0.5);
				vD.setDisable(true);
				
			} catch (NoSuchFieldException |IllegalAccessException|IllegalArgumentException| SecurityException e) {
				e.printStackTrace();
			}  
		
		for(int i = 0; i < persA; i++) {
			if (equipaA[i].equals(user + " AS " + heroiO))
				equipaA[i] = user + " AS " + heroiD;
		}
	}


	
	/**
	 * Envia para o servidor que escolheu doomfist.
	 */
	@FXML
	public void doomfistOnAction() {
		cw.write("0");
	}
	
	/**
	 * Envia para o servidor que escolheu genji.
	 */
	@FXML
	public void genjiOnAction() {
		cw.write("1");
	}

	/**
	 * Envia para o servidor que escolheu mccree.
	 */
	@FXML
	public void mccreeOnAction() {
		cw.write("2");
	}

	/**
	 * Envia para o servidor que escolheu pharah.
	 */
	@FXML
	public void pharahOnAction() {
		cw.write("3");
	}
	
	/**
	 * Envia para o servidor que escolheu reaper.
	 */
	@FXML
	public void reaperOnAction() {
		cw.write("4");
	}

	/**
	 * Envia para o servidor que escolheu soldier76.
	 */
	@FXML
	public void soldier76OnAction() {
		cw.write("5");
	}
	
	/**
	 * Envia para o servidor que escolheu sombra.
	 */
	@FXML
	public void sombraOnAction() {
		cw.write("6");
	}
	
	/**
	 * Envia para o servidor que escolheu tracer.
	 */
	@FXML
	public void tracerOnAction() {
		cw.write("7");
	}
	
	/**
	 * Envia para o servidor que escolheu bastion.
	 */
	@FXML
	public void bastionOnAction() {
		cw.write("8");
	}
	
	/**
	 * Envia para o servidor que escolheu hanzo.
	 */
	@FXML
	public void hanzoOnAction() {
		cw.write("9");
	}

	/**
	 * Envia para o servidor que escolheu junkrat.
	 */
	@FXML
	public void junkratOnAction() {
		cw.write("10");
	}
	
	/**
	 * Envia para o servidor que escolheu mei.
	 */
	@FXML
	public void meiOnAction() {
		cw.write("11");
	}
	
	/**
	 * Envia para o servidor que escolheu torbjorn.
	 */
	@FXML
	public void torbjornOnAction() {
		cw.write("12");
	}
	
	/**
	 * Envia para o servidor que escolheu widowmaker.
	 */
	@FXML
	public void widowmakerOnAction() {
		cw.write("13");
	}
	
	/**
	 * Envia para o servidor que escolheu dva.
	 */
	@FXML
	public void dvaOnAction() {
		cw.write("14");
	}

	/**
	 * Envia para o servidor que escolheu orisa.
	 */
	@FXML
	public void orisaOnAction() {
		cw.write("15");
	}
	
	/**
	 * Envia para o servidor que escolheu reinhardt.
	 */
	@FXML
	public void reinhardtOnAction() {
		cw.write("16");
	}
	
	/**
	 * Envia para o servidor que escolheu roadhog.
	 */
	@FXML
	public void roadhogOnAction() {
		cw.write("17");
	}
	
	/**
	 * Envia para o servidor que escolheu winston.
	 */
	@FXML
	public void winstonOnAction() {
		cw.write("18");
	}
	
	/**
	 * Envia para o servidor que escolheu zarya.
	 */
	@FXML
	public void zaryaOnAction() {
		cw.write("19");
	}
	
	/**
	 * Envia para o servidor que escolheu ana.
	 */
	@FXML
	public void anaOnAction() {
		cw.write("20");
	}
	
	/**
	 * Envia para o servidor que escolheu lucio.
	 */
	@FXML
	public void lucioOnAction() {
		cw.write("21");
	}
	
	/**
	 * Envia para o servidor que escolheu mercy.
	 */
	@FXML
	public void mercyOnAction() {
		cw.write("22");
	}

	/**
	 * Envia para o servidor que escolheu moira.
	 */	
	@FXML
	public void moiraOnAction() {
		cw.write("23");
	}
	
	/**
	 * Envia para o servidor que escolheu symmetra.
	 */
	@FXML
	public void symmetraOnAction() {
		cw.write("24");
	}
	
	/**
	 * Envia para o servidor que escolheu zenyatta.
	 */
	@FXML
	public void zenyattaOnAction() {
		cw.write("25");
	}
	
	/**
	 * Envia para o servidor que escolheu daniel.
	 */
	@FXML
	public void danielOnAction() {
		cw.write("26");
	}
	
	/**
	 * Envia para o servidor que escolheu helena.
	 */
	@FXML
	public void helenaOnAction() {
		cw.write("27");
	}
	
	/**
	 * Envia para o servidor que escolheu mariana.
	 */
	@FXML
	public void marianaOnAction() {
		cw.write("28");
	}
	
	/**
	 * Envia para o servidor que escolheu pedro.
	 */
	@FXML
	public void pedroOnAction() {
		cw.write("29");
	}
}