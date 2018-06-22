package business;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class GameServer implements Runnable{

    private ServerSocket ssocket;
    int port;
    private HashMap<String, Player> players;
    private HashMap<String, Vector<String>> buffers;
    private HashMap<String, ReentrantLock> locks;
    private HashMap<String, Condition> writeConditions;
    private HashMap<String, Boolean> heroesA;
    private HashMap<String, Boolean> heroesB;
    private HashMap<Integer, Boolean> ports;
    private ReentrantLock lock;
    private Condition userLeftGame;
    private boolean somebodyLeft;

    /**
     * Starts a server (that runs the "game")
     * @param port Server port
     */
    public GameServer(int port, HashMap<String, Player> players, HashMap<String, ReentrantLock> locks,
                        HashMap<String, Vector<String>> buffers, HashMap<String, Condition> writeConditions,
                            HashMap<Integer, Boolean> ports){
        try {
            ssocket = new ServerSocket(port);
            this.port = port;
            this.buffers = buffers;
            this.locks = locks;
            this.writeConditions = writeConditions;
            this.players = players;
            this.ports = ports;
            lock = new ReentrantLock();
            userLeftGame = lock.newCondition();
            somebodyLeft = false;
            heroesA = heroesMap();
            heroesB = heroesMap();

            (new Thread(this)).start();
        }
        catch (IOException e) {
            System.err.println("Error when opening new game server: " + e.getMessage());
        }
    }

    /**
     * Returns a new heroes map
     * @return Heroes map
     */
    public HashMap<String, Boolean> heroesMap(){
        HashMap<String, Boolean> heroes = new HashMap<>();
        heroes.put("birdo", false);
        heroes.put("bobomb", false);
        heroes.put("boo", false);
        heroes.put("bowser", false);
        heroes.put("cappy", false);
        heroes.put("daisy", false);
        heroes.put("donkeyKong", false);
        heroes.put("drMario", false);
        heroes.put("dryBones", false);
        heroes.put("goomba", false);
        heroes.put("hammerBro", false);
        heroes.put("iggyKoopa", false);
        heroes.put("kamek", false);
        heroes.put("lemmyKoopa", false);
        heroes.put("ludwigVonKoopa", false);
        heroes.put("luigi", false);
        heroes.put("mario", false);
        heroes.put("mortonKoopa", false);
        heroes.put("nabbit", false);
        heroes.put("peach", false);
        heroes.put("piranha", false);
        heroes.put("rosalina", false);
        heroes.put("royKoopa", false);
        heroes.put("toad", false);
        heroes.put("toadsworth", false);
        heroes.put("waluigi", false);
        heroes.put("wario", false);
        heroes.put("wendyKoopa", false);
        heroes.put("wiggler", false);
        heroes.put("yoshi", false);
        return heroes;
    }

    /**
     * Run
     */
    @Override
    public void run() {
        try {
            //Inform every player to open a new connection to this server
            writeToAll("Game_Starting " + "127.0.0.1 " + port);

            //Inform each player of his team
            writeToTeam("A", "Team A");
            writeToTeam("B", "Team B");

            //Send players username and team to every player
            for (Player p: players.values())
                writeToAll("Player " + p.getUsername() + " " + p.getCurrentTeam());

            //Connect all 10 clients (5 from each team)
            int nClients = 0;
            while (nClients < 10){
                new GameServerReader(ssocket.accept());
                nClients++;
            }

            //Wait 30 seconds, but wake up if somebody leaves
            lock.lock();
            userLeftGame.awaitNanos(30000000000L);
            lock.unlock();

            //Somebody left the game
            if (somebodyLeft) {
                writeToAll("Output:Game Over.#A player left the game.");
            }

            //Nobody left
            else{
                //There are players without heroes (game can't continue)
                if (numberPickedHeroes() < players.size())
                    writeToAll("Output:There are AFK players#Game can't start#Go back to the lobby and try again");

                //Everyone picked a hero
                else {
                    //Reveal all heroes picked by all players
                    writeToAll("Players_Heroes " + infoPlayersHeroes());

                    //Find winner
                    Random rand = new Random();
                    int mvp = rand.nextInt(10);
                    int teamAPoints = rand.nextInt(1000);
                    int teamBPoints = rand.nextInt(1000);
                    if (teamAPoints == teamBPoints)
                        teamAPoints += 1;
                    String winningTeam;
                    if (teamAPoints > teamBPoints)
                        winningTeam = "A";
                    else winningTeam = "B";

                    //Write output message
                    writeToAll("Output:Game over. Team " + winningTeam + " won the game." +
                            "#Team A points - " + teamAPoints +
                            "#Team B points - " + teamBPoints +
                            "#MVP - " + players.values().stream()
                                                        .collect(Collectors.toCollection(ArrayList::new))
                                                        .get(mvp).getUsername());

                    //Update players stats
                    for (Player p : players.values()) {
                        p.addPlayHero(p.getCurrentHero());
                        if (p.getCurrentTeam().equals(winningTeam))
                            p.addWin();
                        else p.addLost();
                    }
                    players.values().stream()
                            .collect(Collectors.toCollection(ArrayList::new))
                            .get(mvp).addMVP();
                }
            }

            //"Release" port number
            ports.put(port, true);

            //Close socket
            ssocket.close();
        }
        catch (Exception e) {
            System.err.println("Error on the game server side: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds the number of picked heroes (used in the end of the hero picker phase
     * to find out if all players picked a hero)
     * @return
     */
    public int numberPickedHeroes(){
        int teamA = (int) heroesA.values().stream().filter(b -> b).count();
        int teamB = (int) heroesB.values().stream().filter(b -> b).count();
        return teamA + teamB;
    }

    /**
     * Returns a hero that has not been picked, changing the map
     * @param player Player that requested an unused hero
     * @return
     */
    public synchronized String getUnusedHero(String player){
        //filter all unused heroes
        String team = players.get(player).getCurrentTeam();
        HashMap<String, Boolean> heroes;
        if (team.equals("A"))
            heroes = heroesA;
        else heroes = heroesB;
        ArrayList<String> unusedHeroes = heroes.entrySet().stream()
                                                          .filter(h -> !h.getValue())
                                                          .map(h -> h.getKey())
                                                          .collect(Collectors.toCollection(ArrayList::new));
        //find random hero
        Random rand = new Random();
        int randomIndex = rand.nextInt(unusedHeroes.size());
        String selectedHero = unusedHeroes.get(randomIndex);

        //release current hero and acquire new
        Player p = players.get(player);
        if (!p.getCurrentHero().equals("<none>"))
            heroes.put(players.get(player).getCurrentHero(), false);
        heroes.put(selectedHero, true);
        p.setCurrentHero(selectedHero);
        writeToTeam(team, infoPlayersHeroesTeam(team));
        return selectedHero;
    }

    /**
     * Tries to get a specific hero
     * @param player Player that requested the hero
     * @param hero   Hero
     * @return True if hero was available, False if not
     */
    public synchronized boolean getHero(String player, String hero){
        String team = players.get(player).getCurrentTeam();
        HashMap<String, Boolean> heroes;
        if (team.equals("A"))
            heroes = heroesA;
        else heroes = heroesB;
        if (!heroes.get(hero)){
            //release current hero and acquire new
            Player p = players.get(player);
            if (!p.getCurrentHero().equals("<none>"))
                heroes.put(players.get(player).getCurrentHero(), false);
            heroes.put(hero, true);
            p.setCurrentHero(hero);
            return true;
        }
        else return false;
    }

    /**
     * Gets the information of a team (player and used hero)
     * @param team Team
     * @return  Info
     */
    public synchronized String infoPlayersHeroesTeam(String team){
        String info = "";
        for (Player p: players.values())
            if (p.getCurrentTeam().equals(team))
                info += p.getUsername() + " " + p.getCurrentHero() + " ";
        return info;
    }

    /**
     * Gets the information of every player
     * @return Info
     */
    public synchronized String infoPlayersHeroes(){
        String info = "";
        for (Player p: players.values())
            info += p.getUsername() + " " + p.getCurrentHero() + " ";
        return info;
    }

    /**
     * Writes as message to every player
     * @param message Message
     */
    public synchronized void writeToAll(String message){
        for (Player p: players.values())
            addMessage(p.getUsername(), message);
    }

    /**
     * Writes a message to all members of a team
     * @param team      Team
     * @param message   Message
     */
    public synchronized void writeToTeam(String team, String message){
        for (Player p: players.values())
            if (p.getCurrentTeam().equals(team))
                addMessage(p.getUsername(), message);
    }

    /**
     * Adds a message to the buffer (that will eventually be sent to the client)
     * @param player  Player username
     * @param message Message to send
     */
    public void addMessage(String player, String message){
        if (players.get(player).isLoggedOn()) {
            locks.get(player).lock();
            buffers.get(player).add(message);
            writeConditions.get(player).signal();
            locks.get(player).unlock();
        }
    }


    ///////////////////
    // Reader Thread //
    ///////////////////

    /**
     * Thread that reads messages from a client
     */
    public class GameServerReader implements Runnable{

        private Socket socket;
        private String username;

        /**
         * Constructor
         * @param socket Socket
         */
        public GameServerReader(Socket socket){
            this.socket = socket;
            this.username = "";
            (new Thread(this)).start();
        }

        /**
         * Run
         */
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //get username from client (first message sent)
                username = in.readLine().split(" ")[0];

                String message;
                //Reads from buffer and writes to client
                while (((message = in.readLine()) != null) && !message.equals("Exit")) {

                    //select a hero
                    if (message.split(" ")[0].equals("Select")){
                        if (getHero(username, message.split(" ")[1])) {
                            addMessage(username, "Selected " + players.get(username).getCurrentHero());
                            String team = players.get(username).getCurrentTeam();
                            writeToTeam(team, "Players_Heroes " + infoPlayersHeroesTeam(team));
                        }
                        else addMessage(username, "Hero already used");
                    }

                    //get unused hero
                    else if (message.equals("Get Unused Hero")){
                        String hero = getUnusedHero(username);
                        addMessage(username, "Selected " + hero);
                        String team = players.get(username).getCurrentTeam();
                        writeToTeam(team, "Players_Heroes " + infoPlayersHeroesTeam(team));
                    }

                    //chat message
                    else if (message.split("::")[0].equals("Chat")){
                        String[] messageSplit = message.split("::");
                        String chat = messageSplit[0] + "::" + username + ": " + messageSplit[1];
                        writeToAll(chat);
                    }

                    //close/exit
                    else if (message.equals("Close") || message.equals("Exit")){
                        lock.lock();
                        writeToAll("Remove " + username);
                        somebodyLeft = true;
                        userLeftGame.signal();
                        lock.unlock();
                    }

                }
            }
            catch (Exception e) {
                System.err.println("User disconnected from the game server");
                lock.lock();
                somebodyLeft = true;
                userLeftGame.signal();
                lock.unlock();
            }
        }
    }
}
