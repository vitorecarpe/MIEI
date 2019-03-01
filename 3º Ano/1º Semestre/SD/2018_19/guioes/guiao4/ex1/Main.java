public class Main{
    public static void main(Strings[] args) {
        int opsTotal = 30;
        int nP=10, nC=10;
        float debito;

        BoundedBuffer buffer = new BoundedBuffer(10);

        Thread[] threadsProd = new Thread[nP];
        Thread[] threadsCons = new Thread[nC];
        int i=0;
        for(int i=0; i<nP; i++){
            threadsCons[i] = new Thread( new Produtor(buffer, 1))
        }
        for(int i=0; i<nC; i++){
            threadsProd[i] = new Thread( new Consumidor(buffer, 1))
        }
        
        long timeInicio = System.currentTimeMillis();t1.start();t2.start();try
        {
            t1.join();
            t2.join();
        }catch(
        InterruptedException e){
            e.printStackTrace();
        }
        long timeFim = System.currentTimeMillis();
    
        debito=opsTotal/(tempoFim-tempoInicio);
        System.out.println(debito);
    }
}