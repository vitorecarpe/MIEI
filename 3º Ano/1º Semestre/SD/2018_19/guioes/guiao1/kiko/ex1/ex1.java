import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class ex1 implements Runnable {
    private int n;

    public ex1(int num){
        this.n = num;
    }

    public void run(){
        System.out.println(n);
    }
    
    public void start() {
        this.run();
    }

    public static void main(String[] args) {
        int num;
        System.out.print("Quantas threads? ");
        Scanner s = new Scanner(System.in);
        num = s.nextInt();
        s.close();
        
        for(int i=1; i<=num; i++){
            new ex1(i).start();
        }
    }
}