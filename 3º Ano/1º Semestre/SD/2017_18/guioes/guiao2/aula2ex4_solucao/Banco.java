package aula2ex4_solucao;


public class Banco {

	private Conta[] contas;

	public Banco(int n){
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

		Integer conta_min = Math.min(conta_origem, conta_destino);
		Integer conta_max = Math.max(conta_origem, conta_destino);

		synchronized(this.contas[conta_min]){		
			synchronized(this.contas[conta_max]){
				//Podia ser antes assim porque o lock é reentrante:
				//this.levantar(conta_origem, valor);
				//this.depositar(conta_destino, valor);
				this.contas[conta_origem].levantar(valor);
				this.contas[conta_destino].depositar(valor);
			}
		}

	}




}
