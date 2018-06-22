import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class TreatClient implements Runnable {
	private Socket s;
	private int pedidos;
	private Matrix jogo;

	TreatClient(Socket s, Matrix m) {
		this.s = s;
		this.pedidos = 0;
		this.jogo = m;
	}


	public void trataPedido(String msg) {
		String[] res = msg.split(" ");
		jogo.pedido(res[0], Integer.parseInt(res[1]), Integer.parseInt(res[2]), ++pedidos);
	}


	@Override 
	public void run() {

		String rd = null;


		try (Socket s = this.s;
			 PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));)
		{

			while(pedidos < 3 && (rd = in.readLine()) != null)
				trataPedido(rd);

			pw.println("Acabaram os seus pedidos aguarde...");

			synchronized (jogo) {
				while(!jogo.jogoAcabou()) jogo.wait();
			}

			pw.println("O vencedor foi: " + jogo.getVencedor());
		}
		catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}