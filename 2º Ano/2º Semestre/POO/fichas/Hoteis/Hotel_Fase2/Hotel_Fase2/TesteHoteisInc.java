import java.io.*;
import java.util.Iterator;
/**
 * Classe de teste do problema dos Hotéis (Ficha 7)
 * 
 * @author anr
 * @version 1.0
 */
public class TesteHoteisInc {

    public static void main(String[] args) {
        
        HoteisInc osHoteis = new HoteisInc();
        osHoteis.setNome("Cama e Pequeno Almoço");
        
        
        HotelStandard hs1 = new HotelStandard("h1", "BragaCentro", "Braga", 45.0, false, 100, 4);
        HotelStandard hs2 = new HotelStandard("h2", "BragaUM", "Braga", 25.0, false, 150, 3);
        
        HotelPremium hp1 = new HotelPremium("h3", "Falperra", "Braga", 75.0, 60.0, 120, 4);
        HotelPremium hp2 = new HotelPremium("h4", "RioEste", "Braga", 95.0, 83.0, 90, 5);
        
        HotelDiscount hd1 = new HotelDiscount("h5", "Avenida", "Porto", 50.0, 50, 4, 50.0);
        HotelDiscount hd2 = new HotelDiscount("h6", "Alameda", "Porto", 60.0, 75, 4, 50.0);
    
        // adicionar hotéis
        osHoteis.adiciona(hs1);
        osHoteis.adiciona(hp1);
        osHoteis.adiciona(hd1);
        osHoteis.adiciona(hs2);
        osHoteis.adiciona(hp2);
        osHoteis.adiciona(hd2);
        
        
        //acrescentar um comparador (o ComparadorNome) ao 
        //map de comparadores. Atenção: é uma variável de classe, logo devemos
        //utilizar o método de classe existente para o efeito
        
        HoteisInc.juntaOrdenacao("Por Nome", new ComparadorNome());
        //acrescentar outro comparador
        HoteisInc.juntaOrdenacao("Por Num Quartos", new ComparadorQuartos());
        
        //criar uma ordenação por nome
        
        Iterator<Hotel> it = osHoteis.ordenarHoteis("Por Nome");
        System.out.println("Iterar o resultado da ordenação");
        while (it.hasNext())
          System.out.println(it.next());
        
        
        //Invocar o toString()
        System.out.println("Invocar o toString()");
        System.out.println("---- HOTÉIS ----");
        System.out.println(osHoteis.toString());
        
        //Gravar em ficheiro de texto
        
        try {
           osHoteis.escreveEmFicheiroTxt("estadoHoteisTXT.txt");
        }
        catch (IOException e) {System.out.println("Erro a aceder a ficheiro!");}
        
       
        //Gravar em ficheiro de objectos
        
        try {
           osHoteis.guardaEstado("estadoHoteis.obj");
        }
                catch (FileNotFoundException e) {System.out.println("Ficheiro não encontrado!");}
        catch (IOException e) {System.out.println("Erro a aceder a ficheiro!");}

        
        
        
    }
    


}
