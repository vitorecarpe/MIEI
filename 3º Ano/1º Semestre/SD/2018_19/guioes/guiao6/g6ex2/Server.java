import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server{
    
    public static void main(String[] args) throws IOException{
        final ServerSocket serverSocket = new ServerSocket(1234);
        while(true){
            Socket clienteSocket = serverSocket.accept();
            System.out.println("Server> Conexao recebida");
            BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clienteSocket.getOutputStream());
        
            String msg;
            while( (msg=in.readLine())!=null && !msg.equals("exit") ){
                System.out.println("Cliente> " + msg);
                out.println("thx for the msg");
                out.flush();
            }
        
            out.close();
            in.close();
            clienteSocket.close();
        }
        // serverSocket.close();
    }
    
}