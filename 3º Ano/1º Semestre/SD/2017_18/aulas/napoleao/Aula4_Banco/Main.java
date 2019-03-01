package aula4_banco;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static final int TEST_SIZE = 3;

	public static void main(String[] args) {
		Conta c = new Conta(1);
		Thread t1 = new Thread_TestaBanco(c);
		Thread t2 = new Thread_TestaBanco2(c);
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
			//		Banco b = new Banco();
			//
			//		ArrayList<Double> test_vals = new ArrayList<Double>();
			//		test_vals.add(10d);
			//		test_vals.add(-5d);
			//		test_vals.add(-6d);
			//
			//		ArrayList<Thread> threads = new ArrayList<Thread>();
			//		for(int i = 0; i < TEST_SIZE; ++i) {
			//			Thread temp = new Thread_TestaBanco(b, test_vals.get(i));
			//			threads.add(temp);
			//			temp.start();
			//		}
			//
			//		try {
			//			for(int i = 0; i < TEST_SIZE; ++i) {
			//				threads.get(i).join();
			//			}
			//		} catch (InterruptedException ex) {
			//			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			//		}
			//
			//		System.out.println(b);
			//
			//		Thread t3 = new Thread_TestaBanco2(b);
			//		t3.start();
			//		try {
			//			t3.join();
			//		} catch (InterruptedException ex) {
			//			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			//		}
			//		System.out.println(b);
	}
}
