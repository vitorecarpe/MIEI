public class Counter {
	public int counter;
	
	public Counter(){
		this.counter=0;
	}
	
	public synchronized void increment(){
		this.counter++;
	}
	
	public int getCounter(){
		return this.counter;
	}
	
}
