import java.net.Socket;

import jdk.internal.org.jline.utils.InputStreamReader;

public class Shuttle implements Controlador{
    private int terminal;
    private int npass;
    private boolean emAndamento;
    private ReentrantLock l;
    private ArrayList<Condition> cond;
    private Condition minPass;

    public Shuttle(){
        this.terminal = 1;
        this.npass = 0;
        this.emAndamento = false;
        this.l = new ReentrantLock();
        this.cond = new ArrayList<>(this.l.newCondition());
        this.minPass = this.l.newCondition();
    }

    void requisita_viagem(int origem, int destino){
        this.l.lock();

        while(origem != this.terminal || this.pass > 30 || this.emAndamento){
            this.cond.get(origem).await();
        }
        this.npass++;
        this.minPass.signal();
        this.l.unlock();
    }

    public void espera(int destino){
        this.l.lock();

        while(destino != this.terminal || this.emAndamento){
            this.cond.get(destino).await();
        }
        this.npass--;
        this.l.unlock();
    }

    public void chegada(){
        this.l.lock();
        this.emAndamento = false;
        this.cond.get(this.terminal).signalAll();
        this.l.unlock();
    }

    public void partida(){
        this.l.lock();
        while(this.npass < 10) this.minPass.await();
        this.emAndamento = true;
        this.l.unlock();
    }
}

public class WorkerShuttle implements Runnable{

    Shuttle sh; 

    public WorkerShuttle(Shuttle sh){
        this.sh = sh;
    }

	public void run(){
		while(true){
			shuttle.partida();
			Thread.sleep(5*60);
			shuttle.chegada();
		}
	}
}

public class WorkerClient implements Runnable{
    Socket sc = new Socket(localhost, 5000);
    Shuttle sh;

	public WorkerClient(Socket s, Shuttle sh){
        this.sc = s;
        this.sh = sh;
	}

	public void run(){
		BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
		PrintWriter out = new PrintWriter(sc.getOutputStream());

        out.println("Onde se encontra?");
        out.flush();
		String origem = in.readLine();
        out.println("Para onde pretende viajar?");
        out.flush();
		String destino= in.readLine();

		shuttle.requisita_viagem(origem, destino);
		shuttle.espera(destino);

        sc.shutdownOutput();
        sc.shutdownInput();
		sc.close();
	}
}

public class Servidor {
	public static void main(){
		ServerSocket ss = new ServerSocket(5000);
		Shuttle shuttle = new Shuttle();

		new Thread(new WorkerShuttle(shuttle)).start();

		while(true){
			Socket sc = ss.accept();
			new Thread(new WorkerClient(sc, shuttle)).start();
		}
	}
}
