package horarios.business;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Troca {
    private int ID;
    private int alunoID1;
    private int alunoID2;
    private int codigoUC;
    private int turnoID1;
    private int turnoID2;
    
    //INSTANCES
    public Troca() {
        this.ID = 0;
        this.alunoID1 = 0;
        this.alunoID2 = 0;
        this.codigoUC = 0;
        this.turnoID1 = 0;
        this.turnoID2 = 0;
    }
    public Troca(int id, int idAlunoRequerente, int idAlunoRequerido, int codigoUC,
                 int numeroTurnoRequerente, int numeroTurnoRequerido) {
        this.ID=id;
        this.alunoID1=idAlunoRequerente;
        this.alunoID2=idAlunoRequerido;
        this.codigoUC=codigoUC;
        this.turnoID1=numeroTurnoRequerente;
        this.turnoID2=numeroTurnoRequerido;
    }
    public Troca(Troca t){
        this.ID = t.getID();
        this.alunoID1 = t.getAID1();
        this.alunoID2 = t.getAID2();
        this.codigoUC = t.getUC();
        this.turnoID1 = t.getTID1();
        this.turnoID2 = t.getTID2();
    }
    
    // SET
    public void setID(int id){
        this.ID = id;
    }
    public void setAID1(int aid1){
        this.alunoID1 = aid1;
    }
    public void setAID2(int aid2){
        this.alunoID2 = aid2;
    }
    public void setUC(int uc){
        this.codigoUC = uc;
    }
    public void setTID1(int tid1){
        this.turnoID1 = tid1;
    }
    public void setTID2(int tid2){
        this.turnoID2 = tid2;
    }

    // GET
    public int getID(){
        return this.ID;
    }
    public int getAID1(){
        return this.alunoID1;
    }
    public int getAID2(){
        return this.alunoID2;
    }
    public int getUC(){
        return this.codigoUC;
    }
    public int getTID1(){
        return this.turnoID1;
    }
    public int getTID2(){
        return this.turnoID2;
    }
    
    //CLONE & TOSTRING & EQUALS
    public Troca clone(){
        return new Troca(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID Troca: "+this.ID+"\n");
        sb.append("Aluno requerente: "+this.alunoID1+"\n");
        sb.append("Aluno requerido: "+this.alunoID2+"\n");
        sb.append("Código UC: "+this.codigoUC+"\n");
        sb.append("Turno do requerente: "+this.turnoID1+"\n");
        sb.append("Turno do requerido: "+this.turnoID2+"\n");
        return sb.toString();
    }
    
    public boolean equals(Troca t){
        return t.getID() == this.ID;
    }      
}