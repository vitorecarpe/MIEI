import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    private List<String> opcoes;
    private int op;
    
    /** Método para obter a última opção lida */
    public int getOpcao() {
        return this.op;
    }
    
    /** Constructor for objects of class Menu */
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }

    /** Método para apresentar o menu e ler uma opção. */
    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }
    
    /** Apresentar o menu */
    private void showMenu() {
        System.out.println("\n --- MENU --- ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.println( i+1+" - "+this.opcoes.get(i) );
        }
        System.out.println("0 - Sair e salvar estado ");
    }
    /** Ler uma opção válida */
    private int lerOpcao() {
        int op; 
        Scanner input = new Scanner(System.in);
        
        System.out.print("Opção: ");
        try { op = input.nextInt(); }
        catch (InputMismatchException e) { // Não foi inscrito um int
            System.out.println("Caracter Invalido!!!");
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opçao Invalida!!!");
            op = -1;
        }
        return op;
    }
}
