package aula9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientInput implements Runnable{
		
		Socket cs= null;
		int clientnumber;
		Log log;
		int pos;
		
		public ChatClientInput(Socket cs, int i, Log g, int pos) {
			this.cs = cs;
			clientnumber= i;
			log = g;			
		}
		
		
		public void createClient() throws IOException{
						
			PrintWriter out = new PrintWriter (cs.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader (cs.getInputStream()));
						
			String current;
			while((current = in.readLine()) != null && !current.equals(":quit")) {
				
				System.out.println("echo client "+ clientnumber +":"+ current); // escreve o que eu estou a receber
				log.add(current);				
			}
			
			System.out.println(":quit " + clientnumber);
			in.close();
			out.close();
			cs.close();
		}

		public void run(){
			try {
				createClient();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}


