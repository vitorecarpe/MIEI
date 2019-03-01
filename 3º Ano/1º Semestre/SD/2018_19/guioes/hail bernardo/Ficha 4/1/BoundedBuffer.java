public class BoundedBuffer {

    private int[] values;
    private int poswrite;
    private int arrsize;

    public BoundedBuffer(int n){
        values = new int[n];
        poswrite = 0;
        arrsize = n;
    }

    public synchronized void put(int v){
        try{

            while(poswrite == arrsize-1){
                this.wait();
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

        values[poswrite] = v;

        poswrite++;

        notifyAll();
    }

    public synchronized int get(){
        int i = -1;

        try{

            while(poswrite == 0){
                this.wait();
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

            i = values[poswrite-1];

            poswrite--;

            notifyAll();



        return i;
    }

    public void printArray(){

        StringBuffer sb = new StringBuffer();

        for(int i = 0 ; i < poswrite ; i++){
            sb.append(values[i]);
            sb.append(" ");
        }

        System.out.println(sb);
    }

}
