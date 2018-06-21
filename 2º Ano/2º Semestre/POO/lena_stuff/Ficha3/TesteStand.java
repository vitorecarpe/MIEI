
/**
 * Classe de teste das classes Stand e Veículo.
 * 
 * Neste exercício faça:
 * 
 * 1a) forneça o código necessário (nas classes Stand e TesteStand) para que 
 *    código funcione correctamente
 * 1b) compile, execute e coloque mais testes no método main
 * 
 * 2) Crie, no método main, um sistema de menus, por forma a que seja
 *    o utilizador a inserir os dados que pretende testar.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TesteStand {

  public static void main(String[] args) {
    Veiculo v1, v2, v3, v4, v5;
    Veiculo d1, d2, d3, d4, d5;
    Stand veiculos_utilitarios;
    Stand veiculos_desportivos;
    
    //chamar os construtores (completar!)
    v1 = new Veiculo("01-01-AA",1000,100,50,4000,2000);
    v2 = new Veiculo("05-05-ZZ",3000,500,60,6000,2000);
    v3 = new Veiculo("09-74-BB",4000,100,50,6000,1200);
    v4 = new Veiculo("10-04-TH",3000,100,50,7400,1240);
    v5 = new Veiculo("34-13-MN",4500,300,40,8300,1260);
    
    d1 = new Veiculo("55-GT-33",10000,400,20,5000,4000);
    d2 = new Veiculo("67-JK-77",20000,500,30,6000,5000);
    d3 = new Veiculo("09-LV-66",30000,600,40,7000,6000);
    d4 = new Veiculo("65-NF-23",40000,700,50,8000,7000);
    d5 = new Veiculo("20-JA-09",50000,800,60,9000,8000);
    
    veiculos_utilitarios = new Stand("Garagem Veiculos Baratos", 10);
    
    veiculos_utilitarios.insereVeiculo(v1);
    veiculos_utilitarios.insereVeiculo(v2);
    veiculos_utilitarios.insereVeiculo(v3);
    veiculos_utilitarios.insereVeiculo(v4);
    veiculos_utilitarios.insereVeiculo(v5);
    
    veiculos_desportivos = new Stand("Garagem Auto Luxo", 10);
    
    veiculos_desportivos.insereVeiculo(d1);
    veiculos_desportivos.insereVeiculo(d2);
    veiculos_desportivos.insereVeiculo(d3);
    veiculos_desportivos.insereVeiculo(d4);
    veiculos_desportivos.insereVeiculo(d5);

    
    System.out.println("Informações do Stand " + veiculos_utilitarios.getNomeStand());
    System.out.println("--------------------------");
    System.out.println("Número de veículos: " + veiculos_utilitarios.getNVeiculos());
    System.out.println("Veículos: ");
    System.out.println(veiculos_utilitarios.toString());
    
    System.out.println("Informações do Stand " + veiculos_desportivos.getNomeStand());
    System.out.println("--------------------------");
    System.out.println("Número de veículos: " + veiculos_desportivos.getNVeiculos());
    System.out.println("Veículos: ");
    System.out.println(veiculos_desportivos.toString());

    
    //o v1 está no stand?
    
    System.out.println("v1 está no stand? " + veiculos_utilitarios.existeVeiculo(v1));
    
    System.out.print("Veículo com mais kms: ");
    Veiculo vx = veiculos_utilitarios.veiculoComMaisKms();
    System.out.println(vx.toString());
    
    
    //....
    // mais operações
      
    
    
  }    
    
    
}
