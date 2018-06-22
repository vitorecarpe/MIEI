package horarios.business;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class Turno {
    private int idTurno;
    private String dia;
    private int hora;
    private int capSala;
    private UC uc;
    private TipoAula tipoAula;
    private Docente docente;
    
    
    //INSTANCES
    public Turno() {
        this.idTurno = 0;
        this.uc = new UC() ;
        this.tipoAula = new TipoAula();
        this.docente = new Docente();
        this.dia = "----";
        this.hora = 0;
        this.capSala = 0;
    }
    public Turno(int idTurno, String dia, int hora, UC uc,
                 int capSala, TipoAula tipoAula, Docente docente){
        this.idTurno = idTurno;
        this.uc = uc;
        this.tipoAula = tipoAula;
        this.docente = docente;
        this.dia = dia;
        this.hora = hora;
        this.capSala = capSala;
    }
    public Turno(Turno t){
        this.idTurno = t.getTurno();
        this.uc = t.getUC();
        this.tipoAula = t.getTipoAula();
        this.docente = t.getDocente();
        this.dia = t.getDia();
        this.hora = t.getHora();
        this.capSala = t.getCapacidade();
    }
    
    // SET
    public void setTurno(int idTurno){
        this.idTurno = idTurno;
    }
    public void setUC(UC uc){
        this.uc = uc;
    }
    public void setTipoAula(TipoAula tipoAula){
        this.tipoAula = tipoAula;
    }
    public void setDocente(Docente docente){
        this.docente = docente;
    }
    public void setDia(String dia){
        this.dia = dia;
    }
    public void setHora(int hora){
        this.hora = hora;
    }
    public void setCapacidade(int capSala){
        this.capSala = capSala;
    }

    // GET
    public int getTurno(){
        return this.idTurno;
    }
    public UC getUC(){
        return this.uc;
    }
    public TipoAula getTipoAula(){
        return this.tipoAula;
    }
    public Docente getDocente(){
        return this.docente;
    }
    public String getDia(){
        return this.dia;
    }
    public int getHora(){
        return this.hora;
    }
    public int getCapacidade(){
        return this.capSala;
    }
    
    //CLONE & TOSTRING & EQUALS
    public Turno clone(){
        return new Turno(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Número turno: "+this.idTurno+"\n");
        sb.append("UC: "+this.uc+"\n");
        sb.append("ID Tipo: "+this.tipoAula+"\n");
        sb.append("ID Docente: "+this.docente+"\n");
        sb.append("Dia: "+this.dia+"\n");
        sb.append("Hora: "+this.hora+"\n");
        sb.append("Capacidade da Sala: "+this.capSala+"\n");
        
        return sb.toString();
    }
    
    public boolean equals(Turno t){
        return t.getTurno() == this.idTurno;
    }
    
}