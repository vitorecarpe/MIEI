package aula3banco;


public class Main {

	public static void main(String[] args) {
		
		
		Banco banco = new Banco();
		//criar 2 contas com saldo 10
		for(int i = 0; i < 2; i++){
			banco.criarConta(10);
		}
		
		Thread t1=new Thread(new Cliente1(banco));
		t1.setName("Cliente1");
		Thread t2=new Thread(new Cliente2(banco));
		t2.setName("Cliente2");
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
