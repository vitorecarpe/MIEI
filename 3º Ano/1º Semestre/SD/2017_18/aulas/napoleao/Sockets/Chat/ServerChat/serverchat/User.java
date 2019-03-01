package serverchat;

import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable {
	public static final int SELF_USER = -1;
	public static final int ALL_USERS = -2;

	private String username;
	private Socket skt;

	public User(String username) {
		this.username = username;
		this.skt = null;
	}

	public String getUsername() {
		return username;

	}

	public void setSocket(Socket skt) {
		this.skt = skt;
	}

	public int sendMessage(Message msg) {
		try {
				SocketIO.writeObject(skt, msg);
		} catch (Exception ex) {
			return 0;
		}

		return 1;
	}
}
