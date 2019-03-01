public class Counter implements Runnable{
    private int num;
    private int i;

    public Counter(int i){
        num = 0;
        this.i = i;
    }

    @Override
    public void run(){
        for(int i = 0; i < this.i; i++)
            this.increment();
        System.out.println(this.num);
    }

    public void increment(){
        this.num++;
    }

    public static void main(String[] args){

        int n = 10;

        int i = 12;

        Thread arr[] = new Thread[n];

        Counter count = new Counter(12);

        for(int c = 0; c < n ; c++){

            arr[c] = new Thread(count);

            arr[c].start();
        }

        for(int c = 0; c < n ; c++){

            try {

                arr[c].join();

            } catch(InterruptedException e){

                e.printStackTrace();

            }
        }


    }
}
