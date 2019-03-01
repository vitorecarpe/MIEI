import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
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
        
            int number;
            int total=0, count=0;;
            while( (number=in.read())!=0 ){
                System.out.println("Cliente> " + number);
                total+=number;
                count++;
                out.println("Total atm: " + total);
                out.flush();
            }
            out.println("Media: " + total/count);
            System.out.println("Server> Conexao terminada");
                
            out.close();
            in.close();
            clienteSocket.close();
        }
        // serverSocket.close();
    }
    
}