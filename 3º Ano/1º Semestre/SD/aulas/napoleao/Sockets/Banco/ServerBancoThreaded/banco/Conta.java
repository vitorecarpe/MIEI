package banco;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Conta {

	private int cod;
	private double saldo;

	public Conta(int cod) {
		this.cod = cod;
		saldo = 0;
	}

	public Conta(int cod, double saldo) {
		this.cod = cod;
		this.saldo = saldo;
	}

	public int getCod() {
		return cod;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double newSaldo) {
		saldo = newSaldo;
	}

	public synchronized void deposito(double val) {
		saldo += val;

		notifyAll();
	}

	public synchronized void levantamento(double val) {
		while (saldo < val) {
				System.out.println(saldo + "<" + val);
			try {

				System.out.println("entrou no wait");
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(Conta.class.getName()).log(Level.SEVERE, null, ex);
			}
				System.out.println("saiu do wait");
		}
		saldo -= val;
	}
}
