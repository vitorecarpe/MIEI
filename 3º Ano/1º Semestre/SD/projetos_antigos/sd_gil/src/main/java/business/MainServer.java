package business;

import data.Data;

import static java.lang.Math.abs;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Main server class
 */
public class MainServer implements Serializable{

    private HashMap<String, Player> players;
    private HashMap<Integer,ArrayList<Player>> queue;
    private HashMap<String, Vector<String>> buffers;
    private HashMap<String, ReentrantLock> locks;
    private HashMap<String, Condition> writeConditions;
    private HashMap<Integer, Boolean> ports;

    /**
     * Constructor
     */
    public MainServer(){
        players = new HashMap<>();
        
        queue = new HashMap<>();
        for (int i = 0; i < 10; i++)
            queue.put(i,new ArrayList<>());
        
        buffers = new HashMap<>();
        locks = new HashMap<>();
        writeConditions = new HashMap<>();

        ports = new HashMap<>();
        for (int i=12346; i<22346; i++)
            ports.put(i, true);
    }

    /**
     * Starts the server
     */
    public void start(){
        try {
            System.out.println("### Starting Server ###");
            System.out.println("Number of players registered: " + players.values().size());
            ServerSocket serverSocket = new ServerSocket(12345);

            //Receives connections
            while (true){
                new MainServerWorker(serverSocket.accept());
                System.out.println("Connection established");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks login
     * @param username Client username
     * @param password Client password
     * @return True if username and password match (and player is not logged on), False if not
     */
    public synchronized boolean check_login(String username, String password) {
        if (players.containsKey(username)) {
            Player p = players.get(username);
            if (p.getPassword().equals(password) && !p.isLoggedOn()){
                p.setLoggedOn(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Register a user
     * @param username  Username
     * @param email     Email
     * @param password  Password
     * @return True if user was successfully registered, False if not
     */
    public synchronized boolean register(String username, String email, String password) {
        if (!players.containsKey(username) && !players.values().stream().anyMatch(p -> p.getEmail().equals(email))) {
            Player p = new Player(username, email, password);
            players.put(p.getUsername(), p);
            return true;
        } else return false;
    }

    /**
     * Logs out a user
     * @param username Username
     */
    public synchronized void logout(String username){
        if (!username.equals("<<not_logged_in>>")) {
            System.out.println("logging out " + username);
            removeFromQueue(username);
            buffers.remove(username);
            locks.remove(username);
            writeConditions.remove(username);
            players.get(username).setLoggedOn(false);
        }
    }

    /**
     * Gets top n players info
     * @return Top n info
     */
    public String topN (int n){
        TreeSet<Player> top = new TreeSet<>(new RankComparator());
        String info = "";

        top.addAll(players.values());

        int size = top.size();
        for (int i=0; i<n && i < size; i++){
            Player p = top.pollFirst();
            info += p.getUsername() + " " + p.getPlays() + " " + p.getRatio() + " " + p.getNumberMVP() + " ";
        }

        return info;
    }

    /**
     * Removes player from queue
     * @param username Username
     */
    public synchronized void removeFromQueue(String username) {
        Player p = players.get(username);
        int rank = p.getRank();
        if (p != null)
            queue.get(rank).removeIf(player -> player.equals(p));
    }

    /**
     * Adds player to queue
     * @param username Username
     */
    public synchronized void addToQueue(String username) {
        Player p = players.get(username);
        int rank = p.getRank();
        
        queue.get(rank).add(p);
    }

    /**
     * Gets an unused port
     * @return Port
     */
    public synchronized int getUnusedPort(){
        return ports.entrySet().stream()
                               .filter(p -> p.getValue() == true)
                               .findFirst()
                               .get()
                               .getKey();
    }
  
    /**
     * Tries to find a game
     * @return Map of players that will play the game
     */
    public synchronized HashMap<String, Player> findGame(String username) {
        HashMap<String, Player> pls = new HashMap<>();
        Player current_player = players.get(username);
        Integer current_rank = current_player.getRank();
        int players_curr_rank =  queue.get(current_rank).size();
        boolean foundGame = false;
        
        // Exists 9 players of the same rank
        if(players_curr_rank >= 9) {
            queue.get(current_rank).stream()
                                   .limit(9)
                                   .forEach(p -> pls.put(p.getUsername(), p));
            foundGame = true;
        }

        //Finds out if exists players in the rank above (1) or the rank below (-1)
        else {
            int r = 0;

            // Exists in the rank above
            if (queue.get(current_rank + 1) != null && queue.get(current_rank + 1).size() + players_curr_rank >= 9)
                r = 1;

            // Exists in the rank below
            else if (queue.get(current_rank - 1) != null && queue.get(current_rank - 1).size() + players_curr_rank >= 9)
                r = -1;

            //found game
            if (r == 1 || r == -1) {

                int missing = 9 - players_curr_rank;

                // Add players from the same rank
                queue.get(current_rank).stream()
                                       .limit(players_curr_rank)
                                       .forEach(p -> pls.put(p.getUsername(), p));

                // Add players from the rank below/above
                queue.get(current_rank + r).stream()
                                           .limit(missing)
                                           .forEach(p -> pls.put(p.getUsername(), p));
                foundGame = true;
            }
        }

        if (foundGame) {
            for (Player p : pls.values())
                removeFromQueue(p.getUsername());
            pls.put(username, current_player);
            return pls;
        }
        else {
            addToQueue(username);
            return null;
        }
    }

    /**
     * Divides the players into two teams
     * @param p
     */
    public synchronized void makeTeams(ArrayList<Player> p) {
        for (int i = 0; i < 10; i++) {
            Player pl = p.get(i);
            pl.setCurrentHero("<none>");
            if((i % 2)==0) // index even
                pl.setCurrentTeam("A");
            else // odd
                pl.setCurrentTeam("B");
        }
    }

    /**
     * Starts a game
     * @param p Map of players that will play the game
     */
    public synchronized void playGame(HashMap<String, Player> p) {
        makeTeams(p.values().stream().collect(Collectors.toCollection(ArrayList::new)));

        int port = getUnusedPort();
        ports.put(port, false);

        System.out.println("Starting a game");

        new GameServer(port, p, locks, buffers, writeConditions, ports);
    }

    /**
     * Clears last session data (because save game saves the entire object)
     */
    public void clearLastSessionData(){
        queue.clear();
        buffers.clear();
        locks.clear();
        writeConditions.clear();
        ports.clear();

        for (int i = 0; i < 10; i++)
            queue.put(i,new ArrayList<>());

        for (int i=12346; i<22346; i++)
            ports.put(i, true);

        for (Player p: players.values())
            p.setLoggedOn(false);
    }

    /**
     * Saves server data
     */
    public synchronized void saveData(){
        try {
            Data.save(this, "data.dat");
        }
        catch (IOException e) {
            System.err.println("Couldn't save data: " + e.getMessage());
        }
    }

    ////////////////////////
    // Main Server Worker //
    ////////////////////////

    /**
     * Class that implements two threads that will be created for every client
     */
    public class MainServerWorker {

        private Socket socket;
        private String client_username;
        private Vector<String> localBuffer;
        private ReentrantLock localLock;
        private ReentrantLock lock;
        private Condition login_attempt;

        /**
         * Constructor
         * @param socket Socket
         */
        public MainServerWorker(Socket socket){
            this.socket = socket;
            client_username = "<<not_logged_in>>";
            localBuffer = new Vector<>();
            localLock = new ReentrantLock();
            login_attempt = localLock.newCondition();
            new MainServerWorkerReader();
            new MainServerWorkerWriter();
        }

        /**
         * Inits the client lock, write condition and buffer
         */
        public void initLockConditionBuffer(){
            lock = new ReentrantLock();
            buffers.put(client_username, new Vector<>());
            locks.put(client_username, lock);
            writeConditions.put(client_username, lock.newCondition());
        }

        /**
         * Adds local message to buffer (local is when the client is in the login menu)
         * @param message Message
         */
        public void addLocalMessage(String message){
            localLock.lock();
            localBuffer.add(message);
            login_attempt.signal();
            localLock.unlock();
        }

        /**
         * Adds message to the buffer
         * @param message Message
         */
        public synchronized void addMessage(String message){
            lock.lock();
            buffers.get(client_username).add(message);
            writeConditions.get(client_username).signal();
            lock.unlock();
        }

        /**
         * Thread that will read client's messages
         */
        public class MainServerWorkerReader implements Runnable {

            private BufferedReader in;

            /**
             * Constructor
             */
            public MainServerWorkerReader() {
                (new Thread(this)).start();
            }

            /**
             * Run
             */
            @Override
            public void run() {
                try {
                    //create buffer
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    //read client messages
                    String message = "";
                    while (!message.equals("Exit") && (message = in.readLine()) != null) {
                        saveData();
                        System.out.println(client_username + ": " + message);

                        //Login
                        if (message.split(" ")[0].equals("Login")){
                            String username = message.split(" ")[1];
                            String password = message.split(" ")[2];
                            if (check_login(username, password)) {
                                client_username = username;
                                initLockConditionBuffer();
                                addLocalMessage("Authenticated");
                            }
                            else addLocalMessage("Wrong username/password");
                        }

                        //Register
                        else if (message.split(" ")[0].equals("Register")){
                            String username = message.split(" ")[1];
                            String email = message.split(" ")[2];
                            String password = message.split(" ")[3];
                            if (register(username, email, password))
                                addLocalMessage("Successfully registered");
                            else addLocalMessage("User already registered");
                        }

                        //Get statistics
                        else if (message.equals("Retrieve Info")){
                            addMessage("Info " + players.get(client_username).getInfo());
                            addMessage("Tops " + topN(100));
                        }

                        //Play/find game
                        else if (message.equals("Play")) {
                            HashMap<String, Player> willPlay = findGame(client_username);
                            if (willPlay !=  null)
                                playGame(willPlay);
                            else addMessage("Added to queue");
                        }

                        //Cancel find game
                        else if (message.equals("Cancel"))
                            removeFromQueue(client_username);

                        //Close/Exit
                        else if (message.equals("Close") || message.equals("Exit")) {
                            if (!client_username.equals("<<not_logged_in>>"))
                                addMessage(message);
                        }

                        else {
                            System.out.println("Couldn't parse message: " + message);
                        }
                    }
                }
                catch (Exception e){
                    System.err.println("Connection to " + client_username + " lost");
                }
                finally {
                    try {
                        in.close();
                        logout(client_username);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * Thread that will write messages to client
         */
        public class MainServerWorkerWriter implements Runnable {

            private BufferedWriter out;

            /**
             * Constructor
             */
            public MainServerWorkerWriter() {
                (new Thread(this)).start();
            }

            /**
             * Run
             */
            @Override
            public void run() {
                try {
                    //create buffer
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String message = "";

                    while (!message.equals("Exit")) {
                        //writes login attempts
                        localLock.lock();
                        while (!message.equals("Authenticated")) {
                            if (localBuffer.size() == 0) {
                                login_attempt.await();
                            }
                            message = localBuffer.firstElement();
                            out.write(message);
                            out.newLine();
                            out.flush();
                            localBuffer.remove(0);
                        }
                        localLock.unlock();

                        //send message to user
                        while (!message.equals("Close") && !message.equals("Exit")) {
                            lock.lock();
                            while (buffers.get(client_username).size() == 0)
                                writeConditions.get(client_username).await();

                            message = buffers.get(client_username).firstElement();
                            out.write(message);
                            out.newLine();
                            out.flush();
                            buffers.get(client_username).remove(0);
                            lock.unlock();
                        }

                        //client logged out
                        System.out.println(client_username + " logged out");
                        logout(client_username);
                        client_username = "<<not_logged_in>>";
                    }
                }
                catch (Exception e){
                    System.err.println("Error in writing thread from client "
                            + client_username + ": " + e.getMessage());
                }
                finally {
                    try {
                        out.close();
                        socket.close();
                    }
                    catch (Exception e) {
                    }
                }
            }
        }
    }
}