package ex1e2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private String hostname;
	private int porto;
	private Socket socket;
	
	public Client(String hostname, int porto){
		this.hostname=hostname;
		this.porto=porto;
	}
	
	public void clientStart(){
		
		try {	
			System.out.println("#### CLIENT ####");
			System.out.println("> Connecting to server...");
			socket = new Socket(this.hostname, this.porto);
			System.out.println("> Connection accepted!");
			
			//criar canais de leitura/escrita no socket
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			//criar canal de leitura do stdin
			BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
			
			String userInput; 	//string para ler o input do utilizador
			String response;	//string para ler a resposta do servidor
			System.out.print("$ ");
			while((userInput = systemIn.readLine())!=null && !userInput.equals("quit")){
				out.write(userInput);
				out.newLine();
				out.flush();
			
				response = in.readLine();
				System.out.println("> Received response from server: " + response);
				System.out.print("\n$ ");			
			}
				
			//fechar sockets
			socket.shutdownOutput();
			socket.shutdownInput();
			socket.close();
			
		} catch (UnknownHostException e) {
			System.out.println("ERRO: Server doesn't exist!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		Client c = new Client("127.0.0.1",12345);
		c.clientStart();
	}
}
