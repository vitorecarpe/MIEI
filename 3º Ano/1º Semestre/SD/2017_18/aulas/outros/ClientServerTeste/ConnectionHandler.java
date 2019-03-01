package ClientServerTeste;

import java.util.*;
import java.net.Socket;
import java.net.SocketException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ConnectionHandler implements Runnable{
    private final TreeMap<String,Socket> listaligacoes;
    private final Socket mySocket;
    private String username = null;

    public ConnectionHandler(TreeMap<String,Socket> connectionList, Socket s){
        this.listaligacoes = connectionList;
        this.mySocket = s;

    }



    public void run() {
        try{
            DataInputStream entrada = new DataInputStream(mySocket.getInputStream());
            DataOutputStream saida = new DataOutputStream(mySocket.getOutputStream());


            while(this.username==null){
                saida.writeUTF("Insira Username");
                String user = entrada.readUTF();
                boolean nickEmUso = false;

                synchronized (listaligacoes){
                    Set listaNicks = listaligacoes.keySet();
                    Iterator<String> it = listaNicks.iterator();
                    while(it.hasNext()&&!nickEmUso){
                        if(it.next().equals(user)){
                            nickEmUso = true;
                        }
                    }
                    if(nickEmUso){
                        saida.writeUTF("O Username "+user+" já está em uso!");
                    }
                    else{
                        this.username=user;
                        this.listaligacoes.put(username,mySocket);
                        broadcast("O Utilizador "+username+" acabou de se ligar!!!");
                    }
                }

            }
            while(true){
                String lido = entrada.readUTF();
                broadcast(lido);
                }

        }catch(SocketException e){
            System.err.println(e.getMessage());
            if(username != null){
                synchronized(listaligacoes){
                    listaligacoes.remove(username);
                }
                broadcast("> " + username + " disconnected.");
            }
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }



    private void broadcast(String msg){
        synchronized (listaligacoes){
            for(Socket t:listaligacoes.values()){
                try {
                    new DataOutputStream(t.getOutputStream()).writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        }
    }
}
