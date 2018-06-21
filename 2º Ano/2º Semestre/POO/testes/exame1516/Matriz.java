import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Matriz implements Serializable {
    private List<List<Object>> linhas;
    private int size=0;

    public Matriz(int n, int m) {/*
        List<List<Object>> novo = new ArrayList<>(n);
        for (List<Object> list : novo) {
            list = new ArrayList<Object>(m);
            for (Object o : list) list.add(null);
        }
        this.linhas = novo;
        
        */int i,j;
        List<List<Object>> novo = new ArrayList<>();
        for(i=0;i<n;i++){
            List<Object> aux = new ArrayList<>();
            for(j=0;j<m;j++){
                aux.add(null);
                size++;
            }
            novo.add(aux);
        }
        this.linhas=novo;
    }
    
    public Matriz(int n) {
        int m = 1;
        List<List<Object>> novo = new ArrayList<>(n);
        for (List<Object> list : novo) {
            list = new ArrayList<Object>(m);
            for (Object o : list) o = null;
            m++;
        }
        this.linhas = novo;
    }

    public Object get(int l, int c) throws ArrayOutOfBoundsException {/*
        
        if(l>linhas.size()||c>linhas.get(l).size()){
            throw new ArrayOutOfBoundsException();
        }
        else return linhas.get(l).get(c);
      
	*/if (l < this.linhas.size()) {
        	List<Object> linha = this.linhas.get(l);
		if (c < linha.size()) {
			Object o = linha.get(c);
			if (o != null) return o;
			else return null;
		}
		else throw new ArrayOutOfBoundsException();
	}
	else throw new ArrayOutOfBoundsException();
    }
    
    public int size() {
        return this.size;
    }
    
    public int count(Object o) {
        int count = 0;
        for (List<Object> e : linhas) {
            for (Object obj : e) {
                if (o.equals(obj)) count++;
            }
        }
        return count;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Matriz mat = (Matriz) o;
        return this.linhas.equals(mat.linhas);
    }
    
    public void save(String f) throws IOException, FileNotFoundException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
        os.writeObject(this);
        os.flush();
        os.close();
    }
    
    public boolean tri() {
        int m = 1;
        boolean f = true;
        for (List<Object> list : this.linhas) {
            if (list.size() != m) f = false;
            m++;
        }
        return f;
    }
}
