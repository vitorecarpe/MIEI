package ex3e4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

			while(true){
				System.out.println("> Server is running waiting for a new connection...");
				Socket socket = servsocket.accept();
				System.out.println("> Connection received!");

				//criar canais de leitura/escrita no socket
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

				int sum = 0;				//soma dos valores recebidos
				int numberValues = 0;		//número de valores recebidos para cálculo da média
				String line;				//string para ler mensagens do cliente
				while((line = in.readLine()) != null){
					System.out.println("\n> Received from client the value: " + line);
					sum += Integer.parseInt(line);
					numberValues++;
					out.write(String.valueOf(sum));
					out.newLine();
					out.flush();
					System.out.println("> Reply with: " + sum);
				}
				
				//calcular média e responder ao cliente
				double average = sum/(double) numberValues;
				System.out.println("> Client disconnected. Send average value: "+average+"\n");
				out.write(String.valueOf(average));
				out.newLine();
				out.flush();

				//fechar sockets
				socket.shutdownInput();
				socket.shutdownOutput();
				socket.close();
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
