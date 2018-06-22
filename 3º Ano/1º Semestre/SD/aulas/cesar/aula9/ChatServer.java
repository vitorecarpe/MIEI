package aula9;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {
	public static void main(String args[]) throws IOException {
		
		ServerSocket ss = null;// cria a ligaçao
		ss = new ServerSocket(9999);
		System.out.println("server on");
		
		Socket cs = null;
		
		Log log = new Log();
		int i=0;
		int pos=0;
		
		
		while(true){
			cs = ss.accept();
			//ou escrever assim	(new Thread(new ClientThread(cs))).start();
			Thread t = new Thread(new ChatClientInput(cs, i, log, pos));
			t.start();
			Thread w = new Thread(new ChatClientOutput(cs,i,log,pos));
			w.start();
			
			
			System.out.println("new client "+i);
			i++;
		}
//ss.close();
	}
}
//para correr isto tem k s correr o programa no pc servidor e no "cliente" faço telnet ip porta e escrevo o k kiser

