package Teste;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 * Date: Nov 26, 2008
 * Time: 10:39:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class Barman implements Runnable{
    private BoundedBuffer bar;
    private int encher;


    public Barman(BoundedBuffer bar, int enche){
        this.bar = bar;
        this.encher = enche;
    }

     public void run(){
        for(int i=0;i<encher;i++){
            bar.beber();
        }
    }

}
