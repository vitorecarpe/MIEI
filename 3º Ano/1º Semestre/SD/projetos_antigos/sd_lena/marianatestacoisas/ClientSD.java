package business;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


public class ClientSD {
	private PrintWriter out;
	private Socket cs;
	private BufferedReader in;

/*	public void trataMsg(String msg) {
		switch(msg) {
			case "autentica": autentica();
							  break;
		}
	}	
	*/

	public void autentica(String username, String password,int i) {
	try {
			this.out.println(username);
			this.out.println(password);
			this.out.println(i); //1 se é signup, 0 se é login
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			// out.println(this.sin.readLine());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}


	public void connect() throws IOException {
		this.cs = new Socket("127.0.0.1", 9999);
		//this.sin = new BufferedReader(new InputStreamReader(System.in));
		this.in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
		this.out = new PrintWriter(cs.getOutputStream(), true);
		
		System.out.println("O socket está ligado");
		
		while(!cs.isClosed()) {
			//if (sin.ready()) out.println(sin.readLine());
			if (in.ready()) System.out.println(in.readLine());

		}
		/*
		cs.shutdownOutput();
		in.close();
		out.close();
		cs.close();
		*/
	}
	

}