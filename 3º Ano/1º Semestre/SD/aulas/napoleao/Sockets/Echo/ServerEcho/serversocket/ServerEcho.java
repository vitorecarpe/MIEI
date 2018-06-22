package serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEcho {

	public void tutorial() throws IOException {
		//criar um novo socket
		ServerSocket s = new ServerSocket(6063);

		//criar um socket sem associar directamente a porta
		ServerSocket s2 = new ServerSocket();
		s.setReuseAddress(true);
		s.bind(new InetSocketAddress(6063));

		Socket c = s.accept();
		c.getInputStream();
		c.close();
	}

	public static void main(String[] args) throws IOException {
		
		ServerSocket s = new ServerSocket();
		s.setReuseAddress(true);
		s.bind(new InetSocketAddress(6063));

		while(true)  {
			Socket c = s.accept();
			BufferedReader input = new BufferedReader(new InputStreamReader(c.getInputStream()));
			PrintWriter output = new PrintWriter(c.getOutputStream());

			String line;
			while((line = input.readLine()) != null) {
				line = line.toUpperCase();
				System.out.println("Sending: " + line);
				output.println(line);
				output.flush();
			}
			c.close();
		}
	}
}