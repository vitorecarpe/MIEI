package Teste;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 * Date: Nov 26, 2008
 * Time: 12:58:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Barreira {
    private final int capacidade;
    private int etapa;
    private int num;

    public Barreira(int capacidade){
        this.capacidade = capacidade;
    }

    public synchronized void sincronizar(){
        int actual =etapa;
        num ++;
        if(num != capacidade){
            while(actual != etapa){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        }else{
            this.num =0;
            this.etapa ++;
            notifyAll();
        }


    }



}
