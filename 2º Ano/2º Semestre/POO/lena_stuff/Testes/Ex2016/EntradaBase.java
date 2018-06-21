public class EntradaBase implements Entrada, Comparable<EntradaBase> {
    private String termo;
    private String definicao;
    
    public EntradaBase(EntradaBase eb) {
        this.termo = eb.getTermo();
        this.definicao = eb.getDefinicao();
    }
    
    public String getTermo() {
        return this.termo;
    }
    
    public String getDefinicao() {
        return this.definicao;
    }
  
    public boolean equals(Object o) {
        if (o == this) return true;
        if ((o== null) || (this.getClass() != o.getClass())) return false;
        EntradaBase eb = (EntradaBase) o;
        return this.termo.equals(eb.getTermo()) && this.definicao.equals(eb.getDefinicao());
    }
    
    public EntradaBase clone(){
        return new EntradaBase(this);
    }
    
    public int compareTo(EntradaBase e) {
        String termoTC = e.getTermo();
        return termoTC.compareTo(this.termo);
    }
}


// DIFERENÇA ENTRE CLASSE ABSTRACTA E INTERFACE
// - Uma classe abstracta pode conter variáveis de instância, métodos abstratos, e métodos definidos, enquanto as interfaces
// só podem ter métodos abstratos (declarações de métodos) e variáveis estáticas e constantes.
// - Os métodos e variáveis de uma classe abstrata podem ser definidos com qualquer visibilidade, enquanto os métodos e
// variáveis de uma interfacem devem ser definidos como public.
// - Uma subclasse só pode herdar uma única classe (abstrata ou não), equanto uma classe pode implementar várias interfaces.
// - Uma subclasse pode definir os métodos abstratos com a mesma ou menor visibilidade, enquanto uma classe a implementar
// uma interface deve definir os métodos com exatamente a mesma visibilidade (public).
// INTERFACE
// Uma interface é um contracto, apenas contém assinaturas de métodos, e não consegue fazer nada: é um padrão. Assim,
// as interfaces definem APIs que as classes que as implementetam devem implementar. Se houverem variáveis na interface,
// estas terão que ser estáticas e constantes, pois, se não forem estáticas, não serão acessíveis e se não forem constantes,
// todas as classes que implementam a interface poderão mudá-la, e haverá conflitos quanto ao seu valor. Não é possível
// criar uma instância de uma interface.
// CLASSE ABSTRATA
// Uma classe abstrata é usada quando estamos numa situação em que o código de uma classe não está totalmente definido.
// Imaginando que temos que manipular formas geométricas, a vantagem em criar uma classe abstracta Forma Geométrica, é que
// podemos no futuro criar novas formas geométricas que são subclasses desta e, para além disso, criar uma API para estas.
// Por exemplo, sabemos que todas as formas geométricas tem área e perímetro, no entanto também sabemos que a área de um
// triângulo é diferente da área de um quadrado, etc. Assim, podemos estipular que todas as subclasses de forma tem que
// implementar estes métodos (de área, perímetro).