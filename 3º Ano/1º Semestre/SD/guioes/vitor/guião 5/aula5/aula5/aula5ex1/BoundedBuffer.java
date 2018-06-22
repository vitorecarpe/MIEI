package aula5ex1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

	private int[] values;
	private int poswrite;
	ReentrantLock lock;
	Condition isEmpty;
	Condition isFull;


	public BoundedBuffer(int size){
		this.values = new int[size];
		this.poswrite=0;
		this.lock= new ReentrantLock();
		this.isEmpty = this.lock.newCondition();
		this.isFull = this.lock.newCondition();
	}

	public void put(int v){
		this.lock.lock();
		try {
			while(this.poswrite == this.values.length){
				//esperar até que buffer tenha espaço
				System.out.println("PUT: buffer está cheio, esperar até que tenha espaço");
				this.isFull.await();
			}

			System.out.println("PUT: inserir valor " + v +" na posição "+poswrite);
			this.values[poswrite]=v;
			poswrite++;

			//acordar threads que estejam à espera de items no buffer
			//System.out.println("PUT: notificar que um valor foi colocado no buffer");
			this.isEmpty.signal();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			this.lock.unlock();
		}
	}


	public int get(){
		this.lock.lock();
		try {
			while(this.poswrite==0){
				//esperar até que buffer tenha items
				System.out.println("GET: buffer está vazio, esperar até que tenha valores");
				this.isEmpty.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int posread = --this.poswrite;
		int res = this.values[posread];
		System.out.println("GET: retirar valor "+res+" na posição "+posread);

		//acordar threads que estejam à espera que buffer tenha espaço
		System.out.println("GET: notificar que um valor foi retirado do buffer");
		this.isFull.signal();
		this.lock.unlock();
		
		return res;
	}

}
