import java.util.*;
import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

public class WantToPlay {
	ArrayList<Queue<Player>> playersByRank;
	ReentrantLock[] locks;
	ReentrantLock lock;

   /**
    * Este construtor da classe WantToPlay cria um ArrayList de 10 índices, 
    * estando em cada um deles uma implementação de Queue (aqui LinkedList), 
    * contendo jogadores com rank dado pelo índice do ArrayList que pretendem
    * jogar - será a variável de instância playersByRank. É também criado um
    * array com 10 instâncias de ReentrantLock, correspondentes aos locks de 
    * cada uma das LinkedList no ArrayList anteriormente mencionado - será a 
    * variável de instância locks. Finalmente é criado o ReetrantLock que será
    * usado no controlo de concorrência neste objeto.
    */
	public WantToPlay() {
		this.playersByRank = new ArrayList<Queue<Player>>(10);
		this.locks = new ReentrantLock[10];
		for(int i = 0; i < 10; i++) {
			this.playersByRank.add(i , new LinkedList<Player>());
			this.locks[i] = new ReentrantLock();
		}
		this.lock = new ReentrantLock ();
	}

   /**
    * Transfere um número nElems de jogadores da implementação de Queue playersR
    * para o ArrayList select (de jogadores selecionados).
    * 
    * @param nElems   número de elementos a serem removidos da Queue playersR
    * @param playersR implementação de Queue de onde serão selecionados jogadores
    * @param selected ArrayList onde serão colocados os jogadores selecionados
    */
	public void addNElemsFromTo(int nElems, Queue<Player> playersR, ArrayList<Player> selected) {
		Player p = null;
		for(int i = 0; i < nElems; i++) {
			p = playersR.poll();
			if(p != null) selected.add(p);
		}
	}
	
   /**
    * Procura partida envolvendo 10 jogadores de rank igual ou diferente em 1 unidade 
    * ao parâmetro ranking. Sendo encontrados, inicia uma thread correspondente a uma
    * partida na qual participam os jogadores selecionados.
    * 
    * @param ranking valor de rank a partir do qual se pretende encontrar 
    * 				 jogadores de habilidade semelhante para uma partida
    */
	public void encontraPartida(int ranking){
		ArrayList<Player> selectedPlayers = new ArrayList<>();

		boolean complete = false;
		if(ranking != 0) locks[ranking - 1].lock();
		locks[ranking].lock();
		int nPlayersCR = playersByRank.get(ranking).size(), nPlayersLR, nPlayersUR;
		//nPlayersCR >= 10 (0 .. 9)
		if(nPlayersCR >= 10) {					//Verifica se o número de jogadores disponíveis de rank igual ao pretendido é suficiente para uma partida.
			addNElemsFromTo(10,playersByRank.get(ranking),selectedPlayers);					//Sendo suficiente, os jogadores são selecionados.
			if(ranking != 0) locks[ranking - 1].unlock();
			complete = true;
		}
		//nPlayersCR < 10 && ranking != 0 (1 .. 9)-
		if(!complete && ranking != 0) {
			nPlayersLR = playersByRank.get(ranking - 1).size();
			if(nPlayersCR + nPlayersLR >= 10) {	//Verifica se o número de jogadores de rank igual ou menor em 1 unidade ao pretendido é suficiente para uma partida.
				addNElemsFromTo(nPlayersCR,playersByRank.get(ranking),selectedPlayers);		//Sendo suficiente, os jogadores são selecionados. Aqui com rank igual, ...
				addNElemsFromTo(10 - nPlayersCR,playersByRank.get(ranking - 1),selectedPlayers);	//... e aqui com rank inferior em 1 unidade.
				complete = true;
			}
			locks[ranking - 1].unlock();
		}
		//nPlayersCR < 10 && nPlayersCR + nPlayersLR < 10 && ranking != 9 (0 .. 8)+
		if(!complete && ranking != 9) {
			locks[ranking + 1].lock();
			nPlayersUR = playersByRank.get(ranking + 1).size();
			if(nPlayersCR + nPlayersUR >= 10) {	//Verifica se o número de jogadores de rank igual ou maior em 1 unidade ao pretendido é suficiente para uma partida.
				addNElemsFromTo(nPlayersCR,playersByRank.get(ranking),selectedPlayers);		//Sendo suficiente, os jogadores são selecionados. Aqui com rank igual, ...
				addNElemsFromTo(10 - nPlayersCR,playersByRank.get(ranking + 1),selectedPlayers);	//... e aqui com rank superior em 1 unidade.
				complete = true;
			}
			locks[ranking + 1].unlock();
		}

		locks[ranking].unlock();
		
		if(complete) {
			Thread jogo = new Thread(new Jogo(selectedPlayers));	//Tendo sido encontrados os 10 jogadores necessários para uma partida com o intervalo de ranks adequado ...
			jogo.start();											//...será iniciada uma partida envolvendo os jogadores anteriormente selecionados.
			System.out.println("Partida Encontrada!");
		}
	}

   /**
    * Obtém o lock do objeto desta classe.
    */
	public void lock() {
		this.lock.lock();
	}

   /**
    * Liberta o lock do objeto desta classe.
    */
	public void unlock() {
		this.lock.unlock();
	}

   /**
    * Adiciona um jogador à fila de jogadores que pretendem jogar. O jogador
    * a ser adicionado é colocado numa fila dedicada a jogadores com o mesmo
    * valor de rank que o seu.
    * 
    * @param p objeto correspondente a um jogador a ser adicionado à fila
    * 		   de jogadores que pretendem jogar com um determinado rank
    */
	public void add(Player p) {
		int rank = p.getRanking();
		try {
			locks[rank].lock();
			playersByRank.get(rank).add(p);
			locks[rank].unlock();
		}
		catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}

   /**
    * Remove um jogador da fila de jogadores que pretendem jogar. O jogador 
    * é removido de uma fila dedicada a jogadores com o mesmo valor de rank
    * que o seu.
    * 
    * @param p objeto correspondente a um jogador a ser removido da fila
    * 		   de jogadores que pretendem jogar
    */
	public void remove(Player p) {
		int rank = p.getRanking();
		try {
			locks[rank].lock();
			playersByRank.get(rank).remove(p);
			locks[rank].unlock();
		}
		catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}

}
