import java.util.Random;

class Barrier {
  final int N;
  int proc=0;
  int stage=0;

  public Barrier(int n) { this.N = n; }

  public synchronized void barrier() throws InterruptedException {
    int local = stage;
    if( proc<N )
      while(local==stage) wait();
    else{
      stage++;
      proc=0;
      notifyAll();
    }
  }
}

class Client implements Runnable {
  Barrier b;
  int n;
  public Client(Barrier b, int n) {
    this.b = b;
    this.n = n;
  }
  public void run() {
    Random rand = new Random();
    int i = 0;
    try {
      while(true) { 
        i++;
        System.out.println("Thread " + n + " started stage " + i);
        Thread.sleep(rand.nextInt(5000));
        System.out.println("Thread " + n + " finished stage " + i);
        b.barrier();
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted");
    }
  }
}

class Main {
  public static void main(String[] args) {
    final int N = 5;
    Barrier b = new Barrier(N);
    for (int i = 0; i < N; i++)
      new Thread(new Client(b, i)).start();
  }
}