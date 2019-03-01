package ChatChaps;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class Server {

    private final static int port = 9876;

    public static void         main(String[] args) throws Exception{
        final ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
        //serverSocket.bind(new InetSocketAddress(port));

        final TreeMap<String, Socket> connectionList = new TreeMap<String, Socket>();

        System.out.println("> server started. listening on port " + port + ".");

        while(true){
            Socket newSocket = serverSocket.accept();
            new Thread(new ConnectionHandler(newSocket, connectionList)).start();
        }

    }

}


class ConnectionHandler implements Runnable{
    private final TreeMap<String,Socket> connectionList;
    private final Socket mySocket;
    private String myUserNick = null;

    public ConnectionHandler(Socket mySocket, TreeMap<String, Socket> connectionList) {
        this.connectionList = connectionList;
        this.mySocket = mySocket;
    }

    public void run() {
        try{
            DataInputStream fromUser = new DataInputStream(mySocket.getInputStream());
            DataOutputStream toUser = new DataOutputStream(mySocket.getOutputStream());

            while(myUserNick == null){
                toUser.writeUTF("> select nickname: ");
                String nickname = fromUser.readUTF();

                boolean nicknameInUse = false;

                synchronized(connectionList){
                    Set nickList = connectionList.keySet();
                    Iterator<String> it = nickList.iterator();
                    while(it.hasNext() && !nicknameInUse){
                        if(it.next().equals(nickname)){
                            nicknameInUse = true;
                        }
                    }
                }

                if(nicknameInUse){
                    toUser.writeUTF("> nickname '" + nickname + "' already in use.");
                }
                else{
                    myUserNick = nickname;
                    synchronized(connectionList){
                        connectionList.put(myUserNick, mySocket);
                    }
                    broadcastMessage("> " + myUserNick + " connected.");
                }
            }

            while(true){
                String str = fromUser.readUTF();
                broadcastMessage("> " + myUserNick + ": " + str);
            }

        }
        catch(SocketException e){
            System.err.println(e.getMessage());
            if(myUserNick != null){
                synchronized(connectionList){
                    connectionList.remove(myUserNick);
                }
                broadcastMessage("> " + myUserNick + " disconnected.");
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void broadcastMessage(String msg){
        synchronized(connectionList){
            connectionList.put(myUserNick, mySocket);
            for(Socket s : connectionList.values()){
                try{
                    new DataOutputStream(s.getOutputStream()).writeUTF(msg);
                }
                catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }
    }

}

