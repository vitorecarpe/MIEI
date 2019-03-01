import java.time.Duration;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args){

        int n = 10;

        int totalOps = 100;

        BoundedBuffer b = new BoundedBuffer(n);

        int cArrSize, pArrSize;

        for(cArrSize = 1; cArrSize < n ; cArrSize++) {

            pArrSize = n - cArrSize;

            Thread[] threads = new Thread[10];

            int i = 0;

            int opsProdutor = totalOps/pArrSize;
            int restoProdutor = totalOps%pArrSize;

            //System.out.println("OpsProd: "+opsProdutor+" RestoProd: "+restoProdutor);

            int opsConsumidor = totalOps/cArrSize;
            int restoConsumidor = totalOps%cArrSize;

            //System.out.println("OpsCons: "+opsConsumidor+" RestoCons: "+restoConsumidor);

            for (; i < cArrSize; i++) {
                if(i == cArrSize - 1)
                    threads[i] = new Thread(new Consumidor(b,opsConsumidor+restoConsumidor));
                else
                    threads[i] = new Thread(new Consumidor(b,opsConsumidor));
            }
            for (; i < n; i++) {
                if(i == n - 1)
                    threads[i] = new Thread(new Produtor(b,opsProdutor+restoProdutor));
                else
                    threads[i] = new Thread(new Produtor(b,opsProdutor));
            }

            long begin = System.nanoTime();

            for (int j = 0; j < n; j++) {
                threads[j].start();
            }

            try {

                for (int j = 0; j < cArrSize + pArrSize; j++) {
                    threads[j].join();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long end = System.nanoTime();

            System.out.println("Time: " + (end-begin)/1000000 + " ms and "+ (end-begin) + "ns with "+pArrSize+" producers and "+cArrSize+" consumers.");



            System.out.println("Débito: " + (float)totalOps/((float)end-(float)begin));

        }

        b.printArray();
    }
}
