/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aula5_copos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naps62
 */
public class Main {

	public static void main(String[] args) {
		Copos c = new Copos(10);
		Thread t1 = new Thread_Barman(c);
		Thread t2 = new Thread_Cliente(c);

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//imaginar agora que encher ou esvaziar um copo leva tempo (em vez de fazer um sleep)
}
