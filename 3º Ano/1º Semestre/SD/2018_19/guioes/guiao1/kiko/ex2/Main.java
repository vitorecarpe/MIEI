import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int nt, ni;

        Scanner s = new Scanner(System.in);
        System.out.print("Quantas threads? ");
        nt = s.nextInt();
        System.out.print("Quantos incrementos (por thread) ? ");
        ni = s.nextInt();
        s.close();

        Counter c = new Counter();
        Thread[] ThreadArray = new Thread[nt];
        for (int i = 0; i < nt; i++) {
            ThreadArray[i] = new Thread(new Incrementor(c, ni));
            ThreadArray[i].start();
        }

        try {
            for (int i = 0; i < nt; i++)
                ThreadArray[i].join();
        } 
        catch (InterruptedException e) {
            System.err.println("Erro no Join!");
        }

        System.out.println("Contador tem o valor: " + c.getCounter());
    }
}