
/**
 * Write a description of class Palavra here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Palavra implements Entrada
{
    private String termo;
    private String definicao;
    
    public Palavra(String termo,String definicao){
        this.termo = termo;
        this.definicao = definicao;
    }
    
    public Palavra(Palavra p){
        this.termo = p.termo;
        this.definicao = p.definicao;
    }
    
    public String getTermo(){
        return this.termo;
    }
    
    public String getDefinicao(){
        return this.definicao;
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
            
        if(this.getClass() != o.getClass())
            return false;
            
        Palavra p = (Palavra) o;
        if(p.getTermo() == this.getTermo() && p.getDefinicao() == this.getDefinicao())
            return true;
            
        return false;
    }
    
    public Palavra clone(){
        return new Palavra(this);
    }
    
}
