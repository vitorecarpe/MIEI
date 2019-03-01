package serverchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerChat {

	private boolean keepRunning;
	private static TreeMap<String, User> users;

	public static void main(String[] args) {
		users = new TreeMap<String, User>();
		ServerChat serverChat = new ServerChat();

		try {
			serverChat.start();
		} catch (IOException ex) {
			Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Erro no servidor");
		}
	}

	public synchronized void stopRunning() {
		keepRunning = false;
	}

	public synchronized boolean serverRunning() {
		return keepRunning;
	}

	public void start() throws IOException {
		ServerSocket server = new ServerSocket();
		server.setReuseAddress(true);
		server.bind(new InetSocketAddress(6063));

		do {
			Socket skt = server.accept();
			addConnection(skt);
		} while(serverRunning());
	}

	private void addConnection(Socket skt) {
		User newUser;
		try {
			newUser = (User) SocketIO.readObject(skt);
		} catch (IOException ex) {
			System.out.println("Erro ao receber user");
			return;
		} catch (ClassNotFoundException ex) {
			System.out.println("Erro ao receber user");
			return;
		}

		if (users.get(newUser.getUsername()) != null) {
			System.out.println("Username ja utilizado");
			return;
		}

		users.put(newUser.getUsername(), newUser);
		newUser.setSocket(skt);
		new ChatConnection(this, skt, newUser).start();
	}

	void sendAll(Message msg) {
		for(User u : users.values()) {
			u.sendMessage(msg);
		}
	}
}
