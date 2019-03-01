package aula3banco;

import java.util.concurrent.locks.ReentrantLock;

public class Conta {

	private int id;
	private double valor;
	private ReentrantLock lockConta;
	
	public Conta(int id){
		this.id = id;
		this.valor=0;
		this.lockConta=new ReentrantLock();
	}
	
	public Conta(int id, double saldo){
		this.id = id;
		this.valor = saldo;
		this.lockConta = new ReentrantLock();
	}

	
	public double consultar(){
		return this.valor;
	}
	
	public void depositar(double valor){
		this.valor=this.valor+valor;
	}
	
	public void levantar(double valor){
		this.valor=this.valor-valor;
	}
	
	//métodos auxiliares
	public int getId(){
		return this.id;
	}
	
	public void lock(){
		System.out.println(Thread.currentThread().getName()+": adquirir lock da conta "+this.id);
		this.lockConta.lock();
	}
	
	public void unlock(){
		System.out.println(Thread.currentThread().getName()+": libertar lock da conta "+this.id);
		this.lockConta.unlock();
	}
}
