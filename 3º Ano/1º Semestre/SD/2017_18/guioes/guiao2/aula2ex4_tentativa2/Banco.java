package aula2ex4_tentativa2;

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
	
	//Deadlock: transferência de 0 para 1 e transferência de 1 para 0 concorrentes
	public void transferir(int conta_origem, int conta_destino, double valor){
		
		System.out.println(Thread.currentThread().getName() + ": a adquirir lock da conta " + conta_origem);
		synchronized(this.contas[conta_origem]){
			System.out.println(Thread.currentThread().getName() + ": adquiriu lock da conta " + conta_origem);
				
			//sleep para simular período de tempo entre aquisição dos dois locks
			//try{ Thread.sleep(100);}catch (InterruptedException e) {}
			
			System.out.println(Thread.currentThread().getName() + ": a adquirir lock da conta " + conta_destino);
			synchronized(this.contas[conta_destino]){
				System.out.println(Thread.currentThread().getName() + ": adquiriu lock da conta " + conta_destino);
				this.contas[conta_origem].levantar(valor);
				this.contas[conta_destino].depositar(valor);
				System.out.println(Thread.currentThread().getName() + ": libertou lock da conta " + conta_destino);
			}
			System.out.println(Thread.currentThread().getName() + ": libertou lock da conta " + conta_origem);
		}
	}
}
