package readwritelock;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    
    public static void main(String[] args) {
        ReadWriteLock rwLock = new ReadWriteLockClass();

		Thread t1 = new Thread_Read(rwLock);
		Thread t2 = new Thread_Write(rwLock);
		Thread t3 = new Thread_Read(rwLock);
		Thread t4 = new Thread_Read(rwLock);
		Thread t5 = new Thread_Read(rwLock);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

}
