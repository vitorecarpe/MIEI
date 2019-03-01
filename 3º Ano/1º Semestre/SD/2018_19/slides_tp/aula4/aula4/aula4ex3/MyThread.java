package aula4ex3;

public class MyThread implements Runnable {

	Barreira b;
	
	public MyThread(Barreira bf){
		this.b = bf;
	}
	
	@Override
	public void run() {
		b.esperar();
	}

}
