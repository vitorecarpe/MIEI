package aula2ex4_solucao;

public class Cliente2 implements Runnable {
	
	private Banco banco;

	public Cliente2(Banco b){
		this.banco=b; 
	}
	
	@Override
	public void run() {
		System.out.println("Cliente 2: Transferência iniciada");
		this.banco.transferir(1, 0, 1000);
		System.out.println("Cliente 2: Transferência realizada");
		
	} 

}