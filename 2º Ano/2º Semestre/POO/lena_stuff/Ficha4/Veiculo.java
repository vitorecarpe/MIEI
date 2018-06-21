/**
 * Write a description of class Veiculo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Veiculo {
    // variáveis de instância
    private String mat;
    private double qT;
    private double qP;
    private double cM;
    private int cap;
    private double cont;

    // constutores 
    public Veiculo (String mat, double qT, double qP, double cM, int cap, double cont) {
        this.mat = mat;
        this.qT = qT;
        this.qP = qP;
        this.cM = cM;
        this.cap = cap;
        this.cont = cont;
    }
    
    // construtor de cópia
    
    public Veiculo (Veiculo carro) {
        this.mat = carro.getMat();
        this.qT = carro.getqT();
        this.qP = carro.getqP();
        this.cM = carro.getcM();
        this.cap = carro.getcap();
        this.cont = carro.getcont();
    }

    // getters
    
    public String toString () {
        StringBuilder s = new StringBuilder();
        s.append ("Veiculo:");
        s.append (mat);
        return s.toString();
    }

    public String getMat() {
        return this.mat;
    }
    
    public double getqT() {
        return this.qT;
    }
    
    public double getcM() {
        return this.cM;
    }
    
    public double getqP() {
        return this.qP;
    }
    
    public int getcap() {
        return this.cap;
    }
    
    public double getcont() {
        return this.cont;
    }
    

    // setters


    public void setqT (double qT) {
        this.qT = qT;
    }
    
    public void setqP (double qP) {
        this.qP = qP;
    }
    
    public void setcM (double cM) {
        this.cM = cM;
    }
    
    public void setcont(double cont) {
        this.cont = cont;
    }
    
    // métodos exercício 3
    
    public void abastecer(int litros) {
        if ((this.getcont()+litros)>=this.getcap()) this.setcont(this.getcap());
        else {
            this.setcont(this.getcont() + litros);
        }
    }
    
    public void resetKms() {
        this.setqP(0);
        this.setcM(0);
    }
    
    public double autonomia () {
        double a = this.getcont()/(this.getcM()/100);
        return a;
    }
    
    public void registarViagem (int kms, double consumo) {
        this.setqT(this.getqT()+kms);
        this.setqP(this.getqP()+kms);
        double a = this.getcont()-consumo;
        this.setcont(a);
        this.setcM(this.getcM() * (this.getqT()/this.getqT() + kms) + (kms/consumo) * (kms/this.getqT() + kms));
    }
    
    public boolean naReserva() {
        return (this.getcont()<10);
    }
    
    public Veiculo clone() {
        return new Veiculo(this);
    }
    
    public boolean equals(Object o) {
        if (o==this) return true;
        if ((o==null) || (o.getClass() != this.getClass())) return false;
        else {
            Veiculo a = (Veiculo) o;
            return (this.mat.equals(a.getMat()) && a.getqT()==this.qT && a.getqP()==this.qP && this.cM==a.getcM() &&
                    this.cap==a.getcap() && this.cont==a.getcont());
        }
    }
    
  
    public double totalCombustivel (double custoLitro) {
        double total = this.getqT() * (this.getcM()/100) * custoLitro;
        return total;
    }
    
    
    public double custoMedioKm (double custoLitro) {
        return custoLitro * (this.getcM()/100);
    }
}
