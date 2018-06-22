import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class TreatClientRead implements Runnable {
	Socket cs;
	EquipaLog l;
	Player p;

   /**
    * Construtor de objetos da classe TreatClientRead, que tratarão de ler mensagens
    * enviadas pelo jogadores ao servidor.
    * 
    * @param cs socket para comunicação com o servidor
    * @param l  objeto EquipaLog com informação relativa à equipa, incluindo o log de ações
    * @param p	objeto relativo ao jogador cuja leitura será tratada
    */
	TreatClientRead (Socket cs, EquipaLog l, Player p)  {
		this.cs = cs;
		this.l = l;
		this.p = p;
	}

   /**
    * Método dos procedimentos correspondentes à leitura de mensagens
    * enviadas pelo jogador ao servidor que será invocado quando 
    * a execução da thread associada a este objeto for iniciada.
    */
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			PrintWriter out = new PrintWriter(cs.getOutputStream(), true);

			String current, user = p.getUsername();
			boolean personagemDef = false;

			while((current = in.readLine()) != null && !current.equals("JogoTerminou")) {
				personagemDef = l.add(current,user);		//personagemDef é um booleano que indica se a personagem do utilizador ficou, ou não, definida.
				if(!personagemDef) out.println("erro/7/");
				else out.println("persDef");
			}
			if (current == null) 
				this.p.logout();
			
			synchronized(p) {
					this.p.notifyAll();
			}
			
			//in.close();
			//out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}