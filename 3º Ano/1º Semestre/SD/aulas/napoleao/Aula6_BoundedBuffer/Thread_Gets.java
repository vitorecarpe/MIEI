/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula6_boundedbuffer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naps62
 */
public class Thread_Gets extends Thread {
	private BoundedBuffer buffer;

	public Thread_Gets(BoundedBuffer b) {
		buffer = b;
	}

	@Override
	public void run() {
		while(true) {
			buffer.get();
			System.out.println("Vazios: " + buffer.getVazios());
		}
	}
}
