package Graphics;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import system.general.Banco;
import system.systemAccounts.CuentaAdministrador;
import system.systemAccounts.CuentaEjecutivo;
import system.systemAccounts.CuentaUsuario;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin extends AbstractController implements Initializable{

    @FXML
    private JFXTextField userField;
    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label label;

    @FXML
    private JFXComboBox comboBox;

    private static Banco banco;

    /**
     * Metodo activado al precionar el boton "Entrar"
     * en la pantalla de login
     * @param event evento ocacionado al precionar "Entrar"
     */
    public void logear(ActionEvent event){
        loginAction();
    }

    /**
     * Metodo activado al precionar la tecla enter y teniendo
     * resaltado el boton logn
     * @param event evento ocacionado la precionar la tecla Enter
     */
    public void logearKey(KeyEvent event){
        if(event.getCode().getName().equals("Enter"))
            loginAction();
    }

    private void loginAction(){
        if(comboBox.getValue().equals("Ejecutivo")){
            CuentaEjecutivo cuentaEjecutivo = banco.isEjecutivoOnBanco(userField.getText());
            if(cuentaEjecutivo != null &&
                    cuentaEjecutivo.getPersona().contrasenaEquals(passwordField.getText())) {
                ControllerEjecutivo.setBanco(banco);
                ControllerEjecutivo.setCuentaEjecutivo(cuentaEjecutivo);
                getScreenController().activate("ScreenEjecutivo");
                clearLabel();
            }
            else{
                label.setText("Error: Usuario y/o contraseña incorrectos");
            }
            clear();
        }
        else if(comboBox.getValue().equals("Administrador")) {
            CuentaAdministrador cuentaAdministrador = banco.isAdministradorOnBanco(userField.getText());
            if(cuentaAdministrador != null &&
                    cuentaAdministrador.getPersona().contrasenaEquals(passwordField.getText())){
                ControllerAdministrador.setBanco(banco);
                ControllerAdministrador.setCuentaAdministrador(cuentaAdministrador);
                getScreenController().activate("ScreenAdministrador");
                clearLabel();
            }
            else{
                label.setText("Error: Usuario y/o contraseña incorrectos");
            }
            clear();
        }
        // Login de usuario
        else if(comboBox.getValue().equals("Usuario")) {
            CuentaUsuario cuentaUsuario = banco.isUsuarioOnBanco(userField.getText());
            if(cuentaUsuario != null &&
                    cuentaUsuario.getPersona().contrasenaEquals(passwordField.getText())){
                ControllerUser.setBanco(banco);
                ControllerUser.setCuentaUsuario(cuentaUsuario);
                getScreenController().activate("ScreenUsuario");
                clearLabel();
            }
            else{
                label.setText("Error: Usuario y/o contraseña incorrectos");
            }
            clear();
        }
    }

    /**
     * Valida el login, es decir busca en la BD si existe esta cuent
     * con esta contrasena
     * @return retorna verdadero si son correctos los datos ingresados.
     */
    private String isValidLogin(){
        String user = userField.getText();
        String password = passwordField.getText();

        // Esto es de prueba
        if(user.equals("ejecutivo") && password.equals("ejecutivo") && comboBox.getValue().equals("Ejecutivo"))
            return "Ejecutivo";
        else if(user.equals("admin") && password.equals("admin") && comboBox.getValue().equals("Administrador"))
            return "Administrador";
        else if (user.equals("user") && password.equals("user") && comboBox.getValue().equals("Usuario"))
            return "Usuario";
        return "None";
    }

    /**
     * Limpia todas las entradas de la ventana
     */
    private void clear(){
        userField.clear();
        passwordField.clear();
    }

    private void clearLabel(){
        label.setText(" ");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll("Usuario", "Ejecutivo", "Administrador");
        comboBox.setValue("Usuario");
    }

    public static void setBanco(Banco banco){
        ControllerLogin.banco = banco;
    }

    // Las funciones a contincuacion son temporales, es para facilitar el desarrollo de la aplicacion
    public void rellenarMario(ActionEvent event){
        userField.setText("17983727-7");
        passwordField.setText("mario123");
    }

    public void rellenarBraulio(ActionEvent event){
        userField.setText("19267473-5");
        passwordField.setText("braulio123");
    }
}
