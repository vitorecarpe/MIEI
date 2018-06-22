import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.StringBuilder;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class Jogo implements Runnable {
	private ArrayList<Player> equipa1;
	private ArrayList<Player> equipa2;
	EquipaLog personagensE1;
	EquipaLog personagensE2;

   /**
    * Construtor de objetos da classe que efetua uma partida.
    * 
    * @param players ArrayList de jogadores que participarão na partida
    */
	Jogo(ArrayList<Player> players) {
		this.equipa1 = new ArrayList<>(5);
		this.equipa2 = new ArrayList<>(5);

		int i = 0;
		for(i = 0; i < 10; i ++){
			equipa1.add(players.get(i++));
			equipa2.add(players.get(i));
		}

		this.personagensE1 = new EquipaLog();
		this.personagensE2 = new EquipaLog();
	}

   /**
    * Método dos procedimentos correspondentes a uma partida
    * que será executado quando a execução da thread associada
    * a este objeto for iniciada.
    */
	@Override
	public void run() {
		Thread[] threads = new Thread[20];
		int i = 0;

		for(Player p : equipa1) {							//Cria e inicia as threads respons�veis por gerir leituras e escritas de jogadores da equipa 1.
			Socket cs = p.getSocket();
			Thread tr = new Thread(new TreatClientRead(cs, personagensE1, p));
			Thread tw = new Thread(new TreatClientWrite(cs, personagensE1, p));
			tr.start();
			tw.start();
			threads[i++] = tr;
			threads[i++] = tw;           
		}

		for(Player p : equipa2) {							//Cria e inicia as threads respons�veis por gerir leituras e escritas de jogadores da equipa 2.
			Socket cs = p.getSocket();
			Thread tr = new Thread( new TreatClientRead(cs, personagensE2, p));
			Thread tw = new Thread( new TreatClientWrite(cs, personagensE2, p));
			tr.start();
			tw.start();
			threads[i++] = tr;
			threads[i++] = tw;
		}

		try {
			Thread.sleep(30000);							//Tempo de escolher as personagens.
		}
		catch(InterruptedException e) {
		  e.printStackTrace();
		}
		if(!this.personagensE1.pronta() || !this.personagensE2.pronta()) {
			personagensE1.writeLog("Erro8");
			personagensE2.writeLog("Erro8");
			return;
		}
		else {												//Registo no log das duas equipas do final do tempo de escolha de personagens.
			personagensE1.writeLog("TimeEnded");
			personagensE2.writeLog("TimeEnded");
		}
	
		StringBuilder equipaAd1Msg = new StringBuilder("equipaAdversaria/");
		StringBuilder equipaAd2Msg = new StringBuilder("equipaAdversaria/");
		
		int j;
		String[] equipaAd1 = personagensE2.getPersonagens();
		for(j = 0; j < 5; j++) equipaAd1Msg.append(equipaAd1[j]).append("/");
		String[] equipaAd2 = personagensE1.getPersonagens();
		for(j = 0; j < 5; j++) equipaAd2Msg.append(equipaAd2[j]).append("/");

		personagensE1.writeLog(equipaAd1Msg.toString());	//Constituição da equipa 2 colocada no log da equipa 1 e...
		personagensE2.writeLog(equipaAd2Msg.toString());	//constituição da equipa 1 colocada no log da equipa 2.

		try {
			Thread.sleep(15000);							//Tempo de mostrar as persongens dos jogadores.
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}       

		Random rand = new Random();
		int randNum = rand.nextInt(2);						//Cálculo do número aleatório (0 ou 1) que determina a equipa vencedora de uma partida.
		if(randNum == 0) {
			for(Player p : equipa1) p.updateRanking(true);	//Atualiza o valor do rank de cada jogador da equipa 1, aqui vencedora.
			personagensE1.writeLog("Venceu");
			for(Player p : equipa2) p.updateRanking(false);	//Atualiza o valor do rank de cada jogador da equipa 2, aqui derrotada.
			personagensE2.writeLog("Perdeu");
		}
		else {
			for(Player p : equipa1) p.updateRanking(false);	//Atualiza o valor do rank de cada jogador da equipa 1, aqui derrotada.
			personagensE1.writeLog("Perdeu");
			for(Player p : equipa2) p.updateRanking(true);	//Atualiza o valor do rank de cada jogador da equipa 2, aqui vencedora.
			personagensE2.writeLog("Venceu");
		}
		randNum = (rand.nextInt(15) + 15) * 1000; 			//Cálculo do tempo de jogo (*1000 para que seja dado em milissegundos).
		personagensE1.writeLog("comecarPartida/" + randNum);
		personagensE2.writeLog("comecarPartida/" + randNum);
		System.out.println("comecarPartida/" + randNum);

		try {
			Thread.sleep(randNum);							//Tempo de jogo
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		personagensE1.writeLog("terminarPartida/");			//Escrita no log de ambas as equipas uma mensagem que marca o término da partida.
		personagensE2.writeLog("terminarPartida/");

		try {												//Aguarda-se o fim das threads de leitura e escrita associadas aos vários clientes (jogadores).
			for(i = 0; i < 20; i++) {
			   threads[i].join();
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}