package aula5ex3;

import java.util.concurrent.locks.*;

/**
 * NOTA: Esta solução sofre de starvation de escritores, 
 * pois permite um cenário em que threads leitores/escritores estão constantemente 
 * a entrar na região crítica e a bloquear o acesso a threads escritores.
 */
public class RWLock implements RWLockInterface{
	private ReentrantLock lock;
	private Condition readersCond;
	private Condition writersCond;
	public int readers;		//flag que indica que há leitores na secção crítica
	public int writer;		//flag que indica que há escritores na secção crítica
	
	public RWLock(){
		this.lock = new ReentrantLock();
		this.readersCond = this.lock.newCondition();
		this.writersCond = this.lock.newCondition();
		this.readers = 0;
		this.writer = 0;
	}
	
	public void readLock(){
		//é preciso proteger acessos aos contadores para evitar escritas concorrentes
		this.lock.lock();
		try{
			//esperar enquanto houver algum escritor dentro da secção crítica
			while(this.writer>0){ 
				this.readersCond.await();
			}
			//entra na secção crítica e incrementar o contador de leitores
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
			// se não houver leitores na secção crítica acorda leitores
			if(this.readers == 0){
				this.writersCond.signalAll();  
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
			//espera enquanto houver leitor/escritor na seccao critica
			while( this.writer>0 || this.readers>0 ){
				this.writersCond.await();
			}
			//entra na seccao critica e incrementa contador de escritores
			this.writer++;
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
			this.writer--;
			// seccao critica fica livre entao acorda todos
			this.readersCond.signalAll();
			this.writersCond.signal();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.lock.unlock();
		}
	}


}
