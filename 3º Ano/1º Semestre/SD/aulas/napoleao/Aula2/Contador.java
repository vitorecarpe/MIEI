/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula2;

public class Contador {
	private int i;

	public Contador(int i) {
		this.i = i;
	}

	public Contador() {
		i = 0;
	}

	public synchronized void inc() {
		i++;
	}

	public synchronized int get() {
		return i;
	}
}
