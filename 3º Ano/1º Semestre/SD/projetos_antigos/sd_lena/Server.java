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
	//private ObjectOutputStream outObject;
	
	TreatClient(Socket cs, Players players, WantToPlay wantToPlay) {
		this.cs = cs;
		this.players = players;
		this.wantToPlay = wantToPlay;
	}

	private void inicial() {
		try {
			String username = in.readLine();
			String password = in.readLine();
			int loginSignUp = Integer.parseInt(in.readLine());

			if (loginSignUp == 0) this.player = this.players.login(username, password, cs); //se é 0 faz login
			else this.player = this.players.signUp(username, password, cs); //se é 1 faz signUp;
			out.println("sucesso");
			System.out.println(username + " is on!");
			mandaStats();
		}
		catch(InexistentUserException e) { 
			String erro = e.getMessage();
			System.out.println(e.getMessage());
			out.println("erro2");
		}
		catch(AlreadyLoggedInException e) { 
			String erro = e.getMessage();
			System.out.println(e.getMessage());
			out.println("erro3");
		}
		catch(ExistentUserException e) { 
			String erro = e.getMessage();
			System.out.println(e.getMessage());
			out.println("erro4");
		} 
		catch(IOException e) { 
			String erro = e.getMessage();
			System.out.println(e.getMessage());
			out.println("erro1");
		}
	}

	/**
	 * Envia ao cliente informação sobre as suas estatísticas no jogo.
	 */

	private void mandaStats() {
		out.println(this.player.getRanking());
		out.println(this.player.getNumMatches());
		out.println(this.player.getWonMatches());
	}

	private void querJogar() {
		wantToPlay.add(player);
		wantToPlay.encontraPartida(player.getRanking());
	}

	private void trataMsg(String current) {
		switch(current) {
			case "jogar": querJogar();
						  break;
			case "autenticar": inicial();
							   break;
			case "desconectar": desconectar();
							    break;
		}
	}

	private void desconectar() {
		String username = player.getUsername();
		out.println(username + " is off!");
		try {
			in.close();
			out.close();
			cs.close();
			System.out.println("Connection Closed");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			this.out = new PrintWriter(cs.getOutputStream(), true); 
			this.in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			//outObject = new ObjectOutputStream(cs.getOutputStream());
			
			String current;

			while((current = in.readLine()) != null) {
				trataMsg(current);
			}
		}
		catch(IOException e) { 
			e.printStackTrace();
		}
	}
}



class Jogo {
	private ArrayList<Player> players;
	private String [] personagensPlayer;


	public Jogo (ArrayList <Player> selected){
		this.players=selected;
	}

	public synchronized void escolherPersonagem(){
		
	}

	public void avisaPlayer(){

	}

	//avisar os players que vao jogar
	//escolha de personagens;


	//jogo de esperar 10s;
}


public class Matchmaker {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(9999);     
		Socket cs = null;
		Players players = new Players();
		WantToPlay wantToPlay = new WantToPlay();

		while(true) {
			cs = ss.accept();
			Thread t = new Thread(new TreatClient(cs, players, wantToPlay));
			t.start();
		}
	}
}
