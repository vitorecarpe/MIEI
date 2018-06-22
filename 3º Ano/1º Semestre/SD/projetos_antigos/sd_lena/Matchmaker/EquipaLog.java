import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class EquipaLog {
	String[] personagens;
	ArrayList<String> log;
	int nPersonagensDef;
	Boolean equipaPronta;

	private final String[] herois = new String[] {
		"doomfist","genji","mccree",
		"pharah","reaper","soldier76","sombra",
		"tracer","bastion","hanzo","junkrat","mei",
		"torbjorn","widowmaker","dva","orisa",
		"reinhardt","roadhog","winston","zarya",
		"ana","lucio","mercy","moira","symmetra",
		"zenyatta","daniel","helena","mariana","pedro"};

   /**
    * Construtor de objetos da classe EquipaLog.
    */
	EquipaLog() {
		this.personagens = new String[30];
		this.log = new ArrayList<String>();
		this.nPersonagensDef = 0;
		this.equipaPronta = false;
	}

   /**
    * Método que utiliza o lock intrínseco do objeto que indica
    * se a equipa est� pronta para iniciar a partida ou não.
    * 
    * @return booleano que indica se a equipa está pronta
    * 		  para iniciar a partida ou não
    */
	public Boolean pronta() {
		synchronized(equipaPronta) {
			return this.equipaPronta;
		}
	}

   /**
    * Escreve uma mensagem no log da equipa a que este objeto
    * se refere.
    * 
    * @param s mensagem a ser escrita no log de uma equipa
    */
	public void writeLog(String s) {
		synchronized(this.log) {
			log.add(s);
			this.log.notifyAll();
		}
	}

   /**
    * Obtém um array de strings que associam os jogadores ao herói
    * a ser utilizado por cada um deles durante a partida.
    * 
    * @return array de strings que associam os jogadores ao herói 
    * 		  a ser utilizado durante a partida
    */
	public String[] getPersonagens() {
		String[] equipa = new String[5];
		int j = 0, i;
		for(i = 0; i < 30; i++) {
			if(personagens[i] != null) equipa[j++] = personagens[i] + " AS " + herois[i];
		}
		return equipa;
	}

   /**
    * Aguarda novos registos no log de uma equipa até ao final da partida e escreve-os para um jogador.
    * 
    * @param pw PrinterWriter onde serão escritas as mensagens para o jogador
    * @param i	índice que se segue ao da mensagem final após o ciclo de escolha de personagens
    */
	public void retornaResultado(PrintWriter pw, int i) {
		try {
			//equipaAdversaria
			synchronized(this.log) {
				while(this.log.size() <= i) this.log.wait();
				pw.println(log.get(i));
			}
			//Venceu/Perdeu
			synchronized(this.log) {
				while(this.log.size() <= i + 1) this.log.wait();
				pw.println(log.get(i + 1));
			}
			//comecarPartida
			synchronized(this.log) {
				while(this.log.size() <= i + 2) this.log.wait();
				pw.println(log.get(i + 2));
			}
			//terminarPartida
			synchronized(this.log) {
				while(this.log.size() <= i + 3) this.log.wait();
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

   /**
    * Verifica se um utilizador tem uma escolha de personagem previamente efetuada.
    * 
    * @param user username do utilizador cuja verificação de uma escolha já existente vamos fazer
    * @return	  -1 caso o user ainda não tenha feito nenhuma escolha ou índice da escolha em caso contrário
    */
	public int userChoiceExists(String user) {
		boolean existe = false;
		int i;
		for(i = 0; i < 30 && !existe; i++) {
			if(personagens[i] != null) existe = user.equals(personagens[i]);
		}
		return existe ? (i-1) : (-1);
	}

   /**
    * Regista uma eventual escolha de personagem por parte de um jogador.
    * 
    * @param index string que conterá um índice que traduzirá uma escolha de uma certa personagem por parte do jogador
    * @param user  username do utilizador a ser adicionado
    * @return	   booleano que indica se foi escolhida uma personagem ou não
    */
	public synchronized boolean add(String index, String user) {
		boolean flag = false;
		int userChoice = -1;
		int idx = Integer.parseInt(index);
		if(personagens[idx] == null) {													//Caso a personagem escolhida pelo utilizador não esteja já escolhida por outro jogador ...
			userChoice = userChoiceExists(user);										//...é feita a verificação de uma escolha prévia de personagem por parte do jogador.
		
			if(userChoice == -1) {														//Caso o utilizador n�o tenha uma escolha prévia ...
				log.add("persEscolhida/" + index + "/" + user);							//...é registada a sua escolha de personagem no log da equipa ...
				this.nPersonagensDef++;													//...e incrementado o número de escolhas de personagens definidas para a equipa.
			}
			else {																		//Caso o utilizador tenha uma escolha prévia ...
				personagens[userChoice] = null;											//...é anulada a sua escolha anterior ...
				log.add("persSubstituida/" + userChoice + "/" + index + "/" + user);	//...e registada a sua escolha de personagem no log da equipa. 
			}

			personagens[idx] = user;													//Finalmente é atribuída a personagem ao utilizador, cuja escolha é registada no array 'personagens'.
			flag = true;																//A flag indicativa da existência de uma escolha toma agora o valor 'true'.

			synchronized (this.log) {
			  this.log.notifyAll();
			}
			
			if(this.nPersonagensDef == 5)												//Se todos os jogadores da equipa têm uma personagem escolhida ...
				this.equipaPronta = true;												//...a equipa considera-se pronta a jogar.
		}
		return flag;
	}

   /**
    * Método correspondente a um ciclo que se mantém ativo enquando não for verificado o final da fase
    * de escolha de personagens, quer as equipas estejam prontas ou não (caso de erro).
    * 
    * @param pw							 PrintWriter onde serão escritas as mensagens para o jogador
    * @return							 índice seguinte ao índice no log onde foi encontrada a mensagem que indica o final da fase de escolha de personagens
    * @throws TimeLimitExceededException lançada se for excedido o tempo de seleção de personagens
    */
	public int loopEscolhaPersonagens(PrintWriter pw) throws TimeLimitExceededException {
		int i = 0; 
		String s;
		Boolean flag = true;
		try {
			while(flag) {
				synchronized(this.log) {
					while(i >= log.size()) this.log.wait();							//Se o i for maior ou igual ao tamanho do log, esse índice do array não terá qualquer mensagem de log.
					s = log.get(i);
					if(s.equals("Erro8")) throw new TimeLimitExceededException();	//A condição será verdadeira se acabou o tempo e as equipas não estão feitas.
					else flag = !s.equals("TimeEnded");								//Se o tempo de escolha acabou e houve escolha, a flag toma agora valor 'false'.
				}
				pw.println(s);
				i++;
			}
			pw.println("EquipaCompleta/");//Acho que n vai ser preciso isto
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		return i;
	}

}
