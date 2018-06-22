package ex1.alt;

public class Incrementor implements Runnable{

	private int incvalue;

	public Incrementor(int incvalue){
		this.incvalue=incvalue;
	}

	@Override
	public void run() {
		for(int i = 1; i<=this.incvalue; i++){
			System.out.println("T"+Thread.currentThread().getName()+":"+i);
		}
	}
}
