package horarios.business;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Curso {
    private int idCurso;
    private String nome;
    private Docente diretor;
    
    
    //INSTANCES
    public Curso() {
        this.idCurso = 0;
        this.nome = "----";
        this.diretor = new Docente();
    }
    public Curso(int idCurso, String nome, Docente diretor) {
        this.idCurso = idCurso;
        this.nome = nome;
        this.diretor = diretor;
    }
    public Curso(Curso c){
        this.idCurso = c.getID();
        this.nome = c.getNome();
        this.diretor = c.getDiretor();
    }
    
    // SET
    public void setID(int id){
        this.idCurso = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setDiretor(Docente diretor){
        this.diretor = diretor;
    }

    // GET
    public int getID(){
        return this.idCurso;
    }
    public String getNome(){
        return this.nome;
    }
    public Docente getDiretor(){
        return this.diretor;
    }
    
    //CLONE & TOSTRING & EQUALS
    public Curso clone(){
        return new Curso(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID Curso: "+this.idCurso+"\n");
        sb.append("Nome: "+this.nome+"\n");
        sb.append("Diretor de Curso: "+this.diretor.getNome()+"\n");
        return sb.toString();
    }
    
    public boolean equals(Curso c){
        return c.getID() == this.idCurso;
    }     
}