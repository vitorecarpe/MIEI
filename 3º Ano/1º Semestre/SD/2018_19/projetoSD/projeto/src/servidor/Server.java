package servidor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author KIKO
 */
public class Server implements Serializable {
    private final ReentrantLock lockServer;
    private final String nome;
    private final String tipo;
    private final double price;
    private int idReserva;
    private double lastBid;
    private boolean isLeilao;
    private String owner;
    private LocalDateTime horaDeInicio;

    public Server() {
        this.lockServer = new ReentrantLock();
        this.nome = "";
        this.tipo = "";
        this.price = 0.0;
        this.lastBid = 0.0;
        this.idReserva = 0;
        this.isLeilao = false;
        this.horaDeInicio = LocalDateTime.now();
        this.owner = null;
    }
    public Server(String nome, String tipo, double price){
        this.lockServer = new ReentrantLock();
        this.nome = nome;
        this.tipo = tipo;
        this.price = price;
        this.idReserva = 0;
        this.lastBid = 0.0;
        this.isLeilao = false;
        this.horaDeInicio = LocalDateTime.now();
        this.owner = null;
    }

    // LOCK AND UNLOCK METHODS //
    public void lock() {
        this.lockServer.lock();
    }
    public void unlock() {
        this.lockServer.unlock();
    }
    /////////////////////////////

    // GETTERS //////////////////
    public String getNome() {
        return this.nome;
    }
    public String getTipo() {
        return this.tipo;
    }
    public double getPrice() {
        return this.price;
    }
    public double getLastBid(){
        double lastBid1;
        lock();
        lastBid1 = this.lastBid;
        unlock();
        return lastBid1;
    }
    public boolean getIsLeilao() {
        boolean leilao;
        lock();
        leilao = this.isLeilao;
        unlock();
        return leilao;
    }
    public int getIdReserva() {
        int reserva;
        lock();
        reserva = this.idReserva;
        unlock();
        return reserva;
    }
    public boolean getUsed() {
        int id;
        boolean ret = false;
        lock();
        id = this.idReserva;
        unlock();
        if(id!=0) ret = true;
        return ret;

    }
    public LocalDateTime getHoraInicio() {
        return this.horaDeInicio;
    }
    public String getOwner(){
        return this.owner;
    }
    // SETTERS //////////////////
    public void setIsLeilao(boolean isLeilao) {
        lock();
        this.isLeilao = isLeilao;
        unlock();
    }
    public void setIdReserva(int idReserva) {
        lock();
        this.idReserva = idReserva;
        unlock();
    }
    public void setLastBid(double valor){
        valor = round(valor,2);
        lock();
        this.lastBid=valor;
        unlock();
    }
    public void setHoraInicio(LocalDateTime horaInicio){
        this.horaDeInicio=horaInicio;
    }
    /////////////////////////////
    
    // SERVER managment /////////
    // Reserva Demand (BUY)
    public void reserva(int id, String email){
        lock();
        this.idReserva = id;
        this.horaDeInicio = LocalDateTime.now();
        this.owner = email;
        unlock();
    }
    // Reserva Leilão (BID)
    public void reservaLeilao(int id, double bid, String email){
        lock();
        this.idReserva = id;
        this.lastBid = round(bid,2);
        this.isLeilao = true;
        this.horaDeInicio = LocalDateTime.now();
        this.owner = email;
        unlock();
    }
    // Libertar Reserva Demand (REM BUY)
    public void freeReserva(){
        lock();
        this.idReserva = 0;
        this.owner = null;
        unlock();
    }
    // Libertar Reserva Leilão (REM BID)
    public void freeReservaLeilao(){
        lock();
        this.idReserva = 0;
        this.lastBid = 0.0;
        this.isLeilao = false;
        this.owner = null;
        unlock();
    }
    /////////////////////////////


    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public String toStringServer(){
        StringBuilder server = new StringBuilder();
        server.append("=>Tipo: ").append(this.tipo).append("\n");
        server.append("  Nome: ").append(this.nome).append("\n");
        server.append("  Preço: ").append(this.price).append("\n");
        server.append("  Reserva: ").append(this.idReserva).append("\n");
        if(this.idReserva>0){
            server.append("  Last Bid: ").append(this.lastBid).append("\n");
            server.append("  Leilão? ").append(this.isLeilao).append("\n");
            server.append("  Hora Inicio: ").append(this.horaDeInicio).append("\n");
        }
        return server.toString();
    }

}
