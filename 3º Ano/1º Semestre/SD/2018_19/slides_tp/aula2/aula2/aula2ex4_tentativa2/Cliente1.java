package aula2ex4_tentativa2;

public class Cliente1 implements Runnable {
	
	private Banco banco;

	public Cliente1(Banco b){
		this.banco=b; 
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+": Transferência da conta 0 para conta 1 iniciada");
		this.banco.transferir(0,1,1000);
		System.out.println(Thread.currentThread().getName()+": Transferência da conta 0 para conta 1 realizada");
		
	} 

}
