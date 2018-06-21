
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
  
  //gets e sets: fazer!
  
  public String getNomeStand() {
      return this.nomeStand;
  }
  
  public Veiculo[] getCarros() {
      Veiculo[] copy = new Veiculo[nveiculos];
      for (int i=0; i<this.nveiculos;i++) {
          copy[i] = this.carros[i].clone();
      }
      return copy;
  }
  
  public int getNVeiculos() {
      return this.nveiculos;
  }
  
  public int getCapacidade() {
      return this.capacidade;
  }
  
  public int getCi() {
      return this.capacidade_inicial;
  }
  
  public void setNomeStand(String nomeStand) {
      this.nomeStand = nomeStand;
  }
  
  public void setCarros (Veiculo[] carros) {
      for (int i=0; i<carros.length;i++) {
          this.carros[i]=carros[i].clone();
      }
  }
  
  public void setNVeiculos (int nveiculos) {
      this.nveiculos = nveiculos;
  }
  
  public void setCapacidade(int capacidade) {
      this.capacidade = capacidade;
  }
  
  //outros métodos
  
  /**
   * Método que insere um veículo no stand
   * 
   */
   public void insereVeiculo(Veiculo v) {
       this.carros[nveiculos] = v.clone();
       this.nveiculos++;
   }
   
   
   
   /**
    * Método que verifica se um determinado veículo está no
    * stand.
    */
    public boolean existeVeiculo(Veiculo v) {
        boolean c=false;
        for (int i=0; i<this.nveiculos && c==false; i++) {
            if (v.equals(this.carros[i])) c=true;
        }
        return c;
    }
    
    
    /**
     * Método que verifica se um veículo, cuja matrícula é conhecida, 
     * está no stand.
     */
    public boolean existeVeiculoPorMatricula(String matricula) {
        boolean c=false;
        for (int i=0; i<this.nveiculos && c==false;i++) {
            if (this.carros[i].getMat().equals(matricula)) c=true;
        }
        return c;
    }
    
    
    /** 
     * Método que devolve o veículo com mais kms.
     * 
     */
    public Veiculo veiculoComMaisKms() {
        Veiculo max = new Veiculo ("",0,0,0,0,0);
        
        for (int i=0; i<this.nveiculos; i++) {
            if (this.carros[i].getqT() > max.getqT()) max = this.carros[i].clone();
        }
        
        return max;
    }
    
    /**
     * Método que devolve o veículo mais gastador (em termos de
     * combustível).
     */
    public Veiculo veiculoMaisGastador() {
        Veiculo max = new Veiculo ("",0,0,0,0,0);
        
        for (int i=0; i<this.nveiculos; i++) {
            if (this.carros[i].getcM() > max.getcM()) max = this.carros[i].clone();
        }
        
        return max;
    }
    
    /**
     * Método que determina o número de kms de todos os veículos
     * da garagem.
     * 
     */
    public double totalKmsTodosVeiculos() {
        double total=0;
        for (int i=0; i<nveiculos; i++) {
            total += this.carros[i].getqT();
        }
        return total;
    }
    
    
    /**
     * equals
     * 
     */
    public boolean equals(Object o) {
        if (o==this) return true;
        if ((o==null) || (o.getClass() != this.getClass())) return false;
        else {
            Stand a = (Stand) o;
            return this.nomeStand.equals(a.getNomeStand()) && this.carros.equals(a.getCarros()) &&
                this.capacidade == a.getCapacidade() && this.nveiculos == a.getNVeiculos() &&
                this.capacidade_inicial == a.getCi();
        }
    }
    
    /**
     * toString
     */

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i=0; i<this.nveiculos; i++) {
            s.append(this.carros[i].getMat());
            s.append("\n");
        }
        return s.toString();
    }
    
    /**
     * clone
     */
    public Stand clone() {
        return new Stand(this);
    }
    
}
