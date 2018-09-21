package monitorUDP;

import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.util.Scanner;

// MonitorUDP
public class MonitorUDP {
    public static void main(String args[]){
        try {
            MonitorUDP monitor = new MonitorUDP(new Tabela());
            monitor.run();
        } catch (UnknownHostException e) {
            System.out.println("<MonitorUDP> Host Address indisponivel");
            System.out.println(e);
        }
    }
    
    private int port;
    private InetAddress inetAddress;
    private Tabela tabela;
    private MonitorListenerThread monitorListenerThread;
    private MonitorProbeRequestThread monitorProbeRequestThread;
    private MonitorCleanInactives monitorCleanThread;
    
    public MonitorUDP(Tabela t) throws UnknownHostException{
        this.port = 8888;
        this.inetAddress = InetAddress.getByName("239.8.8.8");
        this.tabela = t;
        this.monitorListenerThread = new MonitorListenerThread(tabela, port);
        this.monitorProbeRequestThread = new MonitorProbeRequestThread(tabela, inetAddress, port);
        this.monitorCleanThread = new MonitorCleanInactives(tabela);
    }
    
    // Interrompe o Monitor
    public void stopMonitorUDP(){
        this.monitorListenerThread.stopMonitorListener();
        this.monitorProbeRequestThread.stopMonitorProbe();
        this.monitorCleanThread.stopMonitorCleaner();
        System.out.println("\n<MonitorUDP> MonitorUDP terminado!!!");
    }
    
    public void run(){
        System.out.println("<MonitorUDP> Iniciando MonitorListener");
        monitorListenerThread.start();
        System.out.println("<MonitorUDP> Iniciando MonitorProbeRequest");
        monitorProbeRequestThread.start();
        System.out.println("<MonitorUDP> Iniciando MonitorCleaner");
        monitorCleanThread.start();
        
//        Scanner input = new Scanner(System.in);
//        String command = "ohayo";
//        while( !command.equalsIgnoreCase("stop") ){
//            command = input.next();
//            System.out.println();
//        }
//        this.stopMonitorUDP();
//        System.exit(0);
    }
    
}
