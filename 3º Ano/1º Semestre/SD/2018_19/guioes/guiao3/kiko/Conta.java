import java.util.concurrent.locks.ReentrantLock;

public class Conta {
    private double saldo;
    private ReentrantLock lockConta = new ReentrantLock();

    public Conta(double saldo){
        this.saldo = saldo;
    }

    public void lock() {
        this.lockConta.lock();
    }
    public void unlock() {
        this.lockConta.unlock();
    }

    public double consultar() {
        double valor;
        this.lockConta.lock();
        valor = this.saldo;
        this.lockConta.unlock();
        return valor;
    }

    public synchronized void levantar(double valor) {
        this.lockConta.lock();
        this.saldo -= valor;
        this.lockConta.unlock();
    }

    public synchronized void depositar(double valor) {
        this.lockConta.lock();
        this.saldo += valor;
        this.lockConta.unlock();
    }

}