import java.util.List;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Write a description of class Matriz here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Matriz implements Serializable
{
    private List<List<Object>> linhas;
    
    public Matriz(int n){
        this.linhas = new ArrayList<List<Object>>(n);
        for(int i = 0;i<n;i++){
            List<Object> coluna = new ArrayList<Object>(n+1);
            linhas.add(i,coluna);
        }
    }
    
    public Matriz(int n, int m){
        this.linhas = new ArrayList<List<Object>>(n);
        for(int i = 0;i<n;i++){
            List<Object> coluna = new ArrayList<Object>(m);
            linhas.add(i,coluna);
        }
    }
    
    public List<List<Object>> getLinhas(){
        return this.linhas;
    }
    
    public Object get(int l, int c) throws ArrayIndexOutOfBoundsException{
        try{
            return this.linhas.get(l).get(c);
        }
        catch(ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException(
                "Não existem tantas linhas ou colunas na matriz");
        }
    }
    
    public void add(int l, int c, Object o) throws ArrayIndexOutOfBoundsException{
        try{
            this.linhas.get(l).add(c,o);
        }
        catch(ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("Não existe ("+l+","+c+")\n");
        }
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        for(int l = 0;l<this.linhas.size();l++)
            for(int c = 0;c<this.linhas.get(l).size();c++){
               sb.append("Linha: "+l+
                         " Coluna: "+c+
                         " Valor: "+this.getLinhas().get(l).get(c)+"\n");
            }
            
        return sb.toString();
    }
    
    public int size(){
        int size = 0;
        for(int i = 0;i < this.linhas.size();i++){
            size += this.linhas.get(i).size();
        }
        
        return size;
    }
    
    public int count(Object o){
        int count = 0;
        
        for(int i = 0;i < this.linhas.size();i++){
            for(int j = 0;j < this.linhas.get(i).size();j++){
                if(this.linhas.get(i).get(j) == o) count++;
            }
        }
        
        return count;
    }
    
    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        
        Matriz m = (Matriz) o;
        
        if(this.linhas.size() != m.linhas.size()) return false;
        for(int i = 0;i < this.linhas.size();i++){
            if(this.linhas.get(i).size() != m.linhas.get(i).size()) return false;
            for(int j = 0;j < this.linhas.get(i).size();j++){
                if(this.linhas.get(i).get(j) != m.linhas.get(i).get(j)) return false;
            }
        }
        
        return true;
    }
    
    public void save(String f) throws java.io.IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
        
        os.writeObject(this);
        os.flush();
        os.close();
    }
    
    public boolean tri(){
        for(int i = 0;i < this.linhas.size();i++){
            if(this.linhas.get(i).size() != i+1){
                return false;
            }
        }
        return true;
    }
}
