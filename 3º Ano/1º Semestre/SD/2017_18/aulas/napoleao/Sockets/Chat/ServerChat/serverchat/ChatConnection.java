package serverchat;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverchat.Message.MessageCode;

class ChatConnection extends Thread {

	private ServerChat server;
	private Socket skt;
	private User user;

	public ChatConnection(ServerChat server, Socket skt, User user) {
		this.server = server;
		this.skt = skt;
		this.user = user;
	}

	public int send(Message msg) {
		try {
			SocketIO.writeObject(skt, msg);
			return 1;
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public void run() {
		System.out.println(user.getUsername() + " joined the chat");

		Message lastReceived;

		do {
			try {
				System.out.println("waiting for message");
				lastReceived = (Message) SocketIO.readObject(skt);
				System.out.println("message Received");
			} catch (Exception ex) {
				break;
			}
			server.sendAll(lastReceived);
		} while (lastReceived.getMessageCode() != MessageCode.EXIT);

		System.out.println(user.getUsername() + "left the chat");
	}
}
