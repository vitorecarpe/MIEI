import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteMain{
    public static void main(String[] args){
        try {
            final Socket socket = new Socket("127.0.0.1", 1234);
            // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // PrintWriter out = new PrintWriter(socket.getOutputStream());
            // String msg;
            // BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        
            Thread writer = new Thread(new ClienteWriter(socket));
            Thread listener = new Thread(new ClienteListener(socket));
            writer.start();
            listener.start();

            try {
                writer.join();
                listener.join();
            } catch (InterruptedException e) {
                //TODO: handle exception
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}