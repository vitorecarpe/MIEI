/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula3;

import java.util.ArrayList;

/**
 *
 * @author naps62
 */
public class Thread_ContaZeros extends Thread {

	private ArrayList<Integer> array;
	private Contador c;
	private int block;

	public Thread_ContaZeros(ArrayList<Integer> array, Contador c, int block) {
		this.array = array;
		this.block = block;
		this.c = c;
	}

	@Override
	public void run() {
		for(int j = block * array.size() / Main.MAX_THREADS; j < (block + 1) * array.size() / Main.MAX_THREADS; j++) {
			if (array.get(j) == 0) {
				c.inc();
			}
		}
	}

}
