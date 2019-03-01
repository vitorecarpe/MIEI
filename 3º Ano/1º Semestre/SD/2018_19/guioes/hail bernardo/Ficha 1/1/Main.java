public class Main implements Runnable{

    private int num;

    public Main(int i){
        num = i;
    }

    @Override
    public void run(){
        for(int i = 1; i <= num ; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args){

        int n = 10;

        int i = 12;

        Thread arr[] = new Thread[n];

        for(int c = 0; c < n ; c++){

            arr[c] = new Thread(new Main(i));

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
