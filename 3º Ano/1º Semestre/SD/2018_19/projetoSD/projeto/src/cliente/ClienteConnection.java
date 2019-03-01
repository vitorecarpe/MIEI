package cliente;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import servidor.Server;

/**
 *
 * @author KIKO
 */
public class ClienteConnection{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectInputStream fromServer;
    private User user;
    private HashMap<String,ArrayList<Server>> bdServers;

    public ClienteConnection(){
        this.user = null;
        this.bdServers = null;
    }

    // GETTERS //////////////////
    public User getUser(){
        return this.user;
    }
    public HashMap<String,ArrayList<Server>> getServers(){
        return this.bdServers;
    }
    /////////////////////////////

    // Connection managment /////
    public String connect(String email, String password){
        try {
            this.socket = new Socket("127.0.0.1", 1234);
            // prepare all the streams
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // autenticao
            String status = autenticacao(email, password);
            return status;
        }
        catch (IOException e) {
            System.out.println("[ClienteCon] Connect - RIP Socket !!!");
            System.out.println(e);
            return "FAIL";
        }
    }
    private String autenticacao(String email, String password){
        try{
            this.out.println("LOGIN " + email + " " + password);
            this.out.flush();
            String login = this.in.readLine();
            System.out.println("[ClienteCon] Connect> "+login);
            if( login.equals("SUCCESS") ){
                System.out.println("[ClienteCon] Login OK");
                return "SUCCESS";
            }
            else{
                System.out.println("[ClienteCon] Email/Password incorretos!!!");
                this.socket.close();
                return "FAIL";
            }
        }
        catch (IOException e) {
            System.out.println("[ClienteCon] Autenticação - RIP in.readLine() !!!");
            System.out.println(e);
            return "FAIL";
        }
    }
    public void closeConnection(){
        try {
            this.in.close();
            this.out.close();
            this.fromServer.close();
            this.socket.close();
            System.out.println("[ClienteCon] Ligação terminada com sucesso!!!");
        } catch (IOException e) {
            System.out.println("[ClienteCon] CloseConnection RIP!!!");
            System.out.println(e);
        }
    }
    /////////////////////////////


    /**
     * Envia um pedido ao Servidor e recebe o SUCESSO/FAIL do pedido
     * @param request String with the request for the server
     * @return String with the result of the request
     */
    public String sendRequest(String request){
        try{
            System.out.println("[ClienteCon] Request> "+request);
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out.println(request);
            this.out.flush();
            String response = this.in.readLine();
            System.out.println("[ClienteCon] Response> "+response);
            return response;
        }
        catch(IOException e){
            System.out.println("[ClienteCon] SendRequest> Request RIP !!!");
            System.out.println(e);
            return "FAIL";
        }
    }

    // Recebe Object User e HashMap de Servers do Servidor
    public void receiveUserAndServers(){
        try{
            fromServer = new ObjectInputStream(socket.getInputStream());
            try{
                ArrayList<Object> list = (ArrayList<Object>)fromServer.readObject();
                this.user = (User)list.get(0);
                this.bdServers = (HashMap<String,ArrayList<Server>>)list.get(1);

                System.out.println("[ClienteCon] ReceiveUser> \n"+user.toStringUser());
                System.out.print("[ClienteCon] ReceiveServers> \n");
                for(String key : this.bdServers.keySet())
                    for(Server server : this.bdServers.get(key))
                        System.out.print(server.toStringServer());
            }
            catch (ClassNotFoundException e){
                System.out.println("[ClienteCon] User or HashMap(...) class missing...");
                System.out.println(e);
            }
            catch (IOException e) {
                System.out.println("[ClienteCon] Receive> User & Servers RIP !!!");
                System.out.println(e);
            }
        }
        catch (IOException e) {
            System.out.println("[ClienteCon] Receive> ObjectInputStream RIP !!!");
            System.out.println(e);
        }
    }

}