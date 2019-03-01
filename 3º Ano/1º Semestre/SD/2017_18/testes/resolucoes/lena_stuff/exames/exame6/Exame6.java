import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;


class Controlador {
	private int temperatura;
	private int limiar;
	private boolean estado;
	private ReentrantLock lock;

	Controlador() {
		this.temperatura = 0;
		this.limiar = 0;
		this.estado = false;
		this.lock = new ReentrantLock();	}

	public synchronized void temperatura(int centigrados) {

		this.lock.lock();

		try {
			System.out.println("A mudar temperatura para ..." + centigrados);
			this.temperatura = centigrados;
		
			this.notifyAll();
		}
		finally {
			this.lock.unlock();
		}
	}

	public synchronized void limiar(int centigrados) {
		this.lock.lock();

		try {
			System.out.println("A mudar limiar para..." + centigrados);
			this.limiar = centigrados;

			this.notifyAll();
		}
		finally {
			this.lock.unlock();
		}
	}


	synchronized public boolean on_off(boolean estadoatual) {

		boolean save = true;


		// deve bloquear caso o estado fornecido seja idêntico
		// ao estado atual do sistema
		
		try {
			while(estadoatual == false && this.temperatura > this.limiar) this.wait();
			while(estadoatual == true && this.temperatura <= this.limiar) this.wait();
			save = this.estado;

			this.estado = (this.temperatura <= this.limiar);
			System.out.println("A mudar estado atual para ...." + this.estado);

		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		return save != this.estado;
	}
}


class TreatClient implements Runnable {
	private Socket s;
	private Controlador c;

	TreatClient(Socket s, Controlador c) {
		this.s = s;
		this.c = c;
	}

	public void processaPedido(String pedido, PrintWriter out) {
		String[] pedidoP = pedido.split(" ");

		switch(pedidoP[0]) {
			case "temperatura" : c.temperatura(Integer.parseInt(pedidoP[1]));
								 break;
			case "limiar" : c.limiar(Integer.parseInt(pedidoP[1]));
							break;
			case "on_off" : out.println(c.on_off(Boolean.parseBoolean(pedidoP[1])));
							break;
		}
	}


	public void run() {

		String line = null;

		try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			Socket s = this.s;)
		{
			while((line = in.readLine()) != null) {
				processaPedido(line, out);
			}

		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}

public class Exame6 {
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