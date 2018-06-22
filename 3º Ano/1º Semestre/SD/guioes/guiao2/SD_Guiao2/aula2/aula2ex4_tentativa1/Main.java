package aula2ex4_tentativa1;

public class Main {

	public static void main(String[] args) {
		Banco b = new Banco(10);
		b.depositar(0,1000);
		
		Thread t1=new Thread(new Cliente1(b));
		Thread t2=new Thread(new Cliente2(b));
		
		t1.start();
		
		//Forçar Cliente 2 a verificar saldo depois de o Cliente 1 
		//ter iniciado a transferência
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Valor Conta 0 é: " + b.consultar(0));
		System.out.println("Valor Conta 1 é: " + b.consultar(1));

	}

}
