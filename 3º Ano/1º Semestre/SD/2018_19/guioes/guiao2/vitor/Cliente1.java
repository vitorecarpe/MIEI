public class Cliente1 implements Runnable{
	private Banco banco;

	public Cliente1(Banco b){
		this.banco=b;
	}

	public void run(){
		for (int i=0;i<1000;i++){
			this.banco.depositar(0,5);
		}
	}
}