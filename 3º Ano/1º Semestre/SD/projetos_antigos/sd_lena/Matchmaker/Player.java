import java.util.*;
import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

public class Player {
	private int ranking;
	private String username;
	private String password;
	private int numMatches;
	private int wonMatches;
	private boolean loggedIn;
	private Socket cs;

   /**
    * Construtor de objetos correspondentes a um jogador.
    * 
    * @param user username do jogador a ser criado
    * @param pass palavra passe do jogador a ser criado
    * @param cs	  socket para comunicação entre o jogador 
    * 			  e o servidor
    */
	public Player(String user, String pass, Socket cs) {
		this.ranking = 0;
		this.username = user;
		this.password = pass;
		this.numMatches = 0;
		this.wonMatches = 0;
		this.loggedIn = true;
		this.cs = cs;
	}

   /**
    * Construtor utilizado unicamente para casos de teste.
    * 
    * @param user username do jogador a ser criado
    * @param pass palavra passe do jogador a ser criado
    * @param rank rank do jogador a ser criado
    * @param cs   socket para comunica��o entre o jogador 
    * 			  e o servidor
    */
	public Player(String user, String pass, int rank, Socket cs) {
		this.ranking = rank;
		this.username = user;
		this.password = pass;
		this.wonMatches = rank;
		this.numMatches = 9;
		this.loggedIn = false;
		this.cs = cs;
	}

	public String getUsername() {
		return this.username;
	}

	public int getRanking() {
		return this.ranking;
	}

	public int getNumMatches() {
		return this.numMatches;
	}

	public int getWonMatches() {
		return this.wonMatches;
	}

	public Socket getSocket() {
		return this.cs;
	}

	public void setSocket(Socket socket) {
		this.cs = socket;
	}

	public boolean isLoggedIn() {
		return this.loggedIn;
	}

   /**
    * Efetua a autenticação de um jogador.
    * 
    * @param pass					   password do jogador em processo de autenticação
    * @param cs						   objeto Socket utilizado para comunicação entre o servidor e o jogador
    * @throws AlreadyLoggedInException lançada no caso de um jogador já se encontrar autenticado no
    * 								   momento do presente login
    * @throws WrongPasswprdException   lançada no caso de um jogador se tentar autenticar com uma password errada
    */
	public void login(String pass, Socket cs) throws AlreadyLoggedInException, WrongPasswordException {
		if(this.loggedIn == true) throw new AlreadyLoggedInException("Sessão aberta noutra máquina!");
		if(!this.password.equals(pass)) throw new WrongPasswordException("A password usada na autenticação é errada.");
		this.loggedIn = true;
		this.cs = cs;
	}

   /**
    * Efetua o logout de um jogador.
    */
	public void logout() {
		this.loggedIn = false;
		this.cs = null;
	}

   /**
    * Atualiza o valor do rank de um jogador de acordo com
    * o resultado de uma partida que este jogou.
    * 
    * @param matchResult booleano que indica se o jogador venceu 
    * 					 a partida (true) ou se perdeu (false)
    */
	public void updateRanking(boolean matchResult) {
		this.numMatches++;
		if(matchResult == true) this.wonMatches++;
		this.ranking = calcRanking();
	}

   /**
    * Calcula o rank de um jogador de acordo com o número total de 
    * partidas jogadas e o número total de partidas vencidas.
    * 
    * @return	   rank do jogador
    */
	public int calcRanking() {
		int rank = (this.wonMatches*this.wonMatches)/this.numMatches;
		System.out.println(username + "'s ranking is " + rank);
		return (rank > 9) ? 9 : rank;
	}
}