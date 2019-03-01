import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Servidor{
    public static void main(String[] args) throws IOException{
        final ServerSocket serverSocket = new ServerSocket(1234);
        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server> Conexao recebida");

            Thread worker = new Thread(new Worker(clientSocket));
            worker.start();
            System.out.println("Server> Worker criado");
            
        }
        // serverSocket.close();
    }
    
}