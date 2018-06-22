package ui;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;

/**
 * Class that will handle the switch between scenes
 */
public class ChangeScene extends Observable{

    private static String path = "src/main/java/ui/";
    private static String title = "Projeto-SD";
    private static Stage currentStage = null;
    private static HashMap<String, Scene> scenes = new HashMap<>();
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    private static HashMap<String, Boolean> openMenus = new HashMap<>();

    /**
     * Current stage getter
     * @return Current Stage
     */
    public static Stage getCurrentStage(){
        return currentStage;
    }

    /**
     * Sets menu closed
     * @param menu Menu
     */
    public static void setMenuClosed(String menu){
        openMenus.put(menu, false);
    }

    /**
     * Find out if a menu is open
     * @param menu Menu
     * @return True if menu is open, False if not
     */
    public static boolean isMenuOpen(String menu){
        if (openMenus.containsKey(menu))
            return openMenus.get(menu);
        else return false;
    }

    /**
     * Loads a scene
     * @param className Scene's class name
     * @return Loades scene
     */
    private static Scene loadScene(String className){
        try {
            String filename = className + ".fxml";
            openMenus.put(className, true);
            //if scene isn't opened
            if (!scenes.containsKey(filename)) {
                URL url = new File(path + filename).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent parent = loader.load();
                loaders.put(filename, loader);
                Scene scene = new Scene(parent);
                scenes.put(filename, scene);
                return scene;
            }
            //scene was already opened
            else {
                if (filename.equals("LoginMenu.fxml"))
                    ((LoginMenu) loaders.get(filename).getController()).init();
                else if (filename.equals("UserMenu.fxml"))
                    ((UserMenu) loaders.get(filename).getController()).init();
                else if (filename.equals("RegisterMenu.fxml"))
                    ((RegisterMenu) loaders.get(filename).getController()).init();
                else if (filename.equals("GameMenu.fxml"))
                    ((GameMenu) loaders.get(filename).getController()).init();

                return scenes.get(filename);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the stage options
     * @param stage Stage
     */
    private static void setStageOptions(Stage stage){
        try {
            stage.getIcons().add(new Image(String.valueOf((new File(path + "resources/logo.png").toURI().toURL()))));
            stage.setResizable(false);
            stage.sizeToScene();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch a scene
     * @param className Scene's class name
     * @param event Event that triggered the change
     */
    public static void to(String className, Event event){
        Scene scene = loadScene(className);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle(title);
        setStageOptions(window);
        window.show();
    }

    /**
     * Opens a new window
     * @param className Scene's class name
     * @param title Window title
     */
    public static void open(String className, String title){
        Scene scene = loadScene(className);
        Stage dialog = new Stage();
        dialog.setScene(scene);
        dialog.setTitle(title);
        dialog.initModality(Modality.APPLICATION_MODAL);
        setStageOptions(dialog);
        dialog.show();
        currentStage = dialog;
    }
}
