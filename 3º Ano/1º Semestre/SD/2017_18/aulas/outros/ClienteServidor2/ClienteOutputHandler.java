package ClienteServidor2;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClienteOutputHandler implements Runnable {

    Socket socket;
    
    public ClienteOutputHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                String str = fromUser.readLine();
                output.writeUTF(str);
            }
        }
        catch (Exception ex) {
                /**/
        }
    }
}
