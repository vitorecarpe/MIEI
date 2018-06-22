package business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;

import gui.DisplayTeamsController;
import gui.Erro;
import gui.GameController;
import gui.LoadingController;
import gui.LoginController;
import gui.ChooseCharacterController;
import javafx.application.Platform;

/**
 * Responsável pela leitura do socket.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class ClientRead extends Observable implements Runnable {
	private Socket cs;
	private ChooseCharacterController controller;
	private String[] herois;

	/**
	 * Construtor de instâncias da classe ClientRead, que fará a leitura de
	 * mensagens do servidor para o cliente(jogador).
	 * 
	 * @param cs	socket para comunicação entre o servidor e o cliente
	 */
	public ClientRead(Socket cs) {
		this.cs = cs;
		this.herois = new String[] { "doomfist", "genji", "mccree", "pharah", "reaper", "soldier76", "sombra", "tracer",
				"bastion", "hanzo", "junkrat", "mei", "torbjorn", "widowmaker", "dva", "orisa", "reinhardt", "roadhog",
				"winston", "zarya", "ana", "lucio", "mercy", "moira", "symmetra", "zenyatta", "daniel", "helena",
				"mariana", "pedro" };
	}

	/**
	 * Interpreta as mensagens recebidas do servidor, agindo de acordo com estas.
	 * 
	 * @param msg	mensagem recebida do servidor.
	 */
	public void tratamsg(String msg) {
		String delims = "/";
		final String[] tokens = msg.split(delims);
		switch (tokens[0]) {

		// caso de erro
		case "erro":
								Platform.runLater(() -> Erro.start(tokens[1]));
								Platform.runLater(() -> {
									if (tokens[1].equals("8")) {
										ChooseCharacterController.goBackToStats(tokens[2], tokens[3], tokens[4]);
									}
								});
								break;

		// caso em que o login foi efetuado com sucesso.
		case "sucesso":
								Platform.runLater(() -> {
									LoginController.novaJanelaStats(tokens[1], tokens[2], tokens[3]);
								});
								break;

		// caso em que foram encontrados 10 jogadores para uma partida.
		case "PartidaEncontrada":
								Platform.runLater(() -> {
									controller = LoadingController.partidaEncontrada();
								});
								break;

		// caso em que uma personagem foi escolhida por um membro da equipa.
		case "persEscolhida":
								String heroi = herois[Integer.parseInt(tokens[1])];
								System.out.println(heroi);
								Platform.runLater(() -> {
									controller.escolhaHeroi(heroi, tokens[2]);
								});
								break;

		// caso em que uma personagem foi substituida por um membro da equipa.
		case "persSubstituida":
								String heroiO = herois[Integer.parseInt(tokens[1])];
								String heroiD = herois[Integer.parseInt(tokens[2])];
								System.out.println(heroiO + " -> " + heroiD);
								Platform.runLater(() -> {
									controller.alteraEscolhaHeroi(heroiO, heroiD, tokens[3]);
								});
								break;

		// caso em que é enviada pelo servidor a equipa adversária
		case "equipaAdversaria":
								String[] equipaB = new String[5];
								equipaB[0] = tokens[1];
								equipaB[1] = tokens[2];
								equipaB[2] = tokens[3];
								equipaB[3] = tokens[4];
								equipaB[4] = tokens[5];
								Platform.runLater(() -> {
									ChooseCharacterController.showTeams(equipaB);
								});
								break;

		// começo de uma partida
		case "comecarPartida":
								Platform.runLater(() -> {
									DisplayTeamsController.startGame();
								});
								break;

		// envio por parte do servidor dos stats do jogador.
		case "stats":
								Platform.runLater(() -> {
									GameController.goBackToStats(tokens[1], tokens[2], tokens[3]);
								});
								break;
								
		default:				
								break;

		}
	}

	/**
	 * Método dos procedimentos correspondentes à leitura de mensagens do servidor
	 * para o cliente que será invocado quando a execução da thread associada a este
	 * objeto for iniciada.
	 */
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			String status;
			while ((status = in.readLine()) != null) {
				tratamsg(status);
			}
			cs.shutdownInput();
			cs.close();
			Platform.exit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
