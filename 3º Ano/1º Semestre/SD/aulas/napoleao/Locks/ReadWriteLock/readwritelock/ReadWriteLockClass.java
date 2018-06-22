package readwritelock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadWriteLockClass implements ReadWriteLock {

	private ReentrantLock mutex;
	private Condition waitWrite;
	private Condition waitRead;
	private int nWriting;
	private int nReading;
	private int totalWriters;

	public ReadWriteLockClass() {
		totalWriters = 0;
		nWriting = 0;
		nReading = 0;
		mutex = new ReentrantLock();
		waitWrite = mutex.newCondition();
		waitRead = mutex.newCondition();
	}

	public void lock(OperationType op) {
		mutex.lock();
		if (op.equals(OperationType.Read)) { //se for um Read
			while (nWriting > 0) { // espera enquanto houver um Write
				try {
					waitWrite.await(); // espera que deixe de haver Write
				} catch (InterruptedException ex) {
					Logger.getLogger(ReadWriteLockClass.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			nReading++; // mais um Read
		} else { // se for um Write
			while (nWriting > 0) { // enquanto houver um Write
				try {
					waitWrite.await();
				} catch (InterruptedException ex) {
					Logger.getLogger(ReadWriteLockClass.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			while (nReading > 0) { // enquanto houver algum Read
				try {
					waitRead.await();
				} catch (InterruptedException ex) {
					Logger.getLogger(ReadWriteLockClass.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			nWriting++; // passa a haver um Write
			totalWriters++;
		}
		mutex.unlock();
	}

	public void unlock(OperationType op) {
		mutex.lock();
		if (op.equals(OperationType.Read)) { // se for um Read
			nReading--;
			if (nReading == 0) {
				waitRead.signal();
			}
		} else { // se for um Write
			nWriting--;
			waitWrite.signalAll();
		}
		mutex.unlock();
	}

	public synchronized String printCurrent() {
		return "Readers: " + nReading + ", Writers: " + nWriting + ", Total Writers: " + totalWriters;
	}
}
