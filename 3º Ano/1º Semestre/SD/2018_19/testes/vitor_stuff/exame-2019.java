import java.util.concurrent.locks.ReentrantLock;

// EXAME 2019

public class Control implements Control{
    private ReentrantLock l;
    private int nCarros;



    public Control(){
        this.l = new ReentrantLock();
        this.nCarros = 0;
    }

    public void entraCarro(){

    }
    public void saiCarro(){

    }
    public void entraBarco(){

    }
    public void saiBarco(){

    }
}