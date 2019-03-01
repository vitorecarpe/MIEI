package Teste;

public class Main {

    public static void main(String [] args) {
        BoundedBuffer bar = new BoundedBuffer(20);

        Thread[] barmans = new Thread[100];
        Thread[] clientes = new Thread[2000];

        for(int i=0; i<100;i++){
            barmans[i]= new Thread(new Barman(bar,80));
        }

        for (int i=0;i<2000;i++){
            clientes[i]= new Thread(new Cliente(bar,4));
        }

        for(int i=0; i<100;i++){
            barmans[i].start();
        }

        for(int i=0; i<2000;i++){
            clientes[i].start();
        }


        for(int i = 0; i < 100; i++){
            try {
                barmans[i].join();
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }

        for(int i = 0; i < 2000; i++){
                    try {
                        clientes[i].join();
                    } catch (InterruptedException ex) {
                        System.err.println(ex.getMessage());
                    }
                }


        System.out.println(bar.noBalcao());
        
    }
}
