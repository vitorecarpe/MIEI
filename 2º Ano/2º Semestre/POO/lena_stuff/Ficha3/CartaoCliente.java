/**
 * Write a description of class CartaoCliente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class CartaoCliente {
    private int pontos;
    private int valor;
    private String codigo;
    private String nome;
    private int valorBonus;

    public CartaoCliente(int pontos, int valor, String codigo, String nome, int valorBonus) {
        this.pontos = pontos;
        this.valor = valor;
        this.codigo = codigo;
        this.nome = nome;
        this.valorBonus = valorBonus;
    }
    
    // getters
   
    public int getP () {
        return this.pontos;
    }
    
    public int getV () {
        return this.valor;
    }
    
    public String getC () {
        return this.codigo;
    }
    
    public String getN () {
        return this.nome;
    }
    
    public int getvB () {
        return this.valorBonus;
    }
    
    
    // setters
    
    public void setP (int pontos) {
        this.pontos=pontos;
    }
    
    public void setV (int valor) {
        this.valor=valor;
    }
    
    public void setC (String codigo) {
        this.codigo=codigo;
    }
    
    public void setN (String nome) {
        this.nome=nome;
    }
    
    public void setvB (int valorBonus) {
        this.valorBonus = valorBonus;
    }
    
    // métodos
    
    public void descontar (int menu) {
        if (menu==1) setP(this.getP()-10);
        if (menu==2) setP(this.getP()-20);
    }
    
    public void descarregarPontos (CartaoCliente cartao) {
        int pontos = cartao.getP();
        if ((this.getP()+pontos)>this.getvB()) setP(this.getP()+pontos+10);
        else setP(this.getP()+pontos);
    }
    
    public void efectuarCompra (int valor) {
        setV(this.getV() + valor);
        if (valor < 5) {
            if ((this.getP()+1)>this.getvB()) setP(this.getP()+11);
            else setP(this.getP()+1);
        }
        if (valor >= 5) {
            if ((this.getP()+1)>this.getvB()) setP(this.getP()+12);
            else setP(this.getP()+2);
        }
    }
}
