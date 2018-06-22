package ClientServerTeste;

import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;


public class Servidor {
    private static final int port = 50000;


    public static void main(String [] args) throws IOException, InterruptedException {

        final ServerSocket serverSocket = new ServerSocket();
        System.out.println("> server started !! A escutar porta "+port);
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(port));
        final TreeMap<String,Socket> listaclientes = new TreeMap<String,Socket>();


        while(true){
            Socket cliente = serverSocket.accept();
            Thread tratamentoLigacao = new Thread(new ConnectionHandler(listaclientes,cliente));
            tratamentoLigacao.start();


        }

        


    }


}
