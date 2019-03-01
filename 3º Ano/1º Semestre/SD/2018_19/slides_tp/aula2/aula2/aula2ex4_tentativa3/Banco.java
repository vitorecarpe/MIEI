package aula2ex4_tentativa3;

public class Banco {

	private Conta[] contas;
	
	Banco(int n){
		contas = new Conta[n];
		for(int i=0; i<n; i++){
			contas[i]=new Conta();
		}	
	}
	
	public double consultar(int nr_conta){
		synchronized(this.contas[nr_conta]){
			return this.contas[nr_conta].consultar();
		}
	}
	
	public void levantar(int nr_conta, double valor){
		synchronized(this.contas[nr_conta]){
			this.contas[nr_conta].levantar(valor);
		}
	}
	
	
	public void depositar(int nr_conta, double valor){
		synchronized(this.contas[nr_conta]){
			this.contas[nr_conta].depositar(valor);
		}
	}
	
	//Deadlock transf 0 para 1 e transf 1 para 0 concorrentes
	public void transferir(int conta_origem, int conta_destino, double valor){
		
		int conta_min = Math.min(conta_origem, conta_destino);
		int conta_max = Math.max(conta_origem, conta_destino);
		
		System.out.println(Thread.currentThread().getName() + ": a adquirir lock da conta " + conta_min);
		synchronized(this.contas[conta_min]){
			System.out.println(Thread.currentThread().getName() + ": adquiriu lock da conta " + conta_min);
				
			//sleep para simular período de tempo entre aquisição dos dois locks
			//try{ Thread.sleep(100);}catch (InterruptedException e) {}
			
			System.out.println(Thread.currentThread().getName() + ": a adquirir lock da conta " + conta_max);
			synchronized(this.contas[conta_max]){
				System.out.println(Thread.currentThread().getName() + ": adquiriu lock da conta " + conta_max);
				this.contas[conta_origem].levantar(valor);
				this.contas[conta_destino].depositar(valor);
				System.out.println(Thread.currentThread().getName() + ": libertou lock da conta " + conta_max);
			}
			System.out.println(Thread.currentThread().getName() + ": libertou lock da conta " + conta_min);
		}
		
	}
	
	
	
	
}
