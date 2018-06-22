package javaapplication9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSum {

	public static void main(String[] args) throws IOException {

		ServerSocket s = new ServerSocket();
		s.setReuseAddress(true);
		s.bind(new InetSocketAddress(6063));

		while (true) {
			Socket c = s.accept();
			BufferedReader sckInput = new BufferedReader(new InputStreamReader(c.getInputStream()));
			PrintWriter sckOutput = new PrintWriter(c.getOutputStream());

			int sum = 0;
			int current;
			while ((current = sckInput.read()) != -1) {
				sum += current;
				System.out.println("Adding: " + current + "; Total: " + sum);
			}

			System.out.println("Finished with: " + sum);
			sckOutput.write(sum);
			sckOutput.flush();

			c.close();
		}

	}
}
