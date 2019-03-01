import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Banco{
	private HashMap<int,Conta> contas;
	private ReentrantLock lockBanco = new ReentrantLock();

	public Banco(int ncontas){
		// this.contas = new HashMap<int,Conta>();
		// for (int i=0; i<ncontas; i++){
		// 	Conta conta = new Conta(0);
		// 	this.contas[i]=conta;
		// }
	}

	public int criarConta(double saldo){
		this.lockBanco.lock();
		int id = this.contas.size();
		Conta c = new conta(saldo);
		this.contas.put(id,c);
		this.lockBanco.unlock();
		return id;
	}
	public void fecharConta(int id) throws ContaInvalida{
		this.lockBanco.lock();
		if(contas.containsKey(id)){
			Conta c = this.contas.get(id);
			c.lock();
			//double saldo = c.consultar();
			this.conta.remove(id);
			c.unlock();
			this.lockBanco.unlock();
			//return saldo;
		}
		else{
			this.lockBanco.unlock();
			throw new ContaInvalida(id);
		}
	}

	public double consultar(int id) throws ContaInvalida{
		double valor;
		this.lockBanco.lock();
		valor = this.contas.getValue(id).consultar();
		this.lockBanco.unlock();
		return valor;
	}

	public void levantar(int id, double valor) throws ContaInvalida, SaldoInsuficiente{
		this.lockBanco.lock();
		this.contas.getValue(id).lock();
		this.contas.getValue(id).levantar(valor);
		this.contas.getValue(id).unlock();
		this.lockBanco.unlock();
	}

	public void depositar(int nconta, double valor) throws ContaInvalida{
		this.lockBanco.lock();
		this.contas.getValue(id).lock();
		this.contas.getValue(id).depositar(valor);
		this.contas.getValue(id).unlock();
		this.lockBanco.unlock();
	}

	public void transfere(int nco, int ncd, double valor) throws ContaInvalida, SaldoInsuficiente{
		int contaMenorID = Math.min(nco,ncd);
		int contaMaiorID = Math.max(nco,ncd);
		this.contas[contaMenorID].lock();
			this.contas[contaMaiorID].lock();
				this.levantar(nco, valor);
				this.depositar(ncd, valor);
			this.contas[contaMaiorID].unlock();
		this.contas[contaMenorID].unlock();
	}

}