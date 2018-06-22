import java.io.*;
import java.net.*;
import java.util.*;

class Bot implements Runnable {
	private int id;
	private Socket cs;
	private ClientWrite cw;
	private BufferedReader in;

   /**
    * Construtor de instâncias da classe Bot.
    * 
    * @param id número identificador do bot a ser criado
    */
	public Bot(int id) {
		this.id = id;
		this.cs = null;
		this.cw = null;
		this.in = null;
	}
	
   /**
    * Método com ciclo de espera por início de uma partida.
    */
	public void waitForGame() {
		try {
			while(!in.readLine().equals("PartidaEncontrada"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

   /**
    * Após uma escolha de personagem por parte do bot, aguarda por
    * resposta do servidor relativa ao sucesso resultante do 
    * processo de escolha de um personagem.
    * 
    * @return booleano indicativo do sucesso da escolha do bot.
    *		  						 O valor 'true' revela sucesso na escolha e 
    *		  						 'false' o contrário.
    */
	public boolean waitSucessMsg() {
		String msg = "";
		try {
			String[] tokens;
			//ficam no ciclo enquanto n recebem "persDef" ou "erro/7/"
			do {
				msg = in.readLine();
				tokens = msg.split("/");
			} while(tokens[0].equals("persEscolhida") || (tokens[0].equals("persSubstituida")));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		if(msg.equals("persDef")) {
			return true;
		}
		return false;
	}

   /**
    * Espera que o bot seja avisado do início de uma partida e do tempo determinado para a partida cujo 
    * início se aguarda.
    * 
    * @return							 tempo de jogo determinado para a partida
    * @throws TimeLimitExceededException lançada se a partida não foi iniciada por ter sido excedido o tempo 
    * 									 								 limite para a escolha de uma personagem sem que ambas as equipas estejam
    * 									 								 prontas para o início da partida
    */
	public int waitForGameToStart() throws TimeLimitExceededException {
		String msg = "";
		int tempoJogo = 0;
		try {
			String[] tokens;
			do {																			//Ciclo de espera pela mensagem que indique o começo da partida.
				msg = in.readLine();
				tokens = msg.split("/");
				if(tokens[0].equals("erro")) {
					throw new TimeLimitExceededException();
				}
			} while(!tokens[0].equals("comecarPartida"));
			tempoJogo = Integer.parseInt(tokens[1]);	//Tempo de jogo determinado para a partida.
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return tempoJogo;
	}

   /**
    * Método com ciclo de simulação de escolha de personagem por 
    * parte de um bot.
    */
	public void loopEscolherPersonagem() {
		Random rand = new Random();
		Boolean flag = false;
		int randNum;
		String escolha;
		while(!flag) {
			randNum = rand.nextInt(30);
			escolha = Integer.toString(randNum);
			try {
				randNum = rand.nextInt(5) + 2;	//Tempo aleatório de espera para que a escolha pareça mais natural.
				Thread.sleep(randNum * 1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			cw.write(escolha);								//Indica ao servidor qual das personagens o bot escolheu.
			flag = waitSucessMsg();						//Espera por indicador do sucesso resultante da escolha.
		}
	}

   /**
    * Método dos procedimentos correspodente às ações de um bot
    * que será executado quando a execução da thread associada
    * a este objeto for iniciada.
    */
	@Override
	public void run() {
		try {
			cs = new Socket("127.0.0.1", 9999);
			cw = new ClientWrite(cs);

			in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			cw.autentica("bot" + id, "pass" + id, 1);
			int tempoJogo;

			while(true) {
				cw.write("jogar");		  						//Indica ao servidor a vontade de jogar, que coloca o bot em espera.
				waitForGame();			  							//Espera do bot para que seja encontrada uma partida na qual possa participar.
				loopEscolherPersonagem(); 					//Método de escolha de personagem por parte do bot.
				try {
					cw.write("JogoTerminou");
					tempoJogo = waitForGameToStart();	//M�todo de espera pela confirmação do inécio de partida que devolve o tempo de jogo para ela determinado.
					Thread.sleep(tempoJogo);
				}
				catch(TimeLimitExceededException | InterruptedException e) {
					e.printStackTrace();
				}


			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}

public class Teste {

   /**
    * Metodo principal da classe Teste, que quando invocado criará objetos da classe Bot
    * para teste, executando os métodos run das threads a estas associadas.
    * 
    * @param args
    * @throws IOException
    */
	public static void main(String[] args) throws IOException { 
		int nBots = 9;

		if (args.length > 0) {
    		try {
        		nBots = Integer.parseInt(args[0]);
    		} catch (NumberFormatException e) {
        		System.err.println("Argument" + args[0] + " must be an integer.");
        		System.exit(1);
    		}
		}

		Thread[] bots = new Thread[nBots];

		for(int i = 0; i < nBots; i++) {
			bots[i] = new Thread(new Bot(i));
		}

		for(int i = 0; i < nBots; i++) {
			bots[i].start();
		}
	}

}
