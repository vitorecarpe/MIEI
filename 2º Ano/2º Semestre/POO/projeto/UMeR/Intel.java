import java.util.ArrayList;
import java.io.Serializable;
import java.io.*;
import java.lang.*;

public class Intel implements Serializable
{
    private ArrayList<Cliente> clientes;
    private ArrayList<Motorista> motoristas;
    private ArrayList<Carro> carros;
    private ArrayList<Moto> motos;
    private ArrayList<Carrinha> carrinhas;
    private Cliente user;
    private Motorista userM;
    private int data;
    
    /** Getter do array de clientes. */
    public ArrayList<Cliente> getClientes() {
        return this.clientes;
    }
    /** Getter do array dos motoristas. */
    public ArrayList<Motorista> getMotoristas() {
        return this.motoristas;
    }
    /** Getter do arrays de carros. */
    public ArrayList<Carro> getCarros() {
        return this.carros;
    }
    /** Getter do array de motos. */
    public ArrayList<Moto> getMotos(){
        return this.motos;
    }
    /** Getter do array de carrinhas. */
    public ArrayList<Carrinha> getCarrinhas(){
        return this.carrinhas;
    }
    /** Getter do user. */
    public Cliente getUser() {
        return this.user;
    }
    /** Getter do user motorista. */
    public Motorista getUserM() {
        return this.userM;
    }
    /** Getter da data.*/
    public int getData() {
        return this.data;
    }
    /** Setter do array de clientes. */
    public void setClientes(ArrayList<Cliente> c) {
        this.clientes=c;
    }
    /** Setter do array dos motoritas. */
    public void setMotoristas(ArrayList<Motorista> m) {
        this.motoristas=m;
    }
    /** Setter do array dos carros. */
    public void setCarros(ArrayList<Carro> c) {
        this.carros=c;
    }
    /** Setter do array das motos. */
    public void setMotos(ArrayList<Moto> m) {
        this.motos=m;
    }
    /** Setter do array das carrinahs. */
    public void setCarrinhas(ArrayList<Carrinha> ca){
        this.carrinhas=ca;
    }
    /** Setter do user. */
    public void setUser(Cliente c) {
        this.user=c;
    }
    /** Setter do user motorista. */
    public void setUserM(Motorista m) {
        this.userM=m;
    }
    /** Setter da data. */
    public void setData(int d) {
        this.data=d;
    }
    
    
    
    /**
     * Método que guarda em ficheiro de objectos o objecto que recebe a mensagem
     * @param nome do ficheiro onde será guardado um objecto do tipo Intel
     */
    public void save(String nomeFicheiro) throws IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    /**
     * Método que recupera uma instância de Intel de um ficheiro de objectos
     * @param nome do ficheiro onde está guardado um objecto do tipo Intel
     * @return objecto Intel inicializado
     */
    public Intel load(String nomeFicheiro) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Intel intel = (Intel) ois.readObject();
        ois.close();
        return intel;
    }
    /**
     * Método que guarda o estado de uma instância num ficheiro de texto
     * @param nome do ficheiro
     */
    public void saveTxt(String nomeFicheiro) throws IOException {
       PrintWriter txt = new PrintWriter(nomeFicheiro);
       txt.println("------- Info --------");
       txt.println(this.toString());
       txt.flush();
       txt.close();
    }
    
    
    
    /**Criaçao do clientes, motoristas, carros,carrinhas e todas as informacoes necessarias. */
    public Intel() {
        clientes = new ArrayList<Cliente>();
        motoristas = new ArrayList<Motorista>();
        carros = new ArrayList<Carro>();
        motos = new ArrayList<Moto>();
        carrinhas = new ArrayList<Carrinha>();
        data = 20170603;
    }
    /** Construtor que iguala as variaveis aos seus getters. */
    public Intel(Intel i) {
        this.clientes = i.getClientes();
        this.motoristas = i.getMotoristas();
        this.carros = i.getCarros();
        this.motos = i.getMotos();
        this.carrinhas = i.getCarrinhas();
        this.data = i.getData();
    }
    /** Construtor parameterizado que recebe as variaveis. */
    public Intel(ArrayList<Cliente> cli, ArrayList<Motorista> mo, ArrayList<Carro> car, ArrayList<Moto> mot, ArrayList<Carrinha> carr, int data) {
        this.clientes = cli;
        this.motoristas = mo;
        this.carros = car;
        this.motos = mot;
        this.carrinhas = carr;
        this.data = data;
    }
}
