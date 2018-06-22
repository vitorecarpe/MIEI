package aula2ex4_tentativa3;

public class Main {

	public static void main(String[] args) {	
		Banco b = new Banco(10);
		b.depositar(0, 1000);
		Thread t1=new Thread(new Cliente1(b));
		t1.setName("Cliente1");
		Thread t2=new Thread(new Cliente2(b));
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
