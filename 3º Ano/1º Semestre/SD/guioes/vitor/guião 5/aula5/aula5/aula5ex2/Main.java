package aula5ex2;



public class Main {

	public static void main(String[] args) {
		
		//inicializar warehouse com 3 items vazios
		Warehouse wh = new Warehouse();
		wh.supply("item1", 0);
		wh.supply("item2", 0);
		wh.supply("item3", 0);
		
		Thread t1=new Thread(new Producer(wh));
		Thread t2=new Thread(new Consumer(wh));
		
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
