public class Shuttle implements Controlador{
	
	int npass;
	int terminal;
	boolean movimento;
	ReentrantLock lock;
	ArrayList<Condition> conds;

	public Shuttle(int np, int t, boolean m){
		npass = np;
		terminal = t;
		movimento = m;
		lock = new ReentrantLock();
		conds = new ArrayList<>(lock.newCondition());
		condMinPass = lock.newCondition();
	}

	void requisita_viagem(int origem, int destino){
		lock.lock();
		
		while((terminal!=origem) ||
			  (npass<10) ||
			  (npass>30) ||
			  (movimento)){
			conds.get(origem).await();
		}
		npass++;

		lock.unlock();
	}

	void espera(int destino){
		lock.lock();

		while(terminal!=destino ||
			  movimento){
			conds.get(destino).await();
		}

		npass--;

		lock.unlock();
	}

	void chegada(){
		lock.lock();

		movimento=false;
		terminal=(terminal+1)%5;
		conds.get(terminal).signalAll();

		lock.unlock();
	}

	void partida(){
		lock.lock();

		movimento=true;

		lock.unlock();
	}
}



public class WorkerShuttle implements Runnable{
	Controlador shuttle = new Shuttle(0, 1, false);

	public WorkerShuttle(Controlador s){
		this.shuttle=s;
	}

	public void run(){
		while(true){
			partida();
			Thread.sleep(5*60);
			chegada();
			Thread.sleep(2*60);
		}
	}
}

public class WorkerClient implements Runnable{
	Socket sc = new Socket();

	public WorkerClient(Socket s){
		this.sc = s;
	}

	public void run(){
		ObjectInputStream in = ...;
		ObjectOutputStream out = ...;

		out.println("Onde se encontra?");
		String origem = in.readLine();
		out.println("Para onde pretende viajar?");
		String destino= in.readLine();

		shuttle.requisita_viagem(origem, destino);
		shuttle.espera(destino);

		in.close();
		out.close();
	}
}

public class Servidor {
	public static void main(){
		ServerSocket ss = new ServerSocket(5000);
		Controlador shuttle = new Shuttle(0,1,false);

		new Thread(new WorkerShuttle(shuttle)).start();

		while(true){
			Socket sc = ss.accept();
			new Thread(new WorkerClient(sc)).start();
		}

	}
}
