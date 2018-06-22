package aula5_copos;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Copos {

	private int nTotal;
	private int nCheios;

	public Copos(int n) {
		this.nTotal = n;
		this.nCheios = 0;
	}

	public int getSize() {
		return nTotal;
	}

	public int getNCheios() {
		return nCheios;
	}

	public synchronized void esvaziaCopo() {
		while (nCheios == 0) {
			try {
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(Copos.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		nCheios--;

		notifyAll();
	}

	public synchronized void encheCopo() {
		while (nCheios == nTotal) {
			try {
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(Copos.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		nCheios++;

		notifyAll();
	}
}
