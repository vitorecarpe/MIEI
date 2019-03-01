import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class ClienteListener implements Runnable{
    Socket socket;
    BufferedReader in;

    public ClienteListener(Socket clientSocket) throws IOException{
        this.socket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run(){
        try {
            String msg;
            while ((msg = in.readLine()) != null && !msg.equals("exit")) {
                System.out.println("Chat>" + msg);
            }
            in.close();
        } catch (Exception e) {
            //TODO: handle exception
        }

    }

}