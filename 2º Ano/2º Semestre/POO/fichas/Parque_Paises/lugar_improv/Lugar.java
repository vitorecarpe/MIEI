
public class Lugar
{
    /**
     * Variáveis de instancia
     */
    private String matricula;
    private String nome;
    private int minutos;
    private boolean permanente;
    
    
    /**
     * Construtores
     */
    public Lugar()
    {
        this.matricula = "n/a";
        this.nome = "n/a";
        this.minutos = 0;
        this.permanente = false;
    }
    
    public Lugar(String matricula, String nome, int minutos, boolean permanente)
    {
        this.matricula = matricula;
        this.nome = nome;
        this.minutos = minutos;
        this.permanente = permanente;
    }
    
    public Lugar(Lugar lug)
    {
        this.matricula = lug.getMatricula();
        this.nome = lug.getNome();
        this.minutos = lug.getMinutos();
        this.permanente = lug.getPermanente();
    }
    
    /**
     * Métodos gets/sets
     */
    public String getMatricula()
    {
        return this.matricula;
    }
    
    public String getNome()
    {
        return this.nome;
    }
    
    public int getMinutos()
    {
        return this.minutos;
    }
    
    public boolean getPermanente()
    {
        return this.permanente;
    }
    
    public void setMatricula(String mat)
    {
        this.matricula = mat;
    }
    
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public void setMinutos(int min)
    {
        this.minutos = min;
    }
    
    public void setPermanente(boolean perm)
    {
        this.permanente = perm;
    }
    
    /**
     * Equals
     */
    
    public boolean equals(Object o)
    {
        if(this == o)
            return true;
            
        if(o == null || this.getClass() != o.getClass())
            return false;
            
        Lugar lug = (Lugar) o;
        
        return matricula.equals(lug.getMatricula())
                    && nome.equals(lug.getNome())
                    && minutos == lug.getMinutos()
                    && permanente == lug.getPermanente();
    }
    
    /**
     * to String
     */
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb = sb.append("O lugar ");
        sb = sb.append(this.getNome());
        if(this.getMatricula() != "n/a")
        {
            sb = sb.append("Está ocupado pelo veículo ");
            sb = sb.append(this.getMatricula());
            sb = sb.append("Durante ");
            sb = sb.append(this.getMinutos());
        }
        else
            {
                sb = sb.append("Está livre ");
            }
            
        return sb.toString();
    }
    
    /**
     * Clone
     */
    
    public Lugar clone()
    {
        return new Lugar(this);
    }
}
