import java.io.Serializable;

public class Viagem implements Serializable {
    private double distancia;
    private double tempo;
    private double preco;
    private int classMotorista;
    private int data;
    private String cliente;
    private String taxi;
    //private Par coordsCliente;
    //private Par coordsTaxi;
    /** getter da distancia. */
    public double getDistancia(){
        return this.distancia;
    }
    /** Getter do tempo. */
    public double getTempo(){
        return this.tempo;
    }
    /** Getter do preco. */
    public double getPreco(){
        return this.preco;
    }
    /** Getter da data. */
    public int getData(){
        return this.data;
    }
    /** Getter do cliente. */
    public String getCliente(){
        return this.cliente;
    }
    /** Getter do taxi. */
    public String getTaxi(){
        return this.taxi;
    }
    /** Getter classsMotorista. */
    public int getClassMotorista(){
        return this.classMotorista;
    }
    /** Setter da distancia. */
    public void setDistancia(double distancia){
        this.distancia=distancia;
    }
    /** Setter do tempo. */
    public void setTempo(double tempo){
        this.tempo=tempo;
    }
    /** Setter do preco. */
    public void setPreco(double preco){
        this.preco=preco;
    }
    /** Setter da data. */
    public void setData(int data){
        this.data=data;
    }
    /** Setter do cliente. */
    public void setCliente(String cliente){
        this.cliente=cliente;
    }
    /** Setter do taxi. */
    public void setTaxi(String taxi){
        this.taxi=taxi;
    }
    /** Setter classMotorista. */
    public void setClassMotorista(int classif){
        this.classMotorista=classif;
    }
    /** Contrutor padrao que inicializa as variaveis. */
    public Viagem(){
        this.distancia=0;
        this.tempo=0.0;
        this.preco=0;
        this.data=00000000;
        this.cliente="n/a";
        this.taxi="n/a";
        this.classMotorista=0;
    }
    /** Construtor que iguala as variaveis aos seus gets. */
    public Viagem(Viagem v){
        this.distancia=v.getDistancia();
        this.tempo=v.getTempo();
        this.preco=v.getPreco();
        this.data=v.getData();
        this.cliente=v.getCliente();
        this.taxi=v.getTaxi();
        this.classMotorista=v.getClassMotorista();
    }
    /** Construtor que recebe as variaveis. */
    public Viagem(double distancia, double tempo, double preco, int data,
                    String cliente, String taxi, int classMotorista){
        this.distancia=distancia;
        this.tempo=tempo;
        this.preco=preco;
        this.data=data;
        this.cliente=cliente;
        this.taxi=taxi;
        this.classMotorista=classMotorista;
    }
    /** clone da viagem. */
    public Viagem clone() {
        return new Viagem(this);
    }
    /** Funcao toString */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nRELATORIO DE VIAGEM\n");
        sb.append("Distancia: " + this.distancia + " km \n");
        sb.append("Tempo: " + this.tempo + " min \n");
        sb.append("Preço: " + this.preco + "€ \n");
        sb.append("Data: " + this.data + "\n");
        sb.append("Cliente: " + this.cliente + "\n");
        sb.append("Taxista: " + this.taxi + "\n");
        sb.append("Classificaçao Taxista: " + this.classMotorista + "\n");
        return sb.toString();
    }
    /** Verifcia se a distancia, preco e cliente sao iguais. */
    public boolean equals(Viagem v) {
        if( (this.distancia == v.getDistancia()) && (this.tempo == v.getTempo()) && 
            (this.preco == v.getPreco()) && (this.data == v.getData()) && 
            (this.cliente == v.getCliente()) && (this.taxi == v.getTaxi()) ) {
            return true;
        }
        return false;
    }
}
