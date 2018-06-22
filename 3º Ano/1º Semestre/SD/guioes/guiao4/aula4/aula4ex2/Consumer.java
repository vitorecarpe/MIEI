package aula4ex2;



public class Consumer implements Runnable {

	private BoundedBuffer buffer;
	private int tc;
	private int numberOps;

	public Consumer(BoundedBuffer b,int timeToWait,int operations){
		this.buffer=b;
		this.tc=timeToWait;
		this.numberOps=operations;
	}
	
	@Override
	public void run() {
		
		for(int i=0; i<numberOps; i++){
			int val = this.buffer.get();
			System.out.println("Consumer "+Thread.currentThread().getName()+": GET_" + i + " = " + val);
			try {
				Thread.sleep(this.tc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}			
	} 

}
