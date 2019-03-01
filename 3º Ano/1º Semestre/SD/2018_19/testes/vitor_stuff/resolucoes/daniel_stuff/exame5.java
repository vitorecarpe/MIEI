import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.lang.StringBuilder;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;

class Controlador {
	private Map<Integer,Questao> questoes;
	private int nQuestoes;
	private ReentrantLock lock;
	private Condition semPergunta;

	public Controlador (){
		this.questoes = new HashMap <> ();
		this.nQuestoes = 0;
		this.lock = new ReentrantLock ();
		this.semPergunta = lock.newCondition();
	}

	public void adiciona(String pergunta, String resposta){
		this.lock.lock();
		Questao questao = new Questao (pergunta, resposta, nQuestoes);
		this.questoes.put(nQuestoes++,questao);
		this.semPergunta.signalAll();
		this.lock.unlock();
	}

	public Questao obtem(int id){
		Questao questao = null;int i = id + 1;
		boolean flag = false;
		try{	
			while(!flag){
				this.lock.lock();
					for(; i < nQuestoes; i++){
						questao = this.questoes.get(i);
						this.lock.unlock();
						if(questao.valida()) flag = true;
						this.lock.lock();
					}
					if (!flag) this.semPergunta.await();
					this.lock.unlock();
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return questao;
	}
}

class Questao {
	
	private int id;
	private String pergunta;
	private String resposta;
	private int nTentativas;
	private boolean respondidaCorretamente;

	public Questao(String pergunta, String resposta, int id){
		this.id = id;
		this.pergunta = pergunta;
		this.resposta = resposta;
		this.nTentativas = 0;
		this.respondidaCorretamente = false;
	}

	public String getPergunta (){
		return this.pergunta;
	}
 
	public synchronized boolean valida (){
		return (this.respondidaCorretamente == false) && (this.nTentativas <= 10);
	}

	public synchronized String responde (String resposta){
		String res = null;
		this.nTentativas++;
		if (this.resposta.equals(resposta)){
			if (this.respondidaCorretamente == true) res = "R";
			else{
				res = "C";
				this.respondidaCorretamente = true;
			}
		}
		else res = "E";
		return res;
	}

	public int id(){
		return this.id;
	}
}

class Adicionador implements Runnable{
	private Controlador controlador;

	public Adicionador(Controlador c){
		this.controlador = c;
	}

	public void run (){
		String [] pergunta;int i = 0;
		while(true){
			try{
				//pergunta = Util.novaPergunta();
				pergunta = new String [] {"Amas?" + i,"sim"};
				this.controlador.adiciona(pergunta[0],pergunta[1]);
				Thread.sleep(60000);
				i++;
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}

class TreatClient implements Runnable{
	private Socket cs;
	private BufferedReader in;
	private PrintWriter out;
	private Controlador controlador;

	public TreatClient (Controlador c, Socket cs) throws IOException{
		this.cs = cs;
		this.controlador = c;
		this.in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
		this.out = new PrintWriter(cs.getOutputStream(), true);
	}

	private void trataResposta (String status){
		switch(status){
			case "E": out.println("Errada");
					  break;
			case "R": out.println("Respondida");
					  break;
			case "C": out.println("Certa");
					  break;
			default: break;
		}
	}

	public void run(){
		try{
			int id = 0;
			String current;
			while((current = in.readLine()) != null){
				if (current.equals("Pergunta")){
					Questao q = controlador.obtem(id);
					out.println(q.getPergunta());
					String status = q.responde(in.readLine());
					if (!status.equals("E")) id = q.id();
					trataResposta(status);
				}
			}
			cs.shutdownInput();
			cs.shutdownOutput();
			cs.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

public class exame5{
	public static void main (String [] args){
		try{
			Controlador controlador = new Controlador();
			ServerSocket socket = new ServerSocket (12345);
			Thread t = new Thread( new Adicionador (controlador));
			t.start();
			while(true){
				Socket cs = socket.accept();
				t = new Thread(new TreatClient (controlador, cs));
				t.start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}