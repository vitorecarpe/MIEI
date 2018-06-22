package aula3banco;


public class Cliente2 implements Runnable {

	private Banco banco;

	public Cliente2(Banco b){
		this.banco=b; 
	}

	@Override
	public void run() {
		try {
			banco.transferir(0, 1, 10);
		} catch (ContaInvalida | SaldoInsuficiente e) {
			System.out.println("[ERRO] "+e.getMessage());
		}
		try {
			double saldo = banco.fecharConta(1);
			System.out.println(Thread.currentThread().getName()+": conta 1 fechada (saldo = "+saldo+")");
		} catch (ContaInvalida e) {
			System.out.println("[ERRO] "+e.getMessage());
		}	
		try {
			System.out.println(Thread.currentThread().getName()+": consultar total da conta 0");
			double total = banco.consultar(0);
			System.out.println(Thread.currentThread().getName()+": total 0 = "+total);
		} catch (ContaInvalida e) {
			System.out.println("[ERRO] "+e.getMessage());
		}	
	} 
}