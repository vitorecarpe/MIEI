
/**
 * Write a description of class Playlist here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */



import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Playlist {
    private List<Faixa> faixas;

    public Playlist() {
        this.faixas = new ArrayList<Faixa>();
    }
    
    public Playlist(List <Faixa> faixas) {
        this.faixas = new ArrayList<Faixa>();
        for (Faixa f:faixas) {
            this.faixas.add(f.clone());
        }
    }

    public Playlist (Playlist p) {
        this (p.getFaixas());
        
    }
    
    public List<Faixa> getFaixas () {
        List<Faixa> newa = new ArrayList<Faixa>();
        for (Faixa f: this.faixas) {
            newa.add(f.clone());
        }
        return newa;
    }
    
    public List<Faixa> getFaixas2() {
       List<Faixa> aux = new ArrayList<Faixa>(this.faixas.size());
       Iterator<Faixa> it = this.faixas.iterator();
       while (it.hasNext()) {
           Faixa f;
           f = it.next();
           aux.add(f.clone());
       }
       return aux;
   }
   
   public void setFaixas(List <Faixa> faixas) {
       this.faixas = new ArrayList<Faixa>(faixas.size());
       for (Faixa f:faixas) {
           this.faixas.add(f.clone());
       }   
   }
   
   public int numFaixas() {
       return this.faixas.size();
   }
   
   public void addFaixa(Faixa f) {
       this.faixas.add(f.clone());
   }
   
   public void removeFaixa (Faixa m) {
       this.faixas.remove(m);
   }
   
   public void adicionar (List<Faixa> faixas) {
       Iterator<Faixa> it = faixas.iterator();
       
       while (it.hasNext()) {
           Faixa f = it.next();
           if (!this.faixas.contains(f)) this.faixas.add(f.clone());
       }
   }
   
   public void adicionarF (List<Faixa> faixas) {
       faixas.forEach(f -> {if (!this.faixas.contains(f)) this.faixas.add(f.clone());});
   }
   
   public int classificacaoSuperior(Faixa f) {
       Iterator<Faixa> it = this.faixas.iterator();
       int c=0;
       
       while (it.hasNext()) {
           Faixa g = it.next();
           if (f.getClassificacao() < g.getClassificacao())  c++;
       }
       
       return c;
   }
   
   public int classificacaoSUperior2(Faixa f) {
       return (int) this.faixas.stream()
                    .filter(t -> t.getClassificacao() > f.getClassificacao())
                    .count();
   }
   
   public boolean duracaoSuperior(double d) {
       Iterator<Faixa> it = this.faixas.iterator();
       boolean r = false;
       while (it.hasNext() && r==false) {
           Faixa f = it.next();
           if (f.getDuracao() > d) r=true;
       }
       return r;
   }
   
   public boolean duracaoSuperior2(double d) {
       return this.faixas.stream()
              .anyMatch(t -> t.getDuracao() > d);
   }
   
   public List<Faixa> getCopiaFaixas(int n) {
       List<Faixa> copy = new ArrayList<Faixa>(this.faixas.size());
       Iterator <Faixa> it = this.faixas.iterator();
       while (it.hasNext()) {
           Faixa f = it.next().clone();
           f.setClassificacao(n);
           copy.add(f);
       }
       return copy;
   }
   
   /*
   public List<Faixa> getCopiaFaixasF(int n) {
       List<Faixa> copy = new ArrayList<Faixa>(this.faixas.size());
       Iterator <Faixa> it = this.faixas.iterator();
       
       forEach(f -> {f.setClassificao(n);})
              .map(Faixa::clone)
              .collect(Collectors.toList());
   }
   */
   
   public double duracaoTotal() {
       Iterator <Faixa> it = this.faixas.iterator();
       double duracao=0;
       while (it.hasNext()) {
           duracao += it.next().getDuracao();
       }
       return duracao;
   }
   
   public double duracaoTotalF() {
       double duracao = 0;
       this.faixas.forEach(f -> {duracao += f.getDuracao();});
       return duracao;
   }
   
   public void removeFaixas(String autor) {
   }
   
   public void removeFaixasF(String autor) {
   }
}
