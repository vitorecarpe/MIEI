import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatriculaValida
{
    private String matri;
    
    /** Getter da matricula */
    public String getMatri(){
        return this.matri;
    }
    /** Setter da matricula */
    public void setMatri(String matri){
        this.matri = matri.toUpperCase();
    }
    /** Contrutor parameterizado */
    public MatriculaValida(String matri) {
        this.matri = matri.toUpperCase();
    }
    /** Verifica se a matricula e valida ou nao */
    public boolean isValid() {
        if (this.matri.length() > 8) {
            System.out.println("Matriculas nao podem ter mais de 8 carateres");
            return false;
        }
    
        Matcher ma = Pattern.compile("[0-9]{2,2}-[A-Z]{2,2}-[0-9]{2,2}").matcher(this.matri);
        Matcher mb = Pattern.compile("[0-9]{2,2}-[0-9]{2,2}-[A-Z]{2,2}").matcher(this.matri);
        Matcher mc = Pattern.compile("[A-Z]{2,2}-[0-9]{2,2}-[0-9]{2,2}").matcher(this.matri);
        
        if (ma.find() || mb.find() || mc.find()) {
            return true;
        }
        else {
            System.out.println(this.matri + " nao e uma matricula valida!");
            return false;
        }
    }
    
}
