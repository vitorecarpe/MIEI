import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Banco{
	private HashMap<Integer, Conta> contas = new HashMap<>();
	private ReentrantLock lockBanco = new ReentrantLock();

	public Banco(int ncontas){
		this.lockBanco.lock();
		for (int i=0; i<ncontas; i++){
			this.criarConta(10);
		}
		this.lockBanco.unlock();
	}

	public int criarConta(double saldo){
		this.lockBanco.lock();
		int id = this.contas.size();
		Conta c = new Conta(saldo);
		this.contas.put(id,c);
		this.lockBanco.unlock();
		return id;
	}
	
	public double fecharConta(int id) throws NullPointerException{
		this.lockBanco.lock();
		try {
			Conta c = this.contas.get(id);
			c.lock();
			double saldo = c.consultar();
			this.contas.remove(id);
			c.unlock();
			this.lockBanco.unlock();
			return saldo;
		} catch (NullPointerException np) {
			np.printStackTrace();
		}
		this.lockBanco.unlock();
		return 404;
	}

	public double consultar(int id) throws NullPointerException{
		double valor = 0;
		this.lockBanco.lock();
		try{
			valor = this.contas.get(id).consultar();
		} catch (NullPointerException np){
			np.printStackTrace();
		}
		this.lockBanco.unlock();
		return valor;
	}

	public void levantar(int id, double valor){
		this.lockBanco.lock();
		this.contas.get(id).lock();
		this.contas.get(id).levantar(valor);
		this.contas.get(id).unlock();
		this.lockBanco.unlock();
	}

	public void depositar(int id, double valor){
		this.lockBanco.lock();
		this.contas.get(id).lock();
		this.contas.get(id).depositar(valor);
		this.contas.get(id).unlock();
		this.lockBanco.unlock();
	}

	public void transfere(int nco, int ncd, double valor) throws NullPointerException{
		int contaMenorID = Math.min(nco,ncd);
		int contaMaiorID = Math.max(nco,ncd);
		this.lockBanco.lock();
		try{
			this.contas.get(contaMenorID).lock();
			this.contas.get(contaMaiorID).lock();
			this.levantar(nco, valor);
			this.depositar(ncd, valor);
			this.contas.get(contaMaiorID).unlock();
			this.contas.get(contaMenorID).unlock();	
		} catch (NullPointerException np) {
			np.printStackTrace();
		}
		this.lockBanco.unlock();
		
		
	}
}