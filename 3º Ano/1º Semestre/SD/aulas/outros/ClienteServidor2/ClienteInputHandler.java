package ClienteServidor2;

import java.io.DataInputStream;
import java.net.Socket;


public class ClienteInputHandler implements Runnable {

    Socket socket;
    
    public ClienteInputHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            String fromServer;
            while(true){
                fromServer = input.readUTF();
                System.err.println(fromServer);
            }
        }
        catch (Exception ex) {
                /**/
        }
    }
}