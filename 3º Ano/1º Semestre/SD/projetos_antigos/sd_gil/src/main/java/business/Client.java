package business;

import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Client class
 */
public class Client extends Observable {

    private Socket mainServerSocket;
    private Socket gameServerSocket;
    private BufferedWriter outMainServer;
    private BufferedWriter outGameServer;

    //player info
    private String username;
    private int rank;
    private int win;
    private int lost;
    private HashMap<String,Integer> mostUsedHeroes;

    //top players
    private HashMap<String, ArrayList> tops;

    //control variables
    private boolean authenticated;
    private boolean inQueue;
    private boolean successfullyRegistered;
    private boolean gameStarting;
    private boolean botIgnoreUpdade;

    //game info
    private String currentHero;
    private String currentTeam;
    private HashMap<String, String> heroesFriendly;
    private HashMap<String, String> heroesEnemy;
    private String gameOutput;
    private String chatMessage;


    /**
     * Client constructor
     * @param host Main server host
     * @param port Main server port
     * @throws IOException
     */
    public Client(String host, int port) throws IOException{
        mainServerSocket = new Socket(host, port);
        outMainServer = new BufferedWriter(new OutputStreamWriter(mainServerSocket.getOutputStream()));
        currentHero = "";
        rank = 0;
        win = 0;
        lost = 0;
        mostUsedHeroes = new HashMap<>();
        tops = new HashMap<>();
        heroesFriendly = new HashMap<>();
        heroesEnemy = new HashMap<>();
        gameOutput = "";
        inQueue = false;
        authenticated = false;
        gameStarting = false;
        botIgnoreUpdade = false;
        chatMessage = "";
        new ClientReaderMainServer();
    }

    /**
     * Sends a message to the main server (mainServerSocket)
     * @param message Message to send
     */
    public void sendMessageToMainServer(String message){
        try {
            System.out.println("Sending message: " + message);
            outMainServer.write(message);
            outMainServer.newLine();
            outMainServer.flush();
            if (message.equals("Exit"))
                System.exit(0);
        }
        catch (IOException e){
            System.err.println("Error on the client side (sending message to main server): " + e.getMessage());
        }
    }

    /**
     * Connects to game server
     * @param host Game server host
     * @param port Game server port
     */
    public void connectToGameServer(String host, int port) {
        try {
            gameServerSocket = new Socket(host, port);
            outGameServer = new BufferedWriter(new OutputStreamWriter(gameServerSocket.getOutputStream()));
            sendMessageToGameServer(username);
        }
        catch (IOException e) {
            System.err.println("Error on the client side (connecting to game server): " + e.getMessage());
        }
    }

    /**
     * Disconnects from the game server
     */
    public void disconnectFromGameServer(){
        try {
            outGameServer.close();
            gameServerSocket.close();
        }
        catch (IOException e) {
            System.err.println("Error on the client side (disconnecting from main server): " + e.getMessage());
        }
    }

    /**
     * Send message to game server
     * @param message Message to send
     */
    public void sendMessageToGameServer(String message){
        try {
            if (gameServerSocket.isConnected() && gameOutput.equals("")) {
                System.out.println("sending message: " + message);
                outGameServer.write(message);
                outGameServer.newLine();
                outGameServer.flush();
                if (message.equals("Exit") || message.equals("Close"))
                    disconnectFromGameServer();
            }
            else if (message.equals("Exit")) {
                System.exit(0);
            }
            else if (message.equals("Close")) {
                gameStarting = false;
            }
        }
        catch (IOException e){
            System.err.println("Error on the client side (sending message to game server): " + e.getMessage());
        }
    }

    /**
     * Username setter
     * @param username New Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Username getter
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Finds out if the user is authenticated
     * @return True if user is authenticated, False if not
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Finds out if user is in the queue
     * @return True if user is in queue, False if not
     */
    public boolean isInQueue() {
        return inQueue;
    }

    /**
     * CurrentHero getter
     * @return Current hero
     */
    public String getCurrentHero() {
        return currentHero;
    }

