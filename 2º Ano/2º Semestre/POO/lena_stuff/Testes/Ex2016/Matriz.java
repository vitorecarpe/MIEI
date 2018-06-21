import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Matriz implements Serializable {
    private List<List<Object>> linhas;

    public Matriz(int n, int m) {
        int i = 0;
        List<List<Object>> novo = new ArrayList<>(n);
        for (i = 0; i < n; i++) {
            List<Object> objs = new ArrayList<>(m);
            for (int j = 0; j < m; j++) objs.add(null);
            novo.add(objs);
        }
        this.linhas = novo;
    }
    
    public Matriz(int n) {
        int i = 0, m =1;
        List<List<Object>> novo = new ArrayList<>(n);
        for (i = 0; i < n; i++) {
            List<Object> objs = new ArrayList<>(m);
            for (int j = 0; j < m; j++) objs.add(null);
            novo.add(objs);
            m++;
        }
        this.linhas = novo;
    }

    public Object get(int l, int c) throws ArrayOutOfBoundsException {
	if (l < this.linhas.size()) {
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
        int size = 0;
        for (List<Object> e : linhas) {
            size += e.size();
        }
        return size;
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
        boolean tri = false; int m = 1;
        Iterator<List<Object>> it = this.linhas.iterator();
        while(it.hasNext() && !tri) {
            List<Object> objs = it.next();
            tri = (objs.size() == m);
            m++;
        }
        return tri;
    }
}
