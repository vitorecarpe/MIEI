package monitorUDP;

import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

// ProbeRequest do Monitor
public class MonitorProbeRequestThread extends Thread{
    private AtomicBoolean running = new AtomicBoolean(true);
    private int port;
    private InetAddress group;
    private Tabela tabela;
        
    public MonitorProbeRequestThread(Tabela tabela, InetAddress address, int port){
        this.port = port;
        this.group = address;
        this.tabela = tabela;
    }
    
    public void stopMonitorProbe(){
        this.running.set(false);
        this.interrupt();
        System.out.println("\n [MonitorProbeRequest] MonitorProbeRequest terminado!!!");
    }
        
    public void run(){
        while(running.get()){
            try (MulticastSocket udpSocket = new MulticastSocket()) {
                udpSocket.setTimeToLive(16);
                byte[] msg = "PROBING".getBytes();
                DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
                udpSocket.send(packet);

                System.out.println(" [MonitorProbeRequest] Multicast message sent to group.");
                tabela.probeRequest();
//                udpSocket.close();
                Thread.sleep(5000);
                    tabela.printEstado();
                Thread.sleep(5000);
            }
            catch(Exception e){ System.out.println(e); }
        }
    }
}