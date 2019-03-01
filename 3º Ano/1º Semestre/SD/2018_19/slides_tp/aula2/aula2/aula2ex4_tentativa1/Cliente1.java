package aula2ex4_tentativa1;

public class Cliente1 implements Runnable {
	
	private Banco banco;

	public Cliente1(Banco b){
		this.banco=b; 
	}
	
	@Override
	public void run() {
		//Transferencia de conta 0 para 1 de 1000€
		this.banco.transferir(0,1,1000);
	} 

}
