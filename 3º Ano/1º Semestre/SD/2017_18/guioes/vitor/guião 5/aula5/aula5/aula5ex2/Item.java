package aula5ex2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Item {

	ReentrantLock lock;
	Condition isEmpty;
	private int quantity;

	public Item(){
		this.quantity=0;
		this.lock=new ReentrantLock();
		this.isEmpty=this.lock.newCondition();
	}

	public void supply(int quantity){
		lock.lock();
		//acrescentar quantidade e notificar consumidores
		this.quantity+=quantity;
		isEmpty.signalAll();
		lock.unlock();
	}

	public void consume(){
		lock.lock();
		try {
			//esperar enquanto não houver unidades
			while(quantity==0){
				System.out.println("Consumer: não há unidades suficientes => bloquear!");
				isEmpty.await();
			}
			//consumir uma unidade
			this.quantity--;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
}
