package Graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {

    private HashMap<String, AbstractController> controllerMap = new HashMap<>();
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    protected void addScreen(String name, Pane pane, AbstractController controller){
        screenMap.put(name, pane);
        controllerMap.put(name, controller);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        controllerMap.get(name).updateController();
        main.setRoot(screenMap.get(name));
    }
}
