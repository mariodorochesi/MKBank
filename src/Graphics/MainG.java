package Graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.SQL.ConexionSQL;
import system.general.Banco;
import system.general.Loader;
import system.general.Saver;

import java.sql.SQLException;


public class MainG extends Application {

    private Banco banco = Banco.getInstance();

    //Clase que carga los datos desde la base SQL
    private Loader loader;

    //Conexion que se establece con la base SQL
    private ConexionSQL conexionSQL;

    @Override
    public void init(){
        try {
            conexionSQL = new ConexionSQL();
        }
        catch (SQLException e){
            System.out.println("No se pudo conectar a la base de datos");
            System.exit(-1);
        }
        loader =  new Loader(conexionSQL);
        Saver saver = new Saver(conexionSQL);
        banco.setSaver(saver);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Sistema Bancario");
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(900);
        primaryStage.show();

        // Este es el banco que se va a usar a lo largo de la ejecucion de programa
        //banco.loadFiles();
        loader.loadFromSQL();

        ScreenController screenController = new ScreenController(scene);
        AbstractController.setScreenController(screenController);

        screenController.addScreen("Login", fxmlLoader.load(getClass().getResource("Login.fxml").openStream()), fxmlLoader.getController());
        fxmlLoader = new FXMLLoader();
        screenController.addScreen("ScreenEjecutivo", fxmlLoader.load(getClass().getResource("ScreenEjecutivo.fxml").openStream()), fxmlLoader.getController());
        fxmlLoader = new FXMLLoader();
        screenController.addScreen("ScreenUsuario", fxmlLoader.load(getClass().getResource("ScreenUsuario.fxml").openStream()), fxmlLoader.getController());
        fxmlLoader = new FXMLLoader();
        screenController.addScreen("ScreenAdministrador", fxmlLoader.load(getClass().getResource("ScreenAdministrador.fxml").openStream()), fxmlLoader.getController());
        screenController.activate("Login");

    }

    @Override
    public void stop() throws Exception {
        banco.saveFiles();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        launch(args);
    }
}
