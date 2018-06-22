package ex5para3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ServerWorker implements Runnable{

	private Socket socket;
	private int id;

	public ServerWorker (Socket socket, int id){
		this.socket = socket;		
		this.id = id;
	}

	@Override
	public void run() {
		try{
			//criar canais de leitura/escrita no socket
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			int sum = 0;				//soma dos valores recebidos
			int numberValues = 0;		//número de valores recebidos para cálculo da média
			String line;				//string para ler mensagens do cliente
			while((line = in.readLine()) != null){
				System.out.println("\nWorker-"+id+" > Received from client the value: " + line);
				sum += Integer.parseInt(line);
				numberValues++;
				out.write(String.valueOf(sum));
				out.newLine();
				out.flush();
				System.out.println("Worker-"+id+" > Reply with: " + sum);
			}
			
			//calcular média e responder ao cliente
			double average = sum/(double) numberValues;
			System.out.println("\nWorker-"+id+" > Client disconnected. Send average value: "+average+"\n");
			out.write(String.valueOf(average));
			out.newLine();
			out.flush();

			//fechar sockets
			socket.shutdownOutput();
			socket.shutdownInput();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
