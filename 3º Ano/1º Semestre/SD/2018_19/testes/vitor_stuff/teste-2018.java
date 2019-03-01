// TESTE 2018


////////// 1 //////////

public class Control implements Controlador{

	public int npass=0;
	public int posicao=0;
	public boolean emviagem=false;
	public ReentrantLock lock = new ReentrantLock();
	public List<Condition> estacao = new List<>(lock.newCondition());

	public void quero_viajar(int origem){
		lock.lock();
		while(origem!=posicao || emviagem || npass>=20)
			estacao.get(origem).await();
		npass++;
		lock.unlock();
	}

	public void quero_sair(int destino){
		lock.lock();
		while(destino!=posicao || emviagem)
			cond.get(destino).await();
		npass--;
		lock.unlock();
	}

	public void partida(){
		lock.lock();
		emviagem=true;
		lock.unlock();
	}

	public void chegada(){
		lock.lock();
		if(posicao<3) posicao++;
		else posicao=0;
		emviagem=false;
		cond.get(posicao).signalAll();
		lock.unlock();
	}
}

////////// 2 //////////

public class ShuttleControlador implements Runnable {
	private Controlador controlador;

	public ShuttleControlador(Controlador c){
		this.controlador = c;
	}

	public void run(){
		while(true){
			controlador.partida();
			//PARTIU
			Thread.sleep(3*60);
			controlador.chegada();
			//CHEGOU
			Thread.sleep(1*60);
		}
	}
}

////////// EXTRA //////////

public class PassageiroControlador implements Runnable {
	private Controlador controlador;
	private Socket socket;
	private String origem, destino;

	public PassageiroControlador(Controlador c, Socket sc){
		this.controlador = c;
		this.socket = sc;
	}

	public void run(){
		BufferedReader in = ...;
		PrintWriter out   = ...;

		out.println("Onde se encontra?");
		origem = in.readLine();
		out.println("Para onde quer ir?");
		destino= in.readLine();

		while(true){

			out.printLn("Que deseja fazer?");

			if(in.readLine().equals("sair")){
				controlador.quero_sair(destino);
				break;
			}
			else if(in.readLine().equals("viajar"))
				controlador.quero_viajar(origem);
		}
	}
}

public class Servidor{
	public static void main(){
		ServerSocket ss = new ServerSocket(4444);
		Controlador c = new Control();

		new Thread(new ShuttleSession(c)).start();		

		while(true){
			Socket sc = ss.accept();
			new Thread(new ClientSession(c, sc)).start();
		}
	}
}


