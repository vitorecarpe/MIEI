package aula5ex3;

import java.util.concurrent.locks.*;

/**
 * NOTA: Esta solução protege contra starvation de escritores, mas
 * sofre de starvation de leitores, porque, em caso de espera, 
 * os escritores terão sempre prioridade de execução sobre os leitores.
 * @author nunomachado
 *
 */
public class RWLockFairWriter implements RWLockInterface {

	ReentrantLock lock;
	Condition condition;
	int readers;		//flag que indica que há leitores na secção crítica
	int writer;			//flag que indica que há escritores na secção crítica
	int writersRequest; //número de escritores que pretendem adquirir o lock
	
	public RWLockFairWriter(){
		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();
		this.readers = 0;
		this.writer = 0;
		this.writersRequest = 0;
	}
	
	public void readLock(){
		//é preciso proteger os acessos aos contadores para evitar escritas concorrentes
		this.lock.lock();
		try{
			//esperar enquanto houver algum escritor dentro da secção crítica
			//ou escritores à espera (i.e. dar prioridade de execução aos escritores)
			while(this.writer==1 || this.writersRequest > 0){ 
				this.condition.await();
			}
			//entrar na secção crítica e incrementar o contador de leitores
			this.readers++;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.lock.unlock();
		}
	}
	

	public void readUnlock(){
		this.lock.lock();
		try{
			this.readers--;
			//acordar escritores se já não houver leitores dentro da secção crítica 
			if(this.readers == 0){
				this.condition.signalAll();  
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.lock.unlock();
		}
	}
	
	public void writeLock(){
		this.lock.lock();
		try{
			//esperar enquanto houver algum leitor ou escritor na secção crítica
			this.writersRequest++;
			while(this.readers!=0 || this.writer!=0){	
				this.condition.await();
			}
			//entrar na secção crítica e indicar que há um escritor a executar (flag = 1)
			this.writersRequest--;
			this.writer = 1;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.lock.unlock();
		}
	}
	

	public void writeUnlock(){
		this.lock.lock();
		try{
			//indicar que secção crítica está livre (flag = 0) e acordar leitores/escritores bloqueados
			this.writer = 0;
			this.condition.signalAll();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.lock.unlock();
		}
	}


}
