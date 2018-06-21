
/**
 * Write a description of class TesteParque here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.TreeSet;

public class TesteParque {
    public void main (String args[]) {
        Lugar l1 = new Lugar("45-32-TH", "Lugar 1", 50, true);
        Lugar l2 = new Lugar("32-98-SM", "Lugar 2", 50, false);
        Lugar l3 = new Lugar("74-12-LO", "Lugar 3", 80, true);
        Lugar l4 = new Lugar("10-59-JN", "Lugar 4", 70, false);
        Lugar l5 = new Lugar("65-33-ZX", "Lugar 5", 100, true);

        Parque p1 = new Parque();
        
        p1.setNome("Parque 1");
        p1.novoLugar(l1);
        p1.novoLugar(l2);
        p1.novoLugar(l3);
        p1.novoLugar(l4);
        p1.novoLugar(l5);
        
       TreeSet<Lugar> t = (TreeSet<Lugar>) p1.lugaresOrdenadosPorTempo();
        
        System.out.println("Ordenados por tempo: "+t.toString());
        System.out.println("Menor: " +t.first().toString());
        
        TreeSet<Lugar> s = (TreeSet<Lugar>) p1.lugaresOrdenadosPorNome();
        
        System.out.println("Ordenado por nome: "+s.toString());
        
    }
}
