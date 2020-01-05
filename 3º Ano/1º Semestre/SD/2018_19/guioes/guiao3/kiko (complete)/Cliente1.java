import java.util.ArrayList;

public class Cliente1 implements Runnable{
	private Banco banco;

	public Cliente1(Banco b){
		this.banco=b;
	}

	public void run(){
		ArrayList<Integer> contas = new ArrayList<>();
		contas.add(0);
		contas.add(1);
		contas.add(2);
		contas.add(3);
		contas.add(9);
		banco.somaSaldo(contas);

		
	}
}