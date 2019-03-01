package aula2ex1;

import ex2.alt.Counter;
import ex2.alt.Incrementor;

public class Main {

	public static void main(String[] args){
		int N = 10;
		int I = 20;
		Thread[] ThreadArray= new Thread[N];
		Counter c = new Counter();
			
		for(int i=0; i<N; i++){
			ThreadArray[i]= new Thread(new Incrementor(c,I));	
			ThreadArray[i].setName(String.valueOf(i));
			ThreadArray[i].start();
		}
		
		try {
			for(int i=0; i<N; i++){
				ThreadArray[i].join();
			}
		} catch (InterruptedException e) {
			System.err.println("Erro no Join!");
			e.printStackTrace();
		}
		
		System.out.println("Contador tem o valor: " + c.getCounter());
				
	}
}
