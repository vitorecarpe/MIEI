public class Cliente1 implements Runnable{

    private Banco banco;

    public Cliente1(Banco banco){
        this.banco = banco;
    }

    @Override
    public void run(){

        this.banco.transferir(0,1,1000.0);

        /*for(int i = 0; i < 1000 ; i++){
            this.banco.depositar(0,5.0);
            System.out.println(banco.consultar(0)+"\n");

        }
        */
    }

}
