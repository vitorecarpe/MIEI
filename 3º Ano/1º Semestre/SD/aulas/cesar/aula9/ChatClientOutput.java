package aula9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientOutput implements Runnable{
		
	Socket cs= null;
	int clientnumber;
	Log log;
	int pos;
	
	public ChatClientOutput(Socket cs, int i, Log g, int pos) {
		this.cs = cs;
		clientnumber= i;
		log = g;
		
		
	}
	
	
	public void createClient() throws IOException, InterruptedException{
					
		PrintWriter out = new PrintWriter (cs.getOutputStream(), true);
		int pos=0;
		while(true) {			
			out.println(log.read(pos));
			pos++;
		}

	}

	
	
	
	public void run(){
		try {
			createClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
