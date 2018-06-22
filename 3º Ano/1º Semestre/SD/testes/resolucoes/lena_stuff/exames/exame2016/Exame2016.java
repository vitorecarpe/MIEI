import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

class Teste {
	private int id;
	private int[] salas;
	private boolean jaComecou;
	private ReentrantLock lock;

	public Teste(int id, int nrInscritos) {
		this.id = id;
		this.salas = null;
		this.jaComecou = false;
		this.lock = new ReentrantLock();
	}

	public int[] getSalas() {
		return this.salas;
	}

	public void setSalas(int[] salas) {
		this.salas = salas;
	}

	public boolean jaComecou() {
		return this.jaComecou;
	}

	public void jaComecou(boolean bool) {
		this.jaComecou = bool;
	}

	public void lock() {
		this.lock.lock();
	}

	public void unlock() {
		this.lock.unlock();
	}

	public boolean isLocked() {
		return this.lock.isLocked();
	}
}

class Sala {
	private int id;
	private int nrAlunos;
	private ReentrantLock lock;

	public int getId() {
		return this.id;
	}

	public void addAluno() {
		this.lock.lock();
		this.nrAlunos++;
		this.lock.unlock();
	}

	public boolean removeAluno() {
		this.lock.lock();
		this.nrAlunos--;
		this.lock.unlock();

		return (this.nrAlunos == 0);
	}

	public boolean cheia() {
		return this.nrAlunos == 20;
	}

	public boolean vazia() {
		return this.nrAlunos == 0;
	}

	public void lock() {
		this.lock.lock();
	}

	public void unlock() {
		this.lock.unlock();
	}

	public boolean isLocked() {
		return this.lock.isLocked();
	}
}

class Controlador {
	Map<Integer, Teste> testes;
	Map<Integer, Sala> salas;
	ReentrantLock lock;
	Condition livre;
	Condition iniciado;
	Condition limpar;

	Controlador() {
		this.testes = new HashMap<Integer, Teste>();
		this.salas = new HashMap<Integer, Sala>();
		this.lock = new ReentrantLock();
		this.livre = lock.newCondition();
		this.iniciado = lock.newCondition();
		this.limpar = lock.newCondition();
	}


	boolean disponiveis(int[] salaIds) {
		boolean flag = true;
		for(int id : salaIds)
			if(salas.get(id).isLocked()) flag = false;
		return flag;
	}

	void reserva(int testeId, int[] salaIds) {
		
		try {
			// deverá bloquear enquanto todas as salas pretendidas não se encontrarem livres
			while(!disponiveis(salaIds)) livre.await();

			testes.get(testeId).setSalas(salaIds);

			for (int id : salaIds)
				this.salas.get(id).lock();

			// dez minutos após a reserva o teste tenta iniciar;
			Thread.sleep(10 * 60 * 1000);
			iniciado.signal();
			testes.get(testeId).jaComecou(true);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}


	boolean presenca(int testeId) {
		// deverá bloquear até se iniciar o teste, retornando false em caso de atraso
		boolean flag = false;
		try {
			while(testes.get(testeId).jaComecou() == false) {
				flag = true;
				iniciado.await();
			}

			for(int id : this.testes.get(testeId).getSalas()) {
				Sala s = this.salas.get(id);
				if(s.cheia()) continue;
				else {
					s.addAluno();
					break;
				}
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		return flag;
	}

	int salaLimpar() {
		Sala ret = null;

		for(Sala s : this.salas.values()) {
			if (s.isLocked() && s.vazia()) {
				ret = s;
				break;
			}
		}
		if (ret == null) return -1;
		else return ret.getId();
	}


	void entrega(int testeId) {

		for(int id : this.testes.get(testeId).getSalas()) {
			Sala s = this.salas.get(id);
			if(!s.isLocked()) continue;
			else {
				boolean flag = s.removeAluno();
				if(flag == true) limpar.signal();
				break;
			}
		}
	}


	int comecar_limpeza() {
		int s = 0;
		try {
			while((s = salaLimpar()) == -1) limpar.await();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		return s;
	}


	void terminar_limpeza(int salaId) {
		this.salas.get(salaId).unlock();
		livre.signal();
	}
}



class TreatClient implements Runnable {
	Socket s;
	Controlador c;

	TreatClient(Socket s, Controlador c) {
		this.s = s;
		this.c = c;
	}

	public void processaPedido(String line) {
		String[] msg = line.split(" ");

		switch(msg[0]) {
			case "reserva" : int[] salas = new int[msg.length - 2];
							 for(int i = 2; i < msg.length; i++)
							 	salas[i] = Integer.parseInt(msg[i]);
							 c.reserva(Integer.parseInt(msg[1]), salas);
							 break;
			case "presenca" : c.presenca(Integer.parseInt(msg[1]));
							  break;
			case "entrega" : c.entrega(Integer.parseInt(msg[1]));
							 break;
			case "comecar_limpeza" : c.comecar_limpeza();
									 break;
			case "terminar_limpeza" : c.terminar_limpeza(Integer.parseInt(msg[1]));
									  break;
		}
	}

	public void run() {
		String line = null;

		try(BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			Socket s = this.s;)
		{
			while((line = in.readLine()) != null)
				processaPedido(line);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		System.out.println(this.s.isClosed());
	}
}

public class Exame2016 {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(12345);
		Socket s = null;
		Controlador c = new Controlador();

		while(true) {
			s = ss.accept();

			Thread t = new Thread(new TreatClient(s, c));

			t.start();
		}
	}
}