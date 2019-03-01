package clientebanco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteBanco {

	public static void main(String[] args) throws IOException {
		Socket skt = new Socket();
		skt.connect(new InetSocketAddress("localhost", 6063));
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

		BufferedReader sktInput = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		PrintWriter sktOutput = new PrintWriter(skt.getOutputStream());

		String line;
		String response;
		do {
			line = userInput.readLine();
			sktOutput.println(line);
			sktOutput.flush();
			response = sktInput.readLine();
			System.out.println(response + "\n");
		} while(line.equals("close") == false && line.equals("shutdownServer") == false);

	}

}
