package aula6_boundedbuffer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        BoundedBuffer b = new BoundedBufferNotTimed(10);
		Thread t1 = new Thread_Puts(b);
		Thread t2 = new Thread_Gets(b);

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

}
