import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Banco{
	private HashMap<Integer,Conta> contas;
	private ReentrantLock lockBanco = new ReentrantLock();

	public Banco(int ncontas){
		this.contas = new HashMap<Integer,Conta>();
		for (int i=0; i<ncontas; i++){
			Conta conta = new Conta(20);
			this.contas.put(i,conta);
		}
	}

	public int criarConta(double saldo){
		this.lockBanco.lock();
		int id = this.contas.size();
		Conta c = new Conta(saldo);
		this.contas.put(id,c);
		this.lockBanco.unlock();
		return id;
	}
	public double fecharConta(int id) throws ContaInvalida{
		this.lockBanco.lock();
		if(contas.containsKey(id)){
			Conta c = this.contas.get(id);
			c.lock();
			double saldo = c.consultar();
			this.contas.remove(id);
			c.unlock();
			this.lockBanco.unlock();
			return saldo;
		}
		else{
			this.lockBanco.unlock();
			throw new ContaInvalida("");
		}
	}

	public double consultar(int id) throws ContaInvalida{
		this.lockBanco.lock();
		if (this.contas.containsKey(id)){
			double valor = this.contas.get(id).consultar();
			this.lockBanco.unlock();
			return valor;
		}
		else {
			this.lockBanco.unlock();
			throw new ContaInvalida("");
		}
	}

	public void levantar(int id, double valor) throws ContaInvalida, SaldoInsuficiente{
		this.lockBanco.lock();
		if(this.contas.containsKey(id)){
			this.contas.get(id).lock();
			this.contas.get(id).levantar(valor);
			this.contas.get(id).unlock();
			this.lockBanco.unlock();
		}
		else {
			this.lockBanco.unlock();
			throw new ContaInvalida("");
		}
	}

	public void depositar(int id, double valor) throws ContaInvalida{
		this.lockBanco.lock();
		if (this.contas.containsKey(id)) {
			this.contas.get(id).lock();
			this.contas.get(id).depositar(valor);
			this.contas.get(id).unlock();
			this.lockBanco.unlock();
		}
		else {
			this.lockBanco.unlock();
			throw new ContaInvalida("");
		}
	}

	public void transferir(int nco, int ncd, double valor) throws ContaInvalida, SaldoInsuficiente{
		int contaMenorID = Math.min(nco,ncd);
		int contaMaiorID = Math.max(nco,ncd);
		if (this.contas.containsKey(contaMenorID) && this.contas.containsKey(contaMaiorID)) {
			this.contas.get(contaMenorID).lock();
				this.contas.get(contaMaiorID).lock();
					this.levantar(nco, valor);
					this.depositar(ncd, valor);
				this.contas.get(contaMaiorID).unlock();
			this.contas.get(contaMenorID).unlock();
		}
		else {
			this.lockBanco.unlock();
			throw new ContaInvalida("");
		}
	}

}