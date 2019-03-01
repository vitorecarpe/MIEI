public class Consumidor implements Runnable{

    private BoundedBuffer bb;
    private int n;

    public Consumidor(BoundedBuffer b, int n){
        this.bb = b;
        this.n = n;
    }

    public void run(){

        for(int i = 0; i < n; i++){
            bb.get();
        }

    }
}
