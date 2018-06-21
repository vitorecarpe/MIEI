
/**
 * Classe de teste das classes PlayList e Faixa.
 * 
 * 
 * @author anr
 * @version 2017.03.30
 */

public class TestePlayList {
    
  public static void main(String[] args) {
    Playlist pl1 = new Playlist();
    
    Faixa f1, f2, f3, f4, f5;
    
    f1 = new Faixa("Human","Rag'n'Bone Man",200, 4);
    f2 = new Faixa("Loucos","Matias Damásio",270, 3);
    f3 = new Faixa("Amar pelos dois","Salvador Sobral",185, 4);
    f4 = new Faixa("Something Just Like This","The Chainsmokers & Coldplay",247, 2);
    f5 = new Faixa("I Feel It Coming","The Weeknd",269, 5);
    
    pl1.addFaixa(f1);
    pl1.addFaixa(f2);
    pl1.addFaixa(f3);
    pl1.addFaixa(f4);
    pl1.addFaixa(f5);

    
    System.out.println("Número de faixas na playlist: " + pl1.numFaixas());
    
    int num = pl1.classificacaoSuperiorF(f4);
    
    System.out.println("Número de faixas com classificação superior a " + f4.getClassificacao() + " = "+ num);
    
    System.out.println(pl1.faixasPorClass_F());
    
    System.out.println("Faixas por ordem alfabética de autor: ");
    System.out.println(pl1.faixasPorOrdemAutor());
    
  }
    
    
}
