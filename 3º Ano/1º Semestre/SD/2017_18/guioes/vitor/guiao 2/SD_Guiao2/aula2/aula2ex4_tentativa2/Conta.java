package aula2ex4_tentativa2;

public class Conta {

	private double valor;
	
	Conta(){
		this.valor=0;
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
	
}
