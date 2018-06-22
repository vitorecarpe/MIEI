package horarios.business;

/**
 *
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class TipoAula {
    private int idTipo;
    private String tipo;
    private int limite;

    
    //INSTANCES
    public TipoAula() {
        this.idTipo = 0;
        this.tipo = "----";
        this.limite = 0;

    }
    public TipoAula(int idTipo, String tipo, int limite) {
        this.idTipo = idTipo;
        this.tipo = tipo;
        this.limite = limite;
    }
    public TipoAula(TipoAula t){
        this.idTipo = t.getID();
        this.tipo = t.getTipo();
        this.limite = t.getLimite();
    }
    
    // SET
    public void setID(int idTipo){
        this.idTipo = idTipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public void setLimite(int limite){
        this.limite = limite;
    }

    // GET
    public int getID(){
        return this.idTipo;
    }
    public String getTipo(){
        return this.tipo;
    }
    public int getLimite(){
        return this.limite;
    }
    
    
    //CLONE & TOSTRING & EQUALS
    public TipoAula clone(){
        return new TipoAula(this);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID Tipo: "+this.idTipo+"\n");
        sb.append("Nome Tipo: "+this.tipo+"\n");
        sb.append("Limite: "+this.limite+"\n");

        return sb.toString();
    }
    
    public boolean equals(TipoAula t){
        return t.getID() == this.idTipo;
    }
}
