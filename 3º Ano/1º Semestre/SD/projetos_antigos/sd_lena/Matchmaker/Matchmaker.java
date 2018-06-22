import java.net.*;
import java.io.*;
import java.util.*;

class TreatClient implements Runnable {
	private Socket cs;
	private Players players;
	private WantToPlay wantToPlay;
	private Player player;
	private PrintWriter out;
	private BufferedReader in;
	
   /**
	* Construtor da classe TreatClient.
	* 
	* @param cs 		socket do cliente (jogador) para comunicação com o servidor
	* @param players	objeto da classe Players que cont�m um Map com todos os jogadores do jogo
	* @param wantToPlay objeto que contém um ArrayList com filas de jogadores de diversos ranks que pretendem jogar
	*/
	TreatClient(Socket cs, Players players, WantToPlay wantToPlay) {
		this.cs = cs;
		this.players = players;
		this.wantToPlay = wantToPlay;
	}
	
   /**
    * Método inicial da comunicação entre cada cliente (jogador) e o servidor.
    */
	private void inicial() {
		try {
			String username = in.readLine();
			String password = in.readLine();
			int loginSignUp = Integer.parseInt(in.readLine());

			if(loginSignUp == 0) this.player = this.players.login(username, password, cs); //Se for 0 � feita autentica��o do jogador e ...
			else this.player = this.players.signUp(username, password, cs); 			   //...se for 1 � feita a inscri��o.
			System.out.println(username + " is on!");
			sucessologin();
		}
		catch(InexistentUserException e) {
			String erro = e.getMessage();
			System.out.println(erro);
			out.println("erro/2");
		}
		catch(AlreadyLoggedInException e) {
			String erro = e.getMessage();
			System.out.println(erro);
			out.println("erro/3");
		}
		catch(ExistentUserException e) {
			String erro = e.getMessage();
			System.out.println(erro);
			out.println("erro/4");
		} 
		catch(IOException e) {
			String erro = e.getMessage();
			System.out.println(erro);
			out.println("erro/1");
		}
		catch(WrongPasswordException e) {
			String erro = e.getMessage();
			System.out.println(erro);
			out.println("erro/9");
		}
	}

   /**
	* Envia ao cliente (jogador) informação sobre as suas estatísticas no jogo.
	*/
	private void sucessologin() {
		out.println("sucesso/"
					+this.player.getRanking()
					+"/" + this.player.getNumMatches()
					+"/" + this.player.getWonMatches());
	}

   /**
    * Adiciona o jogador à lista de jogadores que pretendem jogar e procura uma
    * partida para jogadores com valor de rank semelhante ao seu.
    */
	private void querJogar() {
		wantToPlay.add(player);
		wantToPlay.encontraPartida(player.getRanking());
		try {
			synchronized(player) {
				player.wait();
			}
		}
		catch(InterruptedException e) {
            e.printStackTrace();
        }
	}

   /**
    * Método de tratamento de mensagens enviadas ao servidor.
    * 	
    * @param current mensagem enviada pelo jogador ao servidor
    */
	private void trataMsg(String current) {
		switch(current) {
			case "jogar": System.out.println("querJogar");
						  querJogar();
						  break;
			case "autenticar": inicial();
							   break;
		}
	}

   /**
    * Método a ser executado após o início desta thread de tratamento.
    * de mensagens relativas à conexão entre o servidor e cada jogador.
    */
	@Override
	public void run() {
		try {
			this.out = new PrintWriter(cs.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			
			String current;
			boolean flag = true;
			while(flag && (current = in.readLine()) != null) {
				trataMsg(current);
				//é necessário quando o utilizador fecha a janela durante um jogo
				if (player != null) flag = player.isLoggedIn();
			}
			//caso o player abandonou o jogo já foi feito logout
			//(nesse caso ele saiu do ciclo porque a flag era false)
			if (flag == true && player != null) player.logout();
			cs.shutdownInput();
			cs.shutdownOutput();
			System.out.println("Connection Closed");
			in.close();
			out.close();
			cs.close();
		}
		catch(IOException e) { 
			e.printStackTrace();
		}
	}

}

public class Matchmaker {
	
   /**
    * Função principal correspondente à execução do servidor.
    * 
    * @param args
    * @throws IOException
    */
	public static void main(String[] args) throws IOException {
		
		ServerSocket ss = new ServerSocket(9999);     
		Socket cs = null;
		Players players = new Players();		
		WantToPlay wantToPlay = new WantToPlay();

		while(true) {
			cs = ss.accept();
			Thread t = new Thread(new TreatClient(cs, players, wantToPlay));	//Criada e posteriormente iniciada thread que tratará da ligação a um cliente (jogador).
			t.start();
		}
	}

}
