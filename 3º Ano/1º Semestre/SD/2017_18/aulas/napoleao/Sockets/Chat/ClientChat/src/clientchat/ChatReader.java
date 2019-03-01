package clientchat;

import java.net.Socket;
import serverchat.*;
import serverchat.Message.MessageCode;

class ChatReader extends Thread {

	private Socket skt;
	private String username;

	ChatReader(Socket skt, String username) {
		this.skt = skt;
		this.username = username;
	}

	@Override
	public void run() {
		Message m;
		do {
			try {
				m = (Message) SocketIO.readObject(skt);
			} catch (Exception ex) {
				return;
			}
			System.out.println(m);
			System.out.println(m.getText());
		} while(m.getMessageCode().equals(MessageCode.EXIT) == false);

	}
}
