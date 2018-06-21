import java.util.Arrays;
import java.lang.System;

public class GrowingArray {
    private Veiculo[] elementos;
    private int size;
    private static final int capacidade_inicial=100;
    
    // construtor por omissão
    public GrowingArray() {
        // this.elementos = new Veiculo [this.capacidade_inicial]
        // this.size = 0;
        this(capacidade_inicial);
    }
    
    public GrowingArray (GrowingArray c) {
        
    }
    
    // construtor que aceita um tamanho inicial para o array interno
    public GrowingArray (int capacidade) {
        this.elementos = new Veiculo [capacidade];
        this.size = 0;
    }
    
    public GrowingArray clone() {
    }
    
    // getters
    
    public Veiculo[] getElementos() {
        Veiculo [] copy = new Veiculo[this.size];
       
    }
    
    public int getSize () {
        return this.size;
    }
    
    public int getCi () {
        return this.capacidade_inicial
    }
    
    // setters
    
    public void setElementos(Veiculo[] lista) {
        this.carros = lista;
    }
    
    public void setSize(int s) {
        this.size = s;
    }
    
    public void setCi (int c) {
        this.capacidade_inicial = c;
    }
    
    // método que devolve o Veículo que está na posição indicada
    public Veiculo get (int indice) {
        if (indice < this.size && indice >= 0) return this.elementos[indice];
        else return null;
    }
    
    private void aumentaArray (int tamanho) {
        if (tamanho > 0.8 * this.elementos.length) {
            int novacapacidade = (int) (this.elementos.length * 2);
            this.elementos = Arrays.copyOf(this.elementos,novacapacidade);
        }
    }
    // método que adiciona um Veiculo à colecção
    public void add (Veiculo v) {
        aumentaArray(this.size);
        this.elementos[this.size++] = v;
    }
    
    // método que adiciona um Veiculo na posição indicada
    public void add (int indice, Veiculo v) {
        int i=0;
        if (indice < this.size && indice >=0) {
            aumentaArray(this.size);
            System.arraycopy(this.elementos,indice,this.elementos,indice+1,this.size-indice);
            this.elementos[indice] = v;
        }
    }
    
    
    // método que actualiza o valor de determinada posição do array interno
    public void set (int indice, Veiculo v) {
        if (indice < this. size && indice >=0) this.elementos[indice] = v;
    }
    
    // método que remove do array interno o Veiculo existente na posição indicada
    public Veiculo remove(int indice) {
        Veiculo v = null;
        if (this.size > indice && indice >= 0) {
            v = this.elementos[indice];
            System.arraycopy(this.elementos,indice+1,this.elementos,indice,this.size-indice-1);
            this.size--;
        }
        return v;
    }
    
    // método que remove do array o Veiculo indicado como parâmetro
    public boolean remove(Veiculo v) {
        int i=0;
        boolean r = false;
        for (i=0; i < this.size && r==false; i++) {
            if (this.elementos[i].equals(v)) {
                v = remove(i);
                r = true;
            }
        }
        return r;
    }
    
    // método que determina a primeira posição em que ocorre no array o Veiculo indicado como parâmetro
    public int indexOf(Veiculo v) {
        int i, r = -1;
        for (i=0; i < this.size && r==-1; i++) {
            if (this.elementos[i].equals(v)) r = i;
        }
        return r;
    }
    
    // método que determina se um Veiculo está presente na colecção
    public boolean contains(Veiculo v) {
        boolean b=false;
        if (indexOf(v)!=-1) b=true;
        return b;
    }
    
    // método que determina se a colecção tem elementos
    public boolean isEmpty() {
        return this.size==0;
    }
}
