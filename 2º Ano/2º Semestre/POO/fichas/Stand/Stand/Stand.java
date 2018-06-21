
/**
 * Classe que representa um Stand de automóveis
 * 
 * @author anr
 * @version 2014.03.16  
 * @version 2017.03.03
 */

public class Stand {
  //variáveis de instância
  private String nomeStand;
  private Veiculo[] carros;
  private int nveiculos;   //número veículos no stand
  
  //capacidade do stand (em número de veículos)
  private int capacidade;
  
  //capacidade inicial do Stand: valor por omissão
  public static final int capacidade_inicial = 10;
  
  //construtores
  public Stand() {
    this.nomeStand = new String();
    this.carros = new Veiculo[capacidade_inicial];
    this.capacidade = capacidade_inicial;
    this.nveiculos = 0;
  }
  
  public Stand(String nome, int capacidade) {
    this.nomeStand = nome;
    this.carros = new Veiculo[capacidade];
    this.capacidade = capacidade;
    this.nveiculos = 0;
  }
  
  public Stand(Stand umStand) {
    this.nomeStand  = umStand.getNomeStand();
    this.carros     = umStand.getCarros();
    this.capacidade = umStand.getCapacidade();
    this.nveiculos  = umStand.getNVeiculos();
  }
  
  //métodos de instância
  
  //gets e sets
  public String getNomeStand(){
      return this.nomeStand;
  }
  
  public Veiculo [] getCarros(){
      Veiculo [] copy = new Veiculo [this.getCapacidade()];int i = 0;
      for(Veiculo car:this.carros){
          copy[i] = car.clone();
          i++;
      }
      return copy;
  }
  
  public int getCapacidade(){
      return this.capacidade;
  }
  
  public int getNVeiculos(){
      return this.nveiculos;
  }

  public void setNomeStand(String nome){
     this.nomeStand = nome;
  }  
  
  public void setCapacidade(int cap){
     this.capacidade = cap;
  }
  
  public void setNVeiculos(int nv){
     this.nveiculos = nv;
  }
  
  
  
  
  //outros métodos
  
  /**
   * Método que insere um veículo no stand
   * 
   */
   public void insereVeiculo(Veiculo v) {
       if (this.nveiculos == this.capacidade) return;
       this.carros[nveiculos] = v;
       this.nveiculos++;
   }
   
   
   /**
    * Método que verifica se um determinado veículo está no
    * stand.
    */
    public boolean existeVeiculo(Veiculo v) {
        int i;
        for(i = 0;i < this.nveiculos && !this.carros[i].equals(v);i++);
        if (i < this.nveiculos) return true;
        else return false;
    }
    
    
    /**
     * Método que verifica se um veículo, cuja matrícula é conhecida, 
     * está no stand.
     */
    public boolean existeVeiculoPorMatricula(String matricula) {
        int i;
        for(i = 0;i < this.nveiculos && !this.carros[i].getMat().equals(matricula);i++);
        if (i < this.nveiculos) return true;
        else return false;
    }
    
    
    /** 
     * Método que devolve o veículo com mais kms.
     * 
     */
    public Veiculo veiculoComMaisKms() {
        Veiculo v = this.carros[0];
        double kmM = v.getKmT();
        int i;
        for (i = 0;i < this.nveiculos;i++){
           if (this.carros[i].getKmT() > kmM) {v = this.carros[i];kmM = v.getKmT();} 
        }
        return v.clone();
        /*for (Veiculo car: this.carros){
            if (car.getKmT() > kmM) {v = car;kmM = v.getKmT();}
        }
        return v.clone(); -- n se pode fazer isto porque o array possui entradas nulas*/ 
    }
    
    /**
     * Método que devolve o veículo mais gastador (em termos de
     * combustível).
     */
    public Veiculo veiculoMaisGastador() {
        Veiculo v = this.carros[0];
        double consM = v.getConsM();
        int i;
        for (i = 0;i < this.nveiculos;i++){
           if (this.carros[i].getConsM() > consM) {v = this.carros[i];consM = v.getConsM();} 
        }
        return v.clone();
        /*for (Veiculo car: this.carros){
            if (car.getConsM() > consM) {v = car;consM = v.getConsM();}
        }
        return v.clone();*/
    }
    
    /**
     * Método que determina o número de kms de todos os veículos
     * da garagem.
     * 
     */
    public double totalKmsTodosVeiculos() {
        double kms = 0;
        int i;
        for (i = 0;i < this.nveiculos;i++){
            kms += this.carros[i].getKmT();
        }
        return kms;
    }
    
    
    /**
     * equals
     * 
     */
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        else{
            Stand s = (Stand) o;
            boolean flag = true;
            for(Veiculo car : this.carros){
                if (!s.existeVeiculo(car)) flag = false;
            }
            return (flag && this.nomeStand.equals(s.getNomeStand()) && s.getCapacidade() == this.capacidade 
            && s.getNVeiculos() == this.nveiculos);
        }
    }
    
    /**
     * toString
     */

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Stand: ");
        s.append(nomeStand);
        s.append(" nVeiculos: ");
        s.append(this.nveiculos);
        return s.toString();
    }
    
    /**
     * clone
     */
    public Stand clone (){
        return new Stand(this);
    }
    
    
    
    
}
