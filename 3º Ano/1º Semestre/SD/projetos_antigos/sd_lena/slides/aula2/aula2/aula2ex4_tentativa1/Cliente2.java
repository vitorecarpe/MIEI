package aula2ex4_tentativa1;

public class Cliente2 implements Runnable {
	
	private Banco banco;

	public Cliente2(Banco b){
		this.banco=b; 
	}
	
	@Override
	public void run() {
		// Consultar saldo da conta 1
		System.out.println("Cliente2: O saldo da conta 1 é " + this.banco.consultar(1));		
		System.out.println("Cliente2: O saldo da conta 0 é " + this.banco.consultar(0));		
	}

}