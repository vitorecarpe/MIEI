package aula4ex3;

public class Barreira {

	private int maxElem;
	private int counterElem;
	private int counterEtapa;

	public Barreira(int n){
		this.maxElem=n;
		this.counterElem=0;
		this.counterEtapa=0;
	}

	public int getEtapa(){
		return counterEtapa;
	}

	public synchronized void esperar(){

		//incrementar número de threads na barreira
		this.counterElem++;
		int cur_etapa = this.counterEtapa;

		//última thread acorda todas e incrementa etapa
		if(this.counterElem==this.maxElem){
			this.notifyAll();
			System.out.println("A barreira tem " + this.counterElem + "/"+this.maxElem+" elementos na etapa "+counterEtapa+" -> Thread "+Thread.currentThread().getName()+" acorda outras threads");

			//preparar barreira para a próxima etapa
			//(threads seguintes já vão esperar)
			this.counterEtapa += 1;
			this.counterElem = 0;
		}
		//se contador da etapa ainda não foi alterado é porque ainda não chegaram as threads todas. Portanto, temos de esperar.
		//NOTA: as boas práticas do Java sugerem proteger sempre um wait() com um ciclo while. 
		//Ver: https://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Object.html#wait(long)
		//Pelo facto de usarmos um ciclo while, não podemos ter aqui a condição "while(counterElem < maxElem)", 
		//senão as threads podem bloquear após a mudança de etapa e recomeço do contador counterElem.
		else while(cur_etapa == this.counterEtapa){
			System.out.println("A barreira tem " + this.counterElem + "/"+this.maxElem+" elementos na etapa "+counterEtapa+" -> Thread "+Thread.currentThread().getName()+" espera");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread "+Thread.currentThread().getName()+" saiu da barreira na etapa "+cur_etapa);
		}
	}
}
