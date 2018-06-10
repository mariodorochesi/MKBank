package Graphics;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import system.general.*;
import system.systemAccounts.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerAdministrador extends AbstractController implements Initializable {

    // recursos de NewUser
    @FXML
    private JFXComboBox comboBox_estadoCivil;
    @FXML
    private JFXComboBox comboBox_cuentaBancariaInicial;
    @FXML
    private JFXComboBox comboBox_sucursalAsociada;

    @FXML
    private JFXTextField tf_nombres;
    @FXML
    private JFXTextField tf_apellidos;
    @FXML
    private JFXTextField tf_rut;
    @FXML
    private JFXTextField tf_rutDB;
    @FXML
    private JFXTextField tf_celular;
    @FXML
    private JFXTextField tf_correo;
    @FXML
    private JFXTextField tf_direccion;
    @FXML
    private JFXTextField tf_ciudad;
    @FXML
    private JFXTextField tf_nacionalidad;
    @FXML
    private JFXCheckBox cb_permisoEjecutivo;
    @FXML
    private JFXCheckBox cb_permisoUsuario;

    @FXML
    private JFXDatePicker dp_fechaNacimiento;


    @FXML
    private JFXCheckBox cb_hombre;
    @FXML
    private JFXCheckBox cb_mujer;

    // Recursos modificar usuario
    @FXML
    private JFXComboBox<String> comboBox_estadoCivil1;
    @FXML
    private JFXComboBox comboBox_nuevaCuentaBancaria;
    @FXML
    private JFXComboBox comboBox_cuentaBancariaEliminar;
    @FXML
    private JFXComboBox comboBox_sucursalAsociada1;

    @FXML
    private JFXTextField tf_searchRut;
    @FXML
    private JFXTextField tf_nombres1;
    @FXML
    private JFXTextField tf_apellidos1;
    @FXML
    private JFXTextField tf_rut1;
    @FXML
    private JFXTextField tf_rutDB1;
    @FXML
    private JFXTextField tf_celular1;
    @FXML
    private JFXTextField tf_correo1;
    @FXML
    private JFXTextField tf_direccion1;
    @FXML
    private JFXTextField tf_ciudad1;
    @FXML
    private JFXTextField tf_nacionalidad1;

    @FXML
    private JFXCheckBox cb_hombre1;
    @FXML
    private JFXCheckBox cb_mujer1;

    @FXML
    private JFXButton b_nuevaCuentaBancaria;
    @FXML
    private JFXButton b_eliminarCuentaBancaria;
    @FXML
    private JFXButton b_eliminarPersona;

    @FXML
    private JFXDatePicker dp_fechaNacimiento1;

    @FXML
    private JFXToggleButton tb_permisosEjecutivo;
    @FXML
    private JFXToggleButton tb_permisosUsuario;


    // Recursos generales
    @FXML
    private JFXButton bmaster_goNewUser;
    @FXML
    private JFXButton bmaster_goEditUser;
    @FXML
    private JFXButton bmaster_goReport;
    @FXML
    private JFXButton bmaster_goGirar;
    @FXML
    private JFXButton bmaster_goAdmSucursales;

    @FXML
    private AnchorPane ap_NewUser;
    @FXML
    private AnchorPane ap_EditUser;
    @FXML
    private AnchorPane ap_Report;
    @FXML
    private AnchorPane ap_Girar;
    @FXML
    private AnchorPane ap_AdmSucursales;

    @FXML
    private JFXTreeTableView treeTableView;

    @FXML
    private StackPane stackPane;


    // Recursos giro
    @FXML
    private JFXComboBox comboBox_cuentaBancariaGiro;
    @FXML
    private JFXTextField tf_montoGirar;
    @FXML
    private JFXTextField tf_searchRutGiro;
    @FXML
    private JFXButton b_depositar;
    @FXML
    private JFXButton b_retirar;

    @FXML
    private ScatterChart scatterChart;

    @FXML
    private JFXTreeTableView treeTableViewReporteCiudad;

    // Recursos del administrador de sucursales
    @FXML
    private JFXTextField tf_nombreNuevaSucursal;
    @FXML
    private JFXTextField tf_direccionNuevaSucursal;
    @FXML
    private JFXTreeTableView treeTableViewSucursales;

    // Labels necesarios:
    @FXML
    private Label lb_1;
    @FXML
    private Label lb_2;
    @FXML
    private Label lb_3;
    @FXML
    private Label lb_4;
    @FXML
    private Label lb_5;

    private String rutBuscadoGiro;
    private String rutBuscado;
    private ArrayList<String> reportLines;
    private static CuentaAdministrador cuentaAdministrador;
    private static Banco banco;

    /**
     * Inicializa lo necesario en la ventana para que funcione el ingresado
     * de parametros..
     * @param location ubicacion de inicializado
     * @param resources recursos de inicializado
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inicializando Controller");
        comboBox_estadoCivil.getItems().removeAll(comboBox_estadoCivil.getItems());
        comboBox_estadoCivil.getItems().addAll("Casada/o", "Separada/o", "Soltera/o");
        comboBox_estadoCivil1.getItems().removeAll(comboBox_estadoCivil.getItems());
        comboBox_estadoCivil1.getItems().addAll("Casada/o", "Separada/o", "Soltera/o");
        comboBox_cuentaBancariaInicial.getItems().remove(comboBox_cuentaBancariaInicial.getItems());
        comboBox_cuentaBancariaInicial.getItems().addAll("Cuenta Vista", "Cuenta Corriente", "Cuenta Ahorro");
        comboBox_nuevaCuentaBancaria.getItems().addAll(comboBox_cuentaBancariaInicial.getItems());

        comboBox_sucursalAsociada.getItems().removeAll(comboBox_sucursalAsociada.getItems());
        comboBox_sucursalAsociada.getItems().addAll(banco.obtenerNombresSucursales());

        comboBox_sucursalAsociada1.getItems().removeAll(comboBox_sucursalAsociada.getItems());
        comboBox_sucursalAsociada1.getItems().addAll(banco.obtenerNombresSucursales());

        updateSucursalesOnTreeTableView();

        reportLines = new ArrayList<>();
        comboBox_cuentaBancariaInicial.setDisable(!cb_permisoUsuario.isSelected());
    }

    /**
     * Funcion accionada al precionar el boton "Crear usuario",
     * obtiene los datos igresados en la caja de textos y los
     * interpreta para guardar en la base de datos los datos.
     * @param event evento creado al precionar el boton.
     */
    public void crearUsuario(ActionEvent event){
        final String nombres = tf_nombres.getText().toUpperCase();
        final String apellidos = tf_apellidos.getText().toUpperCase();
        final String rut = tf_rut.getText() + "-" + tf_rutDB.getText();
        final String correo = tf_correo.getText();
        final String celular = tf_celular.getText();
        final String direccion = tf_direccion.getText().toUpperCase();
        final String ciudad = tf_ciudad.getText().toUpperCase();
        final String eCivil = (String) comboBox_estadoCivil.getValue();
        final String nacionalidad = tf_nacionalidad.getText().toUpperCase();
        final int annoNacimiento = dp_fechaNacimiento.getValue().getYear();
        final int mesNacimiento = dp_fechaNacimiento.getValue().getMonthValue();
        final int diaNacimiento = dp_fechaNacimiento.getValue().getDayOfMonth();
        final String genero; if(cb_hombre.isSelected()) genero = "Hombre"; else genero = "Mujer";
        final String sucursalAsociada = (String) comboBox_sucursalAsociada.getValue();

        // Agregamos la persona al sistema
        System.out.println("Agregando a la persona " + nombres);
        banco.agregarPersona(cuentaAdministrador, nombres, apellidos, rut, ciudad, direccion, correo,
                celular, nacionalidad, annoNacimiento, mesNacimiento, diaNacimiento, eCivil, genero, sucursalAsociada);

        // Dando permisos de usuarios a la persona y agregando su primera cuenta bancaria
        if(cb_permisoUsuario.isSelected()){
            System.out.println("Agregando Permisos Usuario a" + nombres);
            banco.otorgarPermisosUsuario(rut);
            banco.agregarCuentaBancaria(cuentaAdministrador, banco.isUsuarioOnBanco(rut),
                    (String) comboBox_cuentaBancariaInicial.getValue());
        }

        // Dando permisos de ejecutivo a la persona
        if(cb_permisoEjecutivo.isSelected()){
            System.out.println("Agregando Permisos Ejecutivo a " + nombres);
            banco.otorgarPermisosSuperiores(Banco.PERMISO_EJECUTIVO, rut);
        }


        generateDialog("Completado", banco.getLastError() + tf_searchRut.getText());

        resetAll(event);
    }

    /**
     * Reinicia todas las cajas de texto y datos ingresados por
     * el ejecutivo o administrador en el programa.
     * @param event evento creado al precionar el boton.
     */
    public void resetAll(ActionEvent event){
        // Limpiando la seccion de crear usuario
        tf_nombres.clear();
        tf_apellidos.clear();
        tf_celular.clear();
        tf_correo.clear();
        tf_rut.clear();
        tf_rutDB.clear();
        tf_direccion.clear();
        tf_ciudad.clear();
        tf_nacionalidad.clear();

        comboBox_estadoCivil.getSelectionModel().clearSelection();
        comboBox_sucursalAsociada.getSelectionModel().clearSelection();

        cb_hombre.setSelected(false);
        cb_mujer.setSelected(false);

        dp_fechaNacimiento.setValue(null);

        // Limpiando la seccion de modificar usuario
        tf_nombres1.clear();
        tf_apellidos1.clear();
        tf_celular1.clear();
        tf_correo1.clear();
        tf_rut1.clear();
        tf_rutDB1.clear();
        tf_direccion1.clear();
        tf_ciudad1.clear();
        tf_nacionalidad1.clear();

        comboBox_estadoCivil1.getSelectionModel().clearSelection();
        comboBox_cuentaBancariaEliminar.getItems().removeAll(comboBox_cuentaBancariaInicial.getItems());
        comboBox_sucursalAsociada1.getSelectionModel().clearSelection();

        dp_fechaNacimiento1.setValue(null);

        cb_hombre1.setSelected(false);
        cb_mujer1.setSelected(false);

        // Limpiando otros recursos
        tf_searchRut.clear();

    }

    /**
     * Funcion accionada al precionar la CheckBox "hombre" o "mujer"
     * y se encarga de desactivar el sexo opuesto, para evitar errores
     * de tener ambos activados a la ves.
     * @param event evento creado al precionar el CheckBox hombre o mujer.
     */
    public void switchSexo(ActionEvent event){
        if(event.getSource().equals(cb_hombre))
            cb_mujer.setSelected(false);
        else
            cb_hombre.setSelected(false);
    }

    /**
     * Funcion accionada al precionar la CheckBox "hombre" o "mujer"
     * y se encarga de desactivar el sexo opuesto, para evitar errores
     * de tener ambos activados a la ves (Esta funcion pertenece a editar usuario).
     * @param event evento creado al precionar el CheckBox hombre o mujer.
     */
    public void switchSexo1(ActionEvent event){
        if(event.getSource().equals(cb_hombre1))
            cb_hombre1.setSelected(!cb_hombre1.isSelected());
        else
            cb_mujer1.setSelected(!cb_mujer1.isSelected());
    }


    /**
     * Metodo gatillado al precionar el boton Modificar usuario
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goModificarUsuario(ActionEvent event){
        //getScreenController().activate("ModificarUsuario");

        ap_EditUser.setVisible(true);
        ap_NewUser.setVisible(false);
        ap_Report.setVisible(false);
        ap_Girar.setVisible(false);
        ap_AdmSucursales.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goEditUser.setStyle("-fx-background-color: #345a72;");
        bmaster_goNewUser.setStyle("-fx-background-color: #334961;");
        bmaster_goReport.setStyle("-fx-background-color: #334961;");
        bmaster_goGirar.setStyle("-fx-background-color: #334961;");
        bmaster_goAdmSucursales.setStyle("-fx-background-color: #334961;");
    }

    /**
     * Metodo gatillado al precionar el boton Nuevo usuario
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goNewUser(ActionEvent event){
        ap_NewUser.setVisible(true);
        ap_EditUser.setVisible(false);
        ap_Report.setVisible(false);
        ap_Girar.setVisible(false);
        ap_AdmSucursales.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goNewUser.setStyle("-fx-background-color: #345a72;");
        bmaster_goEditUser.setStyle("-fx-background-color: #334961;");
        bmaster_goReport.setStyle("-fx-background-color: #334961;");
        bmaster_goGirar.setStyle("-fx-background-color: #334961;");
        bmaster_goAdmSucursales.setStyle("-fx-background-color: #334961;");
    }

    /**
     * Metodo gatillado al precionar el boton Reporte
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goReport(ActionEvent event){
        updateReporte();
        ap_Report.setVisible(true);
        ap_EditUser.setVisible(false);
        ap_NewUser.setVisible(false);
        ap_Girar.setVisible(false);
        ap_AdmSucursales.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goReport.setStyle("-fx-background-color: #345a72;");
        bmaster_goEditUser.setStyle("-fx-background-color: #334961;");
        bmaster_goNewUser.setStyle("-fx-background-color: #334961;");
        bmaster_goGirar.setStyle("-fx-background-color: #334961;");
        bmaster_goAdmSucursales.setStyle("-fx-background-color: #334961;");
    }

    /**
     * Metodo gatillado al precionar el boton Transferir
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goGirar(ActionEvent event){
        ap_Report.setVisible(false);
        ap_EditUser.setVisible(false);
        ap_NewUser.setVisible(false);
        ap_Girar.setVisible(true);
        ap_AdmSucursales.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goGirar.setStyle("-fx-background-color: #345a72;");
        bmaster_goEditUser.setStyle("-fx-background-color: #334961;");
        bmaster_goNewUser.setStyle("-fx-background-color: #334961;");
        bmaster_goReport.setStyle("-fx-background-color: #334961;");
        bmaster_goAdmSucursales.setStyle("-fx-background-color: #334961;");
    }

    /**
     * Metodo gatillado al precionar el boton Administrar Sucursales
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void goAdministrarSucursales(ActionEvent event){
        ap_Report.setVisible(false);
        ap_EditUser.setVisible(false);
        ap_NewUser.setVisible(false);
        ap_Girar.setVisible(false);
        ap_AdmSucursales.setVisible(true);

        // Cambiando colores de los botones laterales
        bmaster_goGirar.setStyle("-fx-background-color: #334961;");
        bmaster_goEditUser.setStyle("-fx-background-color: #334961;");
        bmaster_goNewUser.setStyle("-fx-background-color: #334961;");
        bmaster_goReport.setStyle("-fx-background-color: #334961;");
        bmaster_goAdmSucursales.setStyle("-fx-background-color: #345a72;");
    }

    /**
     * Metodo gatillado al precionar el boton Privacidad y Seguridad
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void actionSearch(ActionEvent event){
        if(searchUser(tf_searchRut.getText())){
            setVisibleUserInfo(true);
        }
        else{
            setVisibleUserInfo(false);
            generateDialog("Error", "Usuario no encontrado con el rut: " + tf_searchRut.getText());
        }
    }

    /**
     * Realiza una busqueda del usuario con el rut ingresado como parametro
     * @param event evento creado al precionar una tecla en la TextField
     */
    public void actionSearchKey(KeyEvent event){
        if (event.getCode().getName().equals("Enter")){
            if(searchUser(tf_searchRut.getText())){
                setVisibleUserInfo(true);
            }
            else{
                setVisibleUserInfo(false);
                generateDialog("Error", "Usuario no encontrado con el rut: " + tf_searchRut.getText());
            }
        }
    }

    public void disableStateComboboxCuentaBancariaInicial(ActionEvent event){
        comboBox_cuentaBancariaInicial.setDisable(!cb_permisoUsuario.isSelected());
    }

    /**
     * @param rut   Rut que se va a usar para buscar el usuario
     * @return      Retorna true si es que encuentra un usuario con ese rut, false en caso contrario
     */
    private boolean searchUser(String rut){
        // Buscar en el sistema con el rut y retornar true si lo encuentra
        // Editar los valores de los text field con los datos encontrados
        final Persona persona = banco.isPersonaOnBanco(rut);
        final boolean encontrado = persona != null;
        rutBuscado = rut;

        if(encontrado) {
            final String celular = persona.getTelefono();
            final String nombres = persona.getNombres();
            final String apellidos = persona.getApellidos();
            final String ciudad = persona.getCiudad();
            final String bRut = persona.getRut().substring(
                    persona.getRut().length() - 1);
            final String direccion = persona.getDireccion();
            final String correo = persona.getCorreoElectronico();
            final String estadoCivil = persona.getEstadoCivil();
            final LocalDate nacimiento = persona.getFechaNacimiento();
            final String nacionalidad = persona.getNacionalidad();
            final String sucursalAsociada = persona.getSucursalAsociada();

            final boolean hombre = persona.getGenero().equals("Hombre");

            // Setenado valores
            tf_nombres1.setText(nombres.toUpperCase());
            tf_apellidos1.setText(apellidos.toUpperCase());
            tf_ciudad1.setText(ciudad.toUpperCase());
            tf_direccion1.setText(direccion.toUpperCase());
            dp_fechaNacimiento1.setValue(nacimiento);
            tf_rut1.setText(rut.substring(0, rut.length()-2));
            tf_rutDB1.setText(bRut);
            tf_celular1.setText(celular);
            tf_correo1.setText(correo);
            comboBox_estadoCivil1.setValue(estadoCivil);
            cb_hombre1.setSelected(hombre);
            cb_mujer1.setSelected(!hombre);
            tf_nacionalidad1.setText(nacionalidad.toUpperCase());
            comboBox_sucursalAsociada1.setValue(sucursalAsociada);

            tb_permisosEjecutivo.setSelected(banco.contieneCuentaEjecutivo(rut));
            tb_permisosUsuario.setSelected(banco.contieneCuentaUsuario(rut));

            if(banco.contieneCuentaUsuario(rut)) {
                treeTableView.setDisable(false);
                b_eliminarCuentaBancaria.setDisable(false);
                b_nuevaCuentaBancaria.setDisable(false);
                comboBox_nuevaCuentaBancaria.setDisable(false);
                comboBox_cuentaBancariaEliminar.setDisable(false);
                updateTreeViewUserAccounts(persona.getCuentaUsuario());
                updateComboBoxCuentasBancarias(persona.getCuentaUsuario());
            }
            else{
                treeTableView.setDisable(true);
                b_eliminarCuentaBancaria.setDisable(true);
                b_nuevaCuentaBancaria.setDisable(true);
                comboBox_nuevaCuentaBancaria.setDisable(true);
                comboBox_cuentaBancariaEliminar.setDisable(true);
                clearTreeViewUserAccounts();
            }
        }

        return encontrado;
    }

    /**
     * Funcion gatillada al precionar el boton "modificar". Modifica
     * todos los datos almacenados del usuario
     * @param event evento ocacionado al precionar el boton modificar
     */
    public void modificarUsuario(ActionEvent event){
        Persona persona = banco.isPersonaOnBanco(rutBuscado);
        if(banco.getPermisos(cuentaAdministrador.getPersona()) >= banco.getPermisos(persona)) {
            final String nombres = tf_nombres1.getText();
            final String apellidos = tf_apellidos1.getText();
            final String rut = tf_rut1.getText() + "-" + tf_rutDB1.getText();
            System.out.println("Setenado rut " + rut);
            final String correo = tf_correo1.getText();
            final String celular = tf_celular1.getText();
            final String direccion = tf_direccion1.getText();
            final String ciudad = tf_ciudad1.getText();
            final String eCivil = (String) comboBox_estadoCivil1.getValue();
            final String nacionalidad = tf_nacionalidad1.getText();
            final int annoNacimiento = dp_fechaNacimiento1.getValue().getYear();
            final int mesNacimiento = dp_fechaNacimiento1.getValue().getMonthValue();
            final int diaNacimiento = dp_fechaNacimiento1.getValue().getDayOfMonth();
            final String genero; if(cb_hombre1.isSelected()) genero = "Hombre"; else genero = "Mujer";
            final String sucursal_asociada = (String) comboBox_sucursalAsociada1.getValue();

            banco.editarPersona(persona, nombres, apellidos, rut, ciudad, direccion, correo, celular,
                    nacionalidad, annoNacimiento, mesNacimiento, diaNacimiento, eCivil, genero, sucursal_asociada);
        }
        else {
            generateDialog("Error", "permisos insuficientes para modificar datos de "
                    + persona.getNombres() + " " + persona.getApellidos());
        }

    }

    /**
     * Actualiza la visivilidad de los elementos de la pensaña de Modificar Usuario
     * @param visible booleana que se le pasaran a todos los metodos de visibilidad
     */
    private void setVisibleUserInfo(boolean visible){
        comboBox_estadoCivil1.setVisible(visible);
        comboBox_nuevaCuentaBancaria.setVisible(visible);
        tf_nombres1.setVisible(visible);
        tf_apellidos1.setVisible(visible);
        tf_rut1.setVisible(visible);
        tf_rutDB1.setVisible(visible);
        tf_celular1.setVisible(visible);
        tf_correo1.setVisible(visible);
        tf_direccion1.setVisible(visible);
        tf_ciudad1.setVisible(visible);
        dp_fechaNacimiento1.setVisible(visible);
        cb_hombre1.setVisible(visible);
        tb_permisosUsuario.setVisible(visible);
        tb_permisosEjecutivo.setVisible(visible);
        cb_mujer1.setVisible(visible);
        b_nuevaCuentaBancaria.setVisible(visible);
        treeTableView.setVisible(visible);
        tf_nacionalidad1.setVisible(visible);
        comboBox_cuentaBancariaEliminar.setVisible(visible);
        comboBox_sucursalAsociada1.setVisible(visible);
        b_eliminarCuentaBancaria.setVisible(visible);
        b_eliminarPersona.setVisible(visible);
    }

    /**
     * **
     * Metodo usado para actualizar la instancia de banco almacenada en la ventana de ejecutivos
     * @param banco Instancia de banco que se va a usar a lo largo de la ejecucion del programa
     */
    public static void setBanco(Banco banco){
        ControllerAdministrador.banco = banco;
    }

    /**
     * Metodo usado para actualizar la cuenta administrador logueado.
     * @param cuentaAdministrador Instancia de cuenta administrador que se ha logueado
     */
    public static void setCuentaAdministrador(CuentaAdministrador cuentaAdministrador){
        ControllerAdministrador.cuentaAdministrador = cuentaAdministrador;
    }

    /**
     * Desloguea al ejecutivo y lo envia a la ventana de login
     * @param event Evento creado al precionar el boton login.
     */
    public void goLogin(ActionEvent event){
        goNewUser(event);
        resetAll(event);
        getScreenController().activate("Login");
    }

    @Override
    public void updateController(){
        System.out.println("Updated controllerEjecutivo data");
    }

    /**
     * Actualiza la tabla de cuentas bancarias del usuario buscado en la pestaña de modificar usuario
     * @param cuentaUsuario Cuenta de usuario buscado previamente.
     */
    public void updateTreeViewUserAccounts(CuentaUsuario cuentaUsuario){
        ObservableList data = FXCollections.observableArrayList();
        CuentaBancaria cb;
        for(int i = 0; i < cuentaUsuario.getCuentasBancarias().size(); i++){
            cb = cuentaUsuario.getCuentasBancarias().get(i);
            data.add(new CuentaBancariaRecursiveTree(
                    String.valueOf(cb.isCuentaBloqueada() == false), String.valueOf(cb.getIdentificador()),  cb.getTipoCuenta(),
                    String.valueOf(cb.getMonto())
            ));
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
     * Permite limpiar el contenido de la tabla de cuentas bancarias en la pestaña
     * de modificar usuario.
     */
    private void clearTreeViewUserAccounts() {
        ObservableList data = FXCollections.observableArrayList();
        TreeItem<CuentaBancariaRecursiveTree> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
    }

        /**
         * Agrega una nueva cuenta bancaria del tipo seleccionado en el comboBox
         * al usuario buscado previamente.
         * @param event evento creado al precionar el boton de crear cuenta bancaria
         */
    public void agregarCuentaBancaria(ActionEvent event){
        if(comboBox_nuevaCuentaBancaria.getValue() == null) {
            generateDialog("Error", "Primero seleccione el tipo de cuenta bancaria que quiere crear");
            return;
        }
        CuentaUsuario usuario = banco.isUsuarioOnBanco(rutBuscado);
        banco.agregarCuentaBancaria(cuentaAdministrador,usuario,(String)comboBox_nuevaCuentaBancaria.getValue());
        updateTreeViewUserAccounts(usuario);
        updateComboBoxCuentasBancarias(usuario);
        generateDialog("Mensaje",banco.getLastError());
    }

    /**
     * Elimina una cuenta bancaria seleccionada en el comboBox de cuentas
     * bancarias del usuario buscado previamente.
     * @param event evento creado al precionar el boton de eliminar cuenta bancaria
     */
    public void eliminarCuentaBancaria(ActionEvent event){
        if(comboBox_cuentaBancariaEliminar.getValue() == null){
            generateDialog("Error","Se tiene que seleccionar una cuenta bancaria para eliminar");
            return;
        }
        CuentaUsuario usuario =  banco.isUsuarioOnBanco(rutBuscado);
        banco.eliminarCuentaBancaria(cuentaAdministrador,usuario,(Long) comboBox_cuentaBancariaEliminar.getValue());
        updateTreeViewUserAccounts(usuario);
        updateComboBoxCuentasBancarias(usuario);
        generateDialog("Mensaje",banco.getLastError());
    }

    public void eliminarPersona(ActionEvent event){

        String rut = tf_searchRut.getText();
        Persona p = banco.isPersonaOnBanco(rut);
        if(p != null) {
            banco.eliminarPersona(cuentaAdministrador, p.getRut());
            generateDialog("Operacion Exitosa", p.getNombres() + " " + p.getApellidos() + " ha sido eliminado");
        }
        else
            generateDialog("Error", "Usuario no encontrado con el rut indicado");

    }

    /**
     * Actualiza el recurso ComboBox con las cuentas de la cuenta usuario ingresada
     * como paremtro
     * @param cuentaUsuario Cuenta usuario a la que le corresponden las cuentas bancarias
     */
    private void updateComboBoxCuentasBancarias(CuentaUsuario cuentaUsuario){
        comboBox_cuentaBancariaEliminar.getItems().removeAll((comboBox_cuentaBancariaEliminar.getItems()));

        for(CuentaBancaria cuentaBancaria : cuentaUsuario.getCuentasBancarias())
            comboBox_cuentaBancariaEliminar.getItems().add(cuentaBancaria.getIdentificador());
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
     * Realiza una busqueda para realizar un deposito o retiro de dinero
     * @param event evento generado al precionar el boton de buscar
     */
    public void searchUserGiro(ActionEvent event){
        rutBuscadoGiro = tf_searchRutGiro.getText();
        CuentaUsuario cuentaUsuario = banco.isUsuarioOnBanco(rutBuscadoGiro);

        if(cuentaUsuario != null){
            comboBox_cuentaBancariaGiro.getItems().removeAll(comboBox_cuentaBancariaGiro.getItems());
            for(CuentaBancaria c : cuentaUsuario.getCuentasBancarias())
                comboBox_cuentaBancariaGiro.getItems().add(c.getIdentificador());
            setVisibleGiroItems(true);
        }
        else{
            comboBox_cuentaBancariaGiro.getItems().removeAll(comboBox_cuentaBancariaGiro.getItems());
            setVisibleGiroItems(false);
            generateDialog("Error", "Usuario no encontrado con el rut indicado");
        }
    }

    /**
     * Actualiza la visualizacion de los elementos de la pestaña Giro
     * en la pantalla de ejecutivo
     * @param visible  booleana que se le dara a todos los elementos
     */
    private void setVisibleGiroItems(boolean visible){
        tf_montoGirar.setVisible(visible);
        b_depositar.setVisible(visible);
        b_retirar.setVisible(visible);
        comboBox_cuentaBancariaGiro.setVisible(visible);


    }


    /**
     * Realiza un deposito al usuario previamente buscado y a la cuenta
     * seleccionada en el combobox.
     */
    public void hacerDeposito(ActionEvent event){
        /*
        int monto = Integer.parseInt(tf_montoGirar.getText());
        if(comboBox_cuentaBancariaGiro.getValue() == null){
            generateDialog("Error", "Primero se tiene que seleccionar una cuenta bancaria");
            return;
        }
        banco.depositarCuentaBancaria(cuentaEjecutivo, (Long) comboBox_cuentaBancariaGiro.getValue(),
                monto);
        generateDialog(" ", banco.getLastError());
        */
        // FIXME: Ojo, el administrador puede hacer depositos y retiros? si es asi falta programar la sobre carga
    }

    /**
     * Realiza un retiro al usuario previamente buscado y a la cuenta
     * seleccionada en el combobox.
     */
    public void hacerRetiro(ActionEvent event){
        /*
        int monto = Integer.parseInt(tf_montoGirar.getText());
        if(comboBox_cuentaBancariaGiro.getValue() == null){
            generateDialog("Error", "Primero se tiene que seleccionar una cuenta bancaria");
            return;
        }
        banco.retirarCuentaBancaria(cuentaEjecutivo, (Long) comboBox_cuentaBancariaGiro.getValue(),
                monto);
        generateDialog(" ", banco.getLastError());
        */
        // FIXME: Ojo, el administrador puede hacer depositos y retiros? si es asi falta programar la sobre carga
    }

    /**
     * Actualiza la pestaña de reportes con los datos actuales del banco y prepara
     * los datos para escribir el archivo de reporte.
     */
    private void updateReporte(){
        reportLines.removeAll(reportLines);
        String ciudadMasAdinerada = "";
        long ciudadMasAdinerada_valor = 0;
        String ciudadMenosAdinerada = "";
        long ciudadMenosAdinerada_valor = 0;
        boolean primeraIteracion = true;
        long totalValor = 0;
        long valorEstaCiudad = 0;
        long totalCuentas = banco.cantidadCuentasBancarias();

        ObservableList data = FXCollections.observableArrayList();

        // Limpiando data
        scatterChart.getData().removeAll(scatterChart.getData());

        // Creando recursos
        XYChart.Series series = new XYChart.Series();
        HashMap<String, Long> mapaCiudades = banco.generarReportePorCiudad();

        reportLines.add("Ciudad,Valor,Usuarios");

        // Agregando data
        for(String ciudad : mapaCiudades.keySet()) {
            valorEstaCiudad = mapaCiudades.get(ciudad);
            if(primeraIteracion){
                ciudadMasAdinerada = ciudad;
                ciudadMasAdinerada_valor = valorEstaCiudad;
                ciudadMenosAdinerada = ciudad;
                ciudadMenosAdinerada_valor = valorEstaCiudad;
                primeraIteracion = false;
            }
            else{
                if(ciudadMasAdinerada_valor < valorEstaCiudad){
                    ciudadMasAdinerada = ciudad;
                    ciudadMasAdinerada_valor = valorEstaCiudad;
                }
                if(ciudadMenosAdinerada_valor > valorEstaCiudad){
                    ciudadMenosAdinerada = ciudad;
                    ciudadMenosAdinerada_valor = valorEstaCiudad;
                }
            }

            totalValor += valorEstaCiudad;
            series.getData().add(new XYChart.Data(ciudad, mapaCiudades.get(ciudad)));
            data.add(new DineroPorCiudadYClientes(
                    ciudad, String.valueOf(valorEstaCiudad), String.valueOf(banco.totalclientesEnCiudad(ciudad))
            ));
            reportLines.add(ciudad + "," +  String.valueOf(valorEstaCiudad) + "," + String.valueOf(banco.totalclientesEnCiudad(ciudad)));
        }
        reportLines.add("");
        scatterChart.getData().add(series);

        TreeTableColumn c1 = (TreeTableColumn) treeTableViewReporteCiudad.getColumns().get(0);
        TreeTableColumn c2 = (TreeTableColumn) treeTableViewReporteCiudad.getColumns().get(1);
        TreeTableColumn c3 = (TreeTableColumn) treeTableViewReporteCiudad.getColumns().get(2);

        c1.setCellValueFactory(
                new TreeItemPropertyValueFactory<DineroPorCiudadYClientes,String>("ciudad")
        );
        c2.setCellValueFactory(
                new TreeItemPropertyValueFactory<DineroPorCiudadYClientes,String>("monto")
        );
        c3.setCellValueFactory(
                new TreeItemPropertyValueFactory<DineroPorCiudadYClientes,String>("cclientes")
        );

        TreeItem<CuentaBancariaRecursiveTree> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        treeTableViewReporteCiudad.setRoot(root);

        treeTableViewReporteCiudad.setShowRoot(false);

        lb_1.setText("T. Clientes:\t\t" + banco.totalclientes());
        lb_2.setText("T. Cuentas B:\t\t" + banco.cantidadCuentasBancarias());
        lb_3.setText("C. Mas Valor:\t\t" + ciudadMasAdinerada + " (" + ciudadMasAdinerada_valor + ")");
        lb_4.setText("C. Menos Valor:\t" + ciudadMenosAdinerada + " (" + ciudadMenosAdinerada_valor + ")");
        lb_5.setText("T. Valor:\t\t\t" + totalValor);

        reportLines.add("T. Clientes," + banco.totalclientes());
        reportLines.add("T. Cuentas B,\t\t" + banco.cantidadCuentasBancarias());
        reportLines.add("C. Mas Valor,\t\t" + ciudadMasAdinerada + "," + ciudadMasAdinerada_valor);
        reportLines.add("C. Menos Valor,\t" + ciudadMenosAdinerada + "," + ciudadMenosAdinerada_valor);
        reportLines.add("T. Valor,\t\t\t" + totalValor);
    }

    /**
     * Genera un archivo de reporte en la carpeta Resumen.
     * @param event Evento creado al precionar el boton de generar reporte por archivo.
     */
    public void generarReportePorArchivo(ActionEvent event){

        // Esto genera el reporte con el script de python
        PythonRunner pr = new PythonRunner();
        try {
            pr.run("Reportes.py");
        }catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }


        // Esto genera el reporte sin el script de python
        final String sep = ",";
        String filePath = (new File(".").getAbsolutePath()).replace(".", "") + "Resumen\\Reporte simple.csv";
        PrintWriter writer;
        try {
            writer = new PrintWriter(filePath, "UTF-8");
            for(String line : reportLines)
                writer.println(line);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void switchPermisosEjecutivo(){
        if(tb_permisosEjecutivo.isSelected()){
            banco.otorgarPermisosSuperiores(Banco.PERMISO_EJECUTIVO, rutBuscado);
            System.out.println("Otorgando permisos de ejecutivo a: " + rutBuscado);

        }
        else{
            banco.revocarPermisosSuperiores(rutBuscado);
            System.out.println("Rebocando permisos de ejecutivo a: " + rutBuscado);
        }

    }

    private void updateSucursalesOnTreeTableView(){
        ObservableList data = FXCollections.observableArrayList();
        data.addAll(banco.obtenerSucursalesForTable());

        TreeTableColumn c1 = (TreeTableColumn) treeTableViewSucursales.getColumns().get(0);
        TreeTableColumn c2 = (TreeTableColumn) treeTableViewSucursales.getColumns().get(1);
        TreeTableColumn c3 = (TreeTableColumn) treeTableViewSucursales.getColumns().get(2);

        c1.setCellValueFactory(
                new TreeItemPropertyValueFactory<SucursalTreeTableView, String>("nombre")
        );
        c2.setCellValueFactory(
                new TreeItemPropertyValueFactory<SucursalTreeTableView, String>("direccion")
        );
        c3.setCellValueFactory(
                new TreeItemPropertyValueFactory<SucursalTreeTableView, String>("codigo")
        );

        TreeItem<SucursalTreeTableView> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        treeTableViewSucursales.setRoot(root);

        treeTableViewSucursales.setShowRoot(false);

    }

    public void agregarSucursal(){
        String nombre = tf_nombreNuevaSucursal.getText();
        String direccion = tf_direccionNuevaSucursal.getText();

        if(nombre.equals("") || direccion.equals("")){
            generateDialog("Error", "Primero indique el nombre y la direccion de la sucursal");
            return;
        }

        banco.agregarSucursal(nombre, direccion);
        generateDialog("", banco.getLastError());

        // Actualizando recursos graficos
        updateSucursalesOnTreeTableView();
        tf_nombreNuevaSucursal.clear();
        tf_direccionNuevaSucursal.clear();

    }

    public void eliminarSucursal(){
        TreeItem<SucursalTreeTableView> selectedItem = (TreeItem<SucursalTreeTableView>) treeTableViewSucursales.getSelectionModel().getSelectedItem();
        if(selectedItem == null)
            generateDialog("Error", "Primero seleccione una sucursal para borrar");
        else {
            banco.eliminarSucursal(selectedItem.getValue().getNombre());
            generateDialog("", banco.getLastError());
        }

        updateSucursalesOnTreeTableView();
    }
}




