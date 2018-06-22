package aula2ex4_solucao;

public class Main {

	public static void main(String[] args) {

		Banco b = new Banco(10);	
		Thread t1=new Thread(new Cliente1(b));
		Thread t2=new Thread(new Cliente2(b));
	
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
