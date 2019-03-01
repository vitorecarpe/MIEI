public class Main{
	
	public static void main(String[] args){
		int NUM_CONTAS=10;
		Banco b = new Banco(NUM_CONTAS);
		Thread t1 = new Thread(new Cliente1(b));
		Thread t2 = new Thread(new Cliente2(b));

		t1.start();
		t2.start();
		try{
			t1.join();
			t2.join();
		} catch(InterruptedException e) {}

		System.out.println("Valor na conta 0: " + b.consultar(0));
		System.out.println("Valor na conta 1: " + b.consultar(1));
	}
}