package ClienteServidor2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Servidor {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(9876);
        HashSet<Socket> socketList = new HashSet<Socket>();
        
        while(true){
            Socket s = serverSocket.accept();
            socketList.add(s);
            
            new Thread(new ConnectionHandler(socketList, s)).start();

            /*
            s.close();
            */
        }
    }

}
