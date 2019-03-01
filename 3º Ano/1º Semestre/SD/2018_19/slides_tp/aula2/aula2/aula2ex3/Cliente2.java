package aula2ex3;

public class Cliente2 implements Runnable {

	private Banco banco;

	public Cliente2(Banco b){
		this.banco=b; 
	}

	@Override
	public void run() {
		//levanta 1000 euros da conta 1
		this.banco.levantar(1,1000);		
	} 
}