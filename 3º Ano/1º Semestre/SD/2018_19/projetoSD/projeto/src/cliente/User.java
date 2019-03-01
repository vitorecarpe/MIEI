package cliente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import servidor.Server;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author KIKO
 */
public class User implements Serializable {
    private final ReentrantLock lockUser;
    private final String email;
    private final String password;
    private double debt;
    private final ArrayList<Server> servidoresAlocados;

    /**
     * Instancia User com email e password, mas sem Servidores alocados
     * @param email
     * @param password
     */
    public User(String email, String password){
        this.lockUser = new ReentrantLock();
        this.email = email;
        this.password = password;
        this.servidoresAlocados = new ArrayList<>();
    }

    // LOCK AND UNLOCK METHODS //
    public void lock() {
        this.lockUser.lock();
    }
    public void unlock() {
        this.lockUser.unlock();
    }
    /////////////////////////////

    // GETTERS //////////////////
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public double getDebt() {
        double debt1;
        lock();
        debt1 = this.debt;
        unlock();
        return debt1;
    }
    public ArrayList<Server> getServidoresAlocados() {
        ArrayList<Server> servidoresA;
        lock();
        servidoresA = this.servidoresAlocados;
        unlock();
        return servidoresA;
    }
    // SETTERS //////////////////
    public void setDebt(double debt) {
        lock();
        this.debt = round(debt,2);
        unlock();
    }
    /////////////////////////////

    // Server managment
    public void addServer(Server server){
        lock();
        this.servidoresAlocados.add(server);
        unlock();
    }
    public void removeServer(Server server){
        lock();
        this.servidoresAlocados.remove(server);
        unlock();
    }

    // Info
    public double getPayPerHour(){
        double pay = 0;
        ArrayList<Server> servidoresA = this.getServidoresAlocados();
        for( Server s : servidoresA ){
            if( s.getIsLeilao() )
                pay += s.getLastBid();
            else
                pay += s.getPrice();
        }
        return User.round(pay, 2);
    }
    
    
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public String toStringUser(){
        StringBuilder user = new StringBuilder();
        user.append(">Email: ").append(this.email).append("\n");
        user.append(" Password: ").append(this.password).append("\n");
        user.append(" Debt: ").append(this.debt).append("\n");
        for(Server server : this.servidoresAlocados)
            user.append(server.toStringServer());
        return user.toString();
    }

}
