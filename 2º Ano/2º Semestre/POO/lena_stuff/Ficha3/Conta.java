/**
 * Write a description of class Conta here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Date;
import java.util.GregorianCalendar;

public class Conta {
    // variáveis de instância
    private String nome;
    private String codigo;
    private GregorianCalendar data;
    private double montante;
    private double juro;
    private GregorianCalendar prazo;
    
    public Conta (String nome, String codigo, GregorianCalendar data, double montante, double juro, GregorianCalendar prazo) {
        this.nome = nome;
        this.codigo = codigo;
        this.data = data;
        this.montante = montante;
        this.juro = juro;
        this.prazo = prazo;
    }
    

    public int diasPassados() {
        GregorianCalendar today = new GregorianCalendar();
        
        long ms1 = this.data.getTimeInMillis();
        long ms2 = today.getTimeInMillis();
        
        long dif = ms2-ms1;
        int days = (int) dif/(1000*60*60*24);
        
        return days;
    }
}
