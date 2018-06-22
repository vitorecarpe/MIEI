package horarios.business;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Registo {
    private Aluno aluno;
    private UC uc;
    private Turno turno;
    private int faltas;
    private int aulas;
    
    
    //INSTANCES
    public Registo() {
        this.aluno = new Aluno();
        this.uc = new UC();
        this.turno = new Turno();
        this.faltas = 0;
        this.aulas = 0;
    }
    public Registo(Aluno a, UC uc, Turno t, int faltas, int aulas) {
        this.aluno=a;
        this.uc=uc;
        this.turno=t;
        this.faltas=faltas;
        this.aulas=aulas;
    }
    public Registo(Registo f){
        this.aluno = f.getAluno();
        this.uc = f.getUC();
        this.turno = f.getTurno();
        this.faltas= f.getFaltas();
        this.aulas = f.getAulas();
    }
    
    // SET
    public void setAluno(Aluno a){
        this.aluno = a;
    }
    public void setUC(UC uc){
        this.uc = uc;
    }
    public void setTurno(Turno t){
        this.turno = t;
    }
    public void setFaltas(int faltas){
        this.faltas = faltas;
    }
    public void setAulas(int aulas){
        this.aulas = aulas;
    }

    // GET
    public Aluno getAluno(){
        return this.aluno;
    }
    public UC getUC(){
        return this.uc;
    }
    public Turno getTurno(){
        return this.turno;
    }
    public int getFaltas(){
        return this.faltas;
    }
    public int getAulas(){
        return this.aulas;
    }
    
    //CLONE & TOSTRING & EQUALS
    public Registo clone(){
        return new Registo(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Aluno: "+this.aluno.getNome()+"\n");
        sb.append("UC: "+this.uc.getNome()+"\n");
        sb.append("Turno: "+this.turno.getTurno()+"\n");
        sb.append("Faltas: "+this.faltas+"\n");
        sb.append("Aulas: "+this.aulas+"\n");
        return sb.toString();
    }
    
    public boolean equals(Registo r){
        return ((r.getAluno().getID() == this.aluno.getID()) && (r.getTurno().getTurno() == this.turno.getTurno()));
    }    
}
