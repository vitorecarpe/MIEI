package ex1.alt;

public class Main {

	public static void main(String[] args){
		int N = 10;
		int I = 20;
		Thread[] ThreadArray= new Thread[N];

		for(int i=0; i<N; i++){
			ThreadArray[i]= new Thread(new Incrementor(I));
			ThreadArray[i].setName(String.valueOf(i));
		}

		for(int i=0; i<N; i++){
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
	}
}
