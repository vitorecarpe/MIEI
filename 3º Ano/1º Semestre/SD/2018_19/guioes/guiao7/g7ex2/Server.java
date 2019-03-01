import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.io.IOException;

public class Server{
    public static void main(String[] args) throws IOException{
        HashMap clientes = new HashMap<Integer,Socket>();
        final ServerSocket serverSocket = new ServerSocket(1234);
        int counter=0;
        while(true){
            System.out.println("Server Chat> Server pronto !!!");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server Chat> Conexao recebida");

            Thread worker = new Thread(new Worker(clientSocket, counter, clientes));
            worker.start();
            clientes.put(counter,clientSocket);
            System.out.println("Server Chat> Worker " + counter + " criado");
            counter++;
        }
        // serverSocket.close();
    }
    
}