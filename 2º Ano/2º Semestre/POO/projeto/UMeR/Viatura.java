import java.io.Serializable;

public abstract class Viatura implements Serializable{
    /** Variaveis de instancia **/
    private String matricula;    //identificador do taxi
    private int velocidade;      //velocidade media por km
    private double preco;        //preço base por km
    private double fiabilidade;  //capacidade da viatura cumprir tempo estabelecido. Tem influencia nos tempos fornecidos ao cliente
    private Motorista motorista; // motorista 
    private Par coords;          // tipo coordenadas (x,y)
    private Cliente cliente;     // cliente
    private Par destino;         // coordenadas do destino
    private double faturacao;    // total faturado pela viatura
    
    /** Construtor padrao que inicializa todas as variaveis. */
    public Viatura(){
        this.matricula="00-AA-00";
        this.velocidade=0;
        this.preco=1;
        this.fiabilidade=1;
        this.motorista=new Motorista();
        this.coords=new Par();
        this.cliente=new Cliente();
        this.destino=new Par();
        this.faturacao=0;
    }
    /** Construtor parameterizado iguala as matriculas aos seus gets.*/
    public Viatura(Viatura v){
        this.matricula = v.getMatricula();
        this.velocidade = v.getVelocidade();
        this.preco = v.getPreco();
        this.fiabilidade = v.getFiabilidade();
        this.motorista = v.getMotorista();
        this.coords = v.getCoords();
        this.cliente = v.getCliente();
        this.destino = v.getDestino();
        this.faturacao = v.getFaturacao();
    }
    /**Contrutor parameterizado que recebe as variaveis. */
    public Viatura(String matricula, int velocidade, double preco, double fiabilidade, 
                    Motorista motorista, double x1, double y1, 
                    Cliente cliente, double x2, double y2, double faturacao){
        this.matricula=matricula;
        this.velocidade=velocidade;
        this.preco=preco;
        this.fiabilidade=fiabilidade;
        this.motorista=motorista;
        this.coords=new Par(x1,y1);
        this.cliente=cliente;
        this.destino=new Par(x2,y2);
        this.faturacao=faturacao;
    }
    /** Getter da matricula. */
    public String getMatricula(){
        return this.matricula;
    }
    /** Setter da matricula. */
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }
    /** Getter da velocidade. */
    public int getVelocidade() {
        return this.velocidade;
    }
    /** Setter da velocidade. */
    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }
    /** Getter do preco. */
    public double getPreco() {
        return this.preco;
    }
    /** Setter do preco. */
    public void setPreco(double preco) {
        this.preco = preco;
    }
    /** Getter da fiabilidade. */
    public double getFiabilidade(){
        return this.fiabilidade;
    }
    /** Setter da Fiabilidade. */
    public void setFiabilidade(double fiabilidade){
        this.fiabilidade = fiabilidade;
    }
    /** Getter do motorista. */
    public Motorista getMotorista(){
        return this.motorista;
    }
    /** Setter do motorista. */
    public void setMotorista(Motorista motorista){
        this.motorista=motorista;
    }
    /** Getter das coordenadas. */
    public Par getCoords(){
        return this.coords;
    }
    /** Setter das coordenadas. */
    public void setCoords(Par coords){
        this.coords=coords;
    }
    /** Getter do cliente. */
    public Cliente getCliente(){
        return this.cliente;
    }
    /** Setter do cliente */
    public void setCliente(Cliente cliente){
        this.cliente=cliente;
    }
    /** Setter das coordenadas do destino. */
    public Par getDestino(){
        return this.destino;
    }
    /** Getter das coordenadas do destino. */
    public void setDestino(Par destino){
        this.destino=destino;
    }
    /** Getter da faturaçao. */
    public double getFaturacao(){
        return this.faturacao;
    }
    /** Setter da faturaçao */
    public void setFaturacao(double faturacao){
        this.faturacao=faturacao;
    }
    
    /** Funçao clone, nao se usa pois a classe e abstrata.*/
    public abstract Viatura clone();
    /** Funcao toString da classe.*/
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matricula: "+this.matricula+"\n");
        sb.append("Velocidade: "+this.velocidade+"\n");
        sb.append("Preço: "+this.preco+"\n");
        sb.append("Fiabilidade: "+this.fiabilidade+"\n");
        sb.append("Motorista: "+this.motorista+"\n");
        sb.append("Faturaçao: "+this.faturacao+"\n");
        return sb.toString();
    }
    /** Equals que verifica se as matriculas sao iguais */
    public boolean equals(Viatura v) {
        if(v.getMatricula().equals(this.matricula)) {
            return true;
        }
        return false;
    }
    /** Funçao que aumenta a fiabilidade*/
    public void aumentaFiabilidade (){
        if(this.fiabilidade>=0 && this.fiabilidade<15) this.fiabilidade=15;
        else if(this.fiabilidade>=15 && this.fiabilidade<50) this.fiabilidade=this.fiabilidade*1.15;
        else if(this.fiabilidade>=50 && this.fiabilidade<80) this.fiabilidade=this.fiabilidade*1.2;
        else if(this.fiabilidade>=80 && this.fiabilidade<90) this.fiabilidade=this.fiabilidade*1.1;
        if(this.fiabilidade==99) this.fiabilidade=100;   
    }
    /** Funçao que diminui a fiabilidade*/
    public void diminuiFiabilidade (){
        if(this.fiabilidade<20) this.fiabilidade=25;
        else if(this.fiabilidade>=20 && this.fiabilidade<50) this.fiabilidade=this.fiabilidade*0.9;
        else if(this.fiabilidade>=50 && this.fiabilidade<80) this.fiabilidade=this.fiabilidade*0.8;
        else if(this.fiabilidade>=80 && this.fiabilidade<=100) this.fiabilidade=this.fiabilidade*0.9;
    }
    
}
