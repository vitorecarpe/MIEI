package aula5ex3;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
			
			long startTime = System.currentTimeMillis();
			
			int nThreads = 30;

			Thread[] threads = new Thread[nThreads];
			
			LinkedList<Reader> readers = new LinkedList<Reader>();
			LinkedList<Writer> writers = new LinkedList<Writer>();
			
			RWLockInterface lock = new RWLock();	//starvation de escritores
			//RWLockInterface lock = new RWLockFairWriter(); //starvation de leitores
			//RWLockInterface lock = new RWLockFairAll(); //fairness

			for (int i = 0; i < nThreads; i ++) {
				
				if (i % 2 == 0) {
					Reader r = new Reader(lock,i);
					readers.add(r);
					threads[i] = new Thread(r);
					
				} else {
					Writer w = new Writer(lock,i);
					writers.add(w);
					threads[i] = new Thread(w);
				}
				threads[i].start();
			}
			
			for(Thread t : threads) {
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("\nThreads terminaram");
			
			//check waiting times
			long totalWaitingReaders = 0;
			long totalWaitingWriters = 0;
			
			for(Reader r : readers) {
				totalWaitingReaders += r.getWaitingTime();
			}
			
			for(Writer w : writers) {
				totalWaitingWriters += w.getWaitingTime();
			}
			
			//calcular tempos de espera médios - numa solução sem starvation os tempos de espera
			//de leitores e escritores deverão ser próximos
			double timeElapsed = (System.currentTimeMillis() - startTime)/1000;
			System.out.println("Tempo de execução:\t" + timeElapsed+"s");
			System.out.println("Tempo médio de espera dos escritores:\t" + totalWaitingWriters/(1000*nThreads/2)+"s");
			System.out.println("Tempo médio de espera dos leitores:\t" + totalWaitingReaders/(1000*nThreads/2)+"s");
		}
	

}
