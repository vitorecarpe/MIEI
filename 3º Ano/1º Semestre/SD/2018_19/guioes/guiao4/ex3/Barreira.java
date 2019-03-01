public class Barreira{
	private int max;
	private int counter;
	private int round;

	public Barreira(int n){
		this.max = n;
		this.counter = 0;
		this.round = 0;
	}

	public synchronized void esperar() throws InterruptedException{
		counter++;
		int myID = this.counter;
		int myRound = this.round;

		if(myID == max){
			counter = 0;
			this.round++;
			notifyAll();
			System.out.println("ultima chegou!!! open the gates");
		}
		else {
			while (this.round == myRound)
				wait();
		}
	}
}