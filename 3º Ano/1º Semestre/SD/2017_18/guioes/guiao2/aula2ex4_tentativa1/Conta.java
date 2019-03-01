package aula2ex4_tentativa1;

public class Conta {

	private double valor;
	
	Conta(){
		this.valor=0;
	}
	
	public synchronized double consultar(){
		return this.valor;
	}
	
	public synchronized void depositar(double valor){
		this.valor=this.valor+valor;
	}
	
	public synchronized void levantar(double valor){
		this.valor=this.valor-valor;
	}
	
}
