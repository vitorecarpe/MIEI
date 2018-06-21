
/**
 * Write a description of class Produto here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.lang.String;

public class Produto {
    private String codigo;
    private String nome;
    private int qS;
    private int qM;
    private double pC;
    private double pV;

    // construtores
    
    public Produto(String codigo, String nome, int qS, int qM, double pC, double pV) {
        this.codigo = codigo;
        this.nome = nome;
        this.qS = qS;
        this.qM = qM;
        this.pC = pC;
        this.pV = pV;
    }

    // getters
    
    public String getCodigo() {
        return this.codigo;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public int getqS() {
        return this.qS;
    }
    
    public int getqM() {
        return this.qM;
    }
    
    public double getpC() {
        return this.pC;
    }
    
    public double getpV() {
        return this.pV;
    }
    
    // setters
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setqS(int qS) {
        this.qS = qS;
    }
    
    public void setqM(int qM) {
        this.qM = qM;
    }
    
    public void setpC(double pC) {
        this.pC = pC;
    }
    
    public void setpV(double pV) {
        if (pV<this.getpC()) return;
        this.pV = pV;
    }
    
    // métodos
    
    public void modificaStock (int valor) {
        this.setqS(this.getqS()+valor);
    }
    
    public void alteraCodigo (String codigo) {
        if (codigo.length()<8) return;
        this.setCodigo(codigo);
    }
    
    public void defineMargemLuco (double percentagem) {
        this.setpC(this.getpV()/(1+percentagem));
    }
    
    public void efetuaCompra (double valor) {
        int q = (int) (valor/this.getpV());
        this.setqS(this.getqS() + q);
    }
    
    public double lucroTotal() {
        return this.getqS() * this.getpV();
    }
    
    public double precoTotal (int encomenda) {
        double tot = encomenda * this.getpV();
        return tot;
    }
    
    public boolean abaixoValor () {
        return (this.getqS()<this.getqM());
    }
    
}
