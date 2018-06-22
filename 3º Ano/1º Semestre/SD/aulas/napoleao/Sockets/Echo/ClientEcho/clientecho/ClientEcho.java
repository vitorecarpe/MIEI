package clientecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientEcho {

	public static void main(String[] args) throws IOException {
		Socket c = new Socket();
		c.connect(new InetSocketAddress("localhost", 6063));
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader serverInput = new BufferedReader(new InputStreamReader(c.getInputStream()));
		PrintWriter output = new PrintWriter(c.getOutputStream());

		String line;
		while((line = userInput.readLine()).equals("exit") == false) {
			output.println(line);
			output.flush();
			line = serverInput.readLine();
			System.out.println(line);
			
		}
		
	}
}
