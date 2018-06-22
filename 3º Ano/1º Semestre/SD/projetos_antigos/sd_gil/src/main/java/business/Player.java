package business;

import java.io.Serializable;
import java.util.*;

/**
 * Player class
 */
public class Player implements Serializable{

    private String username;
    private String email;
    private String password;
    private int rank;
    private int win;
    private int lost;
    private HashMap<String,Integer> heroes;
    private String currentTeam;
    private String currentHero;
    private boolean loggedOn;
    private int numberMVP;

    /**
     * Empty constructor
     */
    public Player() {
        this.username = "";
        this.email = "";
        this.password = "";
        this.heroes = new HashMap<>();
        initHeroes();
        this.rank = 0;
        this.win = 0;
        this.lost = 0;
        this.currentTeam = "";
        this.currentHero = "";
        numberMVP = 0;
        loggedOn = false;
    }

    /**
     * Constructor with arguments
     * @param username  Username
     * @param email     Email
     * @param password  Password
     */
    public Player(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rank = 0;
        this.win = 0;
        this.lost = 0;
        this.currentTeam = "";
        this.currentHero = "";
        loggedOn = false;
        this.heroes = new HashMap<>();
        initHeroes();
    }

    /**
     * Copy constructor
     * @param p Player
     */
    public Player(Player p) {
        this.username = p.getUsername();
        this.email = p.getEmail();
        this.password = p.getPassword();
        this.heroes = p.getHeroes();
        this.rank = p.getRank();
        this.win = p.getWin();
        this.lost = p.getLost();
        this.currentTeam = p.getCurrentTeam();
        this.currentHero = p.getCurrentHero();
        this.loggedOn = p.isLoggedOn();
    }

    /**
     * Username getter
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Email getter
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Password getter
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Heroes getter
     * @return Heroes
     */
    public HashMap<String, Integer> getHeroes() {
        return heroes;
    }

    /**
     * Rank getter
     * @return Rank
     */
    public int getRank() {
        calcRank();
        return rank;
    }


    /**
     * Number wins getter
     * @return Wins
     */
    public int getWin() {
        return win;
    }

    /**
     * Adds another win
     */
    public void addWin() {
        this.win++;
        calcRank();
    }

    /**
     * Numer losses getter
     * @return Number of losses
     */
    public int getLost() {
        return lost;
    }

    /**
     * Adds another loss
     */
    public void addLost() {
        this.lost++;
        calcRank();
    }

    /**
     * Current team getter
     * @return Current team
     */
    public String getCurrentTeam() {
        return currentTeam;
    }

    /**
     * Current team setter
     * @param team New team
     */
    public void setCurrentTeam(String team){
        currentTeam = team;
    }

    /**
     * Current hero getter
     * @return Current hero
     */
    public String getCurrentHero() {
        return currentHero;
    }

    /**
     * Current hero setter
     * @param currentHero New hero
     */
    public void setCurrentHero(String currentHero) {
        this.currentHero = currentHero;
    }

    /**
     * Find out if a player is logged on
     * @return True if player is logged on, False if not
     */
    public boolean isLoggedOn() {
        return loggedOn;
    }

    /**
     * Changes the loggedOn status
     * @param loggedOn New status
     */
    public void setLoggedOn(boolean loggedOn) {
        this.loggedOn = loggedOn;
    }

    /**
     * Increments the number of times played a specific hero
     * @param hero Hero
     */
    public void addPlayHero(String hero){
        if (heroes.containsKey(hero))
            heroes.put(hero, heroes.get(hero)+1);
        else heroes.put(hero, 1);
    }

    /**
     * Equals
     * @param obj Object to compare
     * @return True if objects are the same, False if not
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Player other = (Player) obj;

        return other.getUsername().equals(this.username);
    }

    /**
     * Clone
     * @return Player copy
     */
    public Player clone(){
        return new Player(this);
    }

    /**
     * To string
     * @param p Info
     */
    public void toString(Player p){
        System.out.println("Username: "+username+"\nPassword: "+ password+"\nEmail: "+email+"\n Herois: "+ heroes+"\nRank: "+rank + "\nWin: "+win+"\nLost: "+lost);
    }

    /**
     * Inits the heroes map
     */
    public void initHeroes(){
        heroes.put("birdo", 0);
        heroes.put("bobomb", 0);
        heroes.put("boo", 0);
        heroes.put("bowser", 0);
        heroes.put("cappy", 0);
        heroes.put("daisy", 0);
        heroes.put("donkeyKong", 0);
        heroes.put("drMario", 0);
        heroes.put("dryBones", 0);
        heroes.put("goomba", 0);
        heroes.put("hammerBro", 0);
        heroes.put("iggyKoopa", 0);
        heroes.put("kamek", 0);
        heroes.put("lemmyKoopa", 0);
        heroes.put("ludwigVonKoopa", 0);
        heroes.put("luigi", 0);
        heroes.put("mario", 0);
        heroes.put("mortonKoopa", 0);
        heroes.put("nabbit", 0);
        heroes.put("peach", 0);
        heroes.put("piranha", 0);
        heroes.put("rosalina", 0);
        heroes.put("royKoopa", 0);
        heroes.put("toad", 0);
        heroes.put("toadsworth", 0);
        heroes.put("waluigi", 0);
        heroes.put("wario", 0);
        heroes.put("wendyKoopa", 0);
        heroes.put("wiggler", 0);
        heroes.put("yoshi", 0);
    }

    /**
     * Gets player info to be printed by server
     * @return
     */
    public String getInfo(){
        StringBuilder info = new StringBuilder();
        info.append("Username "+username+" Email "+email+" Rank "+rank + " Win "+win+" Lost "+lost+" Hero-TimesPlayed ");
        
        for(Map.Entry<String, Integer> entry : heroes.entrySet())
            info.append(entry.getKey() + " " + entry.getValue() + " ");

        return info.toString();
    }

    /**
     * Retorns the number of plays
     * @return Number of plays
     */
    public int getPlays(){
        return win + lost;
    }

    /**
     * Returns the player ratio
     * @return Ration
     */
    public double getRatio(){
        if (win + lost != 0) {
            double w = win;
            double l = lost;
            return (Math.round((w / (w + l)) * 100))/100.0;
        }
        else return 0;
    }

    /**
     * Determines player rank (only valid after 5 plays)
     */
    public void calcRank() {
        if (win + lost >= 5) {
            double w = win;
            double l = lost;
            rank = (int) Math.round(w / (w + l) * 10);

            if (rank > 9)
                rank = 9;
        }
        else rank = 0;
    }

    /**
     * Returns the number of times as MVP
     * @return Number of MVP
     */
    public int getNumberMVP() {
        return numberMVP;
    }

    /**
     * Increments the MVP number
     */
    public void addMVP(){
        numberMVP++;
    }
}