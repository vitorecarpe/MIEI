
/**
 * Write a description of class Veiculo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Veiculo
{
    private String mat;
    private double kmT;
    private double kmP;
    private double consM;
    private int cap;
    private double depA;
    
    public Veiculo(String mat, double kmT, double kmP, double consM, int cap, int depA){
        this.mat = mat;
        this.kmT = kmT;
        this.kmP = kmP;
        this.cap = cap;
        this.depA = depA;
    }
    
    public Veiculo (Veiculo umVeiculo){
    this.mat = umVeiculo.getMat();
    this.kmT = umVeiculo.getKmT();
    this.kmP = umVeiculo.getKmP();
    this.consM = umVeiculo.getConsM();
    this.cap = umVeiculo.getCap();
    this.depA = umVeiculo.getDepA();
    }
    
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        else {
            Veiculo v = (Veiculo) o;
            return v.getMat().equals(this.getMat());
        }
    }
    
    public Veiculo clone(){
        return new Veiculo(this);
    }
    
    public String getMat(){
        return this.mat;
    }
    
    public double getKmT(){
        return this.kmT;
    }
    
    public double getKmP(){
        return this.kmP;
    }
    
    public double getConsM(){
        return this.consM;
    } 
    
    public int getCap(){
        return this.cap;
    }
    
    public double getDepA(){
        return this.depA;
    }
    
    public void setMat(String mat){
        this.mat = mat;
    }
    
    public void setKmT(double kmT){
        this.kmT = kmT;
    }
    
    public void setKmP(double kmP){
        this.kmP = kmP;
    }
    
    public void setConsM(double consM){
        this.consM = consM;
    } 
    
    public void setDepA(double depA){
        this.depA = depA;
    } 
    
    //métodos
    
    public void abastecer (double litros){
        if (this.getDepA() + litros >= this.getCap()) this.setDepA(this.getCap());
        else this.setDepA(this.getDepA() + litros);
    }
    
    public void resetKms (){
        this.setKmP(0);
        this.setConsM(0);
    }  
    
    public double autonomia (){
        return this.getDepA()/this.getConsM();
    }
    
    public void registarViagem(int kms, double consumo){
        this.setKmT(this.getKmT() + kms);
        this.setKmP(this.getKmP() + kms);
        this.setDepA(this.getDepA() - consumo);
        this.setConsM (consumo/kms);
    }
    
    public boolean naReserva (){
        if (this.getDepA() < 10) return true;
        else return false;
    }
    
    public double totalCombustivel(double custoLitro){
        return custoLitro*this.getDepA();
    }
    
    public double custoMedioKm(double custoLitro){
        return custoLitro*(this.getConsM()/100);
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Veiculo: ");
        s.append(mat);
        return s.toString();
    }
}    