
/**
 * Write a description of class complexo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Complexo
{
    //variaveis de instancia
    private double a; 
    private double b;
    
    //construtores
    public Complexo(double a, double b){
        this.a = a;
        this.b = b;
    }
    
    public double getA(){
        return this.a;
    }
    
    public double getB(){
        return this.b;  
    }
    
    public void setA(double a){
        this.a = a;
    }
    
    public void setB(double b){
        this.b = b;
    }
    
    public Complexo conjugado(){
        double a = this.getA();
        double b = this.getB();
        
        return new Complexo(a,-b);
        //return new Complexo(this.getA(),-this.getB());
    }
    
    public Complexo soma (Complexo comp){
        double a = comp.getA();
        double b = comp.getB();
        
        double na = this.a + a;
        double nb = this.b + b;
        
        return new Complexo(na,nb);
        //return new Complexo(this.a + comp.getA(),this.b + comp.getB());
    }
    
    public Complexo produto (Complexo  comp){
        double a = comp.getA();
        double b = comp.getB();
        
        double na = this.a * a - this.b * b;
        double nb = this.b * a + this.a * b;
        
        return new Complexo(na,nb);
    }
    
    public Complexo reciproco(){
        double na = a/(a*a + b*b);
        double nb = b/(a*a + b*b);
        
        return new Complexo(na,-nb);
    }
}
