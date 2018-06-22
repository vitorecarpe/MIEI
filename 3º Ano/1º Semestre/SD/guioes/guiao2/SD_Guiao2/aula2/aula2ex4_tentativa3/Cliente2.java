package aula2ex4_tentativa3;

public class Cliente2 implements Runnable {
	
	private Banco banco;

	public Cliente2(Banco b){
		this.banco=b; 
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+": Transferência da conta 1 para conta 0 iniciada");
		this.banco.transferir(1,0,1000);
		System.out.println(Thread.currentThread().getName()+": Transferência da conta 1 para conta 0 realizada");
	} 

}