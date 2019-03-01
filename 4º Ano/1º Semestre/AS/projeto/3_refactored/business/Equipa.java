package business;

import java.io.Serializable;

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
}