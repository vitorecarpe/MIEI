public class Banco{

    private double contas[];

    public Banco(int n){
        contas = new double[n];
        contas[0] = 1000;
        contas[1] = 0;
    }

    public synchronized double consultar(int conta){
        return contas[conta];
    }

    public synchronized void depositar(int conta, double valor){
        contas[conta] += valor;
    }

    public synchronized void levantar(int conta, double valor){
        contas[conta] -= valor;
    }

    public synchronized void transferir(int de, int para, double valor){
        levantar(de,valor);
        depositar(para,valor);
    }

}
