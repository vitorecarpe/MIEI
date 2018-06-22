package aula5ex1;


public class Consumer implements Runnable {

	private BoundedBuffer buffer;

	public Consumer(BoundedBuffer b){
		this.buffer=b;
	}

	@Override
	public void run() {
		for(int i=0; i<20; i++){
			this.buffer.get(); 
		}
	} 
}
