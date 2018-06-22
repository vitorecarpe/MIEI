package business;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Responsável pela leitura do socket.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public class ClientWrite {
	private Socket cs;
	private PrintWriter out;

   /**
    * Construtor da classe ClientWrite, utilizado na escrita de mensagens
    * do cliente para o servidor. 
    * 
    * @param cs socket utilizado entre o cliente (jogador) e o servidor
    */
	public ClientWrite(Socket cs) {
		this.cs = cs;
		try {
			this.out = new PrintWriter(cs.getOutputStream(), true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Shutdown da aplicação do cliente. Avisa o servidor que não será
	 * mais nada feito do lado do cliente, terminando assim a conexão
	 * e fechando o socket.
	 */
	public void shutdown() {
		try {
			cs.shutdownOutput();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	
   /**
    * Efetua o processo de autenticação de um cliente (jogador)
    * com o servidor.
    * 
    * @param username username do jogador em processo de autenticação
    * @param password password do jogador em processo de autenticação
    * @param i		  indicador de autenticação (0) ou de inscrição 
    * 				  com subsequente autenticação (1) 
    */
	public void autentica(String username, String password, int i) {
		this.out.println("autenticar");
		this.out.println(username);
		this.out.println(password);
		this.out.println(i); 
	}

	
   /**
    * Escreve mensagens do cliente com destino ao servidor.
    * 
    * @param s mensagem do cliente com destino ao servidor
    */
	public void write(String s) {
		this.out.println(s);
	}
}