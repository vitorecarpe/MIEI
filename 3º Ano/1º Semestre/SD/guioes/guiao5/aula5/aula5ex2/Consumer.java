package aula5ex2;


public class Consumer implements Runnable {
	
	private Warehouse armazem;

	public Consumer(Warehouse b){
		this.armazem=b;
	}
	
	@Override
	public void run() {
			String[] items={"item1","item2","item3"};
			armazem.consume(items);
	} 
}
