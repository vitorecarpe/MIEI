package aula2ex3;

public class Cliente1 implements Runnable {

	private Banco banco;

	public Cliente1(Banco b){
		this.banco=b; 
	}

	@Override
	public void run() {
		//transferir 1000 euros da conta 0 para a conta 1
		this.banco.transferir(0,1,1000);		
	} 

}
