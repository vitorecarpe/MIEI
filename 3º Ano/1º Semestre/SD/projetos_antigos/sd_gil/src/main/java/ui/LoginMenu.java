package ui;

import app.StartClient;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


/**
 * Login menu class
 */
public class LoginMenu implements Initializable, Observer{

    @FXML private Button login_button;
    @FXML private Label createNewAccount_label;
    @FXML private Label error_label;
    @FXML private PasswordField password_input;
    @FXML private TextField username_input;

    private Event event;

    /**
     * Initializes controller
     * @param location  Location
     * @param resources Resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        //observe client
        StartClient.client.addObserver(this);

        //hide error label
        error_label.setVisible(false);

        //login button action
        login_button.setOnAction(event -> {
            login_button_pressed(event);
        });

        //trigger button when pressing enter on it
        login_button.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ENTER)
                login_button_pressed(key);
        });

        //opens the register menu
        createNewAccount_label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ChangeScene.open("RegisterMenu", "Registar");
            //change register menu open var to false when closing the register menu
            ChangeScene.getCurrentStage().setOnCloseRequest(e ->{
                ChangeScene.setMenuClosed("RegisterMenu");
            });
        });
    }

    /**
     * Inits data (clears fields)
     */
    public void init(){
        username_input.clear();
        password_input.clear();
    }

    /**
     * Login button action
     * @param event
     */
    private void login_button_pressed(Event event){
        this.event = event;
        String username = username_input.getText();
        String password = password_input.getText();

        //Makes client send a message to main server asking for authentication
        if (!username.isEmpty() && !password.isEmpty()) {
            StartClient.client.setUsername(username);
            StartClient.client.sendMessageToMainServer("Login " + username + " " + password);
        }

        //missing data
        else{
            error_label.setText("Fill all fields");
            error_label.setVisible(true);
        }
    }

    /**
     * Update
     * @param o     Observable
     * @param arg   Arguments
     */
    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            //only updates if register menu isn't open
            if (!ChangeScene.isMenuOpen("RegisterMenu")) {
                //login successful
                if (StartClient.client.isAuthenticated()) {
                    if (!ChangeScene.isMenuOpen("UserMenu")) {
                        error_label.setVisible(false);
                        StartClient.client.sendMessageToMainServer("Retrieve Info");
                        ChangeScene.to("UserMenu", event);
                    }
                }
                //wrong username/password
                else {
                    error_label.setText("Wrong username/password\n or player is already logged in");
                    error_label.setVisible(true);
                }
            }
        });
    }
}