    /**
     * Find out if user registered an account
     * @return True if user registered an account, False otherwise
     */
    public boolean isSuccessfullyRegistered() {
        return successfullyRegistered;
    }

    /**
     * Number of wins getter
     * @return Number of wins
     */
    public int getWin() {
        return win;
    }

    /**
     * Number of losses getter
     * @return Number of losses
     */
    public int getLost() {
        return lost;
    }

    /**
     * Rank getter
     * @return Rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Most used heroes getter
     * @return Most used heroes
     */
    public HashMap<String, Integer> getMostUsedHeroes() {
        return mostUsedHeroes;
    }

    /**
     * Top players getter
     * @return Top players
     */
    public HashMap<String, ArrayList> getTops() {
        return tops;
    }

    /**
     * Finds out if game is starting
     * @return True if game is starting, False if not
     */
    public boolean isGameStarting() {
        return gameStarting;
    }

    /**
     * Game starting setter
     * @param gameStarting New value
     */
    public void setGameStarting(boolean gameStarting) {
        this.gameStarting = gameStarting;
    }

    /**
     * Heroes from the friendly team getter
     * @return Friendly team heroes
     */
    public HashMap<String, String> getHeroesFriendly(){
        return heroesFriendly;
    }

    /**
     * Heroes from the enenmy team getter
     * @return Enemy team heroes
     */
    public HashMap<String, String> getHeroesEnemy(){
        return heroesEnemy;
    }

    /**
     * Game output getter
     * @return Game output
     */
    public String getGameOutput(){
        return gameOutput;
    }

    /**
     * Game output setter
     * @param gameOutput New value
     */
    public void setGameOutput(String gameOutput) {
        this.gameOutput = gameOutput;
    }

    /**
     * Current team getter
     * @return Current team
     */
    public String getCurrentTeam(){
        return currentTeam;
    }

    /**
     * Tell if bot can ignore the current update
     * @return Bot ignore update
     */
    public boolean botIgnoreUpdade() {
        return botIgnoreUpdade;
    }

    /**
     * Chat Message getter
     * @return Chat Message
     */
    public String getChatMessage() {
        return chatMessage;
    }

    /**
     * Parses player info (statistics)
     * @param stats Info
     */
    public void parseInfo(String[] stats){
        mostUsedHeroes.clear();
        for(int i=5; i < stats.length; i+=2){
            if (stats[i].equals("Rank"))
                rank = Integer.parseInt(stats[i+1]);

            else if (stats[i].equals("Win"))
                win = Integer.parseInt(stats[i+1]);

            else if (stats[i].equals("Lost"))
                lost = Integer.parseInt(stats[i+1]);

            else if (stats[i].equals("Hero-TimesPlayed")){
                int n = 0;
                for(i+=1;n<60;i+=2,n+=2)
                    mostUsedHeroes.put(stats[i], Integer.parseInt(stats[i+1]));
            }
        }
    }

    /**
     * Parses top players info
     * @param top Info
     */
    public void parseTops(String[] top){
        tops.clear();
        for (int i=1; i<top.length; i+=4) {
            String username = top[i];
            int timesPlayed = Integer.parseInt(top[i+1]);
            double ratio = Double.parseDouble(top[i+2]);
            int numberMVP = Integer.parseInt(top[i+3]);

            ArrayList info = new ArrayList();
            info.add(timesPlayed);
            info.add(ratio);
            info.add(numberMVP);

            tops.put(username, info);
        }
    }

    /**
     * Parses players heroes
     * @param playersHeroes Info
     */
    public void parsePlayersHeroes(String[] playersHeroes){
        for (int i=1; i<playersHeroes.length; i+=2){
            String username = playersHeroes[i];
            String hero = playersHeroes[i+1];
            if (heroesFriendly.containsKey(username))
                heroesFriendly.put(username, hero);
            else heroesEnemy.put(username, hero);

        }
    }

    /**
     * Tells observers to update
     */
    public void setUpdated(){
        this.setChanged();
        this.notifyObservers();
    }

    ///////////////////
    // Reader Thread //
    ///////////////////

