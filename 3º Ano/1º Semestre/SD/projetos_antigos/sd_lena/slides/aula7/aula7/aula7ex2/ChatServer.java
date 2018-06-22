package aula7ex2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class ChatServer {

	private ServerSocket serverSocket;
	private int porto;
	private Hashtable<String, BufferedWriter> clients;

	public ChatServer(int porto) throws IOException{
		this.porto = porto;
		clients = new Hashtable<String, BufferedWriter>();
	}

	public void startServer(){
		try {
			System.out.println("#### SERVER ####");
			this.serverSocket = new ServerSocket(this.porto);

			while(true) {
				System.out.println("ServerMain > Server is running waiting for a new connection...");
				Socket socket = serverSocket.accept();
				System.out.println("ServerMain > Connection received! Create worker thread to handle connection.");
				Thread t = new Thread(new Worker(this, socket));
				t.start();			
			}
		} catch (IOException e) {
			System.out.println("Error accepting connection: " + e.getMessage());
		}
	}

	public synchronized boolean registerClient(String nick, BufferedWriter writer){
		if(!clients.containsKey(nick)){
			clients.put(nick, writer);
			return true;
		}

		return false;
	}

	public synchronized void deRegisterClient(String nick){
		clients.remove(nick);
	}

	public synchronized void multicast(String userSender, String msg){
		msg = userSender + ": " + msg;
		for(String user : clients.keySet()) {
			if(!user.equals(userSender)){
				try {
					BufferedWriter bw = clients.get(user);
					bw.write(msg);
					bw.newLine();
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ChatServer s = new ChatServer(12345);
		s.startServer();
	}
}

class Worker implements Runnable {
	private String nickname;
	private ChatServer chatServer;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;


	public Worker(ChatServer server, Socket socket) throws IOException {
		this.chatServer = server;
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}


	@Override
	public void run() {
		try {
			//registar nick
			nickname = in.readLine();
			while(!chatServer.registerClient(nickname, out)){
				System.out.println("> Nickname "+nickname+" already registered! Ask client for a different one");
				out.write("false");
				out.newLine();
				out.flush();
				nickname = in.readLine();
			}

			System.out.println("> Nickname "+nickname+" successfully registered!");
			out.write("ok");
			out.newLine();
			out.flush();

			//receber mensagens do utilizador e difundir pelos restantes utilizadores
			String msg = null;
			while( (msg = in.readLine()) != null) {
				chatServer.multicast(nickname, msg); 
			}

			System.out.println("Client: " + nickname + " disconnected.");

			//fechar sockets
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			chatServer.deRegisterClient(nickname);			
		}

	}
}