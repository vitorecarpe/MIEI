public class Produtor implements Runnable{
    private BoundedBuffer buffer;
    private int nRuns;

    public Produtor(BoundedBuffer buffer, int nRuns){
        this.buffer=buffer;
        this.nRuns=nRuns;
    }

    public void run(){
        for (int i = this.nRuns; i > 0; i--){
            buffer.put(3);
            buffer.printArray();
        }
    }
}