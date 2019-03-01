public class Consumidor implements Runnable{

    private BoundedBuffer bb;

    public Consumidor(BoundedBuffer b){
        this.bb = b;
    }

    public void run(){

        for(int i = 0; i < 20; i++){
            bb.get();

            bb.printArray();
        }

    }
}
