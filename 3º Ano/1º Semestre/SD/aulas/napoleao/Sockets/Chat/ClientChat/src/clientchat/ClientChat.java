package clientchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import serverchat.SocketIO;
import serverchat.User;

public class ClientChat {

	public static void main(String[] args) throws IOException {
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Attempting to connect to server...");
		
		Socket skt = new Socket();
		try {
			skt.connect(new InetSocketAddress("localhost", 6063));
		} catch (Exception e) {
			System.out.println("Error while connecting...");
			return;
		}

		System.out.print("Connected. Choose your username please: ");
		String username = userInput.readLine();
		System.out.println("username: " + username);
		User u = new User(username);
		SocketIO.writeObject(skt, u);
		
		ChatReader reader = new ChatReader(skt, username);
		ChatWriter writer = new ChatWriter(skt, username);

		//A LEITURA NAO ADQUIRE LOCK PORQUE A ESCRITA ESTA ENCRAVADA NO ACQUIRE PENSO EU
		reader.start();
		writer.start();

		try {
			reader.join();
			writer.join();
		} catch (Exception e) {
			return;
		}
	}

}
