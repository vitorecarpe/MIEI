
class Espaco implements Runnable{
	private int id;
	private String nome;
	private int port;
	private ServerSocket socket;



}

class Registo{
	private long tempo;
	private String ocasiao; //in ou out
	private String espaco;

	public Registo(long tempo, String ocasiao, String espaco){
		this.tempo = tempo;
		this.ocasiao = ocasiao;
		this.espaco = espaco;
	}

	public String getOcasiao(){
		return this.ocasiao;
	}

	public long getTempo(){
		return this.tempo;
	}

	public String getEspaco(){
		return this.espaco;
	}
}

class RegistoGeral {
	private Map<String,ArrayList<Registo>> registos;
	private Map<String,Integer> servidores;

	public RegistoGeral (){

	}

	public void add (String cliente, long tempo, String espaco, String ocasiao){
		synchronized (this.registos){
			if(!this.registos.containsKey(cliente)) this.registos.put(cliente, new ArrayList <>());
			ArrayList<Registo> regsCliente = this.registos.get(cliente);
		}
		Registo reg = new Registo(tempo,ocasiao,espaco);

	}

	public Registo getUltimoRegisto(String cliente){
		ArrayList<Registo> regsCliente = this.registos.get(cliente);
		if(regsCliente != null) return regsCliente.get(regsCliente.size() - 1);
		else return null;
	}
}

class TreatClient implements Runnable{
	private Servidor server;
	private Socket cs;
	private BufferedReader in;
	private PrintWriter out;

	public TreatClient(Servidor server, Socket cs){
		this.server = server;
		this.cs = cs;
	}

	public void run (){
		try{
			this.in = new BufferedReader (new InputStreamReader(cs.getInputStream()));
			this.out = new PrintWriter(cs.getOutputStream(), true);
			String current;
			try{
				while((current = in.readLine()) != null){
					switch(current){
						case "entrar":
							server.registar()
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

public class Servidor implements Runnable{
	private RegistoGeral geral;
	private String nome;
	private int port;
	private ServerSocket socket;

	public Servidor (String nome, int porta, RegistoGeral geral){
		this.geral = geral;
		this.port = porta;
		this.nome = nome;
	}

	public void run (){
		try{
			this.socket = new ServerSocket(this.port);
			while(true){
				Socket cs = socket.accept();
				System.out.println("Connection received!");
				Thread t = new Thread(new TreatClient(this.geral,cs));
				t.start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main (String [] args){
		RegistoGeral geral = RegistoGeral ();

	}
}