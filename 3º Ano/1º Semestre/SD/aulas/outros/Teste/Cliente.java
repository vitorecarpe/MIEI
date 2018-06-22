package Teste;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 * Date: Nov 26, 2008
 * Time: 11:09:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Cliente implements Runnable{
    private BoundedBuffer bar;
    private int beber;

    public Cliente(BoundedBuffer bar, int bebe){
        this.bar = bar;
        this.beber = bebe;
    }

    public void run(){
       for(int i=0;i<beber;i++){
        this.bar.beber();
        }
    }
}
