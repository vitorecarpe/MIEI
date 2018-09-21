package agenteUDP;

import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.util.Scanner;

// AgenteUDP
public class AgenteUDP {
    public static void main(String[] args){
        try {
            AgenteUDP agente = new AgenteUDP();
            agente.run();
        } catch (UnknownHostException e) {
            System.out.println("<AgenteUDP> Host Address indisponivel");
            System.out.println(e);
        }
    }
    
    private int port;
    private InetAddress group;
    private AgenteProbeListenerThread agenteUDPlistenerThread;
    
    public AgenteUDP() throws UnknownHostException{
        this.port = 8888;
        this.group = InetAddress.getByName("239.8.8.8");
        this.agenteUDPlistenerThread = new AgenteProbeListenerThread(group, port);
    }
    
    // Interrompe o Agente
    public void stopAgenteUDP(){
        this.agenteUDPlistenerThread.stopAgenteProbe();
        System.out.println("\n<AgenteUDP> AgenteUDP terminado!!!");
    }
    
    public void run(){
        System.out.println("<AgenteUDP> Iniciando AgenteUDPListenerThread...");
        agenteUDPlistenerThread.start();
        
//        Scanner input = new Scanner(System.in);
//        String command = "ohayo";
//        while( !command.equalsIgnoreCase("stop") ){
//            command = input.next();
//            System.out.println();
//        }
//        this.stopAgenteUDP();
//        System.exit(0);
    }
}
