import java.util.concurrent.locks.ReentrantLock;

public class Banco{
	private HashMap<Integer,Conta> contas;
	private ReentrantLock lockBanco;

	public int criarConta(double saldoInicial){
		lockBanco.lock();
		int last = contas.size();
		Conta conta = new Conta(saldoInicial);
		contas.put(last+1,conta);
		lockBanco.unlock();
	}

	public double consultar(int nconta){
		if (contas.containsKey(nconta))
			return conta.get(nconta);
		else return 0;
	}

	public synchronized void levantar(int nconta, double valor){
		if (contas.containsKey(nconta)){
			double saldo = consultar(nconta);
			conta.put(nconta,saldo-valor);
		}
	}

	public synchronized void depositar(int nconta, double valor){
		this.contas[nconta] += valor;
	}

	public synchronized void transfere(int nco, int ncd, double valor){
		this.levantar(nco, valor);
		this.depositar(ncd, valor);
	}

}