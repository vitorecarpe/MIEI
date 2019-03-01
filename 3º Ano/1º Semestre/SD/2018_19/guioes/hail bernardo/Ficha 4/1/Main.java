public class Main {

    public static void main(String[] args){

        int n = 10;

        BoundedBuffer b = new BoundedBuffer(n);

        Consumidor c = new Consumidor(b);
        Produtor p = new Produtor(b);

        Thread tc = new Thread(c);
        Thread tp = new Thread(p);

        tc.start();
        tp.start();

        try{
            tc.join();
            tp.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        b.printArray();
    }
}
