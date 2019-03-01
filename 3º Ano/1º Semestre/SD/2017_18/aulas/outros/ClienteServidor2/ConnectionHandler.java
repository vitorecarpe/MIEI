package ClienteServidor2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashSet;

public class ConnectionHandler implements Runnable{
    HashSet<Socket> socketList = new HashSet<Socket>();
    Socket mySocket;
    
    public ConnectionHandler(HashSet<Socket> socketList, Socket s){
        this.socketList = socketList;
        mySocket = s;
    }

    public void run() {
        try{
            DataInputStream input = new DataInputStream(mySocket.getInputStream());
            DataOutputStream output;
            String str;

            while(true){
                str = input.readUTF();
                for(Socket s : socketList){
                    if(s != null){
                        output = new DataOutputStream(s.getOutputStream());
                        output.writeUTF(str);
                    }
                }
            }
        }
        catch(Exception e){
            /**/
        }
    }
    
}
