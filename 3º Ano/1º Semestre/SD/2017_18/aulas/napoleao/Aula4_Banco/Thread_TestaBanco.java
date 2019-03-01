package aula4_banco;

public class Thread_TestaBanco extends Thread {

	private Banco banco;
	private Conta conta;
	private double param1;

	public Thread_TestaBanco(Conta c) {
		conta = c;
	}

	public void run() {
		System.out.println("A espera para levantar 100");
		conta.levantamento(100);
		System.out.println("Foram levantados 100");


//		for(int i = 0; i < Banco.MAX_CONTAS; i++) {
//			banco.deposito(i, param1);
//		}
	}

}
