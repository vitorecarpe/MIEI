package business;

import persistence.Data;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author vitorpeixoto
 */
public class BetESS implements Serializable{
    Data data;
    
    public BetESS() {
        this.data = new Data();
    }
    public BetESS(HashMap<String,User> u, HashMap<Integer,Evento> ev, HashMap<Integer,Equipa> eq, HashMap<Integer,Aposta> ap) {
        this.data = new Data(u,ev,eq,ap);
    }
    public BetESS(Data d) {
        this.data = d;
    }
    
    public Collection<Evento> getEventos(){
        return data.getEventos().values();
    }
    
    public Collection<EventoFutebol> getEventosFutebol(){
        ArrayList<EventoFutebol> efs = new ArrayList<>();
        for(Evento e : data.getEventos().values()){
            EventoFutebol ef = (EventoFutebol) e;
            efs.add(ef);
        }
        return efs;
    }
    
    public Collection<Equipa> getEquipas(){
        return data.getEquipas().values();
    }
    
    public Map<String, User> getUtilizadores(){
        return data.getUtilizadores();
    }
    
    public Map<Integer, Aposta> getApostas(){
        return data.getApostas();
    }
    
    public ArrayList<Aposta> getApostas(Apostador ap){
        ArrayList<Aposta> apostas = new ArrayList<>();
        for(Aposta a : data.getApostas().values()){
            if(a.getApostador().equals(ap)){
                apostas.add(a);
            }
        }
        return apostas;
    }
    
    public User login(String email, String password){
        User u = this.data.getUtilizadores().get(email);
        if(u != null){
            if(u instanceof business.Admin){
                Admin erro = (Admin) u;
                if(erro != null && !erro.verifyPassword(password)) erro = null;
                return erro;
            }
            else if(u instanceof Bookie){
                Bookie erro = (Bookie) u;
                if(erro != null && !erro.verifyPassword(password)) erro = null;
                return erro;
            }
            else{
                Apostador erro = (Apostador) u;
                if(erro != null && !erro.verifyPassword(password)) erro = null;
                return erro;
            }
        }
        return null;
    }
    
    public int registar(String nome, String email, String password, int coins, boolean aut){
        if(aut){
            for(User a : this.getUtilizadores().values()){
                switch(a.getClass().getSimpleName()){
                    case "Admin":
                        Admin ad = (Admin) a;
                        if(ad.getEmail().equals(email)){
                            return 1;
                        }
                        break;
                    case "Apostador":
                        Apostador ap = (Apostador) a;
                        if(ap.getEmail().equals(email)){
                            return 1;
                        }
                        break;
                    case "Bookie":
                        Bookie b = (Bookie) a;
                        if(b.getEmail().equals(email)){
                            return 1;
                        }
                        break;
                }
                
            }
            Apostador novo = new Apostador(email, password, nome, coins);
            data.newApostador(novo);
            this.save();
        }
        else{
            return 2;
        }
        return 0;
    }
    
    public void criarEvento(String equipaC, String equipaF, Double oddV, Double oddE, Double oddD, User u){
        Equipa casa = new Equipa();
        Equipa fora = new Equipa();
        for(Equipa a : this.data.getEquipas().values()){
            if(a.getNome().equals(equipaC)) casa = a;
            else if(a.getNome().equals(equipaF)) fora = a;
        }
        if(equipaC.equals(equipaF)){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/forbidden.png"));
            JOptionPane.showMessageDialog(null, "As equipas selecionadas são a mesma. Por favor escolha outra.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            int id = this.data.getEventos().size()+1;
            Evento evento = new EventoFutebol(id, oddV , oddE, oddD, true, 0, casa, fora, u); 
            data.addEvento(evento);
            this.save();
            
            StringBuilder sb = new StringBuilder();
            sb.append("Evento criado e disponível.");
            if(u != null)
                sb.append("\nSerá notificado quando o evento terminar.");
            
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/check.png"));
            JOptionPane.showMessageDialog(null, sb.toString(), "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    
    public void fecharEvento(String evento, String resultado){
        String[] equipas = evento.split(" X ");
        ArrayList<Evento> evAtiv = new ArrayList<>();
        this.getEventosFutebol().stream().filter(e -> e.getEstado()).forEach((e) -> evAtiv.add(e));
        for(Evento e : evAtiv){
            if(e.getClass().getSimpleName().equals("EventoFutebol")){
                EventoFutebol ef = (EventoFutebol) e;
                if(ef.getEquipaC().getNome().equals(equipas[0]) && ef.getEquipaF().getNome().equals(equipas[1]))
                    this.data.endEvento(e,ef.vencedor(resultado));
            }
        }
            
        this.save();
    }
    
    public void adicionarEquipa(Equipa e){
        data.addEquipa(e);
    }
    
    public void removerEquipa(Equipa e){
        data.removeEquipa(e);
    }

    public void efetuarAposta(Evento e, Apostador a, int res, int val, double odd){
        
            boolean apostou = false;
            for(Aposta ap : data.getApostas().values()){
                if(ap.getApostador().equals(a) && ap.getEvento().equals(e))
                    apostou=true;
            }

            if(apostou) {
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Já registou uma aposta neste evento.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
            }
            else if(a.getESSCoins()-val >= 0){
                Aposta ap = new Aposta(data.getApostas().size()+1, res, val, odd, a, e);
                data.addAposta(ap);
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/check.png"));
                JOptionPane.showMessageDialog(null, "Aposta registada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
                this.save();
                
            }
            else{
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/forbidden.png"));
                JOptionPane.showMessageDialog(null, "Não tem saldo suficiente para realizar a aposta.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
            }
    }
    
    public void cancelarAposta(Aposta a, String apostadorID){
        data.removeAposta(a);
        Apostador ap = (Apostador) data.getUtilizadores().get(apostadorID);
        ap.cancelaAposta(a);
    }
    
    public void levantarCoins(Apostador a, double quantia){
        if (a.getESSCoins()-quantia < 5){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/forbidden.png"));
            JOptionPane.showMessageDialog(null, "O seu saldo atual não lhe permite levantar essa quantia. Tem de manter um saldo mínimo de 5 ESScoins!", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else{
            Apostador ap = (Apostador) this.getUtilizadores().get(a.getEmail());
            ap.levantarESSCoins(quantia);
            this.save();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/check.png"));
            JOptionPane.showMessageDialog(null, "Quantia depositada na sua conta bancária!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    
    public void save() {
        this.data.save();
    }    
    
    public static BetESS load() throws IOException, ClassNotFoundException {
        Data d = Data.load();
        BetESS b = new BetESS(d);
        return b;
    }
}
