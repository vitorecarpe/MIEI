public class Counter implements Runnable{
    private int num;

    public Counter(){
        num = 0;
    }

    @Override
    public void run(){
        this.num++;
        System.out.println(this.num);
    }

    public static void main(String[] args){

        int i = 1000;

        Thread arr[] = new Thread[i];

        Counter count = new Counter();

        for(int c = 0; c < i ; c++){

            arr[c] = new Thread(count);

            arr[c].start();
        }

        for(int c = 0; c < i ; c++){

            try {

                arr[c].join();

            } catch(InterruptedException e){

                e.printStackTrace();

            }
        }


    }
}
