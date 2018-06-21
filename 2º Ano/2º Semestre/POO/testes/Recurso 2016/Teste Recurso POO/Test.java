import java.util.*;
import java.util.stream.Collectors;

/**
 * Write a description of class Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Test
{
    public static void main(String[] args){
        Dicionario dic = new Dicionario("dic");
        Palavra pal0 = new Palavra("xau","salute");
        Palavra pal1 = new Palavra("ola","salute");
        Palavra pal2 = new Palavra("adeus","xau");
        Palavra pal3 = new Palavra("vem","anda");
        Palavra pal4 = new Palavra("vai","ir");
        
        dic.add(pal0);
        dic.add(pal1);
        dic.add(pal2);
        dic.add(pal3);
        dic.add(pal4);
        
        System.out.println(dic.exists("ola"));
        
        Collection<Entrada> col = dic.getAll();
        
        Map<String,List<String>> sinonimos = dic.getSinonimosExterno();
        
        System.out.println("Definição de xau e ola são iguais: "+dic.sinonimos("xau","ola"));
        
        System.out.println(col.stream().map(Entrada::getTermo).map(Object::toString).collect(Collectors.joining(",","-","-")));
    }
}
