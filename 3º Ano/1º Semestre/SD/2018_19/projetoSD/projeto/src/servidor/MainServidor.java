package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author KIKO
 */
public class MainServidor extends Thread{
    public static void main(String[] args){
        try{
            BaseDados bd = new BaseDados();
            MainServidor servidor = new MainServidor(bd);
            servidor.start();
        }
        catch(IOException e){
            System.out.println("[Servidor] MAIN ERROR !!!");
        }
    }
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final int PORT = 1234;
    private final ServerSocket serverSocket;
    private Socket clienteSocket;
    private final BaseDados bd;

    public MainServidor() throws IOException {
        this.serverSocket = new ServerSocket(this.PORT);
        this.bd = new BaseDados();
    }

    public MainServidor(BaseDados bd) throws IOException {
        this.serverSocket = new ServerSocket(this.PORT);
        this.bd = bd;
    }

    @Override
    public void run() {
        System.out.println("[Servidor] Iniciando o Servidor");
        while(this.running.get()){
            // prints com estado da lista de Users e Servers
            System.out.println(this.bd.toStringUsers());
            System.out.println(this.bd.toStringServidores());

            System.out.println("[Servidor] Servidor à escuta na porta " +
                                this.serverSocket.getLocalSocketAddress());
            try{
                // fica a escuta de pedidos para aceitar
                this.clienteSocket = this.serverSocket.accept();
            }
            catch(IOException e){
                System.out.println("[Servidor] Erro a aceitar ligação do cliente");
                System.out.println(e);
            }
            try{
                // cria uma nova thread (worker) para a conexao
                // worker trata da autenticacao
                new MainServidorWorker(clienteSocket,bd).start();
            }
            catch(IOException e){
                System.out.println("[Servidor] Erro a criar Thread para o cliente");
                System.out.println(e);
            }

        }
    }

    // Interrompe o MainServidor
    public void stopServidor(){
        this.running.set(false);
        this.interrupt();
        System.out.println("[Servidor] Servidor terminado !!!");
    }

}
