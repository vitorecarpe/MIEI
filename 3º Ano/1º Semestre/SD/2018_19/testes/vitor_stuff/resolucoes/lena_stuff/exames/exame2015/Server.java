import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(12345);

		Map<String, Integer> jogadores = new HashMap<String, Integer>();

		jogadores.put("joao", 0);
		jogadores.put("maria", 0);

		Socket s = null;
		Matrix m = new Matrix(1000, 1000, jogadores);

		m.iniciar();

		while(true) {
			s = ss.accept();

			Thread t = new Thread(new TreatClient(s, m));

			t.start();
		}
	}
}