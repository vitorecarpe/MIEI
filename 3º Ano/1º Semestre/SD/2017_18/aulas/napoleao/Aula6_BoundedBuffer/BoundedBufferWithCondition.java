/*
 * BoundedBufferTimed - igual a boundedbuffer, mas gets e puts ocupam temporariamente a variavel
 * exemplo: barman, elementos sao os copos que demoram a ser enchidos ou esvaziados, em vez de ser um processo isntantaneo
 * Versao 2 - utiliza locks explicitos com ReentrantLock e Condition
 */
package aula6_boundedbuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundedBufferWithCondition implements BoundedBuffer {

	private int vazios;
	private int cheios;
	private int size;
	private ReentrantLock mutex;
	private Condition esperarPut;
	private Condition esperarGet;

	public BoundedBufferWithCondition(int size) {
		vazios = 0;
		cheios = 0;
		this.size = size;

		mutex = new ReentrantLock();
		esperarPut = mutex.newCondition();
		esperarGet = mutex.newCondition();
	}

	public synchronized int getVazios() {
		return vazios;
	}

	public void get() {
		mutex.lock();
		while (vazios == size) {
			try {
				esperarPut.await();
			} catch (InterruptedException ex) {
				Logger.getLogger(BoundedBufferWithCondition.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		vazios++;
		mutex.unlock();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			Logger.getLogger(BoundedBufferWithCondition.class.getName()).log(Level.SEVERE, null, ex);
		}

		mutex.lock();
		cheios++;
		esperarGet.signal();
		mutex.unlock();
	}

	public void put() {
		mutex.lock();
		while (vazios == 0) {
			try {
				esperarGet.await();
			} catch (InterruptedException ex) {
				Logger.getLogger(BoundedBufferWithCondition.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		mutex.unlock();

		try {
			Thread.sleep(60000);
		} catch (InterruptedException ex) {
			Logger.getLogger(BoundedBufferWithCondition.class.getName()).log(Level.SEVERE, null, ex);
		}

		mutex.lock();
		vazios--;
		esperarPut.signal();
		mutex.unlock();
	}
}
