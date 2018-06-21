
/**
 * Write a description of class Candidato here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Candidato
{
    private String nome;
    
    public Candidato(Candidato c) {
        this.nome = c.getNome();
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public Candidato clone() {
        return new Candidato(this);
    }
}
