package ui;

import app.StartClient;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Duration;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.*;

/**
 * Game menu class
 */
public class GameMenu implements Initializable, Observer{

    @FXML private ImageView close_button;
    @FXML private ImageView birdo_button;
    @FXML private ImageView bobomb_button;
    @FXML private ImageView boo_button;
    @FXML private ImageView bowser_button;
    @FXML private ImageView cappy_button;
    @FXML private ImageView daisy_button;
    @FXML private ImageView donkeyKong_button;
    @FXML private ImageView drMario_button;
    @FXML private ImageView dryBones_button;
    @FXML private ImageView goomba_button;
    @FXML private ImageView hammerBro_button;
    @FXML private ImageView iggyKoopa_button;
    @FXML private ImageView kamek_button;
    @FXML private ImageView lemmyKoopa_button;
    @FXML private ImageView ludwigVonKoopa_button;
    @FXML private ImageView luigi_button;
    @FXML private ImageView mario_button;
    @FXML private ImageView mortonKoopa_button;
    @FXML private ImageView nabbit_button;
    @FXML private ImageView peach_button;
    @FXML private ImageView piranha_button;
    @FXML private ImageView rosalina_button;
    @FXML private ImageView royKoopa_button;
    @FXML private ImageView toad_button;
    @FXML private ImageView toadsworth_button;
    @FXML private ImageView waluigi_button;
    @FXML private ImageView wario_button;
    @FXML private ImageView wendyKoopa_button;
    @FXML private ImageView wiggler_button;
    @FXML private ImageView yoshi_button;
    @FXML private Label choosenHero_label;
    @FXML private Label team_label;
    @FXML private ProgressBar timeStatus_progressBar;
    @FXML private TableView<Map.Entry> enemy_table;
    @FXML private TableView<Map.Entry> friendly_table;
    @FXML private TextArea game_output;
    @FXML private TextField chat_input;

    private HashMap<String, ImageView> heroes;
    private Timeline timeline;
    boolean gameOver;

    /**
     * Initializes controller
     * @param location  Location
     * @param resources Resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        //observe client
        StartClient.client.addObserver(this);

        //progress bar initializer
        progressBar();

        //populate heroes map with the images
        heroes = new HashMap<>();
        heroes.put("birdo", birdo_button);
        heroes.put("bobomb", bobomb_button);
        heroes.put("boo", boo_button);
        heroes.put("bowser", bowser_button);
        heroes.put("cappy", cappy_button);
        heroes.put("daisy", daisy_button);
        heroes.put("donkeyKong", donkeyKong_button);
        heroes.put("drMario", drMario_button);
        heroes.put("dryBones", dryBones_button);
        heroes.put("goomba", goomba_button);
        heroes.put("hammerBro", hammerBro_button);
        heroes.put("iggyKoopa", iggyKoopa_button);
        heroes.put("kamek", kamek_button);
        heroes.put("lemmyKoopa", lemmyKoopa_button);
        heroes.put("ludwigVonKoopa", ludwigVonKoopa_button);
        heroes.put("luigi", luigi_button);
        heroes.put("mario", mario_button);
        heroes.put("mortonKoopa", mortonKoopa_button);
        heroes.put("nabbit", nabbit_button);
        heroes.put("peach", peach_button);
        heroes.put("piranha", piranha_button);
        heroes.put("rosalina", rosalina_button);
        heroes.put("royKoopa", royKoopa_button);
        heroes.put("toad", toad_button);
        heroes.put("toadsworth", toadsworth_button);
        heroes.put("waluigi", waluigi_button);
        heroes.put("wario", wario_button);
        heroes.put("wendyKoopa", wendyKoopa_button);
        heroes.put("wiggler", wiggler_button);
        heroes.put("yoshi", yoshi_button);

        //init
        init();

        //close_button handler
        close_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            StartClient.client.sendMessageToGameServer("Close");
            timeline.stop();
            StartClient.client.setGameStarting(false);
            StartClient.client.setGameOutput("");
            StartClient.client.sendMessageToMainServer("Retrieve Info");
            ChangeScene.setMenuClosed("GameMenu");
            ChangeScene.to("UserMenu", event);
        });

        //chat textfield handler
        chat_input.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER && !chat_input.getText().isEmpty() && !gameOver) {
                StartClient.client.sendMessageToGameServer("Chat::" + chat_input.getText());
                chat_input.clear();
            }
        });

        //automatically scrolls output text area to the bottom
        game_output.textProperty().addListener(event -> {
            game_output.setScrollTop(Double.MAX_VALUE);
        });

        //add event handlers to all the images
        heroesButtonHandler();
    }

    /**
     * Inits data (updates fields)
     */
    public void init(){
        enemy_table.getColumns().clear();
        friendly_table.getColumns().clear();
        game_output.clear();
        disableImagesEffects();
        timeline.play();
        team_label.setText(StartClient.client.getCurrentTeam());
        chat_input.clear();
        updateInfo();
    }

