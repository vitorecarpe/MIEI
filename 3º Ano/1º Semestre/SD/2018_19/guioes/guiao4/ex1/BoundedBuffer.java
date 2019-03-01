public class BoundedBuffer {
    private int[] values;
    private int posWrite=0;

    public BoundedBuffer(int size){
        this.values = new int[size];
    }

    public synchronized void put(int v) {
        try {
        	//se posição de escrita for igual ao tamanho do array...
            while (this.posWrite == this.values.length-1) {
                System.out.println("PUT: buffer cheio!!!");
                wait();
            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }

        values[this.posWrite] = v;
        this.posWrite++;
        //acordar qualquer thread que esteja em wait
        notifyAll();
    }

	public synchronized int get() {
        int value = -1;
        try {
        	//se array estiver vazio...
            while (this.posWrite == 0) {
                System.out.println("GET: buffer vazio!!!");
                wait();
            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
        this.posWrite--;
        value = values[this.posWrite];
        notifyAll();
        return value;
    }

    public synchronized void printArray(){
        System.out.print("BUFFER: ");
        for(int i=0;i<this.posWrite;i++){
            System.out.print(this.values[this.posWrite]);
            System.out.print(" ");
        }
        System.out.println();
    }
    public static void main(String[] args){
        int opsProd = 20, opsCons = 20;

        BoundedBuffer buffer = new BoundedBuffer(10);
        Thread t1 = new Thread(new Produtor(buffer,opsProd));
        Thread t2 = new Thread(new Consumidor(buffer,opsCons));

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}