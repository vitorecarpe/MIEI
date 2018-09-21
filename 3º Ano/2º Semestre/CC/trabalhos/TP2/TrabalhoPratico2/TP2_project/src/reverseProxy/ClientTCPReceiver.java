package reverseProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ClientTCPReceiver extends Thread{
    private InputStream fromClient;
    private OutputStream toServer;
    private byte[] mensagem;
    
    public ClientTCPReceiver(InputStream fromClient, OutputStream toServer){
        this.fromClient = fromClient;
        this.toServer = toServer;
        this.mensagem = new byte[1024];
    }
    
    public void run(){
//        int bytesRead;
        try {
//            while ( (bytesRead = fromClient.read(mensagem)) != -1 ) {
            while ( fromClient.read(mensagem, 0, 1024) != -1 ) {
//                System.out.println("   [ClientTCPReceiver] mensagem: " + mensagem);
                toServer.write(mensagem, 0, 1024);
                toServer.flush();
//                System.out.println("   [ClientTCPReceiver] flushed");
            }
        } 
        catch (IOException e) { System.out.println ("   [ClientTCPReceiver] STATUS: End of request!!!"); }
        
        try{
            System.out.println("   [ClientTCPReceiver] Client closed connection");
            toServer.close();
        } 
        catch (IOException e) { System.out.println ("   [ClientTCPReceiver] ERROR: Client connection could not be closed!!! \n" + e ); }
    }
}
