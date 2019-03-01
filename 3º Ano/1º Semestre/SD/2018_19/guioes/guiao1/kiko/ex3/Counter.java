public class Counter implements Runnable{
    private int num;

    public Counter(){
        num = 0;
    }

    public void run(){
        this.increment();
    }

    public synchronized void increment(){
        this.num++;
        System.out.println(num);
    }
}

class Main{
    public static void main(String[] args) {
        int i = 12;
        Thread arr[] = new Thread[i];
        Counter count = new Counter();

        for (int c = 0; c < i; c++) {
            arr[c] = new Thread(count);
            arr[c].start();
        }

        for (int c = 0; c < i; c++) {
            try {
                arr[c].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
