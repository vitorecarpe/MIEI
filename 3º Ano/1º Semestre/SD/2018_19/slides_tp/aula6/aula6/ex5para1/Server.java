package ex5para1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	private ServerSocket servsocket;
	private int porto;

	public Server (int porto){
		this.porto = porto;
	}

	public void startServer(){
		try {
			System.out.println("#### SERVER ####");
			this.servsocket = new ServerSocket(this.porto);
			int workerCounter = 1; //contador de ids para worker threads

			while(true){
				System.out.println("ServerMain > Server is running waiting for a new connection...");
				Socket socket = servsocket.accept();
				System.out.println("ServerMain > Connection received! Create worker thread to handle connection.");

				ServerWorker sw = new ServerWorker(socket, workerCounter++);
				new Thread(sw).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server s = new Server(12345);
		s.startServer();
	}
}