    /**
     * Thread that will handle all messages directed to this client
     */
    public class ClientReaderMainServer implements Runnable{

        private BufferedReader in;

        /**
         * Start thread
         */
        public ClientReaderMainServer(){
            try {
                in = new BufferedReader(new InputStreamReader(mainServerSocket.getInputStream()));
                (new Thread(this)).start();
            }
            catch (IOException e) {
                System.err.println("Error on the client side (creating reader thread): " + e.getMessage());
            }
        }

        /**
         * ClientReaderMainServer Run
         */
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message received: " + message);

                    //Wrong data
                    if (message.equals("Wrong username/password")){
                        authenticated = false;
                        setUpdated();
                    }

                    //user already exists
                    if (message.equals("User already registered")){
                        successfullyRegistered = false;
                        setUpdated();
                    }

                    //registered
                    if (message.equals("Successfully registered")){
                        successfullyRegistered = true;
                        setUpdated();
                    }

                    //logged in
                    else if (message.equals("Authenticated")){
                        successfullyRegistered = true;
                        authenticated = true;
                        setUpdated();
                    }

                    //added to the queue
                    else if (message.equals("Added to queue"))
                        inQueue = true;

                    //player info
                    else if (message.split(" ")[0].equals("Info")) {
                        parseInfo(message.split(" "));
                        setUpdated();
                    }

                    //tops
                    else if (message.split(" ")[0].equals("Tops")){
                        parseTops(message.split(" "));
                        setUpdated();
                    }

                    //game is about to start
                    else if (message.split(" ")[0].equals("Game_Starting")){
                        heroesFriendly.clear();
                        heroesEnemy.clear();
                        String gameServerHost = message.split(" ")[1];
                        int gameServerPort = Integer.parseInt(message.split(" ")[2]);
                        gameOutput = "";
                        connectToGameServer(gameServerHost, gameServerPort);
                        gameStarting = true;
                        botIgnoreUpdade = false;
                        setUpdated();
                    }

                    //team
                    else if (message.split(" ")[0].equals("Team"))
                        currentTeam = message.split(" ")[1];

                    //others players team
                    else if (message.split(" ")[0].equals("Player")){
                        String name = message.split(" ")[1];
                        String playerTeam = message.split(" ")[2];
                        if (playerTeam.equals(currentTeam))
                            heroesFriendly.put(name, "");
                        else heroesEnemy.put(name, "");
                        botIgnoreUpdade = true;
                        setUpdated();
                    }

                    //hero selected with success
                    else if (message.split(" ")[0].equals("Selected")){
                        currentHero = message.split(" ")[1];
                        botIgnoreUpdade = false;
                        setUpdated();
                    }

                    //hero already chosen
                    else if (message.equals("Hero already used")){
                        botIgnoreUpdade = false;
                        setUpdated();
                    }

                    //teammates (or everyone at the end of the round) heroes
                    else if (message.split(" ")[0].equals("Players_Heroes")){
                        parsePlayersHeroes(message.split(" "));
                        botIgnoreUpdade = true;
                        setUpdated();
                    }

                    //chat message
                    else if (message.split("::")[0].equals("Chat")){
                        chatMessage += message.split("::")[1] + "\n";
                        botIgnoreUpdade = true;
                        setUpdated();
                    }

                    //player existed the game
                    else if (message.split(" ")[0].equals("Remove")){
                        String user = message.split(" ")[1];
                        heroesFriendly.remove(user);
                        heroesEnemy.remove(user);
                        botIgnoreUpdade = true;
                        setUpdated();
                    }

                    //game result
                    else if (message.split(":")[0].equals("Output")) {
                        gameOutput = message.split(":")[1].replace('#', '\n');;
                        botIgnoreUpdade = false;
                        chatMessage = "";
                        setUpdated();
                        disconnectFromGameServer();
                        currentHero = "";
                    }
                }
            }
            catch (IOException e){
                System.err.println("Error on the client side (reading message main server): " + e.getMessage());
            }
            finally {
                try {
                    in.close();
                    mainServerSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}