// package aula5ex1;

public class Main {
	public static void main(String[] args) {
		
		BoundedBuffer b = new BoundedBuffer(10);
		Thread t1=new Thread(new Producer(b));
		Thread t2=new Thread(new Consumer(b));
		Thread t11 = new Thread(new Producer(b));
		Thread t22 = new Thread(new Consumer(b));
		
		t1.start();
		t2.start();
		t11.start();
		t22.start();
	
		try {
			t1.join();
			t2.join();
			t11.join();
			t22.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
