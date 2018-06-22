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
import java.util.LinkedList;
import java.util.TreeSet;

class Sala{
	private int id;
	private int capacidade;
	//private boolean reservada;
	private boolean paraLimpeza;
	private ReentrantLock lock;

	public Sala(int id){
		this.id = id;
		this.capacidade = 20;
		//this.reservada = false;
		this.paraLimpeza = false;
		this.lock = new ReentrantLock();
	}

	public int getId(){
		return this.id;
	}

	public void lock(){
		this.lock.lock();
	}

	public void unlock(){
		this.lock.unlock();
	}

}

class Teste{
	private int id;
	private boolean comecou;
	private int nPresencas;
	private int nEntregas;
	private ArrayList<Sala> salas;

	public Teste (int id, ArrayList<Sala> salas){
		this.id = id;
		this.comecou = false;
		this.nPresencas = 0;
		this.nEntregas = 0;
		this.salas = salas;
	}

	public synchronized boolean entregar(){
		boolean res = false;
		this.nEntregas++;
		if (this.nEntregas == this.nPresencas) res = true;
		return res;
	}

	public synchronized boolean presenca(){
		boolean res = !comecou;
		try{
			if(res) {
				this.nPresencas++;
				this.wait();
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return res; 
	}

	public synchronized void comecar (){
		this.comecou = true;
		this.notifyAll();
	}

	public ArrayList<Sala> getSalas (){
		return this.salas;
	}
}

class Controlador {
	private Map<Integer,Sala> salas;
	private LinkedList<Sala> paraLimpeza;
	private Map<Integer,Teste> testes;

	public Controlador(){
		this.salas = new HashMap <> ();
		for(int i = 0; i < 10; i++){
			Sala sala = new Sala(i);
			this.salas.put(i,sala);
		}
		this.paraLimpeza = new LinkedList <> ();
		this.testes = new HashMap <> ();
	}

	public int comecar_limpeza(){
		Sala sala = null;
		try{
			synchronized(this.paraLimpeza){
				while(this.paraLimpeza.size() == 0) this.paraLimpeza.wait();
				sala = this.paraLimpeza.pop();
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		//mantem o lock preso até terminar a limpeza
		sala.lock();
		return sala.getId();
	}

	public void terminar_limpeza (int salaId){
		Sala sala = null;
		synchronized(this.paraLimpeza){
			sala = this.salas.get(salaId);
			if(sala != null) sala.unlock();
		}
		//com o lock detido(o lock é reentrante)
		//sala.lock();
		//sala.setReservada(false);
		sala.unlock();
	}

	public Teste reserva(int testeId, int [] salaIds){
		TreeSet<Integer> sIds = new TreeSet <Integer> ();
		ArrayList<Sala> salas = new ArrayList<>();
		for(int i = 0; i < salaIds.length; i++) sIds.add(salaIds[i]);
		synchronized(this.salas){
			for(Integer id : sIds){
				Sala s = this.salas.get(id);
				s.lock();
				salas.add(s);
			}
		}
		Teste teste = null;
		synchronized(this.testes){
			if(!this.testes.containsKey(testeId)){
				teste = new Teste(testeId,salas);
				this.testes.put(testeId,teste);
			}
		}
		return teste;
	}

	public boolean presenca (int testeId){
		Teste teste = null;
		synchronized(this.testes){
			teste = this.testes.get(testeId);
		}
		return teste.presenca();
	}

	public void adicionaSalaLimpeza(Teste teste){
		ArrayList<Sala> salas = teste.getSalas();
		synchronized(this.paraLimpeza){
			for(Sala s: salas){
				this.paraLimpeza.add(s);
			}
			this.paraLimpeza.notifyAll();
		}
	}

	public void entrega (int testeId){
		Teste teste = null;
		synchronized(this.testes){
			teste = this.testes.get(testeId);
		}
		boolean acabou = teste.entregar();
		if (acabou){
			synchronized(teste){
				teste.notifyAll();
			}
		}
	}
}

class TreatClient implements Runnable{
	private Socket cs;
	private Controlador controlador;
	private BufferedReader in;
	private PrintWriter out;

	public TreatClient (Controlador controlador, Socket cs){
		this.cs = cs;
		this.controlador = controlador;
	}

	public void run (){
		try{
			in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			out = new PrintWriter(cs.getOutputStream(), true);
			String current;int testeId;
			try{
				while((current = in.readLine()) != null){
					switch(current){
						case "reserva":
							testeId = Integer.parseInt(in.readLine());
							String [] tokens = (in.readLine()).split(" ");
							int [] salas = new int [tokens.length];
							for(int i = 0; i < tokens.length; i++) salas[i] = Integer.parseInt(tokens[i]); 
							Teste t = this.controlador.reserva(testeId,salas);
							System.out.println("Salas reservadas");
							out.println("Salas reservadas");
							Thread.sleep(60000);//1 mins
							t.comecar();
							out.println("Teste Comecou");
							synchronized(t){
								t.wait();
							}
							controlador.adicionaSalaLimpeza(t);
							break;
						case "presenca":
							testeId = Integer.parseInt(in.readLine());
							boolean presenca = this.controlador.presenca(testeId);
							out.println("Presente: " + presenca);
							System.out.println("Presente: " + presenca);
							break;
						case "entrega":
							testeId = Integer.parseInt(in.readLine());
							this.controlador.entrega(testeId);
							out.println("Entregue");
							System.out.println("Entregue");
							break;
						case "comecar_limpeza":
							testeId = this.controlador.comecar_limpeza();
							out.println("Comecar limpeza: " + testeId);
							System.out.println("Comecar limpeza: " + testeId);
							break;
						case "terminar_limpeza":
							testeId = Integer.parseInt(in.readLine());
							this.controlador.terminar_limpeza(testeId);
							out.println("Fim de limpeza de sala");
							System.out.println("Fim de limpeza de sala");
							break;
						default:
							out.println("Operação Inválida");
							break;
					}	
				}
				System.out.println("Connection Closed!");
				this.cs.shutdownInput();
				this.cs.shutdownOutput();
				this.cs.close();
			}
			catch(NumberFormatException e){out.println("Argumento Inválido");}
			catch(InterruptedException e){e.printStackTrace();}
		}catch(IOException e){
			e.printStackTrace();
		}

	}
}

public class exame2016{
	public static void main (String [] args){
		try{
			ServerSocket socket = new ServerSocket(12345);
			Controlador controlador = new Controlador();

			while(true){
				Socket cs = socket.accept();
				System.out.println("Connection Accepted!!");
				Thread t = new Thread( new TreatClient (controlador,cs));
				t.start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}