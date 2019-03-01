package Teste;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 * Date: Nov 26, 2008
 * Time: 10:10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoundedBuffer {
    private Semaphore coposcheios;
    private Semaphore coposvazios;
    private ReentrantLock barmanlock;
    private ReentrantLock clientLock;
    private boolean[] balcao;
    private int proximobeber;
    private int proximoencher;


    public BoundedBuffer(int capacidade){
        coposcheios = new Semaphore(0);
        coposvazios = new Semaphore(capacidade);
        barmanlock = new ReentrantLock();
        clientLock = new ReentrantLock();
        balcao = new boolean[capacidade];
        proximoencher = 0;
        proximobeber = 0;
    }

    public void encher(){
            try {
                coposvazios.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            barmanlock.lock();
            balcao[proximoencher]= true;
            proximoencher = (proximoencher+1) % balcao.length;
            barmanlock.unlock();
            coposcheios.release();
        
    }

    public void beber(){
            try {
                coposcheios.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            clientLock.lock();
            balcao[proximobeber]= false;
            proximobeber = (proximobeber+1) % balcao.length;
            clientLock.unlock();
            coposvazios.release();
        
    }

    public int noBalcao(){
        int i=0;
        for(boolean t: balcao){
            if (t == true)
                i++;
        }
        return i;
    }


}
