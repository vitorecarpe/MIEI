package reverseProxy;
import monitorUDP.Tabela;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReverseProxyListener extends Thread {
    private AtomicBoolean running = new AtomicBoolean(true);
    private final int port;
    
    private Tabela tabela;
    private ServerSocket reverseSocket;
    
    public ReverseProxyListener(Tabela t){
        this.port = 80;
        this.tabela = t;
    }
    
    public void stopReverseProxyListener(){
        this.running.set(false);
        this.interrupt();
        System.out.println("\n  [ReverseProxyListener] ReverseProxyListener terminado!!!");
    }
        
    public void run(){
        try{
            reverseSocket = new ServerSocket(port);
            reverseSocket.setReuseAddress(true);
            while(running.get()){
                Socket clientSocket = reverseSocket.accept();
                InetAddress serverAddress = this.tabela.getBestServer();
                
                if( serverAddress==null ){
                    System.out.println("  [ReverseProxyListener] Nao existem servidores disponiveis");
                    clientSocket.close ();
                }
                else {
                    try {
                        System.out.println("  [ReverseProxyListener] Servidor escolhido: " + serverAddress.toString());
                        tabela.startTCP(serverAddress);
                        Socket httpSocket = new Socket (serverAddress, port);
                        
                        final InputStream fromClient = clientSocket.getInputStream();
                        final OutputStream toClient = clientSocket.getOutputStream();
                        final InputStream fromServer = httpSocket.getInputStream();
                        final OutputStream toServer = httpSocket.getOutputStream();
                        
                        ClientTCPReceiver receiver = new ClientTCPReceiver(fromClient, toServer);
                        ClientTCPSender sender = new ClientTCPSender(fromServer, toClient, tabela, serverAddress);
                        receiver.start();
                        sender.start();
                        System.out.println("  [ReverseProxyListener] Ligacao estabelecida.");
                    }
                    catch(IOException e) { 
                        System.out.println("  [ReverseProxyListener] Connection aborted \n" + e);
                        clientSocket.close();
                        tabela.closeTCP(serverAddress,0);
                    }
//                httpSocket.close ();
                }
            }
        }
        catch (IOException e) { System.out.println("\n [ReverseProxyListener] Port ocupada !!!\n"+e); }
        catch (Exception e) {System.out.println(e);} 
    }
}
