package aula3banco;


public class Cliente1 implements Runnable {

	private Banco banco;

	public Cliente1(Banco b){
		this.banco=b; 
	}

	@Override
	public void run() {
		int id = banco.criarConta(0); 
		System.out.println(Thread.currentThread().getName()+": conta "+id+" criada");
		try {
			banco.transferir(0, 2, 5);

		} catch (ContaInvalida | SaldoInsuficiente e) {
			System.out.println("[ERRO] "+e.getMessage());
		}
		try{
			System.out.println(Thread.currentThread().getName()+": consultar total das contas [0, 1, 2] ");
			banco.consultarTotal(new int[]{0,1,2});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERRO] "+e.getMessage());
		}
	} 

}
