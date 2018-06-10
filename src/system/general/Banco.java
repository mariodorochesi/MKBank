package system.general;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import system.SQL.ConexionSQL;
import system.clasesColecciones.*;
import system.interfaces.Reportable;
import system.systemAccounts.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Banco implements Reportable {

    /*
     Tasas de Interes
    * */

    private float tasaInteresPrestamo; //No implementado aun
    private float tasaInteres; //No implementado aun

    //Mapa de Personas (ADMINS+EJECUTIVOS+USUARIOS)
    private MapaPersonas mapaPersonas;

    //Mapa de Cuentas Bancarias. Se encuentran por un identificador unico
    private MapaCuentaBancarias mapaCuentaBancarias;

    //Mapa de Transferencias Bancarias. Se encuentran por un identificador unico
    private MapaTransferencias mapaTransferencias;

    //Mapa de sucursales, se encuentran por un identificador unico.
    private MapaSucursales mapaSucursales;

    //Variable para enviar mensajes por pantalla
    private String lastError;

    private Saver saver;

    //Constante Identificador Ejecutivo
    public static final int PERMISO_EJECUTIVO = 1;
    //Constante Identificador Administrador
    public static final int PERMISO_ADMINISTRADOR = 2;
    //Constante Identificador SuperAdministrador
    public static final int PERMISO_SUPERADMINISTRADOR = 3;

    /**
     * Constructor por defecto del Banco
     */

    public Banco(){
        this.tasaInteres = 0;
        this.tasaInteresPrestamo = 0;
        mapaCuentaBancarias = new MapaCuentaBancarias();
        mapaPersonas = new MapaPersonas();
        mapaTransferencias = new MapaTransferencias();
        mapaSucursales = new MapaSucursales();
        //listaSucursales = new ArrayList<>();
        lastError = "";
    }

    public void setSaver(Saver saver) {
        this.saver = saver;
    }

    /*
    *   Metodos de Agregado de Personas al Banco
    * */


    /**
     *  Esta funcion es llamada cuando un SuperAdministrador esta logueado
     *  y desea agregar a una Persona al Banco.
     *
     *  El SuperAdministrador puede agregar Administradores, Ejecutivos
     *  y Usuarios. El parametro tipoCuentaPersona indica que tipo de
     *  cuenta se desea agregar, y en caso de ser un Usuario en la
     *  GUI se crea un nuevo parametro, por lo cual se genera una sobrecarga
     *  de metodos con el siguiente metodo.
     *
     *  Si crea un Administrador lo agrega al mapa de Personas y al mapa de
     *  Administradores.
     *  Si crea un Ejecutivo lo agrega la mapa de Personas y al mapa de Ejecutivos
     *
     * @param cuentaSuperAdministrador
     * @param nombres Nombres de la Persona a agregar
     * @param apellidos Apellidos de la Persona a agregar
     * @param rut Rut de la Persona a agregar
     * @param ciudad Ciudad de la Persona a agregar
     * @param direccion Direccion de la Persona a agregar
     * @param correoElectronico CorreoElectronico de la Persona a agregar
     * @param telefono Telefono de la Persona a agregar
     * @param nacionalidad Nacionalidad de la Persona a agregar
     * @param annoNacimiento Año de Nacimiento de la Persona a agregar
     * @param mesNacimiento Mes de Nacimiento de la Persona a agregar
     * @param diaNacimiento Dia de Nacimiento de la Persona a agregar
     * @param estadoCivil Estado Civil de la Persona a agregar
     * @param genero Genero (Sexo) de la Persona a agregar
     * @param tipoCuentaPersona Tipo de Cuenta Persona a agregar
     */
    public void agregarPersona(CuentaSuperAdministrador cuentaSuperAdministrador,
                               String nombres ,
                               String apellidos ,
                               String rut,
                               String ciudad,
                               String direccion ,
                               String correoElectronico,
                               String telefono,
                               String nacionalidad ,
                               int annoNacimiento ,
                               int mesNacimiento ,
                               int diaNacimiento ,
                               String estadoCivil ,
                               String genero,
                               String tipoCuentaPersona)
    {
        if(mapaPersonas.existePersona(rut)){
            System.out.println("La persona ya existia previamente en el mapa. No ha sido agregada");
            lastError = "La persona ya existia previamente en el mapa. No ha sido agregada";
            return;
        }

        mapaPersonas.agregarPersona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,
                telefono,nacionalidad,annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil, genero);

        if(tipoCuentaPersona.equals("Cuenta Ejecutivo")){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            if(cuentaSuperAdministrador.crearCuentaEjecutivo(persona)){
            }
        }
        else if(tipoCuentaPersona.equals("Cuenta Administrador")){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            if(cuentaSuperAdministrador.crearCuentaAdministrador(persona)){
            }
        }
        lastError = cuentaSuperAdministrador.getLastError();
    }

    /**
     * Sobrecarga del metodo anterior, en donde ademas de recibir todos los datos de la Persona
     * recibe el Tipo de Cuenta Bancaria a agregar.
     *
     * Agrega al mapaUsuarios el nuevo Usuario
     * Agrega al mapaPersonas la nueva Persona
     * Agrega la mapaCuentaBancarias la nueva Cuenta Bancaria
     *
     * @param cuentaSuperAdministrador
     * @param nombres Nombres de la Persona a agregar
     * @param apellidos Apellidos de la Persona a agregar
     * @param rut Rut de la Persona a agregar
     * @param ciudad Ciudad de la Persona a agregar
     * @param direccion Direccion de la Persona a agregar
     * @param correoElectronico CorreoElectronico de la Persona a agregar
     * @param telefono Telefono de la Persona a agregar
     * @param nacionalidad Nacionalidad de la Persona a agregar
     * @param annoNacimiento Año de Nacimiento de la Persona a agregar
     * @param mesNacimiento Mes de Nacimiento de la Persona a agregar
     * @param diaNacimiento Dia de Nacimiento de la Persona a agregar
     * @param estadoCivil Estado Civil de la Persona a agregar
     * @param genero Genero (Sexo) de la Persona a agregar
     * @param tipoCuentaPersona Tipo de Cuenta Persona a agregar
     * @param tipoCuentaBancaria Tipo de Cuenta Bancaria a agregar
     */
    public void agregarPersona(CuentaSuperAdministrador cuentaSuperAdministrador ,
                               String nombres ,
                               String apellidos ,
                               String rut,
                               String ciudad,
                               String direccion ,
                               String correoElectronico,
                               String telefono,
                               String nacionalidad,
                               int annoNacimiento,
                               int mesNacimiento,
                               int diaNacimiento,
                               String estadoCivil,
                               String genero,
                               String tipoCuentaPersona,
                               String tipoCuentaBancaria)
    {

        if(mapaPersonas.existePersona(rut)){
            System.out.println("La persona ya existia previamente en el mapa. No ha sido agregada");
            lastError = "La persona ya existia previamente en el mapa. No ha sido agregada";
            return;
        }

        mapaPersonas.agregarPersona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,
                telefono,nacionalidad,annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil, genero);

        if(tipoCuentaPersona.equals("Cuenta Usuario")){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            if(cuentaSuperAdministrador.crearCuentaUsuario(persona)){
                lastError += cuentaSuperAdministrador.getLastError();
                long identificador = generarIdentificador();
                if(cuentaSuperAdministrador.crearCuentaBancaria(persona.getCuentaUsuario(),tipoCuentaBancaria,identificador)){
                    mapaCuentaBancarias.agregarCuentaBancaria(identificador,persona.getCuentaUsuario().isCuentaInCuentasBancarias(identificador));
                }
            }
        }
        lastError += " " + cuentaSuperAdministrador.getLastError();
    }

    /**
     Esta funcion es llamada cuando un Administrador esta logueado
     *  y desea agregar a una Persona al Banco.
     *
     *  El Administrador puede agregar Ejecutivos y Usuarios.
     *  El parametro tipoCuentaPersona indica que tipo de
     *  cuenta se desea agregar, y en caso de ser un Usuario en la
     *  GUI se crea un nuevo parametro, por lo cual se genera una sobrecarga
     *  de metodos con el siguiente metodo.
     *
     *  Si crea un Ejecutivo lo agrega la mapa de Personas y al mapa de Ejecutivos
     * @param cuentaAdministrador Administrador que agregara a la Persona
     * @param nombres Nombres de la Persona a agregar
     * @param apellidos Apellidos de la Persona a agregar
     * @param rut Rut de la Persona a agregar
     * @param ciudad Ciudad de la Persona a agregar
     * @param direccion Direccion de la Persona a agregar
     * @param correoElectronico CorreoElectronico de la Persona a agregar
     * @param telefono Telefono de la Persona a agregar
     * @param nacionalidad Nacionalidad de la Persona a agregar
     * @param annoNacimiento Año de Nacimiento de la Persona a agregar
     * @param mesNacimiento Mes de Nacimiento de la Persona a agregar
     * @param diaNacimiento Dia de Nacimiento de la Persona a agregar
     * @param estadoCivil Estado Civil de la Persona a agregar
     * @param genero Genero (Sexo) de la Persona a agregar
     * @param sucursalAsociada Sucursal a la que esta asociada la persona
     */
    public void agregarPersona(CuentaAdministrador cuentaAdministrador,
                               String nombres ,
                               String apellidos ,
                               String rut,
                               String ciudad,
                               String direccion ,
                               String correoElectronico,
                               String telefono,
                               String nacionalidad,
                               int annoNacimiento,
                               int mesNacimiento,
                               int diaNacimiento,
                               String estadoCivil,
                               String genero,
                               String sucursalAsociada)
    {

        if(mapaPersonas.existePersona(rut)){
            System.out.println("La persona ya existia previamente en el mapa. No ha sido agregada");
            lastError = "La persona ya existia previamente en el mapa. No ha sido agregada";
            return;
        }
        if(!mapaSucursales.existeSucursal(sucursalAsociada)){
            System.out.println("Error, la sucursal " + sucursalAsociada + " no existe");
            lastError = "Error, la sucursal " + sucursalAsociada + " no existe";
            return;
        }


        mapaPersonas.agregarPersona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,
                telefono,nacionalidad,annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil, genero);

        Persona p = mapaPersonas.obtenerPersona(rut);
        p.setSucursalAsociada(sucursalAsociada);
        mapaSucursales.obtenerSucursal(sucursalAsociada).agregarPersonaSucursal(p);

        saver.agregarPersonaSQL(mapaPersonas.obtenerPersona(rut),0);
        lastError = cuentaAdministrador.getPersona().getNombres() + " " + cuentaAdministrador.getPersona().getApellidos() +
                " ha ingresado " + nombres + " " + apellidos + " correctamente";
    }




    /**
     Esta funcion es llamada cuando un Administrador esta logueado
     *  y desea agregar a una Persona al Banco.
     *
     *  Crea y Agrega una nueva Persona con Permisos de Usuario
     *
     * Agrega al mapaUsuarios el nuevo Usuario
     * Agrega al mapaPersonas la nueva Persona
     * Agrega la mapaCuentaBancarias la nueva Cuenta Bancaria
     *
     * @param cuentaAdministrador Administrador que agregara a la Persona
     * @param nombres Nombres de la Persona a agregar
     * @param apellidos Apellidos de la Persona a agregar
     * @param rut Rut de la Persona a agregar
     * @param ciudad Ciudad de la Persona a agregar
     * @param direccion Direccion de la Persona a agregar
     * @param correoElectronico CorreoElectronico de la Persona a agregar
     * @param telefono Telefono de la Persona a agregar
     * @param nacionalidad Nacionalidad de la Persona a agregar
     * @param annoNacimiento Año de Nacimiento de la Persona a agregar
     * @param mesNacimiento Mes de Nacimiento de la Persona a agregar
     * @param diaNacimiento Dia de Nacimiento de la Persona a agregar
     * @param estadoCivil Estado Civil de la Persona a agregar
     * @param tipoCuentaBancaria Stringque señala que tipo de Cuenta Bancaria se esta creando (Ejemplo : Cuenta Vista)
     * @param tipoCuentaPersona String que señala que tipo de Persona se esta creando (Ejemplo: Cuenta Ahorro)
     */
    public void agregarPersona(CuentaAdministrador cuentaAdministrador,
                               String nombres ,
                               String apellidos ,
                               String rut,
                               String ciudad,
                               String direccion ,
                               String correoElectronico,
                               String telefono,
                               String nacionalidad,
                               int annoNacimiento,
                               int mesNacimiento,
                               int diaNacimiento,
                               String estadoCivil,
                               String genero,
                               String tipoCuentaPersona ,
                               String tipoCuentaBancaria)
    {
        if(mapaPersonas.existePersona(rut)){
            System.out.println("La persona ya existia previamente en el mapa. No ha sido agregada");
            lastError = "La persona ya existia previamente en el mapa. No ha sido agregada";
            return;
        }

        mapaPersonas.agregarPersona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,
                telefono,nacionalidad,annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil, genero);
        if(tipoCuentaPersona.equals("Cuenta Usuario")){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            if(cuentaAdministrador.crearCuentaUsuario(persona)){
                long identificador = generarIdentificador();
                if(cuentaAdministrador.crearCuentaBancaria(persona.getCuentaUsuario(),tipoCuentaBancaria,identificador)){
                    mapaCuentaBancarias.agregarCuentaBancaria(identificador,persona.getCuentaUsuario().isCuentaInCuentasBancarias(identificador));
                }
            }
        }
        lastError = cuentaAdministrador.getLastError();
    }

    /**
     Esta funcion es llamada cuando un Administrador esta logueado
     *  y desea agregar a una Persona al Banco.
     *
     *  Crea y Agrega una nueva Persona con Permisos de Usuario
     *
     * Agrega al mapaUsuarios el nuevo Usuario
     * Agrega al mapaPersonas la nueva Persona
     * Agrega la mapaCuentaBancarias la nueva Cuenta Bancaria
     *
     * @param cuentaAdministrador Administrador que agregara a la Persona
     * @param nombres Nombres de la Persona a agregar
     * @param apellidos Apellidos de la Persona a agregar
     * @param rut Rut de la Persona a agregar
     * @param ciudad Ciudad de la Persona a agregar
     * @param direccion Direccion de la Persona a agregar
     * @param correoElectronico CorreoElectronico de la Persona a agregar
     * @param telefono Telefono de la Persona a agregar
     * @param nacionalidad Nacionalidad de la Persona a agregar
     * @param annoNacimiento Año de Nacimiento de la Persona a agregar
     * @param mesNacimiento Mes de Nacimiento de la Persona a agregar
     * @param diaNacimiento Dia de Nacimiento de la Persona a agregar
     * @param estadoCivil Estado Civil de la Persona a agregar
     */
    public void agregarPersona(CuentaAdministrador cuentaAdministrador,
                               String nombres ,
                               String apellidos ,
                               String rut,
                               String ciudad,
                               String direccion ,
                               String correoElectronico,
                               String telefono,
                               String nacionalidad,
                               int annoNacimiento,
                               int mesNacimiento,
                               int diaNacimiento,
                               String estadoCivil,
                               String genero)
    {
        if(mapaPersonas.existePersona(rut)){
            System.out.println("La persona ya existia previamente en el mapa. No ha sido agregada");
            lastError = "La persona ya existia previamente en el mapa. No ha sido agregada";
            return;
        }

        mapaPersonas.agregarPersona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,
                telefono,nacionalidad,annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil, genero);

        saver.agregarPersonaSQL(mapaPersonas.obtenerPersona(rut),0);
        lastError = cuentaAdministrador.getPersona().getNombres() + " " + cuentaAdministrador.getPersona().getApellidos() +
                " ha ingresado " + nombres + " " + apellidos + " correctamente";
    }

    /**
     * Esta funcion es llamada cuando un Ejecutivo esta logueado
     *  y desea agregar a una Persona al Banco.
     *
     *  El Ejecutivo puede agregar Usuarios.
     *  El parametro tipoCuentaBancaria indica que tipo de
     *  cuenta se desea agregar.
     *
     * Agrega al mapaUsuarios el nuevo Usuario
     * Agrega al mapaPersonas la nueva Persona
     * Agrega la mapaCuentaBancarias la nueva Cuenta Bancaria
     *
     * El ejecutivo unicamente, puede agregar Usuarios y los usuarios pueden tener solamente
     * cuentas bancarias, por lo cual se le inicializa inmediatamente una cuenta bancaria
     * @param cuentaEjecutivo Ejecutivo que agregar a la Persona
     * @param nombres Nombres de la Persona a agregar
     * @param apellidos Apellidos de la Persona a agregar
     * @param rut Rut de la Persona a agregar
     * @param ciudad Ciudad de la Persona a agregar
     * @param direccion Direccion de la Persona a agregar
     * @param correoElectronico CorreoElectronico de la Persona a agregar
     * @param telefono Telefono de la Persona a agregar
     * @param nacionalidad Nacionalidad de la Persona a agregar
     * @param annoNacimiento Año de Nacimiento de la Persona a agregar
     * @param mesNacimiento Mes de Nacimiento de la Persona a agregar
     * @param diaNacimiento Dia de Nacimiento de la Persona a agregar
     * @param estadoCivil Estado Civil de la Persona a agregar
     * @param genero Genero (Sexo) de la Persona a agregar
     * @param tipoCuentaBancaria Tipo de Cuenta Bancaria a Asignarle
     */
    public void agregarPersona(CuentaEjecutivo cuentaEjecutivo,
                               String nombres ,
                               String apellidos ,
                               String rut,
                               String ciudad,
                               String direccion ,
                               String correoElectronico,
                               String telefono,
                               String nacionalidad,
                               int annoNacimiento,
                               int mesNacimiento,
                               int diaNacimiento,
                               String estadoCivil,
                               String genero,
                               String tipoCuentaBancaria,
                               String sucursalAsociada)
    {

        if(mapaPersonas.existePersona(rut)){
            System.out.println("La persona ya existia previamente en el mapa. No ha sido agregada");
            lastError = "La persona ya existia previamente en el mapa. No ha sido agregada";

            return;
        }
        if(!mapaSucursales.existeSucursal(sucursalAsociada)){
            System.out.println("La sucursal no existe");
            lastError = "La sucursal no existe";
            return;
        }

        mapaPersonas.agregarPersona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,
            telefono,nacionalidad,annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil, genero);

        Persona persona = mapaPersonas.obtenerPersona(rut);
        if (cuentaEjecutivo.crearCuentaUsuario(persona)) {
            lastError = cuentaEjecutivo.getLastError();
            long identificador = generarIdentificador();
            if (cuentaEjecutivo.crearCuentaBancaria(persona.getCuentaUsuario(), tipoCuentaBancaria, identificador)) {
                mapaCuentaBancarias.agregarCuentaBancaria(identificador, persona.getCuentaUsuario().isCuentaInCuentasBancarias(identificador));
            }

        }

        // Agregando a la persona en la sucursal
        persona.setSucursalAsociada(sucursalAsociada);
        mapaSucursales.obtenerSucursal(sucursalAsociada).agregarPersonaSucursal(persona);


        saver.agregarPersonaSQL(persona,getPermisos(persona));
        lastError += " " + cuentaEjecutivo.getLastError();
    }

    /**
     * Agrega una persona al banco y a la sucursal a la que pertenece
     * @param nombre            - Nombre de la persona.
     * @param apellido          - Apellido de la persona.
     * @param rut               - Rut de la persona.
     * @param ciudad            - Ciudad donde vive de la persona.
     * @param direccion         - Direccion donde vive la persona.
     * @param correoElectronico - Correo electronico de contacto.
     * @param telefono          - Telefono de contacto.
     * @param nacionalidad      - Nacionalidad de la persona.
     * @param annoNacimiento    - Año de nacimiento de la persona.
     * @param mesNacimiento     - Mes de nacimiento de la persona.
     * @param diaNacimiento     - Dia de nacimiento de la persona.
     * @param estadoCivil       - Estado civil de la persona.
     * @param genero            - Genero de la persona.
     * @param contrasena        - Contraseña de login de la persona.
     * @param sucursal          - Sucursal asociada a la persona.
     */
    public void agregarPersona(
            String nombre,
            String apellido,
            String rut,
            String ciudad,
            String direccion,
            String correoElectronico,
            String telefono,
            String nacionalidad,
            int annoNacimiento,
            int mesNacimiento,
            int diaNacimiento,
            String estadoCivil,
            String genero,
            String contrasena,
            String sucursal){
        System.out.println("Agregando a " + nombre + " " + apellido);

        Persona nuevaPersona = new Persona(nombre,apellido,rut,ciudad,direccion,correoElectronico,telefono,nacionalidad,
                annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil,genero,contrasena);
        mapaPersonas.agregarPersona(nuevaPersona);

        // Agregando la persona a la sucursal asociada.
        nuevaPersona.setSucursalAsociada(sucursal);
        if(mapaSucursales.existeSucursal(sucursal))
            mapaSucursales.obtenerSucursal(sucursal).agregarPersonaSucursal(nuevaPersona);
        else{
            System.out.println("Error: la sucursal " + sucursal + "no existe");
            System.exit(-1);
        }
    }

    /**
     * Agrega una nueva sucursal al mapa de sucursales, esta sobrecarga se usa
     * cuando se estan cargando las sucursales desde la base de datos, por lo que no
     * se tiene que agregar nuevamente a la base de datos
     * @param nombre        - Nombre de la sucursal que se esta agregando
     * @param direccion     - Direccion de la sucursal que se esta agregando
     * @param codigo        - Codigo de la sucursal que se esta agregando
     */

    public void agregarSucursal(String nombre, String direccion, int codigo){
        // TODO: esto deberia agregar una nueva sucursal al mapa de sucursales con los parametros ignresados
        mapaSucursales.agregarSucursal(nombre, direccion, codigo);
    }

    
    /**
     * Agrega una nueva sucursal al mapa de sucursales y la agrega a la base de datos
     * @param nombre        - Nombre de la nueva sucursal
     * @param direccion     - Direccion de la nueva sucursal
     */

    public void agregarSucursal(String nombre, String direccion){
        mapaSucursales.agregarSucursal(nombre, direccion, mapaSucursales.generarIdentificador());
        lastError = mapaSucursales.getLastError();

        // Agregando la sucursal a la base de datos
        saver.agregarSucursalSQL(mapaSucursales.obtenerSucursal(nombre));
    }

    /**
     * Metodo que elimina una sucursal del Banco, verificando previamente que esta exista
     * @param nombre Nombre de la sucursal a eliminar
     */

    public void eliminarSucursal(String nombre){
        if(!mapaSucursales.existeSucursal(nombre)){
            lastError = "Error, la sucursal no existe";
            return;
        }

        if(mapaSucursales.eliminarSucursal(nombre))
            saver.eliminarSucursalSQL(nombre);

        lastError = mapaSucursales.getLastError();

    }

    /**
     * Metodo que retorna una Sucursal en Caso de Existir
     * @param nombre Nombre de la sucursal a retornar
     * @return Sucursal en caso de existir, false en caso contrario
     */

    public Sucursal obtenerSucursal(String nombre){
        return mapaSucursales.obtenerSucursal(nombre);
    }

    /**
     * Metodo que permite agregar permisos de usuario a una Persona al cargarlo
     * @param rut Rut de la Persona
     */

    public void otorgarPermisosUsuarioCarga(String rut){
        if(mapaPersonas.existePersona(rut)){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            persona.setCuentaUsuario(new CuentaUsuario(persona));
        }
    }

    /**
     * Metodo que permite otorgar permisos superiores a una Persona al cargarlo
     * desde la Base de Datos
     * @param permisos ID del permiso a Cargar
     * @param rut Rut de la Persona
     */

    public void otorgarPermisosSuperioresCarga(int permisos,String rut){
        if(mapaPersonas.existePersona(rut)){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            if(permisos == Banco.PERMISO_EJECUTIVO)
                persona.setCuentaEjecutivo(new CuentaEjecutivo(persona));
            else if(permisos == Banco.PERMISO_ADMINISTRADOR)
                persona.setCuentaAdministrador(new CuentaAdministrador(persona));
            else if(permisos == Banco.PERMISO_SUPERADMINISTRADOR)
                persona.setCuentaSuperAdministrador(new CuentaSuperAdministrador(persona));
            else
                System.out.println("Permisos no validos para la persona " + persona.getNombres() + " " + persona.getApellidos());
        }
    }

    /**
     * Metodo que otorgar permisos al Usuario (Fuera de la Carga de la Base de Datos)
     * @param rut Rut de la Persona
     */

    public void otorgarPermisosUsuario(String rut){
        if(mapaPersonas.existePersona(rut)){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            saver.modificarPermisoUsuarioSQL(persona, 1);
            persona.setCuentaUsuario(new CuentaUsuario(persona));
        }
    }

    /**
     * Metodo que otorgar Permisos superiores a una Persona (Fuera de la Carga de la Base de Datos)
     * @param permisos ID permisos superiores
     * @param rut Rut de la Persona
     */

    public void otorgarPermisosSuperiores(int permisos,String rut){
        if(mapaPersonas.existePersona(rut)){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            if(permisos == Banco.PERMISO_EJECUTIVO)
                persona.setCuentaEjecutivo(new CuentaEjecutivo(persona));
            else if(permisos == Banco.PERMISO_ADMINISTRADOR)
                persona.setCuentaAdministrador(new CuentaAdministrador(persona));
            else if(permisos == Banco.PERMISO_SUPERADMINISTRADOR)
                persona.setCuentaSuperAdministrador(new CuentaSuperAdministrador(persona));
            else
                System.out.println("Permisos no validos para la persona " + persona.getNombres() + " " + persona.getApellidos());
            saver.modificarPermisosSQLSuperior(persona, permisos);
        }
    }

    /**
     * Metodo que reocoa los Permisos Superiores a una Persona
     * @param rut Rut de la Përsona
     */

    public void revocarPermisosSuperiores(String rut){
        if(mapaPersonas.existePersona(rut)){
            Persona persona = mapaPersonas.obtenerPersona(rut);
            persona.setCuentaEjecutivo(null);
            persona.setCuentaAdministrador(null);
            persona.setCuentaSuperAdministrador(null);
            saver.modificarPermisosSQLSuperior(persona, 0);
            System.out.println("Permisos revocados y actualizados en la BD");
        }
    }

    /**
     * Metodo en donde un SuperAdministrador agrega una Cuenta Bancaria a un Usuario determinado
     * Se agrega la cuenta al mapa de cuentas del Usuario y ademas se
     * @param cuentaSuperAdministrador
     * @param cuentaUsuario
     * @param tipoCuentaBancaria
     * @param identificador
     * @param monto
     */

    public void agregarCuentaBancaria(CuentaSuperAdministrador cuentaSuperAdministrador,
                                      CuentaUsuario cuentaUsuario,
                                      String tipoCuentaBancaria,
                                      long identificador,
                                      long monto){
        if(cuentaSuperAdministrador.crearCuentaBancaria(cuentaUsuario,tipoCuentaBancaria,identificador)){
            mapaCuentaBancarias.agregarCuentaBancaria(identificador,cuentaUsuario.isCuentaInCuentasBancarias(identificador));
        }
        mapaCuentaBancarias.obtenerCuentaBancaria(identificador).setMonto(monto);
        lastError = cuentaSuperAdministrador.getLastError();
    }


    /**
     * Metodo en donde un SuperAdministrador agrega una Cuenta Bancaria a un Usuario determinado
     * Se agrega la cuenta al mapa de cuentas del Usuario y ademas se agrega la cuenta al
     * mapa global de CuentasBancarias
     * @param cuentaSuperAdministrador SuperAdministrador que crea la cuenta Bancaria
     * @param cuentaUsuario Usuario a quien se le crea la cuenta Bancaria
     * @param tipoCuentaBancaria Tipo de cuenta a ser creada
     */

    public void agregarCuentaBancaria(CuentaSuperAdministrador cuentaSuperAdministrador,
                                      CuentaUsuario cuentaUsuario,
                                      String tipoCuentaBancaria)
    {
        long identificador = generarIdentificador();
        if(cuentaSuperAdministrador.crearCuentaBancaria(cuentaUsuario,tipoCuentaBancaria,identificador)){
            mapaCuentaBancarias.agregarCuentaBancaria(identificador,cuentaUsuario.isCuentaInCuentasBancarias(identificador));
            System.out.println("La cuenta Bancaria " + identificador + " ha sido agregada al Mapa de Cuentas Bancarias. " +
                    "El SuperAdministrador " +cuentaSuperAdministrador.getPersona().getNombres() + " " + cuentaSuperAdministrador.getPersona().getApellidos() + " ha dado la orden de agregarla.");
        }
        lastError = cuentaSuperAdministrador.getLastError();
    }

    /**
     * Metodo en donde un Administrador agrega una Cuenta Bancaria a un Usuario determinado
     * Se agrega la cuenta al mapa de cuentas del Usuario y ademas se agrega la cuenta
     * al mapa global de Cuentas Bancarias
     * @param cuentaAdministrador Administrador que crea la cuenta Bancaria
     * @param cuentaUsuario Usuario a quien se le crea la cuenta Bancaria
     * @param tipoCuentaBancaria Tipo de cuenta a ser creada
     */

    public void agregarCuentaBancaria(CuentaAdministrador cuentaAdministrador,
                                      CuentaUsuario cuentaUsuario,
                                      String tipoCuentaBancaria)
    {

        long identificador = generarIdentificador();
        if(cuentaAdministrador.crearCuentaBancaria(cuentaUsuario,tipoCuentaBancaria,identificador)){
            mapaCuentaBancarias.agregarCuentaBancaria(identificador,cuentaUsuario.isCuentaInCuentasBancarias(identificador));
            System.out.println("La cuenta Bancaria " + identificador + " ha sido agregada al Mapa de Cuentas Bancarias. "+
            "El Administrador " + cuentaAdministrador.getPersona().getNombres() + " " + cuentaAdministrador.getPersona().getApellidos() + "" +
                    " ha dado la orden de agregarla.");
        }
        saver.agregarCuentaBancariaSQL(mapaCuentaBancarias.obtenerCuentaBancaria(identificador));
        lastError = cuentaAdministrador.getLastError();
    }

    /**
     * Metodo en donde un Ejecutivo agrega una Cuenta Bancaria a un Usuario determiando
     * Se agrega la cuenta al mapa de las cuentas del Usuario, y ademas se agrega la cuenta
     * al mapa global de cuentas bancarias
     * @param cuentaEjecutivo Ejecutivo que crea la cuenta Bancaria
     * @param cuentaUsuario Usuario a quien se le crea la cuenta Bancaria
     * @param tipoCuentaBancaria Tipo de cuenta a ser creada
     */

    public void agregarCuentaBancaria(CuentaEjecutivo cuentaEjecutivo,
                                      CuentaUsuario cuentaUsuario,
                                      String tipoCuentaBancaria)
    {

        long identificador = generarIdentificador();
        if(cuentaEjecutivo.crearCuentaBancaria(cuentaUsuario,tipoCuentaBancaria,identificador)){
            mapaCuentaBancarias.agregarCuentaBancaria(identificador,cuentaUsuario.isCuentaInCuentasBancarias(identificador));
            saver.agregarCuentaBancariaSQL(mapaCuentaBancarias.obtenerCuentaBancaria(identificador));
            System.out.println("La cuenta Bancaria " + identificador + " ha sido agregada al Mapa de Cuentas Bancarias." +
            " El Ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " " + cuentaEjecutivo.getPersona().getApellidos() + "" +
                    " ha dado la orden de agregarla.");
        }
        lastError = cuentaEjecutivo.getLastError();
    }

    /**
     * Metodo en donde un SuperAdministrador elimina una Cuenta Bancaria a un Usuario determinado
     * Se elimina la cuenta del mapa de cuentas del Usuario y ademas se elimina la cuenta del mapa global
     * de cuentas bancarias
     * @param cuentaSuperAdministrador Cuenta del SuperAdministrador
     * @param cuentaUsuario Cuenta del Usuario a quien se le eliminara la cuenta Bancaria
     * @param identificador Identificador de la Cuenta
     */

    public void eliminarCuentaBancaria(CuentaSuperAdministrador cuentaSuperAdministrador,
                                       CuentaUsuario cuentaUsuario,
                                       long identificador){
        if(cuentaSuperAdministrador.eliminarCuentaBancaria(cuentaUsuario,identificador)){
            mapaCuentaBancarias.eliminarCuentaBancaria(identificador);
            System.out.println("La cuenta " + identificador + " ha sido eliminada del mapa global de cuentas bancarias." + "" +
                    " El SuperAdministrador " + cuentaSuperAdministrador.getPersona().getNombres() + cuentaSuperAdministrador.getPersona().getApellidos() + "" +
                    " ha dado la orden de eliminarla.");
            saver.eliminarCuentaBancariaSQL(identificador);
        }
        else
            System.out.println("Ha ocurrido un error");
        lastError = cuentaSuperAdministrador.getLastError();
    }

    /**
     * Metodo en donde un Administrador elimina una Cuenta Bancaria a un Usuario determinado
     * Se elimina la cuenta del mapa de cuentas del Usuario y ademas se elimina la cuenta del mapa global
     * de cuentas bancarias
     * @param cuentaAdministrador Cuenta el Administrador
     * @param cuentaUsuario Cuenta del Usuario a quien se le eliminara la cuenta Bancaria
     * @param identificador Identificador de la Cuenta
     */

    public void eliminarCuentaBancaria(CuentaAdministrador cuentaAdministrador,
                                       CuentaUsuario cuentaUsuario,
                                       long identificador){

        if(cuentaAdministrador.eliminarCuentaBancaria(cuentaUsuario,identificador)){
            mapaCuentaBancarias.eliminarCuentaBancaria(identificador);
            System.out.println("La cuenta " + identificador + " ha sido eliminada del mapa global de cuentas bancarias." + "" +
                    " El Administrador " + cuentaAdministrador.getPersona().getNombres() + " " + cuentaAdministrador.getPersona().getApellidos() + "" +
                    "ha dado la orden de eliminarla.");
            saver.eliminarCuentaBancariaSQL(identificador);
        }
        else
            System.out.println("Ha ocurrido un error");

        lastError = cuentaAdministrador.getLastError();

    }

    /**
     * Metodo en donde un Ejecutivo elimina una Cuenta Bancaria a un Usuario determiando
     * Se elimina la cuenta del mapa de cuentas del Usuario y ademas se elimina la cuenta del mapa global
     * de cuentas bancarias
     * @param cuentaEjecutivo Cuenta del ejecutivo
     * @param cuentaUsuario Cuenta del Usuario a quien se le eliminara la cuenta Bancaria
     * @param identificador Identificador de la Cuenta
     */

    public void eliminarCuentaBancaria(CuentaEjecutivo cuentaEjecutivo,
                                       CuentaUsuario cuentaUsuario,
                                       long identificador){
        if(cuentaEjecutivo.eliminarCuentaBancaria(cuentaUsuario,identificador)){
            mapaCuentaBancarias.eliminarCuentaBancaria(identificador);
            System.out.println("La cuenta " + identificador + " ha sido eliminada del mapa global de cuentas bancarias." + "" +
                    " El Ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " " + cuentaEjecutivo.getPersona().getApellidos() + "" +
                    " ha dado la orden de eliminarla.");
            saver.eliminarCuentaBancariaSQL(identificador);
        }
        else
            System.out.println("Ha ocurrido un error");

        lastError = cuentaEjecutivo.getLastError();
    }

    /**
     * Metodo que agrega dinero a una Cuenta Bancaria.
     * Este metodo es el que se utiliza cuando una persona va a la sucursal a depositar a una cuenta.
     * @param cuentaEjecutivo   Cuenta ejecutivo que va a hacer el deposito
     * @param identificador     Identificador de la cuenta bancaria a depositar
     * @param monto             Monto a depositar
     */

    public void depositarCuentaBancaria(CuentaEjecutivo cuentaEjecutivo,
                                        long identificador,
                                        int monto)
    {
        CuentaBancaria cuentaBancaria = isCuentaBancariaOnBanco(identificador);
        if(cuentaBancaria != null){
            cuentaEjecutivo.depositarCuentaBancaria(cuentaBancaria.getPersona().getCuentaUsuario(),identificador,monto);
            lastError = cuentaEjecutivo.getLastError();
        }
        else{
            lastError = "La cuenta bancaria no existe en el banco";
        }

    }

    /**
     * Metodo que quita dinero de una Cuenta Bancaria.
     * Este metodo es el que se utiliza cuando una persona va la sucursal a retirar dinero
     * @param cuentaEjecutivo   Cuenta ejecutivo que va a hacer el retiro
     * @param identificador     Identificador de la cuenta bancaria a la que se va a retirar dinero
     * @param monto             Monto a retirar
     */

    public void retirarCuentaBancaria(CuentaEjecutivo cuentaEjecutivo,
                                      long identificador,
                                      int monto){

        CuentaBancaria cuentaBancaria = isCuentaBancariaOnBanco(identificador);
        if(cuentaBancaria != null){
            final  boolean resultado = cuentaEjecutivo.retirarCuentaBancaria(cuentaBancaria.getPersona().getCuentaUsuario(),identificador,monto);
            lastError = cuentaEjecutivo.getLastError();
        }
        else{
            lastError = "La cuenta bancaria no existe en el banco";
        }
    }

    /**
     * Transfiere dinero desde una cuenta origen a una destino si y solo si se cumplen las siguientes condiciones
     * -La cuenta destino existe
     * -El monto de transferencia es positivo
     * -La cuenta origen no esta bloqueada
     * -Y si hay fondos suficientes
     * @param cuentaBancaria                Cuenta Bancaria Origen
     * @param identificadorCuentaDestino    Identificador tipo long de la cuenta destino
     * @param monto                         Monto a transferir
     * @return                              Retorna true si la transferencia se realizo satisfactoriamente
     */

    public boolean transferirDinero(CuentaBancaria cuentaBancaria ,
                                    long identificadorCuentaDestino,
                                    String tipoCuenta,
                                    String rutDestino,
                                    int monto,
                                    String comentario)
    {
        CuentaBancaria cuentaDestino = mapaCuentaBancarias.obtenerCuentaBancaria(identificadorCuentaDestino);
        if(cuentaDestino == null){
            lastError = "La cuenta destino no existe. Compruebe los datos";
            return false;
        }
        else {
            if (monto <= 0) {
                lastError = "Imposible transferir cantidades negativas.";
                return false;
            } else {
                if (cuentaBancaria.isCuentaBloqueada() == true) {
                    lastError = "Imposible transferir dinero desde cuentas bloqueadas.";
                    return false;
                } else {
                    if (cuentaBancaria.getMonto() <= monto) {
                        lastError = "La cuenta bancaria " + cuentaBancaria.getIdentificador() + " no posee los fondos suficientes.";
                        return false;
                    } else {
                        if(!tipoCuenta.equals(cuentaDestino.getTipoCuenta()) || !rutDestino.equals(cuentaDestino.getPersona().getRut())){
                            lastError = "El tipo de Cuenta o el Rut destinatario no coinciden con la cuenta " + cuentaDestino.getIdentificador();
                            return false;
                        }
                        else {
                            cuentaBancaria.transferir(monto);
                            cuentaDestino.depositar(monto);
                            saver.depositarCuentaSQL(cuentaDestino,monto);
                            saver.transferirCuentaSQL(cuentaBancaria,monto);
                            lastError = "La transaccion ha sido realizada correctamente. Ha transferido " + monto +
                                    " a la persona " + cuentaDestino.getPersona().getNombres() + " " + cuentaDestino.getPersona().getApellidos();
                            Long identificador = generarIdentificadorTransferencias();
                            Transferencias transferencia_origen = new Transferencias(cuentaBancaria, cuentaDestino, identificador, monto, comentario);
                            cuentaDestino.getPersona().getCuentaUsuario().agregarTransferencia(cuentaBancaria, cuentaDestino, identificador, monto, comentario);
                            cuentaBancaria.getPersona().getCuentaUsuario().agregarTransferencia(cuentaBancaria, cuentaDestino, identificador, monto, comentario);
                            mapaTransferencias.agregarTranferencia(identificador,transferencia_origen);
                            saver.agregarTransferenciaSQL(transferencia_origen);
                        }
                        return true;
                    }
                }
            }
        }
    }

    /**
     * Metodo que agrega una Transferencia a cada uno de los Usuarios (En carga de SQL)
     * @param id Id de la transferencia
     * @param t Transferencia a Cargar
     */

    public void agregarTransferencia(long id,Transferencias t){
        mapaTransferencias.agregarTranferencia(id,t);
        CuentaUsuario origen = isUsuarioOnBanco(t.getRutOriginario());
        CuentaUsuario destino = isUsuarioOnBanco(t.getRutDestinatario());
        if(origen != null)
            origen.agregarTransferencia(t);
        if(destino!=null)
            destino.agregarTransferencia(t);
    }


    public boolean agregarCuentaPersona(CuentaSuperAdministrador cuentaSuperAdministrador , Persona persona, String cuentaAgregar){
        if(cuentaAgregar.equals("Cuenta SuperAdministrador")){
            System.out.println(cuentaSuperAdministrador.getPersona().getNombres() + " " + cuentaSuperAdministrador.getPersona().getApellidos()
                    + " no tiene los permisos necesarios para crear una " + cuentaAgregar);
            return false;
        }
        else if(cuentaAgregar.equals("Cuenta Administrador")){
             if(cuentaSuperAdministrador.crearCuentaAdministrador(persona)){
                 return true;
             }
        }
        return true;
    }

    /*
    *   Metodos de Eliminacion de Personas y/o Cuentas
    * */

    /**
     * Metodo para Eliminar una Persona si se esta logueado como SuperAdministrador
     *
     * Si la Persona existe, entonces se procede a verificar que sus permisos sean menores a los de un
     * SuperAdministrador.
     *
     * Si eso se cumple, entonces se revisa si tiene una Cuenta Usuario asociada. De ser asi entonces se eliminan primero
     * todas sus cuentasBancarias.
     *
     * Posteriormente se elimina su Cuenta Usuario, Cuenta Administrador y finalmente se elimina a la Persona del Banco.
     *
     * @param cuentaSuperAdministrador SuperAdministrador que sera el encargado de borrar a la Persona
     * @param rut Rut de la Persona a Borrar
     * @return True si borra a la Persona / False en caso contrario
     */
    public boolean eliminarPersona(CuentaSuperAdministrador cuentaSuperAdministrador ,String rut){
        if(!mapaPersonas.existePersona(rut)){
            System.out.println("La persona que desea eliminar no se encuentra en el banco.");
            lastError = "La persona ya existia previamente en el mapa. No ha sido agregada";
            return false;
        }
        else{
            Persona personaEliminar = mapaPersonas.obtenerPersona(rut);
            if(personaEliminar.getCuentaSuperAdministrador() != null) {
                System.out.println(cuentaSuperAdministrador.getPersona().getNombres() +" " + cuentaSuperAdministrador.getPersona().getApellidos() +
                " no es posible eliminar a la Persona " + personaEliminar.getNombres() + " " + personaEliminar.getApellidos() + " ya que tiene" +
                "permisos superiores a los suyos");
                lastError = "No es posible eliminar a la Persona " + personaEliminar.getNombres() + " " + personaEliminar.getApellidos() + "" +
                        "ya que tiene privilegios superiores a los suyos.";
                return false;
            }
            else if(personaEliminar.getCuentaAdministrador() != null){
                if(eliminarAdministrador(personaEliminar.getRut())) {
                    lastError = personaEliminar.getCuentaAdministrador().getLastError();
                    return eliminarPersonaBanco(personaEliminar);
                }
                else {
                    lastError = personaEliminar.getCuentaAdministrador().getLastError();
                    return false;
                }
            }
            else if(personaEliminar.getCuentaEjecutivo() != null){
                if(eliminarEjecutivo(personaEliminar.getRut())){
                    lastError = personaEliminar.getCuentaEjecutivo().getLastError();
                    return eliminarPersonaBanco(personaEliminar);
                }

                else{
                    lastError = personaEliminar.getCuentaEjecutivo().getLastError();
                    return false;
                }

            }
            else if(personaEliminar.getCuentaUsuario() != null){
                if(eliminarUsuario(personaEliminar.getRut())){
                    lastError = personaEliminar.getCuentaUsuario().getLastError();
                    return eliminarPersonaBanco(personaEliminar);
                }
                else{
                    lastError = personaEliminar.getCuentaUsuario().getLastError();
                    return false;
                }
            }
            else
                return eliminarPersonaBanco(personaEliminar);

        }
    }

    /**
     * Metodo para Eliminar una Persona si se esta logueado como Administrador
     *
     * Si la Persona existe, entonces se procede a verificar que sus permisos sean menores a los de un
     * Administrador.
     *
     * Si eso se cumple, entonces se revisa si tiene una Cuenta Usuario asociada. De ser asi entonces se eliminan primero
     * todas sus cuentasBancarias.
     *
     * Posteriormente se elimina su Cuenta Usuario, Cuenta Administrador y finalmente se elimina a la Persona del Banco.
     *
     * @param cuentaAdministrador SuperAdministrador que sera el encargado de borrar a la Persona
     * @param rut Rut de la Persona a Borrar
     * @return True si borra a la Persona / False en caso contrario
     */

    public boolean eliminarPersona(CuentaAdministrador cuentaAdministrador , String rut){
        if(!mapaPersonas.existePersona(rut)){
            System.out.println("La persona que desea eliminar no se encuentra en el banco.");
            return false;
        }
        else{
            Persona personaEliminar = mapaPersonas.obtenerPersona(rut);
            if(personaEliminar.getCuentaSuperAdministrador() != null){
                System.out.println(cuentaAdministrador.getPersona().getNombres() +" " + cuentaAdministrador.getPersona().getApellidos() +
                        " no es posible eliminar a la Persona " + personaEliminar.getNombres() + " " + personaEliminar.getApellidos() + " ya que tiene" +
                        "permisos superiores a los suyos");
                return false;
            }
            else if(personaEliminar.getCuentaAdministrador() != null){
                System.out.println(cuentaAdministrador.getPersona().getNombres() +" " + cuentaAdministrador.getPersona().getApellidos() +
                        " no es posible eliminar a la Persona " + personaEliminar.getNombres() + " " + personaEliminar.getApellidos() + " ya que tiene" +
                        "permisos iguales a los suyos");
                return false;
            }
            else if(personaEliminar.getCuentaEjecutivo() != null){
                if(eliminarEjecutivo(personaEliminar.getRut()))
                    return eliminarPersonaBanco(personaEliminar);
                else
                    return false;
            }
            else if(personaEliminar.getCuentaUsuario() != null){
                if(eliminarUsuario(personaEliminar.getRut()))
                    return eliminarPersonaBanco(personaEliminar);
                else
                    return false;
        }
            else
                return eliminarPersonaBanco(personaEliminar);
        }
    }

    /**
     * Metodo para Eliminar una Persona si se esta logueado como Ejecutivo
     *
     * Si la Persona existe, entonces se procede a verificar que sus permisos sean menores a los de un
     * Ejecutivo.
     *
     * Si eso se cumple, entonces se revisa si tiene una Cuenta Usuario asociada. De ser asi entonces se eliminan primero
     * todas sus cuentasBancarias.
     *
     * Posteriormente se elimina su Cuenta Usuario, Cuenta Administrador y finalmente se elimina a la Persona del Banco.
     *
     * @param cuentaEjecutivo SuperAdministrador que sera el encargado de borrar a la Persona
     * @param rut Rut de la Persona a Borrar
     * @return True si borra a la Persona / False en caso contrario
     */
    public boolean eliminarPersona(CuentaEjecutivo cuentaEjecutivo, String rut){
        if(!mapaPersonas.existePersona(rut)){
            System.out.println("La persona que desea eliminar no se encuentra en el banco.");
            return false;
        }
        else{
            Persona personaEliminar = mapaPersonas.obtenerPersona(rut);
            if(personaEliminar.getCuentaSuperAdministrador() != null){
                System.out.println(cuentaEjecutivo.getPersona().getNombres() +" " + cuentaEjecutivo.getPersona().getApellidos() +
                        " no es posible eliminar a la Persona " + personaEliminar.getNombres() + " " + personaEliminar.getApellidos() + " ya que tiene" +
                        "permisos superiores a los suyos");
                return false;
            }
            else if(personaEliminar.getCuentaAdministrador() != null){
                System.out.println(cuentaEjecutivo.getPersona().getNombres() +" " + cuentaEjecutivo.getPersona().getApellidos() +
                        " no es posible eliminar a la Persona " + personaEliminar.getNombres() + " " + personaEliminar.getApellidos() + " ya que tiene" +
                        "permisos superiores a los suyos");
                return false;
            }
            else if(personaEliminar.getCuentaEjecutivo() != null){
                System.out.println(cuentaEjecutivo.getPersona().getNombres() +" " + cuentaEjecutivo.getPersona().getApellidos() +
                        " no es posible eliminar a la Persona " + personaEliminar.getNombres() + " " + personaEliminar.getApellidos() + " ya que tiene" +
                        "permisos iguales a los suyos");
                return false;
            }
            else if(personaEliminar.getCuentaUsuario() != null){
                if(eliminarUsuario(personaEliminar.getRut()))
                    return eliminarPersonaBanco(personaEliminar);
                else
                    return false;
            }
            else
                return eliminarPersonaBanco(personaEliminar);
        }
    }


    /**
     * Si la Persona tiene una Cuenta Administrador asociada, entonces se comprueba si ademas tiene una Cuenta Usuario.
     * En caso de tenerla primero se eliminan sus cuentas bancarias y posteriormente se procede a eliminar al administrador
     * del mapa de administradores.
     * @param rut Rut de la persona a eliminar
     * @return True si pudo eliminar al Administrador, False en caso contrario
     */

    public boolean eliminarAdministrador(String rut){
        Persona personaEliminarCuentaAdministrador = mapaPersonas.obtenerPersona(rut);
        if(personaEliminarCuentaAdministrador.getCuentaUsuario() != null) {
            if (!eliminarUsuario(personaEliminarCuentaAdministrador.getRut())) {
                System.out.println("No se puede eliminar a la Persona " + personaEliminarCuentaAdministrador.getNombres() + " " +
                        personaEliminarCuentaAdministrador.getApellidos() + " ya que aun tiene dinero en el banco.");
                return false;
            }
        }
        System.out.println("Eliminando la Cuenta Administrador de " + personaEliminarCuentaAdministrador.getNombres() +
                " " + personaEliminarCuentaAdministrador.getApellidos() + " del mapa de Administradores");
        mapaPersonas.eliminarCuentaAdministrador(rut);
        return true;
    }


    /**
     * Si la Persona tiene una Cuenta Ejectuvio asociada, entonces se comprueba si ademas tiene una Cuenta Usuario.
     * En caso de tenerla primero se eliminan sus cuentsa bancarias y posteriormente se procede a eliminar al administrador
     * del mapa de ejectutivos
     * @param rut Rut de persona a eliminar
     * @return True si pudo eliminar al Ejectuvo, False en caso contrario
     */

    public boolean eliminarEjecutivo(String rut){
        Persona personaEliminarCuentaEjecutivo = mapaPersonas.obtenerPersona(rut);
        if(personaEliminarCuentaEjecutivo.getCuentaUsuario() != null) {
            if (!eliminarUsuario(personaEliminarCuentaEjecutivo.getRut())) {
                System.out.println("No se puede eliminar a la Persona " + personaEliminarCuentaEjecutivo.getNombres() + " " +
                        personaEliminarCuentaEjecutivo.getApellidos() + " ya que aun tiene dinero en el banco.");
                return false;
            }
        }
        System.out.println("Eliminando la Cuenta Ejecutivo de " + personaEliminarCuentaEjecutivo.getNombres() +
                " " + personaEliminarCuentaEjecutivo.getApellidos() + " del mapa de Ejecutivos");
        mapaPersonas.eliminarCuentaEjecutivo(rut);
        return true;
    }

    /**
     * Se eliminan todas las cuentasBancarias asociadas al usuario, posteriormente se elimina al usuario del mapa de
     * usuarios.
     *
     * Verifica que ninguna cuenta bancaria asociada tenga dinero, de lo contrario no sera posible eliminar a la persona.
     *
     * @param rut Rut persona a eliminar
     * @return True si pudo eliminar a la persona, false en caso contrario
     */

    public boolean eliminarUsuario(String rut){
        Persona personaEliminarCuentaUsuario = mapaPersonas.obtenerPersona(rut);
        if(personaEliminarCuentaUsuario.getCuentaUsuario() == null)
            return false;
        else {
            if (personaEliminarCuentaUsuario.getCuentaUsuario().montoTotalUsuario() == 0) {
                ArrayList<CuentaBancaria> cuentasBancariasUsuario = personaEliminarCuentaUsuario.getCuentaUsuario().getCuentasBancarias();
                for (int i = 0; i < cuentasBancariasUsuario.size(); i++) {
                    CuentaBancaria iterador = cuentasBancariasUsuario.get(i);
                    if (mapaCuentaBancarias.eliminarCuentaBancaria(iterador.getIdentificador())) {
                        System.out.println("Eliminando la Cuenta " + iterador.getIdentificador() +
                                " de la persona " + iterador.getPersona().getNombres() + " " + iterador.getPersona().getApellidos());
                    }
                }
                personaEliminarCuentaUsuario.getCuentaUsuario().eliminarCuentasBancarias();
                personaEliminarCuentaUsuario.setCuentaUsuario(null);
                System.out.println("Eliminando la Cuenta Usuario de " + personaEliminarCuentaUsuario.getNombres() +
                        " " + personaEliminarCuentaUsuario.getApellidos() + " del mapa de Usuarios");
                mapaPersonas.eliminarCuentaUsuario(rut);
                personaEliminarCuentaUsuario.setCuentaUsuario(null);
                return true;
            } else {
                System.out.println("No se puede eliminar a la Persona " + personaEliminarCuentaUsuario.getNombres() + " " +
                        personaEliminarCuentaUsuario.getApellidos() + " ya que aun tiene dinero en el banco.");
                return false;
            }
        }
    }

    /**
     * Metodo que permite obtener la lista de las ultimas N transferencias de un Usiarop
     * @param usuario Usuario
     * @param cantidadMostrar Cantidad de Transfernecias a Mostrar
     * @return Retorna un ArrayList con las ultimas N transferencias
     */

    public ArrayList<Transferencias> obtenerListaTransferencia(CuentaUsuario usuario , int cantidadMostrar){
        return usuario.historialTransacciones(cantidadMostrar);
    }


    /**
     * Elimina a la Persona del mapa de Cuentas Bancarias
     * @param personaEliminar
     * @return
     */

    public boolean eliminarPersonaBanco(Persona personaEliminar){
        System.out.println("Eliminando a la Persona " + personaEliminar.getNombres() +
                " " + personaEliminar.getApellidos() + " del mapa de Personas");
        saver.eliminarPersona(personaEliminar.getRut());
        mapaPersonas.eliminarPersona(personaEliminar.getRut());
        return true;
    }

    /*
    *   Metodo de Edicion de Personas
    * */

    /**
     * Edita la Informacion Personal de una Persona a traves de sus setters
     * @param persona
     * @param nombre
     * @param apellidos
     * @param rut
     * @param ciudad
     * @param direccion
     * @param correoElectornico
     * @param telefono
     * @param nacionalidad
     * @param annoNacimiento
     * @param mesNacimiento
     * @param diaNacimiento
     * @param estadoCivil
     * @param genero
     */
    public void editarPersona(Persona persona,
                              String nombre,
                              String apellidos,
                              String rut,
                              String ciudad,
                              String direccion,
                              String correoElectornico,
                              String telefono,
                              String nacionalidad,
                              int annoNacimiento,
                              int mesNacimiento,
                              int diaNacimiento,
                              String estadoCivil,
                              String genero,
                              String sucursalAsociada){

        persona.setNombres(nombre);
        persona.setApellidos(apellidos);
        persona.setRut(rut);
        persona.setCiudad(ciudad);
        persona.setDireccion(direccion);
        persona.setCorreoElectronico(correoElectornico);
        persona.setTelefono(telefono);
        persona.setNacionalidad(nacionalidad);
        persona.setFechaNacimiento(LocalDate.of(annoNacimiento,mesNacimiento,diaNacimiento));
        persona.setEstadoCivil(estadoCivil);
        persona.setGenero(genero);

        // Modificacion de las sucursales
        mapaSucursales.obtenerSucursal(persona.getSucursalAsociada()).eliminarPersonaSucursal(persona);
        persona.setSucursalAsociada(sucursalAsociada);
        mapaSucursales.obtenerSucursal(sucursalAsociada).agregarPersonaSucursal(persona);

        System.out.println("Guardando las modificaciones en la base de datos");
        saver.editarPersonaSQL(persona);
        System.out.println("Datos guardados");
    }



    /*
    *   Metodos Auxiliares
    * */

    /**
     * Busca a un SuperAdministrador en el mapa de SuperAdministradores mediante su
     * clave Rut.
     * @param rut
     * @return cuentaSuperAdministrador si es que fue encontrado en el mapa
     *         null en caso contrario
     */

    public CuentaSuperAdministrador isSuperAdministradorOnBanco(String rut){
        if(mapaPersonas.existePersona(rut)){
            return mapaPersonas.obtenerPersona(rut).getCuentaSuperAdministrador();
        }
        return null;
    }

    /**
     * Busca a un Administrador en el mapa de Administradores mediante su
     * clave Rut.
     * @param rut
     * @return cuentaAdministrador si es que fue encontrado en el mapa
     *         null en caso contrario
     */

    public CuentaAdministrador isAdministradorOnBanco(String rut){

            if(mapaPersonas.existePersona(rut)){
                return mapaPersonas.obtenerPersona(rut).getCuentaAdministrador();
            }
            return null;

    }


    /**
     * Busca a un Ejecutivo en el mapa de Ejecutivos mediante su
     * clave Rut.
     * @param rut
     * @return cuentaEjecutivosi es que fue encontrado en el mapa
     *         null en caso contrario
     */
    public CuentaEjecutivo isEjecutivoOnBanco(String rut){

        if(mapaPersonas.existePersona(rut)){
            return mapaPersonas.obtenerPersona(rut).getCuentaEjecutivo();
        }
        return null;
    }

    /**
     * Busca a un Usuario en el mapa de Usuario mediante su
     * clave Rut.
     * @param rut
     * @return cuentaUsuario si es que fue encontrado en el mapa
     *         null en caso contrario
     */
    public CuentaUsuario isUsuarioOnBanco(String rut){
        if(mapaPersonas.existePersona(rut)){
            return mapaPersonas.obtenerPersona(rut).getCuentaUsuario();
        }
        return null;
    }


    /**
     * Busca a un Persona en el mapa de Personas mediante su
     * clave Rut.
     * @param rut
     * @return Persona si es que fue encontrado en el mapa
     *         null en caso contrario
     */
    public Persona isPersonaOnBanco(String rut){
        return mapaPersonas.obtenerPersona(rut);
    }

    /**
     * Busca a una cuenta Bancaria en el mapa de cuentas Bancarias mediante su
     * clave Identificador
     * @param identificador
     * @return cuentabancaria en caso de ser encontrada
     *         null en caso contrario
     */
    public CuentaBancaria isCuentaBancariaOnBanco(long identificador){
        return mapaCuentaBancarias.obtenerCuentaBancaria(identificador);
    }


    /**
     * Verifica cual es el mas alto nivel de privilegio que tiene una Persona, y dependiendo de eso,
     * retorna un valor
     * @param persona
     * @return
     *      0 si la Persona no tiene asociada cuentas
     *      1 si la Persona tiene privilegio maximo de Usuario
     *      2 si la Persona tiene privilegio maximo de Ejecutivo
     *      3 si la Persona tiene privilegio maximo de Administrador
     *      4 si la Persona tiene privilegio maximo de SuperAdministrador
     */
    public short getPermisos(Persona persona){
        if(persona.getCuentaSuperAdministrador() != null)
            return 4;
        else if(persona.getCuentaAdministrador() != null)
            return 3;
        else if(persona.getCuentaEjecutivo() != null)
            return 2;
        else if(persona.getCuentaUsuario() != null)
            return 1;
        else
            return 0;
    }

    /**
     * Comprueba si la persona tiene permisos de ejecutivo
     * @param rut   - Rut de la persona que se quiere comprar que tiene el permiso
     * @return
     */
    public boolean contieneCuentaEjecutivo(String rut) {
        Persona p = isPersonaOnBanco(rut);
        // TODO: cambiar esto por una excepcion
        return p != null && p.getCuentaEjecutivo() != null;
    }

    /**
     * Comprueba si la persona tiene permisos de Administrador
     * @param rut   - Rut de la persona que se quiere comprar que tiene el permiso
     * @return
     */
    public boolean contieneCuentaAdministrador(String rut) {
        Persona p = isPersonaOnBanco(rut);
        // TODO: cambiar esto por una excepcion
        return p != null && p.getCuentaAdministrador() != null;
    }

    /**
     * Comprueba si la persona tiene permisos de super administrador
     * @param rut   - Rut de la persona que se quiere comprar que tiene el permiso
     * @return
     */
    public boolean contieneCuentaSuperAdministrador(String rut) {
        Persona p = isPersonaOnBanco(rut);
        // TODO: cambiar esto por una excepcion
        return p != null && p.getCuentaSuperAdministrador() != null;
    }

    /**
     * Comprueba si la persona tiene permisos de super administrador
     * @param rut   - Rut de la persona que se quiere comprar que tiene el permiso
     * @return
     */
    public boolean contieneCuentaUsuario(String rut) {
        Persona p = isPersonaOnBanco(rut);
        // TODO: cambiar esto por una excepcion
        return p != null && p.getCuentaUsuario() != null;
    }

    /**
     * Genera un Identificador Unico de 9 digitos para una cuenta Bancaria, y comprueba que el Identificador Generado
     * no este asociado a otra cuenta.
     * Si ya esta asociado a otra cuenta, se genera un nuevo Identificador
     * @return Identificador
     */
    private long generarIdentificador(){
        return mapaCuentaBancarias.generarIdentificador();
    }

    private long generarIdentificadorTransferencias(){
        return mapaTransferencias.generarIdentificador();
    }


    /**
     * Carga los datos al Banco a partir del archivo archivoCuentasBancarias.csv
     * Inicializa a todos los clientes, con sus respectivas cuentas asociadas
     */

    public void loadFiles(){
        Persona fakeOne = new Persona("system", "SuperAdmin", "0000000-0", "",
                "", "", "",
                "", 1995, 10, 19, "", "");
        fakeOne.setCuentaSuperAdministrador((new CuentaSuperAdministrador(fakeOne)));

        // Leyendo archivo de personas
        try {
            String filePath = (new File(".").getAbsolutePath()).replace(".", "");
            filePath = filePath + "archivoprueba.csv";
            //System.out.println((new File("/archivoprueba.csv").getAbsolutePath()));
            System.out.println("Esta es la ruta " + filePath);
            String line = "";
            String sep = ",.,"; // Separacion de los datos

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                while ((line = reader.readLine()) != null) {
                    String[] cortado = line.split(sep);
                    loadPeopleFromSplitedLine(cortado);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception ignored){
            System.out.println("Ocurrio un error ignorado");
        }

        // Leyendo archivo de cuentas
        String filePath = (new File(".").getAbsolutePath()).replace(".", "");
        filePath = filePath + "archivoCuentasBancarias.csv";
        //System.out.println((new File("/archivoprueba.csv").getAbsolutePath()));
        //System.out.println("Esta es la ruta (Cuentas Bancarias)" + filePath);
        String line = "";
        String sep = ",.,"; // Separacion de los datos

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] cortado = line.split(sep);
                loadAccountsFromSplitedLine(cortado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Carga todos los datos de una Cuenta
     * @param cortado
     */
    private void loadPeopleFromSplitedLine(String[] cortado){
        final int SINCUENTA = 0;
        final int CUENTA_EJECUTIVO = 1;
        final int CUENTA_ADMINISTRADOR = 2;
        final int CUENTA_SUPERADMINISTRADOR = 3;

        mapaPersonas.agregarPersona(cortado[0],cortado[1],cortado[2],cortado[3],cortado[4],cortado[5],cortado[6],
                cortado[7],Integer.parseInt(cortado[8]),Integer.parseInt(cortado[9]),
                Integer.parseInt(cortado[10]),cortado[11],cortado[12]);


        Persona nuevaPersona = mapaPersonas.obtenerPersona(cortado[2]);
        
        nuevaPersona.setContrasena(cortado[15]);
        if(Integer.parseInt(cortado[13]) == CUENTA_SUPERADMINISTRADOR) {
            nuevaPersona.setCuentaSuperAdministrador(new CuentaSuperAdministrador(nuevaPersona));
        }
        else if(Integer.parseInt(cortado[13]) == CUENTA_ADMINISTRADOR){
            nuevaPersona.setCuentaAdministrador(new CuentaAdministrador(nuevaPersona));
        }
        else if(Integer.parseInt(cortado[13]) == CUENTA_EJECUTIVO){
            nuevaPersona.setCuentaEjecutivo(new CuentaEjecutivo(nuevaPersona));
        }

        if(Integer.parseInt(cortado[14]) == 1){
            nuevaPersona.setCuentaUsuario(new CuentaUsuario(nuevaPersona));
            loadHistorialTransferencias(nuevaPersona);
        }
        System.out.println("Agregando a " + nuevaPersona.getNombres() + " por su RUT " + cortado[2]);
    }

    /**
     * Carga las Transferencias desde la carpeta Transferencias
     * @param p
     */
    private void loadHistorialTransferencias(Persona p){
        try {
            String filePath = (new File(".").getAbsolutePath()).replace(".", "");
            filePath = filePath + "Transferencias/" + p.getRut().substring(0,p.getRut().indexOf('-')) + ".txt";
            System.out.println(filePath);
            String line;
            String sep = ",.,";
            Transferencias t;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                while ((line = reader.readLine()) != null) {
                    String[] cortado = line.split(sep);

                    t = new Transferencias(cortado[0], cortado[1], cortado[2], Long.parseLong(cortado[3]),
                            cortado[4], cortado[5], cortado[6], Long.parseLong(cortado[7]), cortado[8],
                            Long.parseLong(cortado[9]), Integer.parseInt(cortado[10]),Integer.parseInt(cortado[11]),
                            Integer.parseInt(cortado[12]),Integer.parseInt(cortado[13]),cortado[14]);

                    System.out.println("Transferencia cargada " + t.getNumeroTransferencia());
                    mapaTransferencias.agregarTranferencia(t.getNumeroTransferencia(), t);
                    p.getCuentaUsuario().agregarTransferencia(cortado[0], cortado[1], cortado[2], Long.parseLong(cortado[3]),
                            cortado[4], cortado[5], cortado[6], Long.parseLong(cortado[7]), cortado[8],
                            Long.parseLong(cortado[9]), Integer.parseInt(cortado[10]),Integer.parseInt(cortado[11]),
                            Integer.parseInt(cortado[12]),Integer.parseInt(cortado[13]),cortado[14]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al cargar el historial 1");
            }


        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error al cargar el historial 2");
        }
    }

    /**
     * Carga todas las cuentas bancarias a partir de un archivo
     * @param cortado
     */
    private void loadAccountsFromSplitedLine(String[] cortado){
        Persona p = isPersonaOnBanco(cortado[0]);
        if(p == null) {
            System.out.println("Error intentando crear una cuenta para una persona que no existe");
            return;
        }

        if(p.getCuentaUsuario() != null){
            p.getCuentaUsuario().crearCuentaBancaria(cortado[1],Long.parseLong(cortado[3]));
            CuentaBancaria c = p.getCuentaUsuario().isCuentaInCuentasBancarias(Long.parseLong(cortado[3]));
            System.out.println("Cortado 2: " + cortado[2] + " " + ((Integer.parseInt(cortado[2]) == 0)));
            c.setCuentaBloqueada((Integer.parseInt(cortado[2]) == 0));
            c.setMonto(Integer.parseInt(cortado[4]));
            mapaCuentaBancarias.agregarCuentaBancaria(Long.parseLong(cortado[3]),p.getCuentaUsuario().isCuentaInCuentasBancarias(Long.parseLong(cortado[3])));

        }
        else{
            System.out.println("Error cargando la cuenta de la persona " + p.getNombres() + " " + p.getApellidos());
        }
    }


    /**
     * Guarda las modificaciones hechas en dos archivos CSV
     */

    public void saveFiles(){
        // Guardando archivos de las personas
        // Mario Javier,.,Dorochesi Ollino,.,17983727-7,.,Vina del Mar,.,La Marina 330 DPTO 134,.,mario.dorochesio@gmail.com,.,952590319,.,Chilena/o,.,1993,.,12,.,30,.,Soltera/o,.,Hombre,.,2,.,1,.,mario123
        final String sep = ",.,";
        String filePath = (new File(".").getAbsolutePath()).replace(".", "");
        String filePathAux = filePath;
        String filePathUsuario = null;
        filePath = filePath + "archivoprueba.csv";
        String line;
        PrintWriter writer;
        int user;

        try{
            writer = new PrintWriter(filePath, "UTF-8");
            for(Persona p : mapaPersonas.values()){
                filePathUsuario = filePathAux;
                if(isUsuarioOnBanco(p.getRut()) != null) {
                    user = 1;
                    filePathUsuario = filePathAux + "Transferencias/" + p.getRut().substring(0,p.getRut().indexOf('-')) + ".txt";
                    p.getCuentaUsuario().saveTransferencias(filePathUsuario);
                }
                else
                    user = 0;

                line = p.getNombres() + sep + p.getApellidos() + sep + p.getRut() + sep + p.getCiudad() + sep +
                        p.getDireccion() + sep + p.getCorreoElectronico() + sep + p.getTelefono() + sep +
                        p.getNacionalidad() + sep + p.getFechaNacimiento().getYear() + sep +
                        p.getFechaNacimiento().getMonthValue() + sep + p.getFechaNacimiento().getDayOfMonth() + sep +
                        p.getEstadoCivil() + sep + p.getGenero() + sep + (getPermisos(p) - 1) + sep + user + sep +
                        p.getContrasena();
                writer.println(line);

            }
            writer.close();
        }catch (Exception ignored){}


        // Guardando archivo de cuentas bancarias
        filePath = (new File(".").getAbsolutePath()).replace(".", "");
        filePath = filePath + "archivoCuentasBancarias.csv";
        int i;
        try{
            writer = new PrintWriter(filePath, "UTF-8");
            for(CuentaBancaria c : mapaCuentaBancarias.values()){
                if(!c.isCuentaBloqueada()) i = 1;
                else i = 0;

                line = c.getPersona().getRut() + sep + c.getTipoCuenta() + sep + i + sep +
                        c.getIdentificador() + sep + c.getMonto();

                writer.println(line);

            }
            writer.close();
        }catch (Exception ignored){}
    }


    /**
     * Genera un HashMap <Ciudad, Dinero>
     * Se utiliza para generar los
     *s por pantalla y por archivo.
     * @return HashMap Clave: Ciudad, Valor: Dinero en la Ciudad
     */

    public HashMap<String, Long> generarReportePorCiudad(){
        HashMap<String, Long> mapaDineroPorCiudades = new HashMap<>();
        Long aux;
        Persona p;

        for(Persona p2 : mapaPersonas.values())
            if(!p2.getCiudad().equals("NONE"))
                mapaDineroPorCiudades.put(p2.getCiudad(), 0L);

        for (CuentaBancaria c : mapaCuentaBancarias.values()) {
            p = c.getPersona();
            if(!p.getCiudad().equals("NONE")) {
                aux = mapaDineroPorCiudades.get(p.getCiudad()) + c.getMonto() / 1000000;
                System.out.println("Ciudad: " + p.getCiudad() + " " + aux);
                mapaDineroPorCiudades.remove(p.getCiudad());
                mapaDineroPorCiudades.put(p.getCiudad(), aux);
            }
        }
        return mapaDineroPorCiudades;
    }

    /**
     * Retorna la cantidad total de cuentas bancarias en el Banco
     * @return
     */

    public int cantidadCuentasBancarias(){
        return mapaCuentaBancarias.values().size();
    }

    /**
     * Retorna la cantidad total de clientes en una ciudad en especifico
     * @param ciudad
     * @return
     */

    public int totalclientesEnCiudad(String ciudad){
        int total = 0;

        for(Persona p : mapaPersonas.values()){
            if(isUsuarioOnBanco(p.getRut()) != null && p.getCiudad().equals(ciudad))
                total++;
        }

        return total;
    }

    /**
     * Retornala cantidad total de clientes (usuarios) en el Banco
     * @return
     */

    public int totalclientes(){
        int total = 0;

        for(Persona p : mapaPersonas.values()){
            if(isUsuarioOnBanco(p.getRut()) != null)
                total++;
        }

        return total;
    }

    /*
    *   Setters y Getters
    * */

    public float getTasaInteresPrestamo() {
        return tasaInteresPrestamo;
    }

    public void setTasaInteresPrestamo(float tasaInteresPrestamo) {
        this.tasaInteresPrestamo = tasaInteresPrestamo;
    }

    public float getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(float tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public String getLastError() {
        return lastError;
    }

    public String[] obtenerNombresSucursales(){
        return mapaSucursales.obtenerNombresSucursales();
    }

    public SucursalTreeTableView[] obtenerSucursalesForTable(){
        return mapaSucursales.getSucursalesForTable();
    }


    /**
     * Metodo que sobreescribe el metodo de la Interfaz Reportable, que genera un reporte con todos los datos
     * del banco
     */

    @Override
    public void generarReporte(ArrayList<String> reportLines, ScatterChart grafico, JFXTreeTableView tabla,
                               Label lb1, Label lb2, Label lb3, Label lb4, Label lb5) {
        reportLines.removeAll(reportLines);
        String ciudadMasAdinerada = "";
        long ciudadMasAdinerada_valor = 0;
        String ciudadMenosAdinerada = "";
        long ciudadMenosAdinerada_valor = 0;
        boolean primeraIteracion = true;
        long totalValor = 0;
        long valorEstaCiudad = 0;
        long totalCuentas = cantidadCuentasBancarias();

        ObservableList data = FXCollections.observableArrayList();

        // Limpiando data
        grafico.getData().removeAll(grafico.getData());

        // Creando recursos
        XYChart.Series series = new XYChart.Series();
        HashMap<String, Long> mapaCiudades = generarReportePorCiudad();

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
                    ciudad, String.valueOf(valorEstaCiudad), String.valueOf(totalclientesEnCiudad(ciudad))
            ));
            reportLines.add(ciudad + "," +  String.valueOf(valorEstaCiudad) + "," + String.valueOf(totalclientesEnCiudad(ciudad)));
        }
        reportLines.add("");
        grafico.getData().add(series);

        TreeTableColumn c1 = (TreeTableColumn) tabla.getColumns().get(0);
        TreeTableColumn c2 = (TreeTableColumn) tabla.getColumns().get(1);
        TreeTableColumn c3 = (TreeTableColumn) tabla.getColumns().get(2);

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
        tabla.setRoot(root);

        tabla.setShowRoot(false);

        lb1.setText("T. Clientes:\t\t" + totalclientes());
        lb2.setText("T. Cuentas B:\t\t" + cantidadCuentasBancarias());
        lb3.setText("T. Valor:\t\t\t" + totalValor);
        lb4.setText("C. Mas Valor:\t\t" + ciudadMasAdinerada + " (" + ciudadMasAdinerada_valor + ")");
        lb5.setText("C. Menos Valor:\t" + ciudadMenosAdinerada + " (" + ciudadMenosAdinerada_valor + ")");

        reportLines.add("T. Clientes," + totalclientes());
        reportLines.add("T. Cuentas B,\t\t" + cantidadCuentasBancarias());
        reportLines.add("T. Valor,\t\t\t" + totalValor);
        reportLines.add("C. Mas Valor,\t\t" + ciudadMasAdinerada + "," + ciudadMasAdinerada_valor);
        reportLines.add("C. Menos Valor,\t" + ciudadMenosAdinerada + "," + ciudadMenosAdinerada_valor);
    }
}
