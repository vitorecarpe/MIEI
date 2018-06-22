import java.util.*;
import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class Players {
	Map<String,Player> players;
	ReentrantLock lock = new ReentrantLock();

   /**
    * Construtor da classe Players, que conterá um Map de todos os 
    * jogadores (representados por objetos da classe Player) no jogo.
    */
	public Players() {
		this.players = new HashMap<String,Player>();
	}

   /**
    * Efetua a inscrição de um jogador.
    * 
    * @param user					username selecionado pelo jogador
    * @param pass					password a ser usada pelo jogador
    * @param cs						socket para comunicação com o servidor a ser passado ao cliente
    * @return						objeto correspondente ao jogador que se acaba de inscrever
    * @throws ExistentUserException lançada no caso do jogador que se encontra em processo de inscrição já se encontrar registado
    */
	public Player signUp(String user, String pass, Socket cs) throws ExistentUserException {
		Player p;
		lock.lock();
		
		if(players.containsKey(user)) {
			lock.unlock();
			throw new ExistentUserException("Utilizador existente!");
		}
		else {
			p = new Player(user, pass, cs);
			players.put(user,p);
		}
		lock.unlock();

		return p;
	}

   /**
    * Efetua a autenticação de um jogador registado.
    * 
    * @param user					   username do jogador no processo de autenticação
    * @param pass					   password do jogador no processo de autenticação
    * @param cs						   socket para comunicação com o servidor a ser passado ao cliente
    * @return						   objeto correspondente ao jogador que acaba de se autenticar
    * @throws InexistentUserException  lançada se o utilizador que está no processo de autenticação não existe
    * @throws AlreadyLoggedInException lançada se o utilizador já se encontra autenticado
    */
	public Player login(String user, String pass, Socket cs) throws InexistentUserException, AlreadyLoggedInException, WrongPasswordException {
		lock.lock();
		Player p = players.get(user);
		lock.unlock();
		if (p == null) throw new InexistentUserException("Utilizador inexistente!");
		p.login(pass, cs); //throw AlreadyLoggedInException

		return p;
	}

   /**
    * Efetua o logout para um jogador.
    * 
    * @param user username do jogador em processo de logout
    */
	public void logout(String user) {
		lock.lock();
		Player p = players.get(user);
		lock.unlock();

		if (p != null) p.logout();
	}

   /**
    * Adquire o lock desta instância da classe.
    */
	public void lock() {
		this.lock.lock();
	}

   /**
    * Liberta o lock desta instância do objeto.
    */
	public void unlock() {
		this.lock.unlock();
	}
	
   /**
    * Adiciona um jogador ao Map de jogadores.
    * 
    * @param p objeto correspondente a um jogador a ser adicionado
    * 		   ao Map de jogadores
    */
	public void add(Player p) {
		players.put(p.getUsername(),p);
	}

   /**
    * Remove um jogador do Map de jogadores.
    * 
    * @param user username do jogador a ser removido
    * 		 	  do Map de jogadores
    */
	public void remove(String user) {
		players.remove(user);
	}

}
