import java.io.*;
/**
 * Classe base Hotel
 * @author Rui Couto
 * @version 1.0
 *
 * @author anr
 * @version 2.0
 *
 * @author jfc
 * @version 2.1
 */
public abstract class Hotel implements Comparable<Hotel>, Serializable {
    /** O código do hotel */
    private String codigo;
    /** O nome do hotel */
    private String nome;
    /** Localidade do hotel */
    private String localidade;
    /** Preço base por quarto */
    private double precoBaseQuarto;
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
        this.precoBaseQuarto = 0;
        this.numeroQuartos = 0;
        this.estrelas = 1;
    }

    /**
     * Construtor por cópia.
     * @param c 
     */
    public Hotel(Hotel c) {
        this.codigo = c.getCodigo();
        this.nome = c.getNome();
        this.localidade = c.getLocalidade();
        this.precoBaseQuarto = c.getPrecoBaseQuarto();
        this.numeroQuartos = c.getNumeroQuartos();
        this.estrelas = c.getEstrelas();
    }

    /**
     * Construtor por parametro
     * @param codigo
     * @param nome
     * @param localidade
     * @param precoBaseQuarto 
     * @param estrelas
     */
    public Hotel(String codigo, String nome, String localidade, double precoBaseQuarto, int numQuartos, int estrelas) {
        this.codigo = codigo;
        this.nome = nome;
        this.localidade = localidade;
        this.precoBaseQuarto = precoBaseQuarto;
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
    public double getPrecoBaseQuarto() {
        return precoBaseQuarto;
    }

    /**
     * Definir o preço base do quarto
     * @param precoBaseQuarto 
     */
    public void setPrecoBaseQuarto(double precoBaseQuarto) {
        this.precoBaseQuarto = precoBaseQuarto;
    }

    /**
     * Obter o número de quartos do hotel
     */
    public int getNumeroQuartos() { 
        return this.numeroQuartos; 
    }

    /**
     * Obter o número de estrelas do hotel
     */
    public int getEstrelas() { 
        return this.numeroQuartos; 
    }

    
    /**
     * Método que determina o valor de uma noite de um quarto.
     * Este método é abstracto na classe Hotel e implementado em 
     * cada uma das subclasses, de acordo com as regras definidas no 
     * enunciado.
     * 
     */
    
    public abstract double precoNoite();
    
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
                o.getLocalidade().equals(this.localidade) && o.getPrecoBaseQuarto() == this.precoBaseQuarto 
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
        sb.append("Preço por quarto: ").append(this.precoBaseQuarto).append("€");
        sb.append("Numero de Quartos: ").append(this.numeroQuartos);
        sb.append("Estrelas: ").append(this.estrelas).append("**");
        return sb.toString();
    }


    /**
    * Definição de  clone.
    * Tem de ser um método abstracto, porque só as subclasse é que o podem 
    * implementar.
    */
    public abstract Hotel clone();

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
}