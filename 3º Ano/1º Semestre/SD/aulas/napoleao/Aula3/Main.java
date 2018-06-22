/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula3;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naps62
 */
public class Main {

	public static int MAX_THREADS = 4;
	
	public static void main(String[] args) {
		Contador c = new Contador();
		int result = 0;

		ArrayList<Integer> array = new ArrayList<Integer>();
		for(int i = 0; i < 10000000; i++) {
			array.add(0);
		}
		array.set(1000, 1);
		array.set(1200, 2);
		System.out.println(array.size());

		//cria threads para contar zeros em paralelo
		ArrayList<Thread_ContaZeros> threads = new ArrayList<Thread_ContaZeros>();
		for(int i = 0; i < MAX_THREADS; i++) {
			Thread_ContaZeros temp = new Thread_ContaZeros(array, c, i);
			threads.add(temp);
			temp.start();
		}

		for(int i = 0; i < MAX_THREADS; i++) {
			try {
				threads.get(i).join();

			} catch (InterruptedException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		System.out.println(c.get());

	}
}
