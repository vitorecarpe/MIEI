// package aula5ex2;

public class Producer implements Runnable {
	
	private Warehouse wh;

	public Producer(Warehouse w){
		this.wh = w;
	}
	
	public void run() {
		System.out.println("Producer: adicionei uma unidade de item 1");
		this.wh.supply("item1", 1);

		try {Thread.sleep(2000);}
		catch (InterruptedException e) { }

		System.out.println("Producer: adicionei uma unidade de item 2");
		this.wh.supply("item2", 1);

		try { Thread.sleep(2000);}
		catch (InterruptedException e) { }

		System.out.println("Producer: adicionei uma unidade de item 3");
		this.wh.supply("item3", 1);				
	}
}