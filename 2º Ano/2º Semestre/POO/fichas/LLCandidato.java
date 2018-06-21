
/**
 * Write a description of class nl here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LLCandidato
{
    private Candidato head;
    private LLCandidato next;
    private int size;
    
    public LLCandidato(){
        this.head = null;
        this.next = null;
        this.size = 0;
    }
    
    public void setHead (Candidado c){
        this.head=c;
    }
    
    public void getHead () {
        return this.head.get();
    }
    
    public int size () {
        return this.size;
    }
    
    public LLCandidato getNext (){
        return this.next;
    }
    
    public void setNext (LLCandidato lc) {
        this.next=lc;
    }
    
    public int size () {
        return this.size;
    }
   
    public void add(Candidato c){
        if (head==null) {
            head = c;
            LLCandidato novo = new LLCandidado();
            next = novo;
        }
        else this.next.add(c);
        size++;
    }
    
    public Candidato get(int i) throw CandidatoException{
        if (i==0){
            if (head == null) throw new CandidatoException("Nao existe");
            return head;
        }
        else return next.get(i-1);
    }
    
    public boolean equals (Object obj){
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        LLCandidato lc = (LLCandidato ) obj;
        if (this.head.equals(lc.getHead()) 
            return equals (this.next.equals(lc.getNext()));
            
    }
}
