package aula7ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;


public class Banco {

	private HashMap<Integer, Conta> contas;
	private ReentrantLock lockBanco;


	public Banco(){
		this.contas = new HashMap<Integer, Conta>();
		this.lockBanco=new ReentrantLock();
	}

	public int criarConta(double saldo){

		this.lockBanco.lock();
		int id = contas.size();
		try{
			System.out.println("Worker-"+Thread.currentThread().getId()+": criar conta "+id+" com saldo "+saldo);
			Conta c = new Conta(id, saldo);
			this.contas.put(id, c);
		}			
		catch(Exception e){
			e.printStackTrace();
		} 
		finally{
			//precisamos de proteger todo o método porque a classe HashMap do Java
			//não está protegida internamente contra acessos concorrentes de várias threads
			//Alternativa: Usar ConcurrentHashMap
			this.lockBanco.unlock();
		}

		return id;
	}


	public double fecharConta(int id) throws ContaInvalida{

		//adquirir lock da conta, caso exista
		this.lockBanco.lock();
		System.out.println("Worker-"+Thread.currentThread().getId()+": fechar conta "+id);
		if(!contas.containsKey(id)){
			//conta não existe
			this.lockBanco.unlock();
			throw new ContaInvalida(id);
		}
		Conta c = this.contas.get(id); //obter objecto Conta para poder libertar lock após remover do hashmap
		c.lock();

		//consultar saldo e apagar conta
		double saldo = c.consultar();
		this.contas.remove(id);
		c.unlock();

		//precisamos de proteger todo o método porque a classe HashMap do Java
		//não está protegida internamente contra acessos concorrentes de várias threads
		//Alternativa: Usar ConcurrentHashMap
		this.lockBanco.unlock(); 

		return saldo;
	}

	public double consultar(int id) throws ContaInvalida{

		double saldo = 0;

		this.lockBanco.lock();
		if(!contas.containsKey(id)){
			//conta não existe
			this.lockBanco.unlock();
			throw new ContaInvalida(id);
		}
		//caso exista adquirir lock 
		this.contas.get(id).lock();
		this.lockBanco.unlock();

		// retornar saldo
		saldo = this.contas.get(id).consultar();
		this.contas.get(id).unlock();

		return saldo;
	}

	public double consultarTotal(int[] contasInput) {

		double saldoTotal = 0;

		//guarda os ids das contas que, de facto, existem no banco
		ArrayList<Integer> contasLocked = new ArrayList(contasInput.length);

		this.lockBanco.lock();

		//adquirir locks de todas as contas válidas
		for(int i = 0; i < contasInput.length; i++){
			int id = contasInput[i];
			if(!contas.containsKey(id)){
				//conta não existe
				System.out.println("Worker-"+Thread.currentThread().getId()+": Conta "+id+" não existe! Calcular saldo para as restantes contas.");
			}
			else{
				//conta existe, pelo que temos de adquirir o lock 
				this.contas.get(id).lock();
				contasLocked.add(id);
			}
		}
		this.lockBanco.unlock();

		//consultar saldo das contas e libertar locks
		for(int id : contasLocked){
			saldoTotal += contas.get(id).consultar();
			contas.get(id).unlock();
		}

		System.out.println("Worker-"+Thread.currentThread().getId()+": total "+contasLocked+" = "+saldoTotal);

		return saldoTotal;
	}


	public void levantar(int id, double valor) throws SaldoInsuficiente, ContaInvalida {

		this.lockBanco.lock();
		if(!contas.containsKey(id)){
			//conta não existe
			this.lockBanco.unlock();
			throw new ContaInvalida(id);
		}
		//caso exista adquirir lock 
		this.contas.get(id).lock();
		this.lockBanco.unlock();

		//consultar valor e levantar
		if(this.contas.get(id).consultar()<valor){
			throw new SaldoInsuficiente(id);
		}
		this.contas.get(id).levantar(valor);
		this.contas.get(id).unlock();
	}


	public void depositar(int id, double valor) throws ContaInvalida{

		this.lockBanco.lock();
		if(!contas.containsKey(id)){
			//conta não existe
			this.lockBanco.unlock();
			throw new ContaInvalida(id);
		}
		//caso exista adquirir lock 
		this.contas.get(id).lock();
		this.lockBanco.unlock();

		//depositar valor
		this.contas.get(id).depositar(valor);
		this.contas.get(id).unlock();

	}

	public void transferir(int conta_origem, int conta_destino, double valor) throws ContaInvalida, SaldoInsuficiente{

		System.out.println("Worker-"+Thread.currentThread().getId()+": transferir "+valor+"€ de "+conta_origem+" para "+conta_destino);
		Integer conta_min = Math.min(conta_origem, conta_destino);
		Integer conta_max = Math.max(conta_origem, conta_destino);

		//verificar se contas existem e adquirir locks correspondentes
		lockBanco.lock();
		if(this.contas.containsKey(conta_min)){
			this.contas.get(conta_min).lock();
		}
		else{
			lockBanco.unlock();
			throw new ContaInvalida(conta_min);
		}

		if(this.contas.containsKey(conta_max)){
			this.contas.get(conta_max).lock();
		}
		else{
			//se conta_max não existir, libertar lock da conta_min 
			//já adquirido para evitar deadlocks
			this.contas.get(conta_min).unlock();
			lockBanco.unlock();
			throw new ContaInvalida(conta_max);
		}
		lockBanco.unlock();

		//verificar se conta origem tem saldo suficiente
		if(this.contas.get(conta_origem).consultar() < valor){
			//libertar locks e lançar excepção
			this.contas.get(conta_min).unlock();
			this.contas.get(conta_max).unlock();
			throw new SaldoInsuficiente(conta_origem);
		}

		//fazer transferência e libertar locks
		this.contas.get(conta_origem).levantar(valor);		
		this.contas.get(conta_destino).depositar(valor);

		this.contas.get(conta_min).unlock();
		this.contas.get(conta_max).unlock();

	}
}


class SaldoInsuficiente extends Exception{
	public SaldoInsuficiente() {}

	public SaldoInsuficiente(int id){
		super("Conta "+id+" não tem saldo suficiente para operação!");
	}
}

class ContaInvalida extends Exception{
	public ContaInvalida() {}

	public ContaInvalida(int id){
		super("Conta "+id+" não existe!");
	}
}
