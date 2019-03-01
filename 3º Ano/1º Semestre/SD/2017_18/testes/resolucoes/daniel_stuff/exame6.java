import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.lang.StringBuilder;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;

interface Controlador{
	public void temperatura(int centigrados);
	public void limiar(int centigrados);
	public boolean on_off(boolean estadoatual);
}

class Controlador_Temp implements Controlador{
	private int temperatura;
	private int limiar;
	private ReentrantLock lock;
	private Condition inalterado;

	public Controlador_Temp(int temperatura, int limiar){
		this.temperatura = temperatura;
		this.limiar = limiar;
		this.lock = new ReentrantLock();
		this.inalterado = lock.newCondition();
	}

	public void temperatura(int centigrados){
		this.lock.lock();
		int tempAnterior = this.temperatura;
		this.temperatura = centigrados;
		if (tempAnterior != centigrados) this.inalterado.signalAll();
		this.lock.unlock();
	}

	public void limiar(int centigrados){
		this.lock.lock();
		int limiarAnterior = this.temperatura;
		this.limiar = centigrados;
		if (limiarAnterior != centigrados) this.inalterado.signalAll();
		this.lock.unlock();
	}

	public boolean on_off(boolean estadoatual){
		boolean res = false;
		try{
			this.lock.lock();
			if(estadoatual == true){
				while(temperatura < limiar) inalterado.await();
				res = false;
			}else{
				while(temperatura >= limiar) inalterado.await();
				res = true;
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		finally{
			this.lock.unlock();
		}
		return res;
	}
}

class TreatClient implements Runnable{
	private Socket cs;
	private Controlador_Temp controlador;
	private BufferedReader in;
	private PrintWriter out;

	public TreatClient (Controlador_Temp controlador, Socket cs){
		this.cs = cs;
		this.controlador = controlador;
	}

	public void run (){
		try{
			in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			out = new PrintWriter(cs.getOutputStream(), true);
			String current;int temp;
			try{
				while((current = in.readLine()) != null){
					switch(current){
						case "temperatura":
							temp = Integer.parseInt(in.readLine());
							this.controlador.temperatura(temp);
							System.out.println("Temperatura Atual: " + temp);
							out.println("Temperatura Atual: " + temp);
							break;
						case "limiar":
							temp = Integer.parseInt(in.readLine());
							this.controlador.limiar(temp);
							out.println("Limiar Atual: " + temp);
							System.out.println("Limiar Atual: " + temp);
							break;
						case "on_off":
							boolean bool = Boolean.parseBoolean(in.readLine());
							this.controlador.on_off(bool);
							out.println("Estado da caldeira: " + (!bool));
							System.out.println("Estado da caldeira: " + (!bool));
							break;
						default:
							out.println("Operação Inválida");
							break;
					}	
				}
				System.out.println("Connection Closed!");
				this.cs.shutdownInput();
				this.cs.shutdownOutput();
				this.cs.close();
			}
			catch(NumberFormatException e){out.println("Argumento Inválido");}
		}catch(IOException e){
			e.printStackTrace();
		}

	}
}

public class exame6{
	public static void main (String [] args){
		try{
			ServerSocket socket = new ServerSocket(12345);
			Controlador_Temp controlador = new Controlador_Temp(14,19);

			while(true){
				Socket cs = socket.accept();
				System.out.println("Connection Accepted!!");
				Thread t = new Thread( new TreatClient (controlador,cs));
				t.start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}