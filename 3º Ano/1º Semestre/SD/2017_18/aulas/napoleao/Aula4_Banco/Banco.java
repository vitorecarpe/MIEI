package aula4_banco;

import java.util.TreeMap;

public class Banco {
	private TreeMap<Integer, Conta> contas;
	private static int nextCod = 1;

	public Banco() {
		contas = new TreeMap<Integer, Conta>();
	}

	public double consultaSaldo(int conta) {
		synchronized(contas.get(conta)) {
			return contas.get(conta).getSaldo();
		}
	}

	public void setSaldo(int conta, double newSaldo) {
		synchronized(contas.get(conta)) {
			contas.get(conta).setSaldo(newSaldo);
		}
	}

	public void deposito(int conta, double val) {
		synchronized(contas.get(conta)) {
			contas.get(conta).deposito(val);
		}
	}

	public void levantamento(int conta, double val) {
		synchronized(contas.get(conta)) {

			contas.get(conta).levantamento(val);
		}
	}

	public void transferencia(int origem, int destino, double val) {
		if (origem > destino) {
			int temp = destino;
			destino = origem;
			origem = temp;
			val = -val;
		}
		synchronized(contas.get(origem)) {
			synchronized(contas.get(destino)) {
				levantamento(origem, val);
				deposito(origem, val);
			}
		}
	}

	public synchronized double balanco() {
		double total = 0;
		for(Conta c : contas.values()) {
			total += c.getSaldo();
		}

		return total;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();

		for(Conta c : contas.values()) {
			synchronized(c) {
				ret.append("Conta ").append(c.getCod()).append(": ").append(c.getSaldo()).append(System.getProperty("line.separator"));
			}
		}

		return ret.toString();
	}
}
