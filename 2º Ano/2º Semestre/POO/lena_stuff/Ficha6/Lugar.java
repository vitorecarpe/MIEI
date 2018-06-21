public class Lugar {
    private String matricula;
    private String nome;
    private int minutos;
    private boolean permanente;
    
    public Lugar() {
        this.matricula = "N/A";
        this.nome = "N/A";
        this.minutos = 0;
        this.permanente = true; 
    }
    
    public Lugar (String matricula, String nome, int minutos, boolean permanente) {
        this.matricula = matricula;
        this.nome = nome;
        this.minutos = minutos;
        this.permanente = permanente;
    }
    
    public Lugar (Lugar l) {
        this.matricula = l.getMatricula();
        this.nome = l.getNome();
        this.minutos = l.getMinutos();
        this.permanente = l.getPermanente();
    }
    
    /* GETTERS */
    
    public String getMatricula () {
        return this.matricula;
    }
    
    public String getNome () {
        return this.nome;
    }
    
    public int getMinutos () {
        return this.minutos;
    }
    
    public boolean getPermanente () {
        return this.permanente;
    }

    /* SETTERS */

    public void setMatricula (String matricula) {
        this.matricula = matricula;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public void setMinutos (int min) {
        this.minutos = min;
    }

    public void setPermanente (boolean permanente) {
        this.permanente = permanente;
    }
    
    
    public Lugar clone() {
        return new Lugar(this);
    }
    
    public String toString() {
        return "Matrícula:"+this.matricula+", Nome:"+this.nome+", min ="+minutos+", permanente = "+ this.permanente+"\n";
    }
}
