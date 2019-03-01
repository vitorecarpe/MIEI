package clientchat;

import java.net.Socket;
import java.util.Scanner;
import serverchat.*;

class ChatWriter extends Thread {

	private Socket skt;
	private String username;

	ChatWriter(Socket skt, String username) {
		this.skt = skt;
		this.username = username;
	}

s
	@Override
	public void run() {
		Scanner input = new Scanner(System.in);
		input.useDelimiter(System.getProperty("line.separator"));
		
		String line;
		String response;
		do {
			try {
				System.out.print("Write a message: ");
				line = input.next();
				Message m = new Message(User.SELF_USER, User.ALL_USERS, "username: " + line);
				System.out.println("Messsage sent!");
				SocketIO.writeObject(skt, m);
				System.out.println("Messsage sent!");
			} catch (Exception ex) {
				break;
			}
		} while(line.equals("/exit") == false);
	}

}
