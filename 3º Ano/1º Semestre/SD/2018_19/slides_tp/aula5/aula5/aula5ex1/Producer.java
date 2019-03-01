package aula5ex1;


public class Producer implements Runnable {

	private BoundedBuffer buffer;

	public Producer(BoundedBuffer b){
		this.buffer=b;
	}

	@Override
	public void run() {
		for(int i=0; i<20; i++){
			this.buffer.put(i);	
		}				
	} 
}
