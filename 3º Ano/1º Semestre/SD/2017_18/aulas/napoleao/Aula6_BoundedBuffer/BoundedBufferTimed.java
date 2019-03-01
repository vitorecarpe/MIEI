/*
 * BoundedBufferTimed - igual a boundedbuffer, mas gets e puts ocupam temporariamente a variavel
 * exemplo: barman, elementos sao os copos que demoram a ser enchidos ou esvaziados, em vez de ser um processo isntantaneo
 */
package aula6_boundedbuffer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundedBufferTimed implements BoundedBuffer {

	private int vazios;
	private int cheios;
	private int size;

	public BoundedBufferTimed(int size) {
		vazios = 0;
		cheios = 0;
		this.size = size;
	}

	public synchronized int getVazios() {
		return vazios;
	}

	public void get() {
		synchronized (this) {
			while (vazios == size) {
				try {
					wait();
				} catch (InterruptedException ex) {
					Logger.getLogger(BoundedBufferTimed.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			vazios++;
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			Logger.getLogger(BoundedBufferTimed.class.getName()).log(Level.SEVERE, null, ex);
		}

		synchronized (this) {
			cheios++;
			notifyAll();
		}
	}

	public void put() {
		synchronized (this) {
			while (vazios == 0) {
				try {
					wait();
				} catch (InterruptedException ex) {
					Logger.getLogger(BoundedBufferTimed.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		try {
			Thread.sleep(60000);
		} catch (InterruptedException ex) {
			Logger.getLogger(BoundedBufferTimed.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		synchronized (this) {
			vazios--;
			notifyAll();
		}
	}
}
