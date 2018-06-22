package horarios.business;

/**
 * 
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Docente extends Utilizador{
    
    //INSTANCES
    public Docente() {
        super();
    }
    public Docente(int id, String nome, String password, String email, String estatuto) {
        super(id,nome,password,email,estatuto);
    }
    public Docente(Docente a){
        super(a);
    }
    
    
    //CLONE & TOSTRING & EQUALS
    @Override
    public Docente clone(){
        return new Docente(this);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        return sb.toString();
    }
    @Override
    public boolean equals(Utilizador u){
        return u.getID() == super.getID();
    }
}