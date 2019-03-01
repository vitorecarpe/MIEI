package aula7ex1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerWorker implements Runnable {

	private Socket socket;
	private Banco b;

	public ServerWorker (Socket socket, Banco bank){
		this.socket = socket;
		this.b = bank;
	}

	@Override
	public void run() {

		try{
			//criar canais de leitura/escrita no socket
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());


			OpsBanco op;
			while(true){

				//variáveis para ler operações enviadas pelo cliente
				double saldo = 0;
				int id = 0;
				double valor = 0;
				op = (OpsBanco) in.readObject();

				try{
					System.out.print("\nWorker-"+Thread.currentThread().getId()+" > Received operation from client: ");
					switch(op){
					case CRIAR_CONTA:
						System.out.println(OpsBanco.CRIAR_CONTA);

						//ler saldo inicial e responder com id da nova conta
						saldo = in.readDouble();
						id = b.criarConta(saldo);
						out.writeInt(id);
						out.flush();
						break;

					case FECHAR_CONTA:
						System.out.println(OpsBanco.FECHAR_CONTA);

						//ler id da conta a fechar e responder com saldo final
						id = in.readInt();
						saldo = b.fecharConta(id);
						out.writeInt(OpsBanco.OK.ordinal());
						out.writeDouble(saldo);
						out.flush();
						break;

					case CONSULTAR:
						System.out.println(OpsBanco.CONSULTAR);

						//ler id da conta a consultar e responder com saldo
						id = in.readInt();
						saldo = b.consultar(id);
						out.writeInt(OpsBanco.OK.ordinal());
						out.writeDouble(saldo);
						out.flush();
						break;

					case CONSULTAR_TOTAL:
						System.out.println(OpsBanco.CONSULTAR_TOTAL);

						//ler número de contas a consultar e respectivos ids 
						//responder com saldo total
						int numContas = in.readInt();
						int[] contasInput = new int[numContas];
						for(int i=0; i<numContas; i++){
							contasInput[i] = in.readInt();
						}
						saldo = b.consultarTotal(contasInput);
						out.writeDouble(saldo);
						out.flush();
						break;

					case LEVANTAR:
						System.out.println(OpsBanco.LEVANTAR);

						//ler id da conta e valor a levantar
						id = in.readInt();
						valor = in.readDouble();
						b.levantar(id, valor);
						out.writeInt(OpsBanco.OK.ordinal());
						out.flush();
						break;

					case DEPOSITAR:
						System.out.println(OpsBanco.DEPOSITAR);

						//ler id da conta e valor a depositar
						id = in.readInt();
						valor = in.readDouble();
						b.depositar(id, valor);
						out.writeInt(OpsBanco.OK.ordinal());
						out.flush();
						break;

					case TRANSFERIR:
						System.out.println(OpsBanco.TRANSFERIR);

						//ler id da operação, id da conta origem e destino e valor a levantar
						id = in.readInt();
						int idDest = in.readInt();
						valor = in.readDouble();
						b.transferir(id, idDest, valor);
						out.writeInt(OpsBanco.OK.ordinal());
						out.flush();
						break;

					default:
						System.out.println("\nWorker-"+Thread.currentThread().getId()+" > Unknown operation "+op);
						out.writeInt(OpsBanco.OP_INVALIDA.ordinal());
						out.flush();				
					}
				}
				catch(ContaInvalida ci){
					out.writeInt(OpsBanco.CONTA_INVALIDA.ordinal());
					out.flush();
				}
				catch(SaldoInsuficiente si){
					out.writeInt(OpsBanco.SALDO_INSUFICIENTE.ordinal());
					out.flush();
				}
			}
		} catch (IOException ioe) {
			try{
				//fechar sockets
				System.out.println("\nWorker-"+Thread.currentThread().getId()+" > Client disconnected. Connection is closed.\n");
				socket.shutdownOutput();
				socket.shutdownInput();
				socket.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		} catch (ClassNotFoundException ce){
			ce.printStackTrace();
		}
	}
}
