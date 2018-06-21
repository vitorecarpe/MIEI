/**
 * Classe base Hotel
 * @author Rui Couto
 * @version 1.0
 *
 * @author anr
 * @version 2.0
 */
public class Hotel implements Comparable<Hotel> {
    /** O código do hotel */
    private String codigo;
    /** O nome do hotel */
    private String nome;
    /** Localidade do hotel */
    private String localidade;
    /** Preço base por quarto */
    private double precoQuarto;
    /** Numero de quartos */
    private int numeroQuartos;
    /** Estrelas **/
    private int estrelas;
    
    
    
    /**
    * outras variáveis.
    * 
    */
    
    /**
     * Cria uma instância de hotel
     */
    public Hotel() {
        this.codigo = "n/a";
        this.nome = "n/a";
        this.localidade = "n/a";
        this.precoQuarto = 0;
        this.numeroQuartos = 0;
        //... outras variáveis
        //...
    }

    /**
     * Construtor por cópia.
     * @param c 
     */
    public Hotel(Hotel c) {
        this.codigo = c.getCodigo();
        this.nome = c.getNome();
        this.localidade = c.getLocalidade();
        this.precoQuarto = c.getPrecoQuarto();
        this.numeroQuartos = c.getNumeroQuartos();
        this.estrelas = c.getEstrelas();
    }

    /**
     * Construtor por parametro
     * @param codigo
     * @param nome
     * @param localidade
     * @param precoQuarto 
     * @param estrelas
     */
    public Hotel(String codigo, String nome, String localidade, double precoQuarto, int numQuartos, int estrelas) {
        this.codigo = codigo;
        this.nome = nome;
        this.localidade = localidade;
        this.precoQuarto = precoQuarto;
        this.numeroQuartos = numQuartos;
        this.estrelas = estrelas;
    }
    
    
    /**
     * Obter o código do hotel
     * @return 
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Definir o código do hotel
     * @param codigo 
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obter o nome do hotel
     * @return 
     */
    public String getNome() {
        return nome;
    }

    /**
     * Definir o nome do hotel
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumeroQuartos(int nquartos)
    {
      this.numeroQuartos = nquartos;
    }
    
    /**
     * Obter a localidade do hotel
     * @return 
     */
    public String getLocalidade() {
        return localidade;
    }
    
    /**
     * Definir a localidade do hotel
     * @param localidade 
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Obter o preço base de um quarto
     * @return 
     */
    public double getPrecoQuarto() {
        return precoQuarto;
    }

    /**
     * Definir o preço do quarto
     * @param precoQuarto 
     */
    public void setPrecoQuarto(double precoQuarto) {
        this.precoQuarto = precoQuarto;
    }

    public int getNumeroQuartos()
    { return this.numeroQuartos; }
    /**
     * Devolve uma cópia desta instância
     * @return 
     * public abstract Hotel clone()
     *

    
    /**
     * Compara a igualdade com outro objecto
     * @param obj
     * @return 
     */
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Hotel o = (Hotel) obj;
        return o.getCodigo().equals(this.codigo) && o.getNome().equals(this.nome) && 
                o.getLocalidade().equals(this.localidade) && o.getPrecoQuarto() == this.precoQuarto 
                && o.getEstrelas() == this.estrelas;
    }

    /**
     * Devolve uma representação no formato textual
     * @return 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hotel '").append(nome).append("'\n");
        sb.append("(").append(this.codigo).append(") ").append(this.localidade);
        sb.append("Preço por quarto: ").append(this.precoQuarto).append("€");
        sb.append("Numero de Quartos: ").append(this.numQuartos);
        sb.append("Estrelas: ").append(this.estrelas).append("**");
        return sb.toString();
    }


    /**
    * Implementação de clone.
    *
    */
    public Hotel clone() {
        return new Hotel(this);
    }

    /**
     * Código de hash
     * @return o hashcode. Por simplificação, utiliza-se apenas o hashcode de String (na variável de instância código).
     * É importante alterar este método!!
     */
    public int hashCode() {
        return codigo.hashCode();
    }
    
    
    /**
    * Implementação da ordem natural de comparação de instâncias de Hotel.
    * Por simplificação, apenas se está a comparar os códigos.
    *
    */
    
    public int compareTo(Hotel h) {
        return h.getCodigo().compareTo(codigo);
    }
    
    public double getPrecoBaseQuarto(){
        return this.precoBaseQuarto;
    }
    
    public abstract double precoNoite();
    

}