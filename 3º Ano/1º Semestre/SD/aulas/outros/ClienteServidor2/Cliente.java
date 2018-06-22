package ClienteServidor2;

import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException{
        Socket clientSocket = new Socket("localhost", 9876);
        new Thread(new ClienteInputHandler(clientSocket)).start();
        new Thread(new ClienteOutputHandler(clientSocket)).start();
    }
}