package various;

import java.util.concurrent.locks.ReentrantLock;

public class Stack {
	
	int N = 10 ;
	private int n = 0, array[]=null;
	ReentrantLock r1 = new ReentrantLock();
	
	
	public synchronized void push(int v) throws InterruptedException{
		if (n==N) wait();
		array[n]=v;
		n++;
		notifyAll();
	}
	
	public synchronized int pop() throws InterruptedException{
		if (n==0) wait();
		notifyAll();
		return n--;
		
	}
	
	public synchronized int top() throws InterruptedException{
		while(n==0) Thread.sleep(1000);
		return array[n];
	}
	
	public synchronized int sum(){
		return sum_aux(0);
	}
	
	public synchronized int sum_aux(int i){
		if(i==n) return 0;
		return array[i] + sum_aux(i+1);
	}
	
	public synchronized void transfer(int i, int j , int v) throws InterruptedException{
		while(i>= n || j>= n) wait();
		array[i]-=v;
		array[j]+=v;
		
	}
	
	public int length(){
		return n;
	}
	
	public void reset(){
		r1.lock();
		n=0;
		r1.unlock();
	}


	
}

