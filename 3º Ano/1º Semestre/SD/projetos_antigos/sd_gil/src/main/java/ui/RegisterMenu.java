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
import javafx.scene.paint.Paint;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Register menu class
 */
public class RegisterMenu implements Initializable, Observer{

    @FXML private Label message_label;
    @FXML private TextField username_input;
    @FXML private PasswordField cpassword_input;
    @FXML private PasswordField password_input;
    @FXML private TextField email_input;
    @FXML private Button register_button;

    private Event event;

    /**
     * Initializes controller
     * @param location  Location
     * @param resources Resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        init();

        //observe client
        StartClient.client.addObserver(this);

        //hide the message label (default)
        message_label.setVisible(false);

        //make button execute method
        register_button.setOnAction(event -> {
            register_button_pressed(event);
        });

        //trigger button when pressing enter on it
        register_button.addEventHandler(KeyEvent.KEY_PRESSED, key ->  {
            if (key.getCode() == KeyCode.ENTER)
                register_button_pressed(key);
        });
    }

    /**
     * Inits data (clears fields)
     */
    public void init(){
        username_input.clear();
        email_input.clear();
        password_input.clear();
        cpassword_input.clear();
        message_label.setVisible(false);
    }

    /**
     * Register button action
     * @param event
     */
    private void register_button_pressed(Event event){
        String username = username_input.getText();
        String email = email_input.getText();
        String password = password_input.getText();
        String cpassword = cpassword_input.getText();

        //Makes client send message to the main server, asking for registration
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !cpassword.isEmpty() && password.equals(cpassword)){
            this.event = event;
            StartClient.client.sendMessageToMainServer("Register " + username + " " + email + " " + password);
        }

        //missing data
        else {
            message_label.setText("Fill out all fields!");
            message_label.setTextFill(Paint.valueOf("red"));
            message_label.setVisible(true);
        }
    }

    /**
     * Update
     * @param o     Observable
     * @param arg   Arguments
     */
    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (StartClient.client.isSuccessfullyRegistered()) {
                    message_label.setText("User Registered!");
                    message_label.setTextFill(Paint.valueOf("green"));
                    message_label.setVisible(true);
                }
                else{
                    message_label.setText("User already exists!");
                    message_label.setTextFill(Paint.valueOf("red"));
                    message_label.setVisible(true);
                }
            }
        });
    }
}
