package business;

import java.io.IOException;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Bot class
 */
public class Bot implements Observer, Runnable{

    private String username;
    private String email;
    private String password;
    private Client client;
    private boolean play;
    private boolean loginTry;
    private boolean gameStarted;

    /**
     * Bot constructor
     * @param username  Username
     * @param password  Password
     * @param email     Email
     */
    public Bot (String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.play = false;
        this.loginTry = false;
        this.gameStarted = false;
    }

    /**
     * Run
     */
    @Override
    public void run() {
        try {
            this.client = new Client("127.0.0.1", 12345);
            client.addObserver(this);

            if (email == null){
                client.sendMessageToMainServer("Login " + username + " " + password);
                loginTry = true;
            }

            else client.sendMessageToMainServer("Register " + username + " " + email + " " + password);

        }
        catch (IOException e) {
            System.err.println("Server not found");
        }

    }

    /**
     * Update
     * @param o     Observable
     * @param arg   Arguments
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!client.botIgnoreUpdade()) {
            //login
            if (!loginTry) {
                client.sendMessageToMainServer("Login " + username + " " + password);
                client.setUsername(username);
                loginTry = true;
            }
            else if (!client.isSuccessfullyRegistered()) {
                if (email != null) {
                    email = null;
                    client.sendMessageToMainServer("Login " + username + " " + password);
                }
                else {
                    System.err.println("Wrong username/password");
                    return;
                }
            }
            //play a game
            else if (client.isAuthenticated() && !play) {
                play = true;
                client.sendMessageToMainServer("Play");
            }
            //game starting
            else if (client.isGameStarting() && !gameStarted) {
                client.sendMessageToGameServer("Select mario");
                gameStarted = true;
            }
            //game started and mario was already selected
            else if (gameStarted && client.getCurrentHero().equals("")) {
                //waiting a few seconds before picking a hero
                Random rand = new Random();
                int waitingTime = rand.nextInt(20);
                try {
                    Thread.sleep(waitingTime * 1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.sendMessageToGameServer("Get Unused Hero");
            }
            //game over - try to play again
            else if (!client.getGameOutput().equals("")) {
                gameStarted = false;
                client.sendMessageToMainServer("Play");
            }
        }
    }
}
