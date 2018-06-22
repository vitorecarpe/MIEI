package aula4ex3;

public class Main {

	public static void main(String[] args) {

		int N = 10;
		Barreira b = new Barreira(N);

		//criar 20 threads
		for (int i=0;i<20;i++){
			Thread t = new Thread(new MyThread(b));
			t.setName(String.valueOf(i));
			t.start();
		}
	}

}
