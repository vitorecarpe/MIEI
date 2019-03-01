/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author vitorpeixoto
 */
public class Aposta implements Serializable{
    private int id;
    private int resultado; //resultado em que o apostador apostou (V/E/D)
    private int valor;
    private double odd;
    private Apostador ap;
    private Evento ev;
    
    public Aposta(){
        this.id=9999;
        this.resultado=0;
        this.valor=0;
        this.odd=0.0;
        this.ap = null;
        this.ev = null;
    }
    
    public Aposta(int id, int resultado, int valor, double odd, Apostador ap, Evento ev){
        this.id = id;
        this.resultado = resultado;
        this.valor= valor;
        this.odd = odd;
        this.ap = ap;
        this.ev = ev;
    }
    
    public Aposta(Aposta a){
        this.id = a.getID();
        this.resultado = a.getResultado();
        this.valor = a.getValor();
        this.odd = a.getOdd();
        this.ap = a.getApostador();
        this.ev = a.getEvento();
    }

    public int getID(){
        return this.id;
    }
    public int getResultado(){
        return this.resultado;
    }
    public int getValor(){
        return this.valor;
    }
    public double getOdd(){
        return this.odd;
    }
    public Apostador getApostador(){
        return this.ap;
    }
    public Evento getEvento(){
        return this.ev;
    }
    
    public void setResultado(int resultado){
        this.resultado=resultado;
    }
    public void setValor(int valor){
        this.valor=valor;
    }
    
    public double earnings(){
        return odd * (double) valor;
    }
}
