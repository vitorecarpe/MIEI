/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naps62
 */
public class ServerBancoThread implements Runnable {

	private static enum Operation {
		error,
		shutdownServer, /** desliga o servidor */
		close, /** desliga a conexao do cliente */
		setSaldo,
		getSaldo,
		deposito,
		levantamento,
		transferencia,
		balanco
	}

	private BufferedReader sktInput;
	private PrintWriter sktOutput;
	private Operation resultFlag;
	private Banco banco;
	Socket skt;

	ServerBancoThread(Banco banco, Socket skt) {
		this.skt = skt;
		this.banco = banco;

		try {
			sktInput = new BufferedReader(new InputStreamReader(skt.getInputStream()));
			sktOutput = new PrintWriter(skt.getOutputStream());
		} catch (IOException ex) {
			Logger.getLogger(ServerBancoThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void run() {
		System.out.println("Connection started from: " + skt.getInetAddress());


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

			skt.close();

		} catch (IOException ex) {
			Logger.getLogger(ServerBancoThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String handleRequest(String line) {
		String[] words = line.split(" ");
		int conta = 0;
		double val = 0;

		try {
			if (words.length > 1) {
				conta = Integer.parseInt(words[1]);
			}
			if (words.length > 2) {
				val = Double.parseDouble(words[2]);
			}
		} catch (NumberFormatException e) {
		}

		if (words[0].equals(Operation.shutdownServer.toString())) {
			resultFlag = Operation.shutdownServer;
			ServerBanco.stopServer();
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
		} else if (words[0].equals(Operation.balanco.toString())) {
			resultFlag = Operation.balanco;
			return "Balanco actual: " + banco.balanco();
		} else {
			resultFlag = Operation.error;
			return "Operação Inválida";
		}
	}
}
