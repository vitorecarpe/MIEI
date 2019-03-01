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
public class Evento implements Serializable{
    private int id;
    private double oddV;
    private double oddE;
    private double oddD;
    private boolean estado;
    private String resultado;
    private Equipa equipaC;
    private Equipa equipaF;
    
    public Evento(){
        this.id = 9999;
        this.oddV = 0.0;
        this.oddE = 0.0;
        this.oddD = 0.0;
        this.estado = false;
        this.resultado = "";
        this.equipaC = new Equipa();
        this.equipaF = new Equipa();
    }
    public Evento(int id, double oddV, double oddE, double oddD, boolean estado, String resultado, Equipa c, Equipa f){
        this.id = id;
        this.oddV = oddV;
        this.oddE = oddE;
        this.oddD = oddD;
        this.estado = estado;
        this.resultado = resultado;
        this.equipaC = c;
        this.equipaF = f;
    }
    public Evento(Evento e){
        this.id = e.getID();
        this.oddV = e.getOddV();
        this.oddE = e.getOddE();
        this.oddD = e.getOddD();
        this.estado = e.getEstado();
        this.resultado = e.getResultado();
        this.equipaC = e.getEquipaC();
        this.equipaF = e.getEquipaF();
    }
    
    public int getID(){
        return this.id;
    }
    public double getOddV(){
        return this.oddV;
    }
    public double getOddE(){
        return this.oddE;
    }
    public double getOddD(){
        return this.oddD;
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
    public Equipa getEquipaF(){
        return this.equipaF;
    }
    public void setID(int id){
        this.id=id;
    }
    public void setOddV(double oddV){
        this.oddV=oddV;
    }
    public void setOddE(double oddE){
        this.oddE=oddE;
    }
    public void setOddD(double oddD){
        this.oddD=oddD;
    }
    public void setEstado(boolean estado){
        this.estado=estado;
    }
    public void setResultado(String resultado){
        this.resultado=resultado;
    }
    public void setEquipaC(Equipa equipaC){
        this.equipaC=equipaC;
    }
    public void setEquipaF(Equipa equipaF){
        this.equipaF=equipaF;
    }
    
    public boolean equals(Evento e) {
        if(e.getID() == this.id) {
            return true;
        }
        return false;
    }
    
}
