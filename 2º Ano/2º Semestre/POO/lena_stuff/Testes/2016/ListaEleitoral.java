import java.util.*;
/**
 * Write a description of class ListaEleitoral here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ListaEleitoral {
    private String partido;
    private Set<Candidato> eleitos;
    private List<Candidato> porEleger;

    public ListaEleitoral(String partido, Collection <Candidato> candidatos) {
        this.partido = partido;
        this.eleitos = new HashSet<Candidato>();
        this.porEleger = new ArrayList<Candidato>();
        candidatos.forEach(c -> this.porEleger.add(c.clone()));
    }

    public Candidato aEleger() throws SemCandidatosException {
        if (porEleger.isEmpty())
            throw new SemCandidatosException("Não há candidatos a eleger!");
       return porEleger.get(0);
    }
    
    public void elege() throws SemCandidatosException {
        if (porEleger.isEmpty())
            throw new SemCandidatosException("Não há candidatos a eleger!");
        Candidato c = porEleger.remove(0);
        eleitos.add(c);
    }
    
    public void elege(int n) throws SemCandidatosException {
        if (porEleger.size() >= n) {
               while (n > 0) {
                   this.elege();
                   n--;
                }
        } else throw new SemCandidatosException("Não há candidatos suficientes!");
    }
    
    public Collection<Candidato> candidatos() {
        Collection<Candidato> ret = new TreeSet<Candidato>();
        eleitos.forEach(c -> ret.add(c.clone()));
        porEleger.forEach(c -> ret.add(c.clone()));
        return ret;
    }
}
