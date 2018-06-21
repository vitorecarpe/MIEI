import java.io.*;

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
    /** Estrelas */
    private int estrelas;
    
    public String getCodigo() {
        return this.codigo;
    }
}
