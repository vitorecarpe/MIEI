package aula2ex4_solucao;

public class Cliente1 implements Runnable {
	
	private Banco banco;

	public Cliente1(Banco b){
		this.banco=b; 
	}
	
	@Override
	public void run() {
		System.out.println("Cliente 1: Transferência iniciada");
		this.banco.transferir(0,1,1000);
		System.out.println("Cliente 1: Transferência realizada");
		
	} 

}
