package clientsum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientSum {

	public static void main(String[] args) throws IOException {

		Socket c = new Socket();
		c.connect(new InetSocketAddress("localhost", 6063));

		Scanner userInput = new Scanner(new InputStreamReader(System.in));
		BufferedReader sckInput = new BufferedReader(new InputStreamReader(c.getInputStream()));
		PrintWriter sckOutput = new PrintWriter(c.getOutputStream());

		int current;
		try {
			while (true) {
				current = userInput.nextInt();
				sckOutput.write(current);
				sckOutput.flush();
			}
		} catch (NoSuchElementException ex) { //input terminou
			c.shutdownOutput();
			int sum = sckInput.read();
			System.out.println("Resultado: " + sum);
		}

	}
}
