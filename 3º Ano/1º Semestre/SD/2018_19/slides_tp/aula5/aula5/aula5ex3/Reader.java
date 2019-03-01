package aula5ex3;


public class Reader implements Runnable {

	private final RWLockInterface lock;
	private final int id;

	private long waitingTime;

	public Reader(RWLockInterface lock, int id) {
		this.lock = lock;
		this.id = id;
		this.waitingTime = 0;
	}

	@Override
	public void run() {
		try {
			System.out.println("Leitor " + id + " tenta entrar na secção crítica");	
			long beforeLock = System.currentTimeMillis();

			this.lock.readLock();
			//secção crítica
			System.out.println("Leitor " + id + " adquire lock");
			waitingTime += (System.currentTimeMillis() - beforeLock);
			Thread.sleep(1000);
			System.out.println("Leitor " + id + " liberta lock");
			this.lock.readUnlock();
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public long getWaitingTime() {
		return waitingTime;
	}

}
