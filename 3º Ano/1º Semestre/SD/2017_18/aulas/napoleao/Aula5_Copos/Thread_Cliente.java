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
public class Thread_Cliente extends Thread {
	private Copos copos;

	public Thread_Cliente(Copos c) {
		copos = c;
	}

	@Override
	public void run() {
		while(true) {
			copos.esvaziaCopo();
			System.out.println(copos.getNCheios() + " - Copo esvaziado");
		}
	}
}
