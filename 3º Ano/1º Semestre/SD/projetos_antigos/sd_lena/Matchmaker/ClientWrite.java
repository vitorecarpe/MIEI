import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWrite {
	private Socket cs;
	private PrintWriter out;

   /**
    * Construtor de objetos da classe ClientWrite, usados pelos bots criados para teste.
    * 
    * @param cs socket para comunicação entre o cliente (jogador) e o servidor
    */
	public ClientWrite(Socket cs) {
		this.cs = cs;
		try {
			this.out = new PrintWriter(cs.getOutputStream(), true);
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}

   /**
    * Regista e autentica um bot.
    * 
    * @param username username do bot, dado por "bot"  + id
    * @param password password do bot, dada por "pass" + id
    * @param i		  indicador de necessidade de registo do bot
    */
	public void autentica(String username, String password, int i) {
		this.out.println("autenticar");
		this.out.println(username);
		this.out.println(password);
		this.out.println(i); 
	}

   /**
    * Escreve mensagens para a comunicação entre os bots e 
    * o servidor.
    * 
    * @param s mensagem a ser escrita com destino ao servidor
    */
	public void write(String s) {
		this.out.println(s);
	}

}
