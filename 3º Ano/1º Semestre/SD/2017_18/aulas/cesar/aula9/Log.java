package aula9;

import java.util.ArrayList;

public class Log {

	private ArrayList<String> log;
//	private int indexR;
//	private int indexW;
	
	public Log(){
		log = new ArrayList<String>();
//		indexR = 0;
//		indexW = 0;
	}

	/*
	public String getLog() {
		String aux= new String();
		while(indexR<indexW){
		
			aux.concat(this.log.get(indexR));
			indexR++;
		}
		return aux;
	}

	public void putLog(String newLine) {
		this.log.add(indexW,newLine);
		this.indexW++;
	}
	
	public int getIndexR(){
		return indexR;
	}
	
	public int getIndexW(){
		return indexW;
	}
	
	*/
	
	public synchronized void add(String newLine){
		log.add(newLine);
		notifyAll();
		
	}
	
	public synchronized String read(int pos) throws InterruptedException{
		while(pos > log.size()-1) wait();
		return log.get(pos);
	}
	
}
