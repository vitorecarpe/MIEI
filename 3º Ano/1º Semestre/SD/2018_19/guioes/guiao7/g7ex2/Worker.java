import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.io.IOException;

public class Worker implements Runnable{
    private Socket clientSocket;
    private int id;
    private HashMap<Integer,Socket> clientes;

    public Worker(Socket clientSocket, int id, HashMap<Integer,Socket> clientes){
        this.clientSocket = clientSocket;
        this.id = id;
        this.clientes = clientes;
    }

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out;
            // PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            
            // falta uma parte em que recebe uma primeira mensagem com nickname
            // para substituir o ID
            System.out.println("Worker"+id+"> Processando conexao");
            String msg;
            while ((msg = in.readLine()) != null && !msg.equals("exit")) {
                System.out.println(" Cliente"+id+"> " + msg);

                for( int key : this.clientes.keySet()){
                    if( !(this.id==key) ){
                        out = new PrintWriter(this.clientes.get(key).getOutputStream());
                        out.println(" Cliente"+id+"> "+msg);
                        out.flush();
                    }
                }
                
            }
            
            // out.close();
            in.close();
            clientSocket.close();
            this.clientes.remove(id);
            System.out.println("Worker"+id+"> Terminando conexao");
        }
        catch(IOException e){}
    }
}