package banco;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Thread_TestaBanco2 extends Thread {

	private Banco banco;
	private Conta conta;

	public Thread_TestaBanco2(Conta c) {
		conta = c;
	}

	public void run() {
		conta.deposito(50);
		System.out.println("Foram depositados 50");
		conta.deposito(100);
		System.out.println("Foram depositados 100");
		//		for (int i = 0; i < Banco.MAX_CONTAS; i++) {
		//			banco.transferencia(i, (i + 1) % Banco.MAX_CONTAS, 10);
		//		}
	}
}
