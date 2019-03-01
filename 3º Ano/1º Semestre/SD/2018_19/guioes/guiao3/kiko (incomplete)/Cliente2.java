public class Cliente2 implements Runnable{
	private Banco banco;

	public Cliente2(Banco b){
		this.banco=b;
	}

	public void run(){
		banco.transfere(0, 1, 10);
		banco.fecharConta();
		
	}
}