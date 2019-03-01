// EXAME 2015
// (Batalha Naval)

////////// 1 //////////

public class Jogo {
	
	public int[n][m] matriz = iniciar();
	public HashMap<String,Integer> pontuacoes;
	public HashMap<String,Integer> jogadas;
	public ReentrantLock lock = new ReentrantLock();

	public Jogo(ArrayList<String> jogadores){
		for(String j : jogadores){
			pontuacoes.put(j,0);
			jogadas.put(j,0);
		}
	}

	public void iniciar(){...}

	public void disparar(String jog, int n, int m){
		
		lock.lock();
		
		int pontuacao=0;

		if(jog.disparos<3){
			for(int x=m-4; x<m+4; x++){
				pontuacao+=matriz[n][x];
				matriz[n][x]=0;
			}
		}
		int pontAux = pontuacoes.get(jog);
		pontAux+=pontuacao;
		pontuacoes.put(jog,pontAux);

		int jogAux = jogadas.get(jog);
		jogAux++;
		jogadas.put(jog,jogAux);
		
		lock.unlock();
	}

	public boolean terminou(){

		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				if(matriz[i][j]==1) return false;
			}
		}
		return true;
	}
}

public class Cliente{
	private Socket s;
	private Jogo j;

	public Cliente(Socket s, Jogo j){
		this.s = s;
		this.j = j;
	}

	public void run(){
		String venceu = null;
		ObjectInputStream in = ...;
		ObjectOutputStream out = ...;

		while(!jogo.terminou()){
			...
		}
	}
}









