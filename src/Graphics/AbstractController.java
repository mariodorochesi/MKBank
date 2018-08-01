package Graphics;

import system.general.Banco;

public class AbstractController {

    private static ScreenController screenController;
    private static Banco banco;

    public static void setScreenController(ScreenController screenController) {
        AbstractController.screenController = screenController;
    }

    public static ScreenController getScreenController(){
        return screenController;
    }

    public void updateController(){

    }

}
