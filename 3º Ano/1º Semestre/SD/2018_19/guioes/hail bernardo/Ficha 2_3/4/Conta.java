public class Conta {

    private double valor;

    public Conta(double valor){
        this.valor = valor;
    }

    public synchronized double consultar(){
        return this.valor;
    }

    public synchronized void depositar(double valor){
        this.valor += valor;
    }

    public synchronized void levantar(double valor){
        this.valor -= valor;
    }
}
