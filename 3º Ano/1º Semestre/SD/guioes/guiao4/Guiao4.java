//Aula 4 - 24/10/2017

class BoundedBuffer{
	private int[] a;
	private int n, p, g;

	public BoundedBuffer(int N){
		a=new int[N];
		n=0;
		p=0;
		g=0;
	}

	public void put(int v) throws InterruptedException{
		while(n==a.length)
			wait();
		a[p]=v;
		p=(p+1)%a.length;
		notifyAll(); 
		n+=1;
	}

	public synchronized int get() throws InterruptedException{
		while(n==0)
			wait();
		int res = a[g];
		g=(g+1)%a.length;
		n-=1;
		notifyAll(); //notify(); ou notifyAll(); ???
		return res;
	}
}

class Producer extends Thread{
	BoundedBuffer b;
	
}

public class Buffer {
	private final int MaxBuffSize;
	private char[] store;
	private int BufferStart, BufferEnd, BufferSize;
	
	public Buffer(int size) {
		MaxBuffSize = size;
		BufferEnd = -1;
		BufferStart = 0;
		BufferSize = 0;
		store = new char[MaxBuffSize];
	}
	
	public synchronized void insert(char ch) {
		try {
			while (BufferSize == MaxBuffSize) {
				wait();
			}
		BufferEnd = (BufferEnd + 1) % MaxBuffSize;
		store[BufferEnd] = ch;
		BufferSize++;
		notifyAll();
	} catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	}

	
}
