/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Apostador implements Serializable, User{
    private final String email;
    private String password;
    private String nome;
    private double esscoins;
    private List<String> notifications;
    private List<Aposta> apostas;
    
    public Apostador(){
        this.email = "";
        this.password = "";
        this.nome = "";
        this.esscoins = 0.0;
        this.notifications = new ArrayList<>();
        this.apostas = new ArrayList<>();
    }
    
    public Apostador(String email, String password, String nome, double esscoins){
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.esscoins = esscoins;
        this.notifications = new ArrayList<>();
        this.apostas = new ArrayList<>();
    }
    
    public Apostador(Apostador a){
        this.email = a.getEmail();
        this.password = a.password;
        this.nome = a.getNome();
        this.esscoins = a.getESSCoins();
        this.notifications = a.getNotif();
        this.apostas = a.getApostas();
    }
   
    public String getEmail(){
        return this.email;
    }
    public String getNome(){
        return this.nome;
    }
    public double getESSCoins(){
        return this.esscoins;
    }
    public List<String> getNotif(){
        return this.notifications;
    }
    public List<Aposta> getApostas(){
        return this.apostas;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setNome(String nome){
        this.nome=nome;
    }
        
    public boolean hasNotif(){
        return !notifications.isEmpty();
    }
    public void clearNotifs(){
        this.notifications = new ArrayList<>();
    }
    public void adicionarESSCoins(double coins){
        this.esscoins+=coins;
    }
    public void levantarESSCoins(double coins){
        this.esscoins-=coins;
    }
    public void registarAposta(Aposta a){
        this.apostas.add(a);
    }
    public void cancelaAposta(Aposta a){
        this.apostas.remove(a);
    }
    public boolean verifyPassword(String pass){
        return this.password.equals(pass);
    }
    @Override
    public double update(Evento e, double d) {
        d = 0.0d;
        
        Aposta a = new Aposta();
        
        for(Aposta ap : this.apostas){
            if(ap.getEvento().equals(e) && ap.getApostador().equals(this))
                a=ap;
        }
            
        EventoFutebol ef = (EventoFutebol) e;
        if(a.getResultado() == ef.getResultado()){
            this.esscoins += a.earnings();
        }
        d += a.getValor();

        StringBuilder sb = new StringBuilder();
        sb.append("");
        switch(ef.getResultado()){
                case 1:
                    sb.append(ef.getEquipaC().getNome()).append(" ganha contra ").append(ef.getEquipaF().getNome()).append("!\n");
                    break;
                case 2: 
                    sb.append(ef.getEquipaC().getNome()).append(" empata contra ").append(ef.getEquipaF().getNome()).append("!\n");
                    break;
                case 3:
                    sb.append(ef.getEquipaC().getNome()).append(" perde contra ").append(ef.getEquipaF().getNome()).append("!\n");
                    break;
        }
        if(ef.getResultado() == a.getResultado()){
            
            sb.append("Ganhos: ").append(a.earnings()).append(" ESScoins.");
            d -= a.earnings();
        }
        else
            sb.append("Não ganhou desta vez. Melhor sorte para a próxima!");

        this.notifications.add(sb.toString());

        return d;
    }
}
