package banco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

public class ServerBanco {

	private static Banco banco;
	private static boolean keepRunning = true;

	public static void main(String[] args) throws IOException {
		TreeMap<Integer, Conta> contas = criarDadosBanco();

		banco = new Banco(contas);

		runSocketBanco();


	}

	public static synchronized void stopServer() {
		keepRunning = false;
	}

	public static synchronized boolean serverRunning() {
		return keepRunning;
	}

	public static void runSocketBanco() throws IOException {

		ServerSocket server = new ServerSocket();
		server.setReuseAddress(true);
		server.bind(new InetSocketAddress(6063));

		do {
			runSocketRequest(server);
		} while (serverRunning());
	}

	public static void runSocketRequest(ServerSocket server) throws IOException {
		Socket skt = server.accept();
	
		new Thread(new ServerBancoThread(banco, skt)).start();
	}

	

	public static TreeMap<Integer, Conta> criarDadosBanco() {
		TreeMap<Integer, Conta> contas = new TreeMap<Integer, Conta>();
		contas.put(1, new Conta(1, 2.0));
		contas.put(2, new Conta(2, 1.0));
		contas.put(3, new Conta(3, 5.0));
		contas.put(4, new Conta(4, 100.5));
		contas.put(5, new Conta(5, 0.0));

		return contas;
	}
}
