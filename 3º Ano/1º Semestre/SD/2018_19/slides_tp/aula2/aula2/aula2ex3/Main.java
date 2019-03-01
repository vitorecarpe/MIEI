package aula2ex3;

public class Main {

	public static void main(String[] args) {
		Banco b = new Banco(10);
		
		Thread t1=new Thread(new Cliente1(b));
		Thread t2=new Thread(new Cliente2(b));
		
		b.depositar(0, 1000);
		t1.start();
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
