package monitorUDP;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

// Tabela de servidores
public class Tabela {
    private HashMap<String,ServerInfo> listaServidores;
    
    public Tabela(){
        listaServidores = new HashMap<>();
    }
    
    public synchronized void addServer(InetAddress address){
        ServerInfo server = listaServidores.get(address.toString());
        
        if(server == null){
            server = new ServerInfo(address);
            listaServidores.put(address.toString(), server);
        }
    }
    public synchronized void removeServer(InetAddress address){
        listaServidores.remove(address.toString());
    }
    
    public synchronized void probeRequest(){
        for(ServerInfo server : listaServidores.values())
            server.probeRequest();
    }
    public synchronized void probeAnswer(InetAddress address){
        ServerInfo server = listaServidores.get(address.toString());
        if(server == null)
            addServer(address);
        else {
            server.probeAnswer();
            listaServidores.put(address.toString(),server);
        }
    }
    
    // Melhor servidor (melhor RTT) 
    public synchronized InetAddress getBestServer(){
        InetAddress melhorAddress = null;
        float bestRating = 0;
        
        for(ServerInfo servidor: this.listaServidores.values()){
            float rating = servidor.getRating() ;
            if( rating > bestRating ){
                bestRating = rating;
                melhorAddress = servidor.getAddress();
            }
        }
        
        return melhorAddress;
    }
    
    public synchronized void startTCP(InetAddress address) {
        ServerInfo server = this.listaServidores.get(address.toString());
        if(server==null) return;
        server.incrementaNumConexoes();
    }
    
    public synchronized void closeTCP(InetAddress address, double larguraBanda) {
        ServerInfo server = this.listaServidores.get(address.toString());
        if(server==null) return;
        if(larguraBanda!=0) server.setLarguraBanda(larguraBanda);
        server.decrementaNumConexoes();
    }
    
    public synchronized void removeInativos(){
        ArrayList<String> remover = new ArrayList<>();
        
        for(ServerInfo s: listaServidores.values())
            if(s.inactive() == true)
                remover.add(s.getAddress().toString());
            
        for(String s: remover)
            listaServidores.remove(s);
    }
    
    public void printEstado(){
        System.out.println();
        System.out.println("##################################################");
        System.out.println("#              Lista de Servidores               #");
        System.out.println("##################################################");
        for(ServerInfo s: listaServidores.values()) 
            System.out.println(s.toString());
        System.out.println("##################################################");
    }
    
}
