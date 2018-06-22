package horarios.business;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
import java.util.Collection;
import java.util.HashSet;

public class Aluno extends Utilizador {
    private int ano;
    private int semestre;
    
    
    //INSTANCES
    public Aluno() {
        super();
        this.ano = 0;
        this.semestre = 0;
    }
    public Aluno(int id, String nome, String password, String email, 
                 String estatuto, int ano, int semestre) {
        super(id,nome,password,email,estatuto);
        this.ano = ano;
        this.semestre = semestre;
    }
    public Aluno(Aluno a) {
        super(a);
        this.ano = a.getAno();
        this.semestre = a.getSemestre();
    }
    
    
    // SET
    public void setAno(int a){
        this.ano = a;
    }
    public void setSemestre(int s){
        this.semestre = s;
    }

    // GET
    public int getAno(){
        return this.ano;
    }
    public int getSemestre(){
        return this.semestre;
    }
    
    
    //CLONE & TOSTRING & EQUALS
    @Override
    public Aluno clone(){
        return new Aluno(this);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Ano: "+this.ano+"\n");
        sb.append("Semestre: "+this.semestre+"\n");
        return sb.toString();
    }
    @Override
    public boolean equals(Utilizador u){
        return u.getID() == super.getID();
    }
    
}