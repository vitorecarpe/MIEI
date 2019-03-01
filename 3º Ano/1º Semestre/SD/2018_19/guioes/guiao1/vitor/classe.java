public class Classe implements Runnable{
	int classe;

	public Classe(int n){
		this.classe = n;
	}

	public void run(){
		System.out.println(classe);
	}
}