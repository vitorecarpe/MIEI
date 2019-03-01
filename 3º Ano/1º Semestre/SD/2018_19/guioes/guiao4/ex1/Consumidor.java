public class Consumidor implements Runnable{
    private BoundedBuffer buffer;
    private int nRuns;

    public Consumidor(BoundedBuffer buffer, int nRuns){
        this.buffer=buffer;
        this.nRuns=nRuns;
    }

    public void run(){
        for (int i = this.nRuns; i > 0; i--){
            buffer.get();
            buffer.printArray();
        }
    }
}