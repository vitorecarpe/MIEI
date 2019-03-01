import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class Worker implements Runnable{
    private Socket clientSocket;

    public Worker(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
    
            System.out.println("Worker> Processando conexao");
    
            String msg;
            while ((msg = in.readLine()) != null && !msg.equals("exit")) {
                System.out.println("Cliente> " + msg);
                out.println("thx for the msg");
                out.flush();
            }
            
            out.close();
            in.close();
            clientSocket.close();
            System.out.println("Worker> Terminando conexao");
        }
        catch(IOException e){}

    }
}