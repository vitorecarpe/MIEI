package aula2ex4_tentativa1;

public class Banco {

	private Conta[] contas;
	
	public Banco(int n){
		contas = new Conta[n];
		for(int i=0; i<n; i++){
			contas[i]=new Conta();
		}	
	}
	
	public double consultar(int nr_conta){
		return this.contas[nr_conta].consultar();
	}
	
	public void levantar(int nr_conta, double valor){
		this.contas[nr_conta].levantar(valor);
	}
	
	
	public void depositar(int nr_conta, double valor){
		
		this.contas[nr_conta].depositar(valor);
		
	}
	
	//Se antes de depositar na conta 1 existe um levantamento concorrente, o dinheiro pode não estar disponível
	public void transferir(int conta_origem, int conta_destino, double valor){
		
		this.contas[conta_origem].levantar(valor);
		System.out.println("Banco: Acabámos de retirar " + valor + " à conta " + conta_origem);
		
		//sleep para simular período de tempo entre aquisição dos dois locks
		try{ Thread.sleep(100);}catch (InterruptedException e) {}
		
		this.contas[conta_destino].depositar(valor);
		System.out.println("Banco: Acabámos de depositar " + valor + " à conta " + conta_destino);
	}
	
	
	
	
}
