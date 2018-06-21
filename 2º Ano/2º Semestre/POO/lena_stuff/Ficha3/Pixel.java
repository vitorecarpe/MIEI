/**
 * Write a description of class Pixel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Pixel {
    private double x;
    private double y;
    private int color;

    // construtores
    public Pixel(double x, double y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    // getters
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public int getC() {
        return this.color;
    }

    // setters
    
    public void setX (double x) {
        this.x = x;
    }
    
    public void setY (double y) {
        this.y = y;
    }
    
    public void setC (int color) {
        this.color=color;
    }
    
    // métodos
    
    public void desloca (double x, double y) {
        setX(this.getX() + x);
        setY(this.getY() + y);
    }
    
    public void mudarCor (int cor) {
        setC(cor);
    }
    
    public String nomeCor() {
        int c = getC();
        if (c==0) return "Preto";
        if (c==1) return "Azul marinho";
        if (c==2) return "Verde escuro";
        if (c==3) return "Azul petróleo";
        if (c==4) return "Castanho";
        if (c==5) return "Púrpura";
        if (c==6) return "Verde oliva";
        if (c==7) return "Cinza claro";
        if (c==8) return "Cinza escuro";
        if (c==9) return "Azul";
        if (c==10) return "Verde";
        if (c==11) return "Azul turquesa";
        if (c==12) return "Vermelho";
        if (c==13) return "Fúcsia";
        if (c==14) return "Amarelo";
        if (c==15) return "Branco";
        return "Cor inválida";
    }
}
