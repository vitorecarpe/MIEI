/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula5_copos;

/**
 *
 * @author naps62
 */
public class Thread_Barman extends Thread {
	private Copos copos;

	public Thread_Barman(Copos c) {
		copos = c;
	}

	@Override
	public void run() {
		while(true) {
			copos.encheCopo();
			System.out.println(copos.getNCheios() + " - Copo cheio");
		}
	}
}
