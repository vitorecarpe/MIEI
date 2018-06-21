import java.util.GregorianCalendar;

/**
 * Write a description of class Email here.
 * 
 * @author Rui Couto
 * @version 1.0 2016
 */
public class Mail
{
    private String remetente;
    private GregorianCalendar envio;
    private GregorianCalendar recepcao;
    private String assunto;
    private String texto;

    public Mail() {
        this.remetente = "";
        this.envio = new GregorianCalendar();
        this.recepcao = new GregorianCalendar();
        this.assunto = "";
        this.texto = "";
    }

    public Mail(String remetente, GregorianCalendar envio, GregorianCalendar recepcao, String assunto, String texto) {
        this.remetente = remetente;
        this.envio = envio;
        this.recepcao = recepcao;
        this.assunto = assunto;
        this.texto = texto;
    }

    public Mail(Mail n) {
       this.remetente = n.getRemetente();
        this.envio = n.getEnvio();
        this.recepcao = n.getRecepcao();
        this.assunto = n.getAssunto();
        this.texto = n.getTexto(); 
    }
    
    
    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public GregorianCalendar getEnvio() {
        return envio;
    }

    public void setEnvio(GregorianCalendar envio) {
        this.envio = envio;
    }

    public GregorianCalendar getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(GregorianCalendar recepcao) {
        this.recepcao = recepcao;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    protected Mail clone() {
        return new Mail(this);
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Mail m = (Mail) obj;
        return m.getRemetente().equals(remetente) && m.getAssunto().equals(assunto) &&
                m.getEnvio().equals(envio) && m.getRecepcao().equals(recepcao) && m.getTexto().equals(texto);
    }

    public String toString() {
        return "From: " + remetente + "\nSubject: " + assunto + "\nOn: " +envio+"/"+recepcao+"\n"+texto;
    }
    
}
