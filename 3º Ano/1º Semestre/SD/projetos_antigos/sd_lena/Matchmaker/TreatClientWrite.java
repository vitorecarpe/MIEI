import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class TreatClientWrite implements Runnable {
	Socket cs;
	EquipaLog l;
	Player p;
	int idxLog;

   /**
    * Construtor de objetos da classe TreatClientWrite, que tratarão de 
    * escrever mensagens do servidor para os jogadores.
    * 
    * @param cs socket para comunicação com o servidor
    * @param l	objeto EquipaLog com informação relativa à equipa, incluindo o log de ações
    * @param p	objeto relativo ao jogador cuja leitura será tratada
    */
	TreatClientWrite (Socket cs, EquipaLog l, Player p) {
		this.cs = cs; 
		this.l = l;
		this.p = p;
		this.idxLog = 0;
	}

   /**
    * Método dos procedimentos correspondentes à escrita de mensagens
    * enviadas pelo servidor a cada jogador que será invocado quando 
    * a execução da thread associada a este objeto for iniciada.
    */
	@Override
	public void run() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(cs.getOutputStream(), true);

			out.println("PartidaEncontrada");

			idxLog = l.loopEscolhaPersonagens(out);	//Método de ciclo que se mantém ativo até à fase final da fase de escolha de personagens ...
													//...e que devolve índice seguinte ao da mensagem após a escolha.
			l.retornaResultado(out, idxLog);		//Método com ciclos que se mantém ativo até ao final da partida.

			out.println("stats/"					//Escreve no PrintWriter os dados estatísticos do jogador.
				  + p.getRanking()
				  + "/" + p.getNumMatches()
				  + "/" + p.getWonMatches());
		}
		catch(TimeLimitExceededException e) {		//No caso do tempo de escolha ter sido excedido e não tenha havido escolha...
			out.println("erro/8/" + p.getRanking()
				  + "/" + p.getNumMatches()
				  + "/" + p.getWonMatches());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
