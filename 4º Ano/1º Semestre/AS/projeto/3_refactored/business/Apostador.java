package business;

import data.BetESS;
import java.io.Serializable;
import java.util.ArrayList;

public class Apostador implements Serializable{
    private int id;
    private String email;
    private String password;
    private String nome;
    private double esscoins;
    private ArrayList<Aposta> apostas;
    
    public Apostador(){
        this.id = 9999;
        this.email = "";
        this.password = "";
        this.nome = "";
        this.esscoins = 0.0;
        this.apostas = new ArrayList<>();
    }
    
    public Apostador(int id, String email, String password, String nome, double esscoins, ArrayList<Aposta> apostas){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.esscoins = esscoins;
        this.apostas = apostas;
    }
    
    public Apostador(Apostador a){
        this.id = a.getID();
        this.email = a.getEmail();
        this.password = a.getPassword();
        this.nome = a.getNome();
        this.esscoins = a.getESSCoins();
        this.apostas = a.getApostas();
    }
    
    public int getID(){
        return this.id;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public String getNome(){
        return this.nome;
    }
    public double getESSCoins(){
        return this.esscoins;
    }
    public ArrayList<Aposta> getApostas(){
        return this.apostas;
    }
    public ArrayList<Aposta> getApostasAtivas(){
        ArrayList<Aposta> aps = new ArrayList<>();
        for(Aposta ap : apostas)
            if(ap.getEstadoEvento())
                aps.add(ap);
        return aps;
    }
    
    public boolean efetuarAposta(Aposta aposta){
        if(podeApostar(aposta)){
            apostas.add(aposta);
            levantarCoins(aposta.getValor());
            BetESS ex = new BetESS();
            ex.popupWindow(1, "Aposta registada com sucesso!", "Sucesso");
            return true;
        }
        return false;
    }
    
    public boolean podeApostar(Aposta aposta){
        BetESS ex = new BetESS();
        boolean saldoInsuf = esscoins-aposta.getValor() < 0;
        boolean noSelec = aposta.getPalpite()==0;
        boolean jaApostou  = false;
        for (Aposta ap : apostas)
            if(ap.getEventoID() == aposta.getEventoID())
                jaApostou = true;

        if(jaApostou){
            ex.popupWindow(3, "Já registou uma aposta neste evento.", "Aviso");
            return false;}
        
        else if(saldoInsuf){
            ex.popupWindow(3, "Não tem saldo suficiente para realizar a aposta.", "Aviso");
            return false;}
        
        else if(noSelec){
            ex.popupWindow(3, "Não selecionou nenhum palpite.", "Aviso");
            return false;}
        
        return true;
    }
    
    public void removerAposta(Aposta a){
        if(a.getEstadoEvento()){
            apostas.remove(a);
            this.esscoins+=a.getValor();
        }
    }
    
    public void adicionarCoins(double coins){
        this.esscoins+=coins;
    }
    public void levantarCoins(double coins){
        this.esscoins-=coins;
    }
    
    public int notificarEventos(){
        int i=0;
        for(Aposta a : apostas){
            if(!a.getVisto()){
                i++;
                BetESS ex = new BetESS();
                ex.popupWindow(4, "Resultado final: " + 
                                    a.getEquipaCasaNome()  + " " +
                                    a.getResultadoEvento() + " " +
                                    a.getEquipaForaNome()  +
                                    "\nGanhos: " + a.ganhos(false) + " ESScoins", 
                                    "Evento terminado");
                a.visto();
            }
        }
        return i;
    }
    
    public boolean checkEmail(String em){
        return email.equals(em);
    }
}
