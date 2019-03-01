public class Conta {
    private int saldo;

    public Conta(int saldo){
        this.saldo = saldo;
    }

    public double consultar() {
        return this.saldo;
    }

    public synchronized void levantar(double valor) {
        this.saldo -= valor;
    }

    public synchronized void depositar(double valor) {
        this.saldo += valor;
    }

}