package servidor;

import cliente.User;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static servidor.ServerComparator.priceComparator;

/**
 *
 * @author KIKO
 */
public class BaseDados {
    private int lastIdReserva;
    private final HashMap<String,User> users;
    private final HashMap<String,ArrayList<Server>> servers;

    public BaseDados(){
        this.lastIdReserva = 0;
        this.users = new HashMap<>();
        this.servers = new HashMap<>();

        // USERS ////////////////////
        // criar user
        User debug = new User("kiko", "Password");
        User kiko = new User("kiko@email.com", "kiko");
        User vitor = new User("vitor@email.com", "vitor");
        User camaz = new User("camaz@email.com", "camaz");
        User raul = new User("raul@email.com", "raul");
        // inserir no hashmap
        this.users.put(debug.getEmail(), debug);
        this.users.put(kiko.getEmail(), kiko);
        this.users.put(vitor.getEmail(), vitor);
        this.users.put(camaz.getEmail(), camaz);
        this.users.put(raul.getEmail(), raul);

        // SERVERS //////////////////
        // criar servidor
        Server batatasS1 = new Server("batatasS1","potato.small",3600.00);
        Server batatasS2 = new Server("batatasS2","potato.small",3600.00);
        Server batatasM1 = new Server("batatasM1","potato.medium",60.00);
        Server nodeS1 = new Server("nodeS1","node.small",1800.00);
        Server nodeS2 = new Server("nodeS2","node.small",1800.00);
        Server nodeS3 = new Server("nodeS3","node.small",1800.00);
        Server nodeB1 = new Server("nodeB1","node.big",7200.00);
        Server nodeB2 = new Server("nodeB2","node.big",7200.00);
        Server nodeB3 = new Server("nodeB3","node.big",7200.00);
        // criar ArrayList para cada tipo de server
        ArrayList<Server> potatosmall = new ArrayList<>();
        ArrayList<Server> potatomedium = new ArrayList<>();
        ArrayList<Server> nodesmall = new ArrayList<>();
        ArrayList<Server> nodebig = new ArrayList<>();
        // adicionar servidor ao ser ArrayList respetivo
        potatosmall.add(batatasS1);
        potatosmall.add(batatasS2);
        potatomedium.add(batatasM1);
        nodesmall.add(nodeS1);
        nodesmall.add(nodeS2);
        nodesmall.add(nodeS3);
        nodebig.add(nodeB1);
        nodebig.add(nodeB2);
        nodebig.add(nodeB3);
        // adicionar server a hash
        this.servers.put("potato.small", potatosmall);
        this.servers.put("potato.medium", potatomedium);
        this.servers.put("node.small", nodesmall);
        this.servers.put("node.big", nodebig);
    }

    // LOCK AND UNLOCK METHODS /////////////////////////////////////////////////
    public void lockAllServers(){
        for (ArrayList<Server> a : this.servers.values()) {
            a.stream().forEach((s) -> {
                s.lock();
            });
        }
    }
    public void unlockAllServers(){
        for (ArrayList<Server> a : this.servers.values()) {
            a.stream().forEach((s) -> {
                s.unlock();
            });
        }
    }
    public void lockAllUsers(){
        this.users.values().stream().forEach((u) -> {
            u.lock();
        });
    }
    public void unlockAllUsers(){
        this.users.values().stream().forEach((u) -> {
            u.unlock();
        });
    }
    ////////////////////////////////////////////////////////////////////////////
    // GETTERS
    public synchronized HashMap<String,User> getAllUsers(){
        lockAllUsers();
        HashMap<String,User> usrs = this.users;
        unlockAllUsers();
        return usrs;
    }
    public synchronized HashMap<String,ArrayList<Server>> getAllServers(){
        lockAllServers();
        HashMap<String, ArrayList<Server>> servs = this.servers;
        unlockAllServers();
        return servs;
    }
    public synchronized User getUser(String email){
        this.users.get(email).lock();
        User u = this.users.get(email);
        this.users.get(email).unlock();
        return u;
    }
    public synchronized ArrayList<Server> getServersByType(String type){
        lockAllServers();
        ArrayList<Server> lista = this.servers.get(type);
        unlockAllServers();
        return lista;
    }
    // SETTERS
    public synchronized void setUser(User user){
        lockAllServers();
        this.users.put(user.getEmail(),user);
        unlockAllServers();
    }
    ////////////////////////////////////////////////////////////////////////////
    // GETTERS para o menu
    public synchronized HashMap<String,ArrayList<Server>> getDemandableServers()
    {
        lockAllServers();

        HashMap<String,ArrayList<Server>> lista = new HashMap<>();
        for(String tipo : this.servers.keySet()){
            ArrayList<Server> aux = new ArrayList<>();
            for(Server server : this.servers.get(tipo)){
                if( !server.getUsed() || (server.getUsed() && server.getIsLeilao()) ){
                    aux.add(server);
                }
            }
            
            if(!aux.isEmpty()){
                lista.put(tipo, aux);
            }
        }
        unlockAllServers();

        return lista;
    }
    public synchronized HashMap<String,ArrayList<Server>> getBidableServers(){
        // LOCK ALL SERVERS
        lockAllServers();

        HashMap<String,ArrayList<Server>> lista = new HashMap<>();
        for(String tipo : this.servers.keySet()){
            ArrayList<Server> aux = new ArrayList<>();
            for(Server server : this.servers.get(tipo)){
                if( !server.getUsed() || (server.getUsed() && server.getIsLeilao()) ){
                    aux.add(server);
                }
            }
            if(!aux.isEmpty()){
                lista.put(tipo, aux);
            }
        }
        // UNLOCK ALL SERVERS
        unlockAllServers();

        return lista;
    }
    ////////////////////////////////////////////////////////////////////////////

    // unica funçao que vai tocar no nextIdReserva por isso synchronized chega
    public synchronized int nextIdReserva() {
        this.lastIdReserva++; // auto incrementaçao
        return lastIdReserva;
    }

    public synchronized void userAddServer(String email, Server server){
        User user = this.getUser(email);
        user.addServer(server);
        this.setUser(user);
    }

    // Requests
    public int demand(String email, Server server){
        int idReserva = this.nextIdReserva();
        server.reserva(idReserva,email);
        this.userAddServer(email, server);
        return idReserva;
    }
    public int bid(String email, Server server, double bid){
        int idReserva = this.nextIdReserva();
        server.reservaLeilao(idReserva,bid,email);
        this.userAddServer(email, server);
        return idReserva;
    }
    public void freeServer(String email, int idReserva){
        Server serverAux=null;
        String tipoAux=null;
        
        lockAllUsers();
        User user = this.users.get(email);
        unlockAllUsers();
        
        user.lock();
        for(Server s : user.getServidoresAlocados()){
            if(s.getIdReserva() == idReserva){
                serverAux=s;
                tipoAux=s.getTipo();
            }
        }
        user.removeServer(serverAux);
        user.unlock();

        this.lockAllServers();
        for(Server server : this.servers.get(tipoAux)){
            if(server.getIdReserva()==idReserva){
                serverAux=server;
            }
        }
        LocalDateTime horaInicio = serverAux.getHoraInicio();
        LocalDateTime horaSaida = LocalDateTime.now();
        double days = (double)ChronoUnit.DAYS.between(horaInicio, horaSaida);
        double hours = (double)ChronoUnit.HOURS.between(horaInicio, horaSaida);
        double minutes = (double)ChronoUnit.MINUTES.between(horaInicio, horaSaida);
        double seconds = (double)ChronoUnit.SECONDS.between(horaInicio, horaSaida);
        double price = days*24*serverAux.getPrice() + seconds*serverAux.getPrice()/3600;
        System.out.println("[BD] DEBUG> DIAS:"+days+" h:"+hours+
                            " m:"+minutes+" s:"+seconds);
        System.out.println("[BD] DEBUG> preço:"+price);
        user.lock();
        user.setDebt(user.getDebt() + price);
        user.unlock();
        if( !serverAux.getIsLeilao() )
            serverAux.freeReserva();
        else
            serverAux.freeReservaLeilao();
        this.unlockAllServers();
    }

    public List<Server> getDemandableServersByType(String type){
        lockAllServers();
        
        ArrayList<Server> serversD = this.servers.get(type);
        List<Server> ret = serversD.stream().filter(s -> ( !s.getUsed() || s.getUsed() && s.getIsLeilao() ))
                            .collect(Collectors.toList());
        unlockAllServers();
        return ret;
    }

    public List<Server> getBidableServersByTypeFree(String type)
    {
        lockAllServers();
        ArrayList<Server> serversB = this.servers.get(type);
        List<Server> ret = serversB.stream().filter(s -> ( !s.getUsed()))
                            .collect(Collectors.toList());
        
        if(ret.isEmpty())
        {
            ret = serversB.stream().filter(s -> ( !s.getUsed() || s.getUsed() && s.getIsLeilao() ))
                            .sorted(priceComparator)
                            .collect(Collectors.toList());
        }
        
        unlockAllServers();
        return ret;
    }
    
    /**
     * Retorna a lista de Users
     * @return String
     */
    public String toStringUsers(){
        StringBuilder users = new StringBuilder();
        users.append("#----------  Users  ----------#\n");
        for(User user : this.users.values())
            users.append(user.toStringUser());
        users.append("#----------  -----  ----------#");
        return users.toString();
    }

    /**
     * Retorna a lista de Servidores
     * @return String
     */
    public String toStringServidores(){
        StringBuilder servers = new StringBuilder();
        servers.append("#---------- Servers ----------#\n");
        for(String key : this.servers.keySet()){
            for(Server server : this.servers.get(key))
                servers.append(server.toStringServer());
        }
        servers.append("#---------- ------- ----------#");
        return servers.toString();
    }

}
