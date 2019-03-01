// EXAME 2018


////////// 1 //////////

public class Partida implements Jogo{
	
	ReentrantLock lock = new ReentrantLock();
	Condition espera = lock.newCondition();
	List<String> jogadores = new List<>();

	public List<String> inscrever(String nome){
		this.lock.lock();
		List<String> jogadoresCP = new List<>();
		try{
			jogadores.add(nome);

			LocalDateTime tempo = LocalDateTime.now().plusMinutes(1);

			while(!podeComecar(tempo)){
				espera.await();
			}
			espera.signalAll();
			jogadoresCP = this.jogadores;

		} finally{
			this.lock.unlock();
		}

		return jogadoresCP;

	}

	public boolean podeComecar(LocalDateTime tempo){
		boolean ret=false;

		LocalDateTime now = LocalDateTime.now();

		if(jogadores.size()==30 ||
		   jogadores.size()>=20 &&
		   jogadores.size()%2==0 &&
		   now-tempo>60) ret = true;

		return ret;
	}
}


////////// 2 //////////


public class Servidor {

	public static void main(String[] args) {
		
		ServerSocket ss = new ServerSocket(4444);
		Partida partida = new Partida();

		while(true){
			Socket sc = ss.accept();
			new Thread(new Session(partida, sc)).start();
		}
	}
}

public class Session implements Runnable{
	private Socket socket;
	private Partida partida;

	public Session(Partida pa, Socket sc){
		this.socket = sc;
		this.partida= pa;
	}

	public void run(){
		try{
			BufferedReader in = ...;
			PrintWriter out   = ...;

			String mensagem;

			while((mensagem=in.readLine()) != null)
				out.println(partida.inscrever(mensagem));

		} catch(IOException exception){}
	}
}







