public class Main {


    public static void main(String args[]){

        Banco banco = new Banco(2);

        Thread c1 = new Thread(new Cliente1(banco));
        Thread c2 = new Thread(new Cliente2(banco));

        c1.start();
        c2.start();

        try {

            c1.join();
            c2.join();

        } catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Conta 0:"+banco.consultar(0)+"\n");
        System.out.println("Conta 1:"+banco.consultar(1)+"\n");

    }
}
