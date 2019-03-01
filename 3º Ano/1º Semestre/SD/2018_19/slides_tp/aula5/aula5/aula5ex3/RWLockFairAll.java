package aula5ex3;

import java.util.concurrent.locks.*;

/**
 * NOTA: Esta solução garante fairness (i.e. protege contra starvation)
 * para escritores e leitores, garantindo que leitores/escritores só têm prioridade
 * num determinado número de rondas. Usa ainda variáveis de condições separadas
 * para leitores e escritores para controlar o tipo de threads que deve acordar a cada momento.
 * @author nunomachado
 *
 */
public class RWLockFairAll implements RWLockInterface {

	ReentrantLock lock;
	int readers;	//flag que indica que há leitores na secção crítica
	int writer;		//flag que indica que há escritores na secção crítica
	private Condition readersCond;	//condição na qual os leitores esperam
	private Condition writersCond;	//condição na qual os escritores esperam
	int writersRequest; 	//número de escritores que pretendem adquirir o lock
	int readersRequest; 	//número de leitores que pretendem adquirir o lock	
	int MAXPRIORITY = 3; 	//número máximo de threads do mesmo tipo que podem executar consecutivamente
	int readersPriority;	//contador de leitores consecutivos que tiveram prioridade antes da execução de escritores
	int writersPriority;	//contador de escritores consecutivos que tiveram prioridade antes da execução de leitores
	
	public RWLockFairAll(){
		lock = new ReentrantLock();
		readersCond = lock.newCondition();
		writersCond = lock.newCondition();
		readers = writer = 0;
		writersPriority = readersPriority = 0;
		readersRequest = writersRequest = 0;
	}
	
	public void readLock(){
		//é preciso proteger os acessos aos contadores para evitar escritas concorrentes
		lock.lock();
		try{
			//esperar enquanto houver algum escritor dentro da secção crítica 
			//ou se o máximo de leitores permitidos já estiverem a executar 
			readersRequest++;
			while(writer == 1
					|| (writersRequest > 0 && readersPriority >= MAXPRIORITY)){ 
				readersCond.await();
			}
			//entrar na secção crítica e incrementar o contador de leitores
			readersRequest--;
			readers++;
			
			//actualizar contadores de prioridade
			writersPriority = 0;
			readersPriority++;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	

	public void readUnlock(){
		lock.lock();
		try{
			readers--;
			//acordar um escritor se já não houver leitores dentro da secção crítica 
			if(readers == 0)
				writersCond.signal();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
	public void writeLock(){
		lock.lock();
		try{
			//esperar enquanto houver algum leitor ou escritor na secção crítica,
			//ou se leitores tiverem prioridade
			writersRequest++;
			while(readers != 0 
					|| writer != 0
					|| (readersRequest > 0 && writersPriority >= MAXPRIORITY)){	
				writersCond.await();
			}
			//entrar na secção crítica e indicar que há um escritor a executar (flag = 1)	
			writersRequest--;
			writer = 1;
			
			//actualizar contadores de prioridade
			readersPriority = 0;
			writersPriority++;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	

	public void writeUnlock(){
		lock.lock();
		try{
			//indicar que secção crítica está livre (flag = 0) 
			writer = 0;	
			
			//acordar todos os leitores e outro escritor
			//fairness será garantida pelas condições de prioridade nos métodos readLock/writeLock
			writersCond.signal();
			readersCond.signalAll();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
}
