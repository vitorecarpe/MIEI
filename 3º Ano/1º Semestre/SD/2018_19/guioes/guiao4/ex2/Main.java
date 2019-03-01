public class Main{
    public static void main(String[] args) {
        int opsTotal = 50; // produz 50 consome 50
        int tC=1, tP=2; // tempo de espera entre cada get/put
        int nP, nC, nT=10;
        float debito;
        BoundedBuffer buffer = new BoundedBuffer(10);        
        Thread[] threadsProd = new Thread[nP];
        Thread[] threadsCons = new Thread[nC];
        long[] timers = new long[nT];
        float[] debitos = new float[nT];

        for(nP=1; nP<nT; nP++){
            long timeInicio = System.currentTimeMillis();
            for(int i=0; i<nP; i++){
                threadsProd[i] = new Thread( new Produtor(buffer, 1));
            }
            for(int i=0; i<nC; i++){
                threadsCons[i] = new Thread( new Consumidor(buffer, 1));
            }
            // stuff happens
            for(int i=0; i<nP; i++){
                try{ threadsProd[i].join(); } 
                catch(InterruptedException e){ e.printStackTrace(); }
            }
            for(int i=0; i<nC; i++){
                try{ threadsCons[i].join(); } 
                catch(InterruptedException e){ e.printStackTrace(); }
            }
            long timeFim = System.currentTimeMillis();
        }

        debito=opsTotal/(timeFim-timeInicio);
        long time=timeFim-timeInicio;
        System.out.println("Duração: " + time + " | Debito: "+debito);
    }
}