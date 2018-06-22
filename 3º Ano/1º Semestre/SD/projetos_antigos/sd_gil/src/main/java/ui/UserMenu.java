package ui;


import app.StartClient;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Pair;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.*;

/**
 * User menu class
 */
public class UserMenu implements Initializable, Observer{

    @FXML private Button play_button;
    @FXML private ImageView close_button;
    @FXML private Label name_label;
    @FXML private Label numberPlays_label;
    @FXML private Label rank_label;
    @FXML private PieChart winLost_piechart;
    @FXML private ProgressIndicator loading_progressIndicator;
    @FXML private TableView<Map.Entry> globalRanks_table;
    @FXML private TableView<Map.Entry> topHeroes_table;

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

        //init
        init();

        //close button handler
        close_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            StartClient.client.sendMessageToMainServer("Close");
            ChangeScene.setMenuClosed("UserMenu");
            ChangeScene.to("LoginMenu", event);
        });

        //play button handler
        play_button.setOnAction(event -> {
            //play game
            if (play_button.getText().equals("Find Game!")) {
                this.event = event;
                loading_progressIndicator.setVisible(true);
                StartClient.client.sendMessageToMainServer("Play");
                play_button.setText("Cancel");
            }
            //cancel game
            else{
                loading_progressIndicator.setVisible(false);
                StartClient.client.sendMessageToMainServer("Cancel");
                play_button.setText("Find Game!");
            }
        });
    }

    /**
     * Inits data (updates fields)
     */
    public void init(){
        name_label.setText(StartClient.client.getUsername());
        topHeroes_table.getColumns().clear();
        globalRanks_table.getColumns().clear();
        loading_progressIndicator.setVisible(false);
        play_button.setText("Find Game!");
        populateInfo();
    }

    /**
     * Populates info
     */
    private void populateInfo(){
        populateStats();
        populateTopTable();
    }

    /**
     * Populates player stats
     */
    private void populateStats(){
        int rank = StartClient.client.getRank();
        int win = StartClient.client.getWin();
        int lost = StartClient.client.getLost();
        HashMap<String, Integer> mostPlayed = StartClient.client.getMostUsedHeroes();

        rank_label.setText(Integer.toString(rank));
        numberPlays_label.setText(Integer.toString(win+lost));
        winLost_piechart.getData().clear();
        winLost_piechart.getData().add(new PieChart.Data("Wins", win));
        winLost_piechart.getData().add(new PieChart.Data("Losses", lost));


        TableColumn mostPlayedName = new TableColumn("Hero");
        mostPlayedName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKey());
            }
        });

        TableColumn mostPlayedTimes = new TableColumn("Times Played");
        mostPlayedTimes.setSortType(TableColumn.SortType.DESCENDING);
        mostPlayedTimes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer> param) {
                return new ReadOnlyStringWrapper(Integer.toString(param.getValue().getValue()));
            }
        });

        topHeroes_table.getColumns().clear();
        topHeroes_table.setItems(FXCollections.observableArrayList(mostPlayed.entrySet()));
        topHeroes_table.getColumns().addAll(mostPlayedName, mostPlayedTimes);
        topHeroes_table.getSortOrder().add(mostPlayedTimes);
    }

    /**
     * Populates tops table
     */
    private void populateTopTable(){
        HashMap<String, ArrayList> tops = StartClient.client.getTops();

        TableColumn topUsername = new TableColumn("Username");
        topUsername.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, String>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKey());
            }
        });

        TableColumn topPlays = new TableColumn("Times Played");
        topPlays.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, Integer>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, Integer> param) {
                return new ReadOnlyIntegerWrapper(((Integer) param.getValue().getValue().get(0)));
            }
        });

        TableColumn topRatio = new TableColumn("Ratio");
        topRatio.setSortType(TableColumn.SortType.DESCENDING);
        topRatio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, Double>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, Double> param) {
                int ratio = ((Integer) param.getValue().getValue().get(0));
                if (ratio >= 5)
                    return new ReadOnlyDoubleWrapper(((Double) param.getValue().getValue().get(1)));
                else return new ReadOnlyDoubleWrapper(-1);
            }
        });

        TableColumn topMVP = new TableColumn("Times as MVP");
        topMVP.setSortType(TableColumn.SortType.DESCENDING);
        topMVP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, Double>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Map.Entry<String, ArrayList>, Double> param) {
                return new ReadOnlyIntegerWrapper(((Integer) param.getValue().getValue().get(2)));
            }
        });


        globalRanks_table.getColumns().clear();
        globalRanks_table.setItems(FXCollections.observableArrayList(tops.entrySet()));
        globalRanks_table.getColumns().addAll(topUsername, topRatio, topPlays, topMVP);
        globalRanks_table.getSortOrder().add(topRatio);
    }

    /**
     * Update
     * @param o     Observable
     * @param arg   Arguments
     */
    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() ->{
            populateInfo();
            if (StartClient.client.isGameStarting() && !ChangeScene.isMenuOpen("GameMenu")){
                ChangeScene.to("GameMenu", event);
            }
        });
    }
}
