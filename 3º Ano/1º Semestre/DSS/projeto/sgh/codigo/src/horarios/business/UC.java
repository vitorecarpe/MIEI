package horarios.business;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class UC {
	private int codigoUC;
	private String nome;
	private int ano;
        private int semestre;
	private Docente doc;
	private String abreviatura;
        private Curso cur;
        
    //INSTANCES
    public UC(){
        this.codigoUC = 0;
        this.nome = "----";
        this.ano = 0;
        this.semestre = 0;
        this.doc = new Docente();
        this.abreviatura = "----";
        this.cur = new Curso();
    }
    public UC(int codigoUC, String nome, int ano, int semestre,
              Docente doc, String abreviatura, Curso cur){
        this.codigoUC = codigoUC;
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.doc = doc;
        this.abreviatura = abreviatura;
        this.cur = cur;
    }
    public UC(UC a){
        this.codigoUC = a.getUC();
        this.nome = a.getNome();
        this.ano = a.getAno();
        this.semestre = a.getSemestre();
        this.doc = a.getDocente();
        this.abreviatura = a.getAbrev();
        this.cur = a.getCurso();
    }
    
    // SET
    public void setUC(int uc){
        this.codigoUC = uc;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setAno(int ano){
        this.ano = ano;
    }
    public void setSemestre(int sem){
        this.semestre = sem;
    }
    public void setDocente(Docente doc){
        this.doc = doc;
    }
    public void setAbrev(String abrev){
        this.abreviatura = abrev;
    }
    public void setCurso(Curso curso){
        this.cur = curso;
    }

    // GET
    public int getUC(){
        return this.codigoUC;
    }
    public String getNome(){
        return this.nome;
    }
    public int getAno(){
        return this.ano;
    }
    public int getSemestre(){
        return this.semestre;
    }
    public Docente getDocente(){
        return this.doc;
    }
    public String getAbrev(){
        return this.abreviatura;
    }
    public Curso getCurso(){
        return this.cur;
    }
    
    //CLONE & TOSTRING & EQUALS
    @Override
    public UC clone(){
        return new UC(this);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo UC: "+this.codigoUC+"\n");
        sb.append("Nome: "+this.nome+"\n");
        sb.append("Abreviatura: "+this.abreviatura+"\n");
        sb.append("Ano: "+this.ano+"\n");
        sb.append("Semestre: "+this.semestre+"\n");
        sb.append("ID Docente: "+this.doc.getNome()+"\n");
        sb.append("ID Curso: "+this.cur.getNome()+"\n");
        return sb.toString();
    }
    
    public boolean equals(UC a) {
        if(a.getUC() == this.codigoUC) {
            return true;
        }
        return false;
    } 
}