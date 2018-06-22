//SO - Guiao 6 - 21 Nov

import java.io.*;
import java.net.*;

//1.
public class EchoClient{
	public static void main(String args[]) throws IOException, UnknowsHostException{
		Socket cs = new Socket("127.0.0.1",12345);

		PrintWriter out = new PrintWriter(cs.getOutputStream(),true); //autoflush=true
		BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));

		String current;
		BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

		while( (current = sin.readLine()) != null ){
			out.println(current);
			System.out.println("got echo: "+in.readLine());
		}

		in.close();
		out.close();
		cs.close();
		ss.close();
	}
}

//2. solução stor
public class Server{
	public static void main(String[] args) throws Exception{
		int port = Integer.parseInt(args[0]);
		ServerSocket srv = new ServerSocket(port);

		while(true){
			Socket cli = srv.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(cli.getInputStream());
			PrintWriter out = new PrintWriter(cli.getOutputStream());
			while(true){
				String str=in.readLine();
				if(str==null)break;
				out.println(str);
				out.flush();
			}

			out.close();
		}
	}
}

//2. solução kiko
public class LoopEcho{
	public static void main(String args[]) throws IOException{
		ServerSocket ss = new ServerSocket(9999);
		Server cs = ss.accept();

		PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));

		String current;
		while( (current = in.readLine()) != null ){
			//assert current !=null;
			out.println(current);
			System.out.println("echo: "+current);
		}

		in.close();
		out.close();
		cs.close();
		ss.close();
	}
}

//2. stor
public class Client{
	public static void main(String args[]) throws Exception{
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		Socket s = new Socket(host, port);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(s.getOutputStream());
		while (true){
			String s1 = System.console().readLine();
			if(s1==null) break;
			out.println(s1);
			out.flush();
			String s2 = in.readLine();
			if(s2==null)break;
			System.out.println(s2);
		}
		s.close();
	}
}



