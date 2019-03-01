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
public class Equipa implements Serializable{
    private int id;
    private String nome;
    private boolean estado;
    private String simbolo;
    
    public Equipa(){
        this.id=9999;
        this.nome="";
        this.estado=false;
        this.simbolo="";
    }
    
    public Equipa(int id, String nome, boolean estado, String simbolo){
        this.id=id;
        this.nome=nome;
        this.estado=estado;
        this.simbolo=simbolo;
    }
    
    public Equipa(Equipa e){
        this.id = e.getID();
        this.nome = e.getNome();
        this.estado = e.getEstado();
        this.simbolo = e.getSimbolo();
    }
    
    public int getID(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public boolean getEstado(){
        return this.estado;
    }
    public String getSimbolo(){
        return this.simbolo;
    }
    
    public void setID(int id){
        this.id=id;
    }
    public void setNome(String nome){
        this.nome=nome;
    }
    public void setEstado(boolean estado){
        this.estado=estado;
    }
    public void setSimbolo(String simbolo){
        this.simbolo=simbolo;
    }
}
