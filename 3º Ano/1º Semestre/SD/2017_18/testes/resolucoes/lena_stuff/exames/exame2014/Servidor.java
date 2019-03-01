import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

class Controlador {
	private int nrCurrEnc;
	private int kgCurrEnc;
	private int entraram;
	private int sairam;
	private ReentrantLock lock;
	private Condition maior200;

	public Controlador() {
		this.nrCurrEnc = 1;
		this.kgCurrEnc = 0;
		this.entraram = 0;
		this.sairam = 0;
		this.lock = new ReentrantLock();
		this.maior200 = lock.newCondition();
	}

	public int pedido(int kg) {
		int ret = -1;

		this.lock.lock();

		this.entraram++;

		try {
			this.kgCurrEnc += kg;

			while(this.kgCurrEnc < 200) {
				maior200.await();
			}

			maior200.signalAll();

			this.sairam++;

			ret = this.nrCurrEnc;

			if (this.entraram == this.sairam) novaEncomenda();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			this.lock.unlock();
		}
		return ret;
	}

	public void novaEncomenda() {
		this.lock.lock();

		try {
			this.kgCurrEnc = 0;
			this.nrCurrEnc++;
		}
		finally {
			this.lock.unlock();
		}
	}
}

class TreatClient implements Runnable {
	private Socket s;
	private Controlador c;

	TreatClient(Controlador c, Socket s) {
		this.c = c;
		this.s = s;
	}

	public void run() {

		try (Socket s = this.s;
			 PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));)
		{
			out.println("Quantos kg tem a sua encomenda?");

			int kg = Integer.parseInt(in.readLine());

			int nr = c.pedido(kg);

			out.println("O número do transporte em que a sua encomenda seguiu foi o " + nr);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}


public class Servidor {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(12345);
		Socket s = null;
		Controlador c = new Controlador();

		while(true) {
			s = ss.accept();
			Thread t = new Thread(new TreatClient(c, s));
			t.start();
		}
	}
}