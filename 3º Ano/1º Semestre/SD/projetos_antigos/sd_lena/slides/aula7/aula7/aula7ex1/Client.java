package aula7ex1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements InterfaceBanco{

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Client(String hostname, int porto){
		try {
			this.socket = new Socket(hostname, porto);
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int criarConta(double saldo) {
		int id = -1;
		try{
			//enviar id da operação e saldo da nova conta
			out.writeObject(OpsBanco.CRIAR_CONTA);
			out.writeDouble(saldo);
			out.flush();

			//receber id da nova conta
			id = in.readInt();
		} catch(IOException e){
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public double fecharConta(int id) throws ContaInvalida {
		double saldo = -1;

		try{
			//enviar id da operação e da conta a fechar
			out.writeObject(OpsBanco.FECHAR_CONTA);
			out.writeInt(id);
			out.flush();

			int status = in.readInt();

			//receber estado da operação e saldo da conta fechada
			if(status == OpsBanco.CONTA_INVALIDA.ordinal())
				throw new ContaInvalida(id);
			
			saldo = in.readDouble();

		} catch(IOException e){
			e.printStackTrace();
		}

		return saldo;
	}

	@Override
	public double consultar(int id) throws ContaInvalida {
		double saldo = -1;

		try{
			//enviar id da operação e da conta a fechar
			out.writeObject(OpsBanco.CONSULTAR);
			out.writeInt(id);
			out.flush();

			//receber estado da operação e saldo da conta
			int status = in.readInt();

			if(status == OpsBanco.CONTA_INVALIDA.ordinal())
				throw new ContaInvalida(id);

			saldo = in.readDouble();
			
		} catch(IOException e){
			e.printStackTrace();
		}

		return saldo;
	}

	@Override
	public double consultarTotal(int[] contasInput) {
		double saldo = -1;

		try{
			//enviar id da operação, número de contas a consultar e seus respectivos ids
			out.writeObject(OpsBanco.CONSULTAR_TOTAL);
			out.writeInt(contasInput.length);
			for(int i=0; i<contasInput.length; i++){
				out.writeInt(contasInput[i]);
			}
			out.flush();

			//receber saldo das contas consultadas
			saldo = in.readDouble();			

		} catch(IOException e){
			e.printStackTrace();
		}

		return saldo;
	}

	@Override
	public void levantar(int id, double valor) throws SaldoInsuficiente,
	ContaInvalida {
		try{
			//enviar id da operação, id da conta e valor a levantar
			out.writeObject(OpsBanco.LEVANTAR);
			out.writeInt(id);
			out.writeDouble(valor);
			out.flush();

			//receber estado da operação 
			int status = in.readInt();

			if(status == OpsBanco.CONTA_INVALIDA.ordinal())
				throw new ContaInvalida(id);
			else if(status == OpsBanco.SALDO_INSUFICIENTE.ordinal())
				throw new SaldoInsuficiente(id);

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void depositar(int id, double valor) throws ContaInvalida {
		try{
			//enviar id da operação, id da conta e valor a depositar
			out.writeObject(OpsBanco.DEPOSITAR);
			out.writeInt(id);
			out.writeDouble(valor);
			out.flush();

			int status = in.readInt();

			//receber estado da operação e saldo da conta fechada
			if(status == OpsBanco.CONTA_INVALIDA.ordinal())
				throw new ContaInvalida(id);

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void transferir(int conta_origem, int conta_destino, double valor)
			throws ContaInvalida, SaldoInsuficiente {

		try{
			//enviar id da operação, id da conta origem e destino e valor a levantar
			out.writeObject(OpsBanco.TRANSFERIR);
			out.writeInt(conta_origem);
			out.writeInt(conta_destino);
			out.writeDouble(valor);
			out.flush();

			//receber estado da operação 
			int status = in.readInt();

			if(status == OpsBanco.CONTA_INVALIDA.ordinal()){
				throw new ContaInvalida();
			}
			else if(status == OpsBanco.SALDO_INSUFICIENTE.ordinal())
				throw new SaldoInsuficiente(conta_origem);

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void close(){
		try{
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client c1 = new Client("127.0.0.1",12345);
		Client c2 = new Client("127.0.0.1",12345);

		c1.criarConta(10);
		c2.criarConta(20);

		try{
			System.out.println("C1: depositar(0, 100)");
			c1.depositar(0, 100);
		} catch(Exception e){System.out.println("ERRO: "+e.getMessage());}
		try{
			System.out.println("C2: transferir(0, 1, 150)");
			c2.transferir(0, 1, 150);
		} catch(Exception e){System.out.println("ERRO: "+e.getMessage());}
		try{
			System.out.println("C2: transferir(0, 1, 50)");
			c2.transferir(0, 1, 50);
		} catch(Exception e){System.out.println("ERRO: "+e.getMessage());}
		try{
			System.out.println("C1: levantar(2, 30)");
			c1.levantar(1, 30);
		} catch(Exception e){System.out.println("ERRO: "+e.getMessage());}
		try{
			double saldo = c2.consultarTotal(new int[]{0,1});
			System.out.println("C2: consultarTotal(new int[]{0,1}) = "+saldo);
		} catch(Exception e){System.out.println("ERRO: "+e.getMessage());}
		try{
			System.out.println("C1: fecharConta(3)");
			c1.fecharConta(3);
		} catch(Exception e){System.out.println("ERRO: "+e.getMessage());}
		try{
			double saldo = c1.fecharConta(0);
			System.out.println("C1: fecharConta(0) = "+saldo);
		} catch(Exception e){System.out.println("ERRO: "+e.getMessage());}
		
		System.out.println("Terminar clientes.");
		c1.close();
		c2.close();

	}

}
