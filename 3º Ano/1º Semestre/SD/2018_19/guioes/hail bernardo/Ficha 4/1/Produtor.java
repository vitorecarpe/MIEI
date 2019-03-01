public class Produtor implements Runnable{

    private BoundedBuffer bb;

    public Produtor(BoundedBuffer b){
        this.bb = b;
    }

    public void run(){

        for(int i = 0; i < 20 ; i++){
            bb.put(2);

            bb.printArray();
        }

    }
}
