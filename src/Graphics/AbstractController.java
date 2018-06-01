package Graphics;

public class AbstractController {

    private static ScreenController screenController;

    public static void setScreenController(ScreenController screenController) {
        AbstractController.screenController = screenController;
    }

    public static ScreenController getScreenController(){
        return screenController;
    }

    public void updateController(){

    }

}
