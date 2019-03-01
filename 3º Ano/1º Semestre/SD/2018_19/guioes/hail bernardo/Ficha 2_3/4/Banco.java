public class Banco {

    private Conta contas[];

    public Banco(int n){
        contas = new Conta[n];
        contas[0] = new Conta(1000);
        contas[1] = new Conta(0);
    }

    public double consultar(int conta){
        return contas[conta].consultar();
    }

    public void depositar(int conta, double valor){
        contas[conta].depositar(valor);
    }

    public void levantar(int conta, double valor){
        contas[conta].levantar(valor);
    }

    public void transferir(int de, int para, double valor){
        Conta menor = contas[Math.min(de,para)];
        Conta maior = contas[Math.max(de,para)];
        synchronized(menor) {
            synchronized (maior) {
                levantar(de, valor);
                depositar(para, valor);
            }
        }
    }

}
