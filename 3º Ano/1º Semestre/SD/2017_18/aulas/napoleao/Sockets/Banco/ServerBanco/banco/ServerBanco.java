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

	private static enum Operation {
		error,
		shutdownServer, /** desliga o servidor */
		close,          /** desliga a conexao do cliente */
		setSaldo,
		getSaldo,
		deposito,
		levantamento,
		transferencia,
		balanco
	}
	private static Banco banco;
	private static Operation resultFlag;
	private static BufferedReader sktInput;
	private static PrintWriter sktOutput;

	public static void main(String[] args) throws IOException {
		TreeMap<Integer, Conta> contas = criarDadosBanco();

		banco = new Banco(contas);

		runSocketBanco();


	}

	public static void runSocketBanco() throws IOException {

		ServerSocket server = new ServerSocket();
		server.setReuseAddress(true);
		server.bind(new InetSocketAddress(6063));

		do {
			runSocketRequest(server);
		} while (resultFlag.equals(Operation.shutdownServer) == false);
	}

	public static void runSocketRequest(ServerSocket server) throws IOException {
		Socket skt = server.accept();

		System.out.println("Connection started from: " + skt.getInetAddress());
		sktInput = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		sktOutput = new PrintWriter(skt.getOutputStream());

		String line;
		String result;
		try {
			do {
				line = sktInput.readLine();
				System.out.println("  Request: " + line);
				result = handleRequest(line);
				System.out.println("  Response: " + result);
				sktOutput.println(result);
				sktOutput.flush();
			} while (resultFlag.equals(Operation.shutdownServer) == false && resultFlag.equals(Operation.close) == false);
		} catch (NullPointerException e) {
			
		}

		skt.close();
	}

	public static String handleRequest(String line) {
		String[] words = line.split(" ");
		int conta = 0;
		double val = 0;
		try {
		if (words.length > 1)
			conta = Integer.parseInt(words[1]);
		if (words.length > 2)
			val = Double.parseDouble(words[2]);
		} catch (NumberFormatException e) {
		}

		if (words[0].equals(Operation.shutdownServer.toString())) {
			resultFlag = Operation.shutdownServer;
			return "Closing Connection...\nServer ShutingDown...\n\nGoodbye";
		} else if (words[0].equals(Operation.close.toString())) {
			resultFlag = Operation.close;
			return "Closing Connection...";
		} else if (words[0].equals(Operation.getSaldo.toString())) {
			resultFlag = Operation.getSaldo;
			return "Saldo da conta " + conta + ": " + banco.consultaSaldo(conta);
		} else if (words[0].equals(Operation.setSaldo.toString())) {
			resultFlag = Operation.setSaldo;
			banco.setSaldo(conta, val);
			return "Saldo da conta " + conta + " actualizado para " + val;
		} else if (words[0].equals(Operation.levantamento.toString())) {
			resultFlag = Operation.levantamento;
			banco.levantamento(conta, val);
			return "Levantadas " + val + " unidades da conta " + conta;
		} else if (words[0].equals(Operation.deposito.toString())) {
			resultFlag = Operation.deposito;
			banco.deposito(conta, val);
			return "Depositadas " + val + " unidades na conta " + conta;
		} else if (words[0].equals(Operation.transferencia.toString())) {
			resultFlag = Operation.transferencia;
			int conta1 = Integer.parseInt(words[1]);
			int conta2 = Integer.parseInt(words[2]);
			val = Double.parseDouble(words[3]);
			banco.transferencia(conta1, conta2, val);
			return "Transferidas " + val + " unidades da conta " + conta1 + " para a conta " + conta2;
		} else if(words[0].equals(Operation.balanco.toString())) {
			resultFlag = Operation.balanco;
			return "Balanco actual: " + banco.balanco();
		} else {
			resultFlag = Operation.error;
			return "Operação Inválida";
		}
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
