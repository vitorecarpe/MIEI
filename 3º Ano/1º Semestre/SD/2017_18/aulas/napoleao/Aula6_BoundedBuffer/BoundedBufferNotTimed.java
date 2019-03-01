/*
 * BoundedBufferNotTimed
 */

package aula6_boundedbuffer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundedBufferNotTimed implements BoundedBuffer {
	private int vazios;
	private int size;

	public BoundedBufferNotTimed(int size) {
		vazios = 0;
		this.size = size;
	}

	public synchronized int getVazios() {
		return vazios;
	}

	public synchronized void get() {
		while(vazios == size) {
			try {
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(BoundedBufferNotTimed.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		vazios++;
		notifyAll();
	}

	public synchronized void put() {
		while(vazios == 0) {
			try {
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(BoundedBufferNotTimed.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		vazios--;
		notifyAll();
	}
}
