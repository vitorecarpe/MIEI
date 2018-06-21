/**
 * Write a description of class Complexo here.
 *
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Complexo {
    // variáveis de instância
    private double a;
    private double b;
    
    // construtores
    public Complexo(double a,double b) {
        this.a = a;
        this.b = b;
    }
    
    public double getA() {
        return this.a;
    }
    
    public double getB() {
        return this.b;
    }
    
    public void setA(double a) {
        this.a = a;
    }
    
    public void setB(double b) {
        this.b = b;
    }
    
    // métodos exercício 1
    
    public Complexo conjugado ()  {
        // a+bi -> a-bi
        double a = this.getA();
        double b = this.getB();
        
        return new Complexo(a,-b);
        // return new Complexo(this.getA(),-this.getB());
    }
    
    public Complexo soma (Complexo complexo) {
        double a = complexo.getA();
        double b = complexo.getB();
        
        double na = this.a + a;
        double nb = this.b + b;
        return new Complexo (na,nb);
        // return new Complexo((this.a + complexo.getA()),(this.b + complexo.getB()));
    }
    
    public Complexo produto (Complexo comp) {
        // (a+bi) * (c+di) = (ac-bd) + (bc+ad)i;
        double a = comp.getA(); //c
        double b = comp.getB(); //d
        
        double na = this.a * a - this.b * b;
        double nb = this.b * a + this.a * b;
        
        return new Complexo (na,nb);
    }
    
    public Complexo reciproco() {
        double na = a/(a*a + b*b);
        double nb = b/(a*a + b*b);
        
        return new Complexo(na,-nb);
    }
}
