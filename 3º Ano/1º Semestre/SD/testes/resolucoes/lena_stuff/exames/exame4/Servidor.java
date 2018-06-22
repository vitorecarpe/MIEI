import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

class TreatClient implements Runnable {
	private Socket s;
	private int espaco_id;
	private int port_id;
	private ArrayList<String> serverList;
	private PrintWriter out;
	private BufferedReader in;
	private boolean connected;


	TreatClient(Socket s, int espaco_id, int port_id, ArrayList<String> servers) {
		this.s = s;
		this.espaco_id = espaco_id;
		this.port_id = port_id;
		this.serverList = servers;
		this.connected = true;
	}

	void regista_presenca() {
		String username = null;

		try {
			out.println("O seu nome?");
			username = in.readLine();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		synchronized(Presencas.map) {
			Presenca p = new Presenca(username, this.espaco_id);
	
			ArrayList<Presenca> pres = Presencas.map.get(username);
	
			if(pres == null)
				pres = new ArrayList<Presenca>();
			
			pres.add(p);
			Presencas.map.put(username, pres);
		}
	}

	void server_list() {
		for(String s : serverList)
			out.println(s);
	}

	void encontra_user(String user) {
		ArrayList<Presenca> pres = null;

		synchronized(Presencas.map) {
			pres = Presencas.map.get(user);
		}

		if(pres == null)
			out.println("O utilizador especificado não existe!");
		else {
			Presenca p = pres.get(pres.size() - 1);
			
			synchronized(p) {
				out.println(p.getUser() + ":");
				out.println("Servidor: " + p.getEspacoId());
				out.println("Hora entrada: " + p.getEntrada());
			}
		}
	}

	void mudar_server(String server) {
		out.println("A mudar o servidor...");

		try {

			this.connected = false;

			new Socket(server, 9999);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	void processa_pedido(String msg) {
		String[] line = msg.split(" ");

		switch(line[0]) {
			case "server_list" : server_list();
								 break;
			case "mudar_server" : mudar_server(line[1]);
								  break;
			case "encontra_user" : encontra_user(line[1]);
								   break;
		}
	}

	public void run() {

		String input = null;

		try (Socket s = this.s;)
		{
			this.out = new PrintWriter(s.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			System.out.println(espaco_id);

			regista_presenca();

			while(connected && (input = in.readLine()) != null) {
				processa_pedido(input);
			}

			this.in.close();
			this.out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}


public class Servidor {
	public static void main(String[] args) throws IOException {
		int espaco_id = Integer.parseInt(args[0]);
		int porto_ip = Integer.parseInt(args[1]);

		ArrayList<String> servers = new ArrayList<String>();

		for(int i = 2; i < args.length; i++)
			servers.add(args[i]);

		ServerSocket ss = new ServerSocket(porto_ip);
		Socket s = null;

		while(true) {
			s = ss.accept();
			Thread t = new Thread(new TreatClient(s, espaco_id, porto_ip, servers));
			t.start();
		}
	}
}