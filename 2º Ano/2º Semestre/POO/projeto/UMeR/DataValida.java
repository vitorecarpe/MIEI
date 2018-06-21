import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DataValida{
    private String dataS;
    /** Getter da dataS.*/
    public String getDataS(){
        return this.dataS;
    }
    /** Setter da dataS . */
    public void setDataS(String dataS){
        this.dataS = dataS;
    }
    /** Construtor parameterizado */
    public DataValida(String dataS) {
        this.dataS = dataS;
    }
    /** Testa se uma determinada data e valida ou nao. */
    public boolean isValid(){
        if (dataS.length() > 10) {
            System.out.println("Datas nao podem ter mais de 10 carateres");
            return false;
        }
        
        Matcher m = Pattern.compile("[1-2]{1,1}[0-9]{3,3}/[0-1]{1,1}[0-9]{1,1}/[0-3]{1,1}[0-9]{1,1}").matcher(dataS);
            
        if (m.find()) {

            return true;
        }
        else {
            System.out.println(dataS + " nao e uma data valida!");
            return false;
        }
    }
    /** Converte uma string para um inteiro */
    public int convertToInt(){
        List<Integer> intList = new ArrayList<Integer>();
        for(String s : this.dataS.split("/")){
            intList.add(Integer.valueOf(s));
        }
        return ( intList.get(0)*10000+intList.get(1)*100+intList.get(2) );
    }
}