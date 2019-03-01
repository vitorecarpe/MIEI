public class Main{
	public static void main(String[] args){
		int i;
		for(i=1;i<10;i++)
			new Thread(new Classe(i)).start();
	}
}