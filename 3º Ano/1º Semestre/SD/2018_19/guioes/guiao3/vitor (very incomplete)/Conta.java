import java.util.concurrent.locks.ReentrantLock;

public class Conta{
	private double saldo;
	private ReentrantLock lockConta;

	public Conta(double saldo){
		this.saldo=saldo;
		this.lockConta= new ReentrantLock();
	}

	public double consultar(){
		return this.saldo;
	}

	public void depositar(double valor){
		this.saldo+=valor;
	}

	public void levantar(double valor){
		this.saldo-=valor;
	}

}