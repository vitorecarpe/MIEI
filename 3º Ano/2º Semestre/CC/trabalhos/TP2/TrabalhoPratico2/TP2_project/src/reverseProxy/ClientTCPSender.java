package reverseProxy;
import monitorUDP.Tabela;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class ClientTCPSender extends Thread{
    private InputStream fromServer;
    private OutputStream toClient;
    private Tabela tabela;
    private InetAddress servidor;
    private byte[] resposta;
    
    public ClientTCPSender(InputStream fromServer, OutputStream toClient, Tabela tabela, InetAddress servidor){
        this.fromServer = fromServer;
        this.toClient = toClient;
        this.tabela = tabela;
        this.servidor = servidor;
        this.resposta = new byte[1024];
    }
    
    public void run(){
//        int bytesRead;
        long start, end;
        double larguraBanda = 0;
        int pacotes = 0;
        start = System.nanoTime();
        try {
//            while ((bytesRead = fromServer.read(resposta)) != -1) {
            while ( fromServer.read(resposta, 0, 1024) != -1 ) {
//                System.out.println("   [ClientTCPSender] resposta: " + resposta);
                toClient.write(resposta, 0, 1024);
                toClient.flush();
                pacotes++;
//                System.out.println("   [ClientTCPSender] flushed");
            }
        } 
        catch (IOException e) { System.out.println ("   [ClientTCPSender] STATUS: End of response!!!"); }
        
            end = System.nanoTime();
            larguraBanda = (pacotes*1024)/(double)((end-start)*Math.pow(10,-9));
            
        try{
            System.out.println("   [ClientTCPSender] Server closed connection");
            toClient.close();
        } 
        catch (IOException e) { System.out.println ("   [ClientTCPSender] ERROR: Server connection could not be closed!!! \n" + e ); }
        finally { 
            tabela.closeTCP(servidor,larguraBanda); 
        }
    }
}
