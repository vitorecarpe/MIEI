import java.util.Map;
import java.util.List;
import java.io.*;

public class AgenciaViagens implements Serializable {
    private Map<String, List<String>> hoteisNIF; /** código de hotel associado a NIFs dos clientes que ficaram no hotel */
    private Map<String, List<Hotel>> hoteis; /** o prof disse que isto deveria estar organizado por localidade, por alguma razão */
    
    /** 5 b) Gravar em modo texto os hoteis de um tipo passado como parametro */
    public void escreveEmFicheiroTxt(String filename, String type) throws FileNotFoundException {
        PrintWriter fich = new PrintWriter(filename);
        fich.println(" --------------- HOTEIS --------------- ");
        for (List<Hotel> listH : this.hoteis.values()) {
            for (Hotel h : listH) {
                boolean hasClients = !hoteisNIF.get(h.getCodigo()).isEmpty();
                boolean sameType = h.getClass().getSimpleName().equals(type);
                if (hasClients && sameType) {
                    fich.println(h.toString());
                }
            }
        }
        
        fich.flush();
        fich.close();
    }

     public void escreveEmFicheiro(String filename, String type) {
         try {
        ObjectOutputStream fich = new ObjectOutputStream(new FileOutputStream(filename));
        for (List<Hotel> listH : this.hoteis.values()) {
            for (Hotel h : listH) {
                boolean hasClients = !hoteisNIF.get(h.getCodigo()).isEmpty();
                boolean sameType = h.getClass().getSimpleName().equals(type);
                if (hasClients && sameType) {
                    fich.writeObject(h);
                }
            }
        }
        fich.flush();
        fich.close();
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
    }
    
    /** 5 c) Reload de uma AgenciadeViagens / Ler binário */
    public AgenciaViagens readFrom(String filename) {
        AgenciaViagens novaAgencia = new AgenciaViagens();
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
            novaAgencia = (AgenciaViagens) is.readObject();
            is.close();
        }
        catch(IOException | ClassNotFoundException e) {
           System.out.println(e.getMessage());
        }
        return novaAgencia;
    }


}
