import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class ClienteWriter implements Runnable{
    Socket socket;
    PrintWriter out;
    BufferedReader systemIn;

    public ClienteWriter(Socket clientSocket) throws IOException{
        this.socket = clientSocket;
        this.out = new PrintWriter(socket.getOutputStream());
        this.systemIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run(){
        try {
            String msg;
            while ((msg = systemIn.readLine()) != null && !msg.equals("exit")) {
                out.println(msg);
                out.flush();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        out.close();
    }

}