package business;

import java.io.Serializable;
import java.util.ArrayList;

public class Evento implements Serializable{
    private int id;
    private Odds odds;
    private boolean estado;
    private String resultado;
    private Equipa equipaC;
    private Equipa equipaF;
    
    public Evento(){
        this.id = 9999;
        this.odds = new Odds();
        this.estado = false;
        this.resultado = "";
        this.equipaC = new Equipa();
        this.equipaF = new Equipa();
    }
    public Evento(int id, Odds odds, boolean estado, String resultado, Equipa c, Equipa f){
        this.id = id;
        this.odds = odds;
        this.estado = estado;
        this.resultado = resultado;
        this.equipaC = c;
        this.equipaF = f;
    }
    public Evento(Evento e){
        this.id = e.getID();
        this.odds = e.getOdds();
        this.estado = e.getEstado();
        this.resultado = e.getResultado();
        this.equipaC = e.getEquipaC();
        this.equipaF = e.getEquipaF();
    }
    
    public int getID(){
        return this.id;
    }
    public Odds getOdds(){
        return this.odds;
    }
    public double getOddV(){
        return this.odds.getOddV();
    }
    public double getOddE(){
        return this.odds.getOddE();
    }
    public double getOddD(){
        return this.odds.getOddD();
    }
    public boolean getEstado(){
        return this.estado;
    }
    public String getResultado(){
        return this.resultado;
    }
    public Equipa getEquipaC(){
        return this.equipaC;
    }
    public String getEquipaCasaNome(){
        return this.equipaC.getNome();
    }
    public String getEquipaCasaSimbolo(){
        return this.equipaC.getSimbolo();
    }
    public Equipa getEquipaF(){
        return this.equipaF;
    }
    public String getEquipaForaNome(){
        return this.equipaF.getNome();
    }
    public String getEquipaForaSimbolo(){
        return this.equipaF.getSimbolo();
    }
    public void setEstado(boolean estado){
        this.estado=estado;
    }
    public void setResultado(String resultado){
        this.resultado=resultado;
    }
    public boolean equals(Evento e) {
        return e.getID() == this.id;
    }
    
    public Integer getVencedor(){
        if(resultado.equals("")) return 2;
        String[] venc = resultado.split("-");
        if(Integer.parseInt(venc[0])>Integer.parseInt(venc[1])) return 1; //equipa casa venceu
        else if(Integer.parseInt(venc[1])>Integer.parseInt(venc[0])) return 3; //equipa fora venceu
        else return 2; //empate
    }
    
    public void distribuirPremios(ArrayList<Apostador> aps){
        for(Apostador a : aps){
            for(Aposta ap : a.getApostas()){
                if(ap.getEvento().equals(this)){
                    a.adicionarCoins(ap.ganhos(false));
                    ap.notificaApostador();
                }
            }
        }
    }
}