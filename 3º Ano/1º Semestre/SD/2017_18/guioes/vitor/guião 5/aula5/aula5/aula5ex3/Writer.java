package aula5ex3;


public class Writer implements Runnable {

	private final RWLockInterface lock;
	private final int id;
	private long waitingTime;

	public Writer(RWLockInterface lock, int id) {
		this.lock = lock;
		this.id = id;
		this.waitingTime = 0;
	}

	@Override
	public void run() {
		try {
			System.out.println("Escritor " + id + " tenta entrar na secção crítica");
			long beforeLock = System.currentTimeMillis();

			this.lock.writeLock();
			//secção crítica
			System.out.println("Escritor " + id + " adquire lock");
			waitingTime += (System.currentTimeMillis() - beforeLock);
			Thread.sleep(1000);
			System.out.println("Escritor " + id + " liberta lock");
			this.lock.writeUnlock();
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public long getWaitingTime() {
		return waitingTime;
	}


}
