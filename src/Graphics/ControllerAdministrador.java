package Graphics;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import criteriosBusqueda.*;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import system.excepciones.PersonaInexistente;
import system.excepciones.RutInvalido;
import system.general.*;
import system.interfaces.Criteria;
import system.systemAccounts.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
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
    private JFXButton bmaster_goBusquedaCriterio;

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
    private AnchorPane ap_BusquedaCriterio;

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

    // Recursos Busqueda por criterio
    @FXML private JFXTreeTableView treeTableViewBusquedaCriterio;
    @FXML private JFXToggleButton tb_criterioGenero;
    @FXML private JFXToggleButton tb_criterioCiudad;
    @FXML private JFXToggleButton tb_criterioDineroTope;
    @FXML private JFXCheckBox cb_hombreCriterio;
    @FXML private JFXCheckBox cb_mujerCriterio;
    @FXML private JFXTextField tf_ciudadCriterio;
    @FXML private JFXTextField tf_dineroTopeCriterio;


    // Otras variables
    private String rutBuscadoGiro;
    private String rutBuscado;
    private ArrayList<String> reportLines;
    private static CuentaAdministrador cuentaAdministrador;
    private Banco banco = Banco.getInstance();
    private final String MARKED_STYLE = "-fx-background-color: #345a72;";
    private final String UNMARKED_STYLE = "-fx-background-color: #334961;";


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

        updateSucursalesOnComboBox();
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
        try {
            banco.agregarPersona(cuentaAdministrador, nombres, apellidos, rut, ciudad, direccion, correo,
                    celular, nacionalidad, annoNacimiento, mesNacimiento, diaNacimiento, eCivil, genero, sucursalAsociada);
        }catch(SQLException e){
            generateDialog("Error", "Problema al conectarse con la base de dato.");
        } catch (PersonaInexistente personaInexistente) {
            generateDialog("Error", "El rut " + rut + " no se eneucntra en el sistema.");
        }catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
            return;
        }

        // Dando permisos de usuarios a la persona y agregando su primera cuenta bancaria
        if(cb_permisoUsuario.isSelected()){
            System.out.println("Agregando Permisos Usuario a" + nombres);
            try {
                banco.otorgarPermisosUsuario(rut);
                banco.agregarCuentaBancaria(cuentaAdministrador, banco.isUsuarioOnBanco(rut),
                        (String) comboBox_cuentaBancariaInicial.getValue());
            }catch (SQLException e){
                generateDialog("Error", "Problema al conectarse con la base de dato.");
            } catch (PersonaInexistente personaInexistente) {
                generateDialog("Error", "El rut " + rut + " no se eneucntra en el sistema.");
            }catch (RutInvalido rutInvalido) {
                generateDialog("Error", "Rut no valido");
                return;
            }
        }

        // Dando permisos de ejecutivo a la persona
        if(cb_permisoEjecutivo.isSelected()){
            System.out.println("Agregando Permisos Ejecutivo a " + nombres);
            try {
                banco.otorgarPermisosSuperiores(Banco.PERMISO_EJECUTIVO, rut);
            }catch (SQLException e){
                generateDialog("Error", "Problema al conectarse con la base de dato.");
            } catch (PersonaInexistente personaInexistente) {
                generateDialog("Error", "El rut " + rut + " no se eneucntra en el sistema.");
            }catch (RutInvalido rutInvalido) {
                generateDialog("Error", "Rut no valido");
                return;
            }
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
        ap_BusquedaCriterio.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goEditUser.setStyle(MARKED_STYLE);
        bmaster_goNewUser.setStyle(UNMARKED_STYLE);
        bmaster_goReport.setStyle(UNMARKED_STYLE);
        bmaster_goGirar.setStyle(UNMARKED_STYLE);
        bmaster_goAdmSucursales.setStyle(UNMARKED_STYLE);
        bmaster_goBusquedaCriterio.setStyle(UNMARKED_STYLE);
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
        ap_BusquedaCriterio.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goNewUser.setStyle(MARKED_STYLE);
        bmaster_goEditUser.setStyle(UNMARKED_STYLE);
        bmaster_goReport.setStyle(UNMARKED_STYLE);
        bmaster_goGirar.setStyle(UNMARKED_STYLE);
        bmaster_goAdmSucursales.setStyle(UNMARKED_STYLE);
        bmaster_goBusquedaCriterio.setStyle(UNMARKED_STYLE);
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
        ap_BusquedaCriterio.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goReport.setStyle(MARKED_STYLE);
        bmaster_goEditUser.setStyle(UNMARKED_STYLE);
        bmaster_goNewUser.setStyle(UNMARKED_STYLE);
        bmaster_goGirar.setStyle(UNMARKED_STYLE);
        bmaster_goAdmSucursales.setStyle(UNMARKED_STYLE);
        bmaster_goBusquedaCriterio.setStyle(UNMARKED_STYLE);
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
        ap_BusquedaCriterio.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goGirar.setStyle(MARKED_STYLE);
        bmaster_goEditUser.setStyle(UNMARKED_STYLE);
        bmaster_goNewUser.setStyle(UNMARKED_STYLE);
        bmaster_goReport.setStyle(UNMARKED_STYLE);
        bmaster_goAdmSucursales.setStyle(UNMARKED_STYLE);
        bmaster_goBusquedaCriterio.setStyle(UNMARKED_STYLE);
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
        ap_BusquedaCriterio.setVisible(false);

        // Cambiando colores de los botones laterales
        bmaster_goGirar.setStyle(UNMARKED_STYLE);
        bmaster_goEditUser.setStyle(UNMARKED_STYLE);
        bmaster_goNewUser.setStyle(UNMARKED_STYLE);
        bmaster_goReport.setStyle(UNMARKED_STYLE);
        bmaster_goAdmSucursales.setStyle(MARKED_STYLE);
        bmaster_goBusquedaCriterio.setStyle(UNMARKED_STYLE);
    }

    /**
     * Evento gatillado al presionar el boton de busqueda por cirterio en la barra
     * lateral izquierda.
     */
    public void goBusquedaCriterio(){
        ap_Report.setVisible(false);
        ap_EditUser.setVisible(false);
        ap_NewUser.setVisible(false);
        ap_Girar.setVisible(false);
        ap_AdmSucursales.setVisible(false);
        ap_BusquedaCriterio.setVisible(true);

        // Cambiando colores de los botones laterales
        bmaster_goGirar.setStyle(UNMARKED_STYLE);
        bmaster_goEditUser.setStyle(UNMARKED_STYLE);
        bmaster_goNewUser.setStyle(UNMARKED_STYLE);
        bmaster_goReport.setStyle(UNMARKED_STYLE);
        bmaster_goAdmSucursales.setStyle(UNMARKED_STYLE);
        bmaster_goBusquedaCriterio.setStyle(MARKED_STYLE);
    }

    /**
     * Metodo gatillado al precionar el boton Privacidad y Seguridad
     * permitiendo limpiar la pantalla y mostrar la que corresponde.
     */
    public void actionSearch(ActionEvent event){
        try {
            if (searchUser(tf_searchRut.getText())) {
                setVisibleUserInfo(true);
            } else {
                setVisibleUserInfo(false);
                generateDialog("Error", "Usuario no encontrado con el rut: " + tf_searchRut.getText());
            }
        }catch (PersonaInexistente personaInexistente){
            generateDialog("Error", "El rut " + tf_searchRut.getText() + " no se eneucntra en el sistema.");
        }catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
        }
    }

    /**
     * Realiza una busqueda del usuario con el rut ingresado como parametro
     * @param event evento creado al precionar una tecla en la TextField
     */
    public void actionSearchKey(KeyEvent event){
        if (event.getCode().getName().equals("Enter")){
            try {
                if (searchUser(tf_searchRut.getText())) {
                    setVisibleUserInfo(true);
                } else {
                    setVisibleUserInfo(false);
                    generateDialog("Error", "Usuario no encontrado con el rut: " + tf_searchRut.getText());
                }
            }catch (PersonaInexistente personaInexistente){
                generateDialog("Error", "El rut " + tf_searchRut + " no se eneucntra en el sistema.");
            }catch (RutInvalido rutInvalido) {
                generateDialog("Error", "Rut no valido");
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
    private boolean searchUser(String rut) throws PersonaInexistente, RutInvalido {
        // Buscar en el sistema con el rut y retornar true si lo encuentra
        // Editar los valores de los text field con los datos encontrados
        final Persona persona;
        try {
            persona = banco.isPersonaOnBanco(rut);
        }catch(PersonaInexistente personaInexistente){
            generateDialog("Error", "El rut " + rut + " no se eneucntra en el sistema.");
            return false;
        }catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
            return false;
        }
        final boolean encontrado = persona != null;
        rutBuscado = rut;

        if(encontrado){
            if(banco.getPermisos(persona) > banco.getPermisos(cuentaAdministrador.getPersona())){
                generateDialog("Error" , "No puede modificar a personas con permisos superiores a los suyos.");
                return false;
            }
        }

        if(cuentaAdministrador.getPersona().getRut().equals(rut)){
            generateDialog("Error" , "No se puede modificar a su misma persona.");
            return false;
        }
        else {

            if (encontrado) {
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

                tb_permisosEjecutivo.setSelected(banco.contieneCuentaEjecutivo(rut));
                tb_permisosUsuario.setSelected(banco.contieneCuentaUsuario(rut));

                // Setenado valores
                tf_nombres1.setText(nombres.toUpperCase());
                tf_apellidos1.setText(apellidos.toUpperCase());
                tf_ciudad1.setText(ciudad.toUpperCase());
                tf_direccion1.setText(direccion.toUpperCase());
                dp_fechaNacimiento1.setValue(nacimiento);
                tf_rut1.setText(rut.substring(0, rut.length() - 2));
                tf_rutDB1.setText(bRut);
                tf_celular1.setText(celular);
                tf_correo1.setText(correo);
                comboBox_estadoCivil1.setValue(estadoCivil);
                cb_hombre1.setSelected(hombre);
                cb_mujer1.setSelected(!hombre);
                tf_nacionalidad1.setText(nacionalidad.toUpperCase());
                comboBox_sucursalAsociada1.setValue(sucursalAsociada);

                if (banco.contieneCuentaUsuario(rut)) {
                    treeTableView.setDisable(false);
                    b_eliminarCuentaBancaria.setDisable(false);
                    b_nuevaCuentaBancaria.setDisable(false);
                    comboBox_nuevaCuentaBancaria.setDisable(false);
                    comboBox_cuentaBancariaEliminar.setDisable(false);
                    updateTreeViewUserAccounts(persona.getCuentaUsuario());
                    updateComboBoxCuentasBancarias(persona.getCuentaUsuario());
                } else {
                    treeTableView.setDisable(true);
                    b_eliminarCuentaBancaria.setDisable(true);
                    b_nuevaCuentaBancaria.setDisable(true);
                    comboBox_nuevaCuentaBancaria.setDisable(true);
                    comboBox_cuentaBancariaEliminar.setDisable(true);
                    clearTreeViewUserAccounts();
                }
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
        Persona persona;
        try {
            persona = banco.isPersonaOnBanco(rutBuscado);
        }catch (PersonaInexistente personaInexistente){
            generateDialog("Error", "El rut " + rutBuscado + " no se eneucntra en el sistema.");
            return;
        }catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
            return;
        }
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

            try {
                banco.editarPersona(persona, nombres, apellidos, rut, ciudad, direccion, correo, celular,
                        nacionalidad, annoNacimiento, mesNacimiento, diaNacimiento, eCivil, genero, sucursal_asociada);
            }catch (SQLException e){
                generateDialog("Error", "Problema al conectarse con la base de dato.");
            }
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
        CuentaUsuario usuario;
        try {
            usuario = banco.isUsuarioOnBanco(rutBuscado);
        }catch (PersonaInexistente personaInexistente){
            generateDialog("Error", "El rut " + rutBuscado + " no se eneucntra en el sistema.");
            return;
        } catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
            return;
        }
        try {
            banco.agregarCuentaBancaria(cuentaAdministrador, usuario, (String) comboBox_nuevaCuentaBancaria.getValue());
        }catch (SQLException e){
            generateDialog("Error", "Problema al conectarse con la base de dato.");
        }
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
        
        CuentaUsuario usuario;
        try {
            usuario = banco.isUsuarioOnBanco(rutBuscado);
        }catch(PersonaInexistente personaInexistente){
            generateDialog("Error", "El rut " + rutBuscado + " no se eneucntra en el sistema.");
            return;
        }catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
            return;
        }

        try {
            banco.eliminarCuentaBancaria(cuentaAdministrador, usuario, (Long) comboBox_cuentaBancariaEliminar.getValue());
        } catch (SQLException e){
            generateDialog("Error", "Problema al conectarse con la base de dato.");
            return;
        }

        updateTreeViewUserAccounts(usuario);
        updateComboBoxCuentasBancarias(usuario);
        generateDialog("Mensaje",banco.getLastError());
    }

    public void eliminarPersona(ActionEvent event){

        String rut = tf_searchRut.getText();
        Persona p;
        try {
            p = banco.isPersonaOnBanco(rut);
        }catch(PersonaInexistente personaInexistente){
            generateDialog("Error", "El rut " + rut + " no se eneucntra en el sistema.");
            return;
        }catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
            return;
        }
        if(p != null) {
            try {
                banco.eliminarPersona(cuentaAdministrador, p.getRut());
            } catch (SQLException e){
                generateDialog("Error", "Problema al conectarse con la base de dato.");
            } catch (PersonaInexistente personaInexistente) {
                generateDialog("Error", "El rut " + rut + " no se eneucntra en el sistema.");
            }catch (RutInvalido rutInvalido) {
                generateDialog("Error", "Rut no valido");
                return;
            }
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
     */
    public void searchUserGiro(){
        rutBuscadoGiro = tf_searchRutGiro.getText();
        CuentaUsuario cuentaUsuario;
        try {
            cuentaUsuario = banco.isUsuarioOnBanco(rutBuscadoGiro);
        }catch (PersonaInexistente personaInexistente){
            generateDialog("Error", "El rut " + rutBuscadoGiro + " no se eneucntra en el sistema.");
            return;
        }catch (RutInvalido rutInvalido) {
            generateDialog("Error", "Rut no valido");
            return;
        }

        if(cuentaUsuario != null && cuentaUsuario.getPersona().getRut().equals(cuentaAdministrador.getPersona().getRut())){
            generateDialog("Error" , "No puede modificar sus propios montos. Intentelo con otra persona.");
            return;
        }

        if(cuentaUsuario != null){
            comboBox_cuentaBancariaGiro.getItems().removeAll(comboBox_cuentaBancariaGiro.getItems());
            for(CuentaBancaria c : cuentaUsuario.getCuentasBancarias())
                comboBox_cuentaBancariaGiro.getItems().add(c.getIdentificador());
            setVisibleGiroItems(true);
        }
        else{
            comboBox_cuentaBancariaGiro.getItems().removeAll(comboBox_cuentaBancariaGiro.getItems());
            setVisibleGiroItems(false);
            generateDialog("Error", "Usuario no encontrado con el rut indicado.");
        }
    }

    /**
     * Realiza una busqueda para realizar un deposito o retiro de dinero
     * @param event evento generado al precionar el enter de buscar
     */
    public void searchUserGiroEnter(KeyEvent event){
        if (event.getCode().getName().equals("Enter")) {
            searchUserGiro();
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
        int monto = Integer.parseInt(tf_montoGirar.getText());
        if(comboBox_cuentaBancariaGiro.getValue() == null){
            generateDialog("Error", "Primero se tiene que seleccionar una cuenta bancaria");
            return;
        }
        try {
            banco.depositarCuentaBancaria(cuentaAdministrador, (Long) comboBox_cuentaBancariaGiro.getValue(),
                    monto);
        } catch(SQLException e){
            generateDialog("Error", "Problema al conectarse con la base de dato.");
        }
        generateDialog(" ", banco.getLastError());
    }

    /**
     * Realiza un retiro al usuario previamente buscado y a la cuenta
     * seleccionada en el combobox.
     */
    public void hacerRetiro(ActionEvent event){
        int monto = Integer.parseInt(tf_montoGirar.getText());
        if(comboBox_cuentaBancariaGiro.getValue() == null){
            generateDialog("Error", "Primero se tiene que seleccionar una cuenta bancaria");
            return;
        }
        try {
            banco.retirarCuentaBancaria(cuentaAdministrador, (Long) comboBox_cuentaBancariaGiro.getValue(),
                    monto);
        } catch (SQLException e){
            generateDialog("Error", "Problema al conectarse con la base de dato.");
        }
        generateDialog(" ", banco.getLastError());
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
            try {
                banco.otorgarPermisosSuperiores(Banco.PERMISO_EJECUTIVO, rutBuscado);
            } catch(SQLException e){
                generateDialog("Error", "Problema al conectarse con la base de dato.");
            } catch (PersonaInexistente personaInexistente) {
                generateDialog("Error", "El rut " + rutBuscado + " no se eneucntra en el sistema.");
            }catch (RutInvalido rutInvalido) {
                generateDialog("Error", "Rut no valido");
                return;
            }
            System.out.println("Otorgando permisos de ejecutivo a: " + rutBuscado);

        }
        else{
            try {
                banco.revocarPermisosSuperiores(rutBuscado);
            } catch(SQLException e){
                generateDialog("Error", "Problema al conectarse con la base de dato.");
            } catch (PersonaInexistente personaInexistente) {
                generateDialog("Error", "El rut " + rutBuscado + " no se eneucntra en el sistema.");
            }catch (RutInvalido rutInvalido) {
                generateDialog("Error", "Rut no valido");
                return;
            }
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

    private void updateSucursalesOnComboBox(){
        comboBox_sucursalAsociada.getItems().removeAll(comboBox_sucursalAsociada.getItems());
        comboBox_sucursalAsociada.getItems().addAll(banco.obtenerNombresSucursales());

        comboBox_sucursalAsociada1.getItems().removeAll(comboBox_sucursalAsociada.getItems());
        comboBox_sucursalAsociada1.getItems().addAll(banco.obtenerNombresSucursales());
    }

    public void agregarSucursal(){
        String nombre = tf_nombreNuevaSucursal.getText();
        String direccion = tf_direccionNuevaSucursal.getText();

        if(nombre.equals("") || direccion.equals("")){
            generateDialog("Error", "Primero indique el nombre y la direccion de la sucursal");
            return;
        }

        try {
            banco.agregarSucursal(nombre, direccion);
        }catch (SQLException e){
            generateDialog("Error", "Problema al conectarse con la base de dato.");
        }
        generateDialog("", banco.getLastError());

        // Actualizando recursos graficos
        updateSucursalesOnTreeTableView();
        updateSucursalesOnComboBox();
        tf_nombreNuevaSucursal.clear();
        tf_direccionNuevaSucursal.clear();

    }

    public void eliminarSucursal(){
        TreeItem<SucursalTreeTableView> selectedItem = (TreeItem<SucursalTreeTableView>) treeTableViewSucursales.getSelectionModel().getSelectedItem();
        if(selectedItem == null)
            generateDialog("Error", "Primero seleccione una sucursal para borrar");
        else {
            try {
                banco.eliminarSucursal(selectedItem.getValue().getNombre());
            } catch (SQLException e){
                generateDialog("Error", "Problema al conectarse con la base de dato.");
            }
            generateDialog("", banco.getLastError());
        }

        updateSucursalesOnTreeTableView();
        updateSucursalesOnComboBox();
    }

    /**
     * Evento gatillado al presionar un toggle button dentro de la ventana de busqueda por criterio.
     * Realiza un update de las habilitaciones de los recursos de los criterios.
     * (Ejemplo, habilita el textField de la ciudad a filtrar si es que el criterio de ciudad esta habilitado)
     */
    public void habilitarRecursosDeCriterio(){
        cb_hombreCriterio.setDisable(!tb_criterioGenero.isSelected());
        cb_mujerCriterio.setDisable(!tb_criterioGenero.isSelected());
        tf_ciudadCriterio.setDisable(!tb_criterioCiudad.isSelected());
        tf_dineroTopeCriterio.setDisable(!tb_criterioDineroTope.isSelected());
    }

    /**
     * Update del treeTableView
     */
    public void filtrarPorCirterio(){
        ArrayList<Criteria> criterios = new ArrayList<>();

        if(tb_criterioDineroTope.isSelected())
            if(!tb_criterioDineroTope.getText().isEmpty())
                criterios.add(new CriterioDinero(Long.parseLong(tf_dineroTopeCriterio.getText())));
            else
                generateDialog("Error", "El criterio de busqueda por Dinero no puede ser Vacio");

        if(tb_criterioGenero.isSelected() && cb_mujerCriterio.isSelected() && cb_hombreCriterio.isSelected())
            criterios.add(new CriterioOr(new CriteriaFemale(), new CriteriaMale()));
        else if(tb_criterioGenero.isSelected() && cb_mujerCriterio.isSelected())
            criterios.add(new CriteriaFemale());
        else if(tb_criterioGenero.isSelected() && cb_hombreCriterio.isSelected())
            criterios.add(new CriteriaMale());
        if(tb_criterioCiudad.isSelected())
            if(!tb_criterioCiudad.getText().isEmpty())
                criterios.add(new CriteriaCiudad(tf_ciudadCriterio.getText()));
            else
                generateDialog("Error", "El criterio de busqueda por Ciudad no puede ser Vacio");


        ArrayList<Persona> personas = banco.filtrarPersonas(criterios);

        // Actualizando tabla
        ObservableList data = FXCollections.observableArrayList();
        for(Persona p : personas){
            if(p.getCuentaUsuario() == null)
                continue;
            // actualizando la data de la tabla
            data.add(new PersonaTreeTableView(
                    p.getRut(), p.getNombres() + " " + p.getApellidos(),
                    String.valueOf(p.getCuentaUsuario().obtenerDineroTotal()), String.valueOf(p.getCuentaUsuario().getNumeroCuentas())
            ));
        }

        TreeTableColumn c1 = (TreeTableColumn) treeTableViewBusquedaCriterio.getColumns().get(0);
        TreeTableColumn c2 = (TreeTableColumn) treeTableViewBusquedaCriterio.getColumns().get(1);
        TreeTableColumn c3 = (TreeTableColumn) treeTableViewBusquedaCriterio.getColumns().get(2);
        TreeTableColumn c4 = (TreeTableColumn) treeTableViewBusquedaCriterio.getColumns().get(3);

        // Linkeando valores de la tabla con valores del objeto
        c1.setCellValueFactory(
                new TreeItemPropertyValueFactory<PersonaTreeTableView, String>("rutCliente")
        );

        c2.setCellValueFactory(
                new TreeItemPropertyValueFactory<PersonaTreeTableView, String>("nomCliente")
        );

        c3.setCellValueFactory(
                new TreeItemPropertyValueFactory<PersonaTreeTableView, String>("dineroTotal")
        );

        c4.setCellValueFactory(
                new TreeItemPropertyValueFactory<PersonaTreeTableView, String>("numeroCuent")
        );

        TreeItem<PersonaTreeTableView> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        treeTableViewBusquedaCriterio.setRoot(root);

        treeTableViewBusquedaCriterio.setShowRoot(false);
    }

    public void dobleClickEnTreeTable(MouseEvent event){
        if(event.getClickCount() == 2){
            System.out.println("Lol");
            TreeItem<PersonaTreeTableView> personaTree = (TreeItem<PersonaTreeTableView>) treeTableViewBusquedaCriterio.getSelectionModel().getSelectedItem();
            PersonaTreeTableView persona = personaTree.getValue();
            goModificarUsuario(null);
            tf_searchRut.setText(persona.getRutCliente());
            actionSearch(null);
        }
    }
}




