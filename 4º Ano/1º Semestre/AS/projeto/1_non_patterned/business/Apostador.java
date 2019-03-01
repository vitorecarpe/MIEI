/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author vitorpeixoto
 */
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
  
    public void setEmail(String email){
        this.email=email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setNome(String nome){
        this.nome=nome;
    }
    public void setApostas(ArrayList<Aposta> apostas){
        this.apostas=apostas;
    }
    
    public void efetuarAposta(Aposta a){
        apostas.add(a);
        levantarESSCoins(a.getValor());
    }
    
    public void removerAposta(Aposta a){
        if(a.getEvento().getEstado()){
            apostas.remove(a);
            this.esscoins+=a.getValor();
        }
    }
    
    public void adicionarESSCoins(double coins){
        this.esscoins+=coins;
    }
    public void levantarESSCoins(double coins){
        this.esscoins-=coins;
    }
}
