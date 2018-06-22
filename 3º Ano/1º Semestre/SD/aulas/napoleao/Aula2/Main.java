package aula2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naps62
 */
public class Main {

    public static void main(String[] args) {
		Contador c = new Contador(0);
        MyThread t1 = new MyThread(c);
		MyThread t2 = new MyThread(c);
		t1.start();
		t2.start();
		System.out.println(c.get());
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(c.get());
    }

}
