import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// EE 2019

public class Museu implements Museu{
    private LinkedList<Condition> condPT;
    private LinkedList<Condition> condEN;
    private LinkedList<Condition> condPoly;
    private Condition condGuide;
    private ReentrantLock l;
    private int countPT;
    private int countEN;
    private int countPoly;
    private int countGuide;
    private boolean blockSignalGuide;

    public Museu(){
        this.l = new ReentrantLock();
        this.condPT = new LinkedList<>(this.l.newCondition());
        this.condEN = new LinkedList<>(this.l.newCondition());
        this.condPoly = new LinkedList<>(this.l.newCondition());
        this.condGuide = this.l.newCondition();
        this.countPT = 0;
        this.countEN = 0;
        this.countPoly = 0;
        this.countGuide = 0;
        this.blockSignalGuide = false;
    }

    public void enterPT(){
        Condition cond = new Condition();
        this.l.lock();

        this.countPT++; 

        while((this.countPT+this.countPoly)<10 || this.countGuide<1){
            this.condPT.add(this.cond.await());
        }

        this.l.unlock();
    }
    public void enterEN(){

    }
    public void enterPoly(){

    }
    public void enterGuide(){
        this.l.lock();

        while((this.countPT+this.countEN+this.countPoly)<10){
            this.condGuide.await();
        }


        this.l.unlock();
    }

}