package app;

import business.Client;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.ChangeScene;

import java.io.IOException;
import java.util.HashMap;

/**
 * Start a client instance
 */
public class StartClient extends Application {

    public static Client client;

    /**
     * Starts the client and the interface
     * @param primaryStage (not used)
     */
    public void start(Stage primaryStage){
        try {
            client = new Client("127.0.0.1", 12345);
            System.setProperty("prism.lcdtext", "false");
            ChangeScene.open("LoginMenu", "Projeto-SD");
            ChangeScene.getCurrentStage().setOnCloseRequest(e ->{
                client.sendMessageToMainServer("Exit");
            });
        }
        catch (IOException e) {
            System.err.println("Server not found");
            System.exit(-1);
        }
    }

    /**
     * Main
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}