public class Produtor implements Runnable{

    private BoundedBuffer bb;
    private int n;

    public Produtor(BoundedBuffer b, int n){
        this.bb = b;
        this.n = n;
    }

    public void run(){

        for(int i = 0; i < n ; i++){
            bb.put(2);
        }

    }
}