    /**
     * Builds the progess bar
     */
    private void progressBar(){
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timeStatus_progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(30), new KeyValue(timeStatus_progressBar.progressProperty(), 1)));

        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Disables all images effects
     */
    private void disableImagesEffects(){
        ColorAdjust color = new ColorAdjust();
        color.setSaturation(0);
        for (ImageView img: heroes.values()) {
            img.setEffect(color);
            img.setDisable(false);
        }
    }

    /**
     * Disables all images (makes them greyscale and not clickable)
     */
    private void disableAllImages(){
        ColorAdjust color = new ColorAdjust();
        color.setSaturation(-1);
        for (ImageView img: heroes.values()) {
            img.setEffect(color);
            img.setDisable(true);
        }
    }

    /**
     * Heroes handler
     */
    private void heroesButtonHandler(){
        for (String hero: heroes.keySet()) {
            ImageView img = heroes.get(hero);
            img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                StartClient.client.sendMessageToGameServer("Select " + hero);
            });
        }
    }

    /**
     * Updates all info
     */
    private void updateInfo(){
        gameOver = false;
        updateHeroes();
        updateTables();
        //game over
        if (!StartClient.client.getGameOutput().equals("")) {
            game_output.setText(StartClient.client.getGameOutput());
            disableAllImages();
            timeline.stop();
            gameOver = true;
        }
        //chat message
        String chat = StartClient.client.getChatMessage();
        if (!gameOver && !chat_input.getText().equals(chat)) {
            game_output.setText(chat);
        }
    }

    /**
     * Updates heroes
     */
    private void updateHeroes(){
        disableImagesEffects();
        ColorAdjust grayScale = new ColorAdjust();
        grayScale.setSaturation(-1);
        HashMap<String, String> selectedHeroes = StartClient.client.getHeroesFriendly();
        for (String h: selectedHeroes.values()){
            if (!h.equals("<none>") && !h.equals("")) {
                heroes.get(h).setEffect(grayScale);
                heroes.get(h).setDisable(true);
            }
        }
        choosenHero_label.setText(StartClient.client.getCurrentHero());
    }

    /**
     * Populates tables
     */
    private void updateTables(){
        HashMap<String, String> friendly = StartClient.client.getHeroesFriendly();
        HashMap<String, String> enemy = StartClient.client.getHeroesEnemy();

        TableColumn friendlyNames = new TableColumn("Name");
        friendlyNames.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKey());
            }
        });

        TableColumn friendlyHeroes = new TableColumn("Hero");
        friendlyHeroes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getValue());
            }
        });

        TableColumn enemyNames = new TableColumn("Name");
        enemyNames.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKey());
            }
        });

        TableColumn enemyHeroes = new TableColumn("Hero");
        enemyHeroes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getValue());
            }
        });

        friendly_table.getItems().clear();
        friendly_table.getColumns().clear();
        friendly_table.setItems(FXCollections.observableArrayList(friendly.entrySet()));
        friendly_table.getColumns().addAll(friendlyNames, friendlyHeroes);
        enemy_table.getColumns().clear();
        enemy_table.getItems().clear();
        enemy_table.setItems(FXCollections.observableArrayList(enemy.entrySet()));
        enemy_table.getColumns().addAll(enemyNames, enemyHeroes);
    }

    /**
     * Update
     * @param o   Observable
     * @param arg Arguments
     */
    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() ->{
            if (ChangeScene.isMenuOpen("GameMenu"))
                updateInfo();
        });
    }
}