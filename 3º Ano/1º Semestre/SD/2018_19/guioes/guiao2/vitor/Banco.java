public class Banco{
	private double[] contas;

	public Banco(int ncontas){
		this.contas = new double[ncontas];
		for (int i=0; i<ncontas; i++){
			contas[i]=0;
		}
	}

	public double consultar(int nconta){
		return this.contas[nconta];
	}

	public synchronized void levantar(int nconta, double valor){
		this.contas[nconta] -= valor;
	}

	public synchronized void depositar(int nconta, double valor){
		this.contas[nconta] += valor;
	}

	public synchronized void transfere(int nco, int ncd, double valor){
		this.levantar(nco, valor);
		this.depositar(ncd, valor);
	}

}