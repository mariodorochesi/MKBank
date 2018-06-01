package Graphics;

import com.jfoenix.controls.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import system.general.Banco;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import system.general.Transferencias;
import system.general.TransferenciasTreeTableView;
import system.systemAccounts.CuentaBancaria;
import system.systemAccounts.CuentaBancariaRecursiveTree;
import system.systemAccounts.CuentaUsuario;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUser extends AbstractController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane ap_estadoCuenta;
    @FXML
    private AnchorPane ap_transferir;
    @FXML
    private AnchorPane ap_historial;
    @FXML
    private AnchorPane ap_privaSeguiridad;

    @FXML
    private JFXButton bmaster_goEstadoCuenta;
    @FXML
    private JFXButton bmaster_goTransferir;
    @FXML
    private JFXButton bmaster_goHistorial;
    @FXML
    private JFXButton bmaster_goPrivaSeguridad;

    @FXML
    private JFXTreeTableView treeTableView;
    @FXML
    private JFXTreeTableView treeTableViewHistorialTransferencias;

    // Recursos transferencias
    @FXML
    private JFXComboBox comboBox_cuentasBancarias;

    @FXML
    private JFXTextField tf_monto;
    @FXML
    private JFXTextField tf_comentario;
    @FXML
    private JFXComboBox comboBox_tipoDeCuentaDestino;
    @FXML
    private JFXTextField tf_numeroDeCuentaDestino;
    @FXML
    private JFXTextField tf_rutDestino;

    private static Banco banco;
    private static CuentaUsuario cuentaUsuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bmaster_goPrivaSeguridad.setVisible(false);
        comboBox_tipoDeCuentaDestino.getItems().removeAll(comboBox_tipoDeCuentaDestino.getItems());
        comboBox_tipoDeCuentaDestino.getItems().addAll("Cuenta Vista", "Cuenta Corriente", "Cuenta Ahorro");
    }

    /**
     * Realiza un update de los recursos situados en la pantalla de estado
     * de la cuenta.
     */
    private void updateEstadoDeCuenta(){
        comboBox_cuentasBancarias.getItems().removeAll(comboBox_cuentasBancarias.getItems());
        ObservableList data = FXCollections.observableArrayList();
        CuentaBancaria cb;
        for(int i = 0; i < cuentaUsuario.getCuentasBancarias().size(); i++){
            cb = cuentaUsuario.getCuentasBancarias().get(i);
            data.add(new CuentaBancariaRecursiveTree(
                String.valueOf(!cb.isCuentaBloqueada()), String.valueOf(cb.getIdentificador()),  cb.getTipoCuenta(),
                    String.valueOf(cb.getMonto())
            ));
            comboBox_cuentasBancarias.getItems().add(cb.getIdentificador());
        }

        TreeTableColumn c1 = (TreeTableColumn) treeTableView.getColumns().get(0);
        TreeTableColumn c2 = (TreeTableColumn) treeTableView.getColumns().get(1);
        TreeTableColumn c3 = (TreeTableColumn) treeTableView.getColumns().get(2);
        TreeTableColumn c4 = (TreeTableColumn) treeTableView.getColumns().get(3);

        // Linkeando valores de la tabla con valores del objeto
        c1.setCellValueFactory(
                new TreeItemPropertyValueFactory<CuentaBancariaRecursiveTree, String>("tipodeCuenta")
        );

        c2.setCellValueFactory(
                new TreeItemPropertyValueFactory<CuentaBancariaRecursiveTree, String>("identificador")
        );

        c3.setCellValueFactory(
                new TreeItemPropertyValueFactory<CuentaBancariaRecursiveTree, String>("habilitado")
        );

        c4.setCellValueFactory(
                new TreeItemPropertyValueFactory<CuentaBancariaRecursiveTree, String>("monto")
        );

        TreeItem<CuentaBancariaRecursiveTree> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);

        treeTableView.setShowRoot(false);

    }

    /**
     * Metodo gatillado al precionar el boton Estado
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goEstadoCuenta(){
        ap_estadoCuenta.setVisible(true);
        ap_transferir.setVisible(false);
        ap_historial.setVisible(false);
        ap_privaSeguiridad.setVisible(false);

        bmaster_goEstadoCuenta.setStyle("-fx-background-color: #345a72;");
        bmaster_goTransferir.setStyle("-fx-background-color: #334961;");
        bmaster_goHistorial.setStyle("-fx-background-color: #334961;");
        bmaster_goPrivaSeguridad.setStyle("-fx-background-color: #334961;");

        updateEstadoDeCuenta();
    }

    /**
     * Metodo gatillado al precionar el boton Transferir
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goTransferir(){
        ap_estadoCuenta.setVisible(false);
        ap_transferir.setVisible(true);
        ap_historial.setVisible(false);
        ap_privaSeguiridad.setVisible(false);

        bmaster_goEstadoCuenta.setStyle("-fx-background-color: #334961;");
        bmaster_goTransferir.setStyle("-fx-background-color: #345a72;");
        bmaster_goHistorial.setStyle("-fx-background-color: #334961;");
        bmaster_goPrivaSeguridad.setStyle("-fx-background-color: #334961;");
    }

    /**
     * Metodo gatillado al precionar el boton Historial
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goHistorial(){
        ap_estadoCuenta.setVisible(false);
        ap_transferir.setVisible(false);
        ap_historial.setVisible(true);
        ap_privaSeguiridad.setVisible(false);

        bmaster_goEstadoCuenta.setStyle("-fx-background-color: #334961;");
        bmaster_goTransferir.setStyle("-fx-background-color: #334961;");
        bmaster_goHistorial.setStyle("-fx-background-color: #345a72;");
        bmaster_goPrivaSeguridad.setStyle("-fx-background-color: #334961;");

        updateHistorialTransferencias();
    }

    /**
     * Metodo gatillado al precionar el boton Privacidad y Seguridad
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goPrivaSeguridad(){
        ap_estadoCuenta.setVisible(false);
        ap_transferir.setVisible(false);
        ap_historial.setVisible(false);
        ap_privaSeguiridad.setVisible(true);

        bmaster_goEstadoCuenta.setStyle("-fx-background-color: #334961;");
        bmaster_goTransferir.setStyle("-fx-background-color: #334961;");
        bmaster_goHistorial.setStyle("-fx-background-color: #334961;");
        bmaster_goPrivaSeguridad.setStyle("-fx-background-color: #345a72;");
    }

    /**
     * Utilizado para establecer el usuario que se ha logueado en la
     * pantalla de login
     * @param cuentaUsuario Cuenta usuario logueado
     */
    public static void setCuentaUsuario(CuentaUsuario cuentaUsuario){
        ControllerUser.cuentaUsuario = cuentaUsuario;
    }

    /**
     * Utilizado para establecer la instancia banco que se va a utilizar
     * a lo largo de la ejecucion del programa
     * @param banco Instancia del banco
     */
    public static void setBanco(Banco banco){
        ControllerUser.banco = banco;
    }

    /**
     * Accion gatillada al hacer click en el boton Login, permitiendo cerrar
     * sesion y volver a la pantalla de login.
     * @param event .
     */
    public void goLogin(ActionEvent event){
        getScreenController().activate("Login");
    }

    @Override
    public void updateController(){
        updateComboBoxCuentasBancarias();
        updateEstadoDeCuenta();
    }

    /**
     * reliza una transferencia con los datos ingresados en la pantalla
     * de transferencias.
     */
    public void hacerTransferencia(){
        CuentaBancaria cuentaOrigen = banco.isCuentaBancariaOnBanco((Long) comboBox_cuentasBancarias.getValue());

        banco.transferirDinero(cuentaOrigen, Long.parseLong(tf_numeroDeCuentaDestino.getText()),
                (String) comboBox_tipoDeCuentaDestino.getValue(), tf_rutDestino.getText(),
                Integer.parseInt(tf_monto.getText()), tf_comentario.getText());

        generateDialog("Mensaje", banco.getLastError());
    }

    /**
     * Actualiza el comboBox de las cuentas bancarias con los datos del usuario
     * logueado.
     */
    public void updateComboBoxCuentasBancarias(){
        comboBox_cuentasBancarias.getItems().removeAll(comboBox_cuentasBancarias.getItems());

        for(CuentaBancaria cb : cuentaUsuario.getCuentasBancarias())
            comboBox_cuentasBancarias.getItems().add(cb.getIdentificador());

        if(comboBox_cuentasBancarias.getItems().size() > 0) {
            comboBox_cuentasBancarias.setValue(comboBox_cuentasBancarias.getItems().get(0));
        }
    }

    /**
     * Genera un dialogo en pantalla lo que permite mostrar errores y otros
     * mensajes
     * @param titulo Titulo del mensaje
     * @param cuerpo Cuerpo del mensaje
     */
    private void generateDialog(String titulo, String cuerpo){
        JFXDialogLayout contenido = new JFXDialogLayout();
        contenido.setHeading(new Text(titulo));
        contenido.setBody(new Text(cuerpo));
        JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);

        JFXButton button = new JFXButton("Entendido");
        button.setOnAction(event -> dialog.close());

        contenido.setActions(button);

        dialog.show();
    }

    /**
     * Actualiza el historial detransferencias que involucren al usuario
     * logueado.
     * Muestra las ultimas 10 transacciones
     */
    private void updateHistorialTransferencias(){
        ObservableList data = FXCollections.observableArrayList();
        for(Transferencias t : cuentaUsuario.historialTransacciones(10)){
            if(cuentaUsuario.getPersona().getRut().equals(t.getRutDestinatario()))
                data.add(
                        new TransferenciasTreeTableView(String.valueOf(t.getNumeroTransferencia()), String.valueOf(t.getNumeroCuentaDestinatario()),
                                t.getTipocuentaOrigen(), String.valueOf(t.getNumeroCuentaOrigen()),
                                t.getComentarioTransferencia(), t.getDiaTransferencia() + " / " + t.getMesTransferencia() + " / " + t.getAnnoTransferencia(),
                                "+" + String.valueOf(t.getMontoTransferencia()))
                );
            else
                data.add(
                    new TransferenciasTreeTableView(String.valueOf(t.getNumeroTransferencia()), String.valueOf(t.getNumeroCuentaOrigen()),
                            t.getTipoCuentaDestinatario(), String.valueOf(t.getNumeroCuentaDestinatario()),
                            t.getComentarioTransferencia(), t.getDiaTransferencia() + " / " + t.getMesTransferencia() + " / " + t.getAnnoTransferencia(),
                            "-" + String.valueOf(t.getMontoTransferencia()))
                );
        }

        TreeTableColumn c1 = (TreeTableColumn) treeTableViewHistorialTransferencias.getColumns().get(0);
        TreeTableColumn c2 = (TreeTableColumn) treeTableViewHistorialTransferencias.getColumns().get(1);
        TreeTableColumn c3 = (TreeTableColumn) treeTableViewHistorialTransferencias.getColumns().get(2);
        TreeTableColumn c4 = (TreeTableColumn) treeTableViewHistorialTransferencias.getColumns().get(3);
        TreeTableColumn c5 = (TreeTableColumn) treeTableViewHistorialTransferencias.getColumns().get(4);
        TreeTableColumn c6 = (TreeTableColumn) treeTableViewHistorialTransferencias.getColumns().get(5);

        // Linkeando valores de la tabla con valores del objeto
        c1.setCellValueFactory(
                new TreeItemPropertyValueFactory<TransferenciasTreeTableView, String>("nTransferencia")
        );

        c2.setCellValueFactory(
                new TreeItemPropertyValueFactory<TransferenciasTreeTableView, String>("tipoCuentaDestino")
        );

        c3.setCellValueFactory(
                new TreeItemPropertyValueFactory<TransferenciasTreeTableView, String>("numeroCuentaDestino")
        );

        c4.setCellValueFactory(
                new TreeItemPropertyValueFactory<TransferenciasTreeTableView, String>("comentario")
        );
        c5.setCellValueFactory(
                new TreeItemPropertyValueFactory<TransferenciasTreeTableView, String>("fecha")
        );
        c6.setCellValueFactory(
                new TreeItemPropertyValueFactory<TransferenciasTreeTableView, String>("monto")
        );

        TreeItem<TransferenciasTreeTableView> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        treeTableViewHistorialTransferencias.setRoot(root);

        treeTableViewHistorialTransferencias.setShowRoot(false);
    }

}
