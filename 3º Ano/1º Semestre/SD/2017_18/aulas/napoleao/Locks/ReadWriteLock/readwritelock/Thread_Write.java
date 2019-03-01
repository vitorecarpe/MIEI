/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readwritelock;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naps62
 */
class Thread_Write extends Thread {
private ReadWriteLock rwLock;

	public Thread_Write(ReadWriteLock rwLock) {
		this.rwLock = rwLock;
	}

	public void run() {
		for(int i = 0; i < 100; i++) {
			rwLock.lock(ReadWriteLock.OperationType.Write);
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {
				Logger.getLogger(Thread_Write.class.getName()).log(Level.SEVERE, null, ex);
			}
			rwLock.unlock(ReadWriteLock.OperationType.Write);
			System.out.println(rwLock.printCurrent());

		}
	}
}
