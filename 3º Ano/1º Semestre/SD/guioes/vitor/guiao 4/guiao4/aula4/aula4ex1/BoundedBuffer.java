package aula4ex1;

public class BoundedBuffer {

	private int[] values;
	private int poswrite;


	public BoundedBuffer(int size){
		this.values = new int[size];
		this.poswrite=0;
	}

	public synchronized void put(int v){
		try {
			while(this.poswrite == this.values.length){
				//esperar até que buffer tenha espaço
				System.out.println("PUT: buffer está cheio, esperar até que tenha espaço");
				this.wait();
			}

			System.out.println("PUT: inserir valor " + v +" na posição "+poswrite);
			this.values[poswrite]=v;
			poswrite++;

			//acordar threads que estejam à espera de items no buffer
			//System.out.println("PUT: notificar que um valor foi colocado no buffer");
			this.notifyAll();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public synchronized int get(){
		try {
			while(this.poswrite==0){
				//esperar até que buffer tenha items
				System.out.println("GET: buffer está vazio, esperar até que tenha valores");
				this.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int posread = --this.poswrite;
		int res = this.values[posread];
		System.out.println("GET: retirar valor "+res+" na posição "+posread);

		//acordar threads que estejam à espera que buffer tenha espaço
		System.out.println("GET: notificar que um valor foi retirado do buffer");
		this.notifyAll();

		return res;
	}

}
