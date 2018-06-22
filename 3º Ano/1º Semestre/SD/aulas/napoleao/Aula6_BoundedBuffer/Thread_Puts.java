/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula6_boundedbuffer;

/**
 *
 * @author naps62
 */
public class Thread_Puts extends Thread {
	private BoundedBuffer buffer;

	public Thread_Puts(BoundedBuffer b) {
		buffer = b;
	}

	@Override
	public void run() {
		while(true) {
			buffer.put();
			System.out.println("Vazios: " + buffer.getVazios());
		}
	}
}
