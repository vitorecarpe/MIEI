import java.io;
import java.net.*;

public class EchoClient{
	public static void main(String args[]) throws IOException, UnknowsHostException{
		Socket cs = new Socket("127.0.0.1",9999);

		PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
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