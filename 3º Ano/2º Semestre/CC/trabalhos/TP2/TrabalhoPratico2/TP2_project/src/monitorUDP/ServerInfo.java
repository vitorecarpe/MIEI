package monitorUDP;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

// Informaçao de um Servidor
public class ServerInfo {
    private InetAddress address;
    private long rtt;
    private int numRTT;
    private int numConexoesTCP;
    private int numPacotesTotal, numPacotesPerdidos;
    private long lastSended, lastReceived;
    private double larguraBanda;

    public ServerInfo(InetAddress address){
        this.address = address;
        this.rtt = 0;
        this.numRTT = 0;
        this.numConexoesTCP = 0;
        numPacotesTotal = numPacotesPerdidos = 0;
        lastSended = lastReceived = 0;
        this.larguraBanda = 0;
    }
    
    public InetAddress getAddress(){
        return this.address;
    }
    public long getRTT(){
        return this.rtt;
    }
    public int getNumConexoesTCP(){
        return this.numConexoesTCP;
    }
    public float getDropRate(){
        return 100*(this.numPacotesPerdidos)/(this.numPacotesTotal);
    }
    public float getRating(){
        return 10000*(101-this.getDropRate())/(this.numConexoesTCP+1)/this.rtt;
    }
    
    public void incrementaNumConexoes(){ this.numConexoesTCP++; }
    public void decrementaNumConexoes(){ this.numConexoesTCP--; }
    
    public void setLarguraBanda(double largura){
        this.larguraBanda = largura;
    }
    
    
    public synchronized void probeRequest(){
        if(lastSended != 0){
            numPacotesPerdidos++;
        }
        numPacotesTotal++;
        lastSended = System.nanoTime();
    }

    public synchronized void probeAnswer(){
        if(lastSended == 0){
            System.out.println("<SERVER STATUS> PACOTE INESPERADO recebido");
            return;
        }
        lastReceived = System.nanoTime();
        long tempo = lastReceived - lastSended;
        rtt = (rtt*numRTT + tempo)/(++numRTT);
        lastSended = 0; // "reset"
    }
    
    // Caso esteja inativo mais que 30 segundos devolve true
    public boolean inactive(){
        long nanosAusente = (System.nanoTime() - lastReceived);
        long segundosAusente = TimeUnit.NANOSECONDS.toSeconds(nanosAusente);
        if( segundosAusente>=30 && this.numConexoesTCP==0 ) 
            return true; 
        return false;
    }
    
    @Override
    public String toString(){
        return "--------------------------------------------------\n" + 
               "| Address: " + this.address.toString() + "\n" + 
               "| Tempo da ultima mensagem: " + this.lastReceived + "(inativo à " + TimeUnit.NANOSECONDS.toSeconds((System.nanoTime() - lastReceived)) + " secs)" + "\n" + 
               "| Numero Total de RTT's: " + this.numRTT + "\n" + 
               "| RTT medio: " + this.rtt + "( " + TimeUnit.NANOSECONDS.toMillis(rtt) + "nanosegundos )" + "\n" + 
               "| Numero Total Pacotes: " + this.numPacotesTotal + "( " + this.numPacotesPerdidos + " perdidos)" + "\n" + 
               "| Drop Rate: " + this.getDropRate() + "%" + "\n" +
               "| Numero Conexoes TCP: " + this.numConexoesTCP + "\n" +
               "| Largura de Banda: " + this.larguraBanda + "\n" +
               "| RATING: " + this.getRating() + "\n" +
               "--------------------------------------------------\n";
    }
}
