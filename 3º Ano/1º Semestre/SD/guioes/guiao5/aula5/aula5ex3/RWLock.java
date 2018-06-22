package aula5ex3;

import java.util.concurrent.locks.*;

/**
 * NOTA: Esta solução sofre de starvation de escritores, 
 * pois permite um cenário em que threads leitores estão constantemente a entrar
 * na região crítica e a bloquear o acesso a threads escritores.
 * @author nunomachado
 *
 */
public class RWLock implements RWLockInterface{

	ReentrantLock lock;
	Condition condition;
	int readers;	//flag que indica que há leitores na secção crítica
	int writer;		//flag que indica que há escritores na secção crítica
	
	public RWLock(){
		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();
		this.readers = 0;
		this.writer = 0;
	}
	
	public void readLock(){
		//é preciso proteger os acessos aos contadores para evitar escritas concorrentes
		this.lock.lock();
		try{
			//esperar enquanto houver algum escritor dentro da secção crítica
			while(this.writer>0){ 
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
			while(this.readers>0 || this.writer>0){
				this.condition.await();
			}
			//entrar na secção crítica e indicar que há um escritor a executar (flag = 1)
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
