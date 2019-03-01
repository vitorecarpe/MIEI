class Counter{
	public int count;

	public Counter(){
		this.count=0;
	}

	public synchronized void increment(){
		this.count++;
	}

	public int getCounter(){
		return this.count;
	}
}

class Incrementor implements Runnable{
	// Estado a que a thread tem acesso
	private Counter counter;
	private int maxI;

	/*
	 * Comportamento da thread
	 */
	public Incrementor(Counter c, int I){
		this.counter=c;
		this.maxI=I;
	}

	public void run(){
		for (int i=0; i<this.maxI; i++){
			// synchronized(counter){
			this.counter.increment();
			//}
		}
	}
}

public class ex2e3{
	public static void main(String[] args){
		int N=10;
		int I=20;
		Counter c = new Counter();

		Thread[] threads = new Thread[N];

		//Create threads
		for (int i=0; i<N; i++){
			threads[i] = new Thread(/*Runnable*/ new Incrementor(c,I));
		}

		//Start threads
		for (int i=0; i<N; i++){
			threads[i].start();
		}

		//Join threads
		for (int i=0; i<N; i++){
			threads[i].join();
		}


		System.out.println("Counter: " + c.getCounter());
	}
}