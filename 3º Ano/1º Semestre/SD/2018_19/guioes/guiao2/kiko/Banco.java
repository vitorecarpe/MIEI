import java.util.ArrayList;

public class Banco{
	private Conta[] contas;

	public Banco(int ncontas){
		this.contas = new Conta[ncontas];
		for (int i=0; i<ncontas; i++){
			Conta conta = new Conta(i,0);
			this.contas[i]=conta;
		}
	}

	public double consultar(int nconta){
		return this.contas[nconta].consultar();
	}

	public synchronized void levantar(int nconta, double valor){
		this.contas[nconta].levantar(valor);
	}

	public synchronized void depositar(int nconta, double valor){
		this.contas[nconta].depositar(valor);
	}

	public synchronized void transfere(int nco, int ncd, double valor){
		int contaMenorID = Math.min(nco,ncd);
		int contaMaiorID = Math.max(nco,ncd);
		synchronized (contas[contaMenorID]) {
			synchronized (contas[contaMaiorID]) {
				this.levantar(nco, valor);
				this.depositar(ncd, valor);
			}
		}
	}

}