package reverseProxy;
import monitorUDP.MonitorUDP;
import monitorUDP.Tabela;

import java.net.UnknownHostException;
//import java.util.Scanner;
//import java.util.Properties;

// ReverseProxy (Servidor Reverso)
public class ReverseProxy {
    public static void main(String[] args) {
        try {
            ReverseProxy reverseProxy = new ReverseProxy();
            reverseProxy.run();
        } catch (UnknownHostException e) {
            System.out.println("<Reverse Proxy> Host Address indisponivel");
            System.out.println(e);
        }
    }
    
    private int port;
    private Tabela tabela;
    private MonitorUDP monitorUDP;
    private ReverseProxyListener reverseProxyListener;
    
    public ReverseProxy() throws UnknownHostException{
        this.port = 80;
        this.tabela = new Tabela();
        this.monitorUDP = new MonitorUDP(tabela);
        this.reverseProxyListener = new ReverseProxyListener(tabela);
    }
    
    // Interrompe o ReverseProxy
    public void stopReverseProxy(){
        this.monitorUDP.stopMonitorUDP();
        this.reverseProxyListener.stopReverseProxyListener();
        System.out.println("<Reverse Proxy> Host Address indisponivel");
    }
    
    public void run(){
//            Properties props = System.getProperties();
//            props.setProperty("java.net.preferIPv4Stack","true");
//            System.setProperties(props);
        
        System.out.println("<Reverse Proxy> Iniciando MonitorUDP...");
        monitorUDP.run();
        System.out.println("<Reverse Proxy> Iniciando ReverseProxy Listener...");
        reverseProxyListener.start();
        
//        Scanner input = new Scanner(System.in);
//        String command = "ohayo";
//        while( !command.equalsIgnoreCase("stop") ){
//            command = input.next();
//            System.out.println();
//        }
//        this.stopReverseProxy();
//        System.exit(0);
    }
}
