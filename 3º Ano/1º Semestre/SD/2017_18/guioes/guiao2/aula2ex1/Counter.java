package aula2ex1;

public class Counter {

	public int counter;

	public Counter(){
		this.counter=0;
	}

	//METODO SYNCHRONIZED
	public synchronized void increment(){
		this.counter++;
	}

	//BLOCO SYNCHRONIZED
	public void increment2(){
		synchronized(this){
			this.counter++;
		}
	}	

	public synchronized int getCounter(){
		return this.counter;
	}

}
