package ChatChaps;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private final static int port = 9876;
    //private final static String host = "localhost";

    public static void main(String[] args) throws Exception{
        System.out.println("> host: ");
        String str = new BufferedReader(new InputStreamReader(System.in)).readLine();

        Socket clientSocket = new Socket(str, port);
        new Thread(new ClienteInputHandler(clientSocket)).start();
        new Thread(new ClienteOutputHandler(clientSocket)).start();
    }
}


class ClienteInputHandler implements Runnable {
    private final Socket socket;
    private final DataInputStream fromServer;

    public ClienteInputHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.fromServer = new DataInputStream(socket.getInputStream());
    }

    public void run() {
        try {
            while(true){
                String str = fromServer.readUTF();
                System.out.println(str);
            }
        }
        catch (Exception e) {
                System.err.println(e.getMessage());
        }
    }
}


class ClienteOutputHandler implements Runnable {
    private Socket socket;
    private final DataOutputStream toServer;
    private final BufferedReader fromUser;

    public ClienteOutputHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.toServer = new DataOutputStream(socket.getOutputStream());
        this.fromUser = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        try {
            while(true){
                String str = fromUser.readLine();
                toServer.writeUTF(str);
            }
        }
        catch (Exception e) {
                System.err.println(e.getMessage());
        }
    }
}