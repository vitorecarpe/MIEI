import java.util.Random;

class Client implements Runnable {
	Barreira b;
	int n;

	public Client(Barreira b, int n) {
		this.b = b;
		this.n = n;
	}

	public void run() {
		Random rand = new Random();
		int i = 0;
		try {
			while (true) {
				i++;
				System.out.println("Thread " + n + " started stage " + i);
				Thread.sleep(rand.nextInt(5000));
				System.out.println("Thread " + n + " calling barreira at stage " + i);
				b.esperar();
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
	}
}

class Main {
	public static void main(String[] args) {
		final int N = 5;
		Barreira b = new Barreira(N);
		for (int i = 0; i < N; i++)
			new Thread(new Client(b, i)).start();
	}
}