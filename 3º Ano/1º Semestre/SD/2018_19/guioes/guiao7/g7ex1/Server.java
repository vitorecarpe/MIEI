import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server{
    public static void main(String[] args) throws IOException{
        final Banco banco = new Banco(5);
        final ServerSocket serverSocket = new ServerSocket(1234);
        int counter=1;
        while(true){
            System.out.println("Server Banco> Server pronto !!!");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server Banco> Conexao recebida");

            Thread worker = new Thread(new Worker(banco, clientSocket, counter));
            worker.start();
            System.out.println("Server Banco> Worker " + counter + " criado");
            counter++;
        }
        // serverSocket.close();
    }
    
}