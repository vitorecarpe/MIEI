/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula2;

public class MyThread extends Thread {
	private Contador cont;

	public MyThread(Contador c) {
		cont = c;
	}

	@Override
	public void run() {
		for(int i=0; i < 100000; i++) {
			cont.inc();
		}
	}
}
