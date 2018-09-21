package monitorUDP;

import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

// Listener do Monitor
public class MonitorListenerThread extends Thread{
    private AtomicBoolean running = new AtomicBoolean(true);
    private int port;
    private String separator = "\n";
    private Tabela tabela;
    
    public MonitorListenerThread(Tabela tabela, int port){
        this.tabela = tabela;
        this.port = port;
    }
    
    public void stopMonitorListener(){
        this.running.set(false);
        this.interrupt();
        System.out.println("\n  [MonitorListener] MonitorListener terminado!!!");
    }
        
    public void run(){
        while(running.get()){
            try(DatagramSocket mcSocket = new DatagramSocket(port)){
                System.out.println("  [MonitorListener] Receiver running at: " + mcSocket.getLocalSocketAddress());

                DatagramPacket packetRecebido = new DatagramPacket(new byte[1024], 1024);
                mcSocket.receive(packetRecebido);
                String msg = new String(packetRecebido.getData(), packetRecebido.getOffset(), packetRecebido.getLength());
//                System.out.println("  [MonitorListener] Received from: " + packetRecebido.getAddress().toString() + ":" + packetRecebido.getPort());
//                System.out.println("  [MonitorListener] Message: \n" + msg);
                
//                if(msg.startsWith("STATUS\n")){ System.out.println("YES - START WITH");}
                String[] msgList = msg.split(separator);
                if(msgList[0].equals("STATUS")) { 
                    System.out.println("  [MonitorListener] STATUS received from Agente. "+ packetRecebido.getAddress().toString() + ":" + packetRecebido.getPort());
                    tabela.probeAnswer(packetRecebido.getAddress());
                    System.out.println("  [MonitorListener] Saved to ServerStatusTable!");
//                    for(String word : msgList) 
//                        System.out.println(word);
                }
//                mcSocket.close();
            } 
            catch (Exception e) { System.out.println(e); }
        }
    }
}
