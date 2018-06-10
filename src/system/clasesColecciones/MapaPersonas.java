package system.clasesColecciones;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import system.general.Persona;
import system.general.PersonaTreeTableView;
import system.systemAccounts.CuentaBancaria;
import system.systemAccounts.CuentaBancariaRecursiveTree;
import system.systemAccounts.CuentaUsuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MapaPersonas {

    // Mapa de Personas
    private HashMap<String, Persona> mapaPersonas;

    // Arreglo de personas (Esto funciona para recorrerlo)
    private Persona[] arrayPersonas;
    private int current = 0;


    /*
    *   Constructor de Mapa Personas
    * */
    public MapaPersonas(){
        mapaPersonas = new HashMap<>();
    }

    /** Agrega a una nueva Persona al Mapa de Personas, recibiendo todos sus atributos
     * @param nombre Nombre de la Persona a Agregar
     * @param apellidos Apellido de la Persona a Agregar
     * @param rut Rut de la Persona a Agregar
     * @param ciudad Ciudad de la Persona a Agregar
     * @param direccion Direccion de la Persona a Agregar
     * @param correoElectronico CorreoElectronico de la Persona a Agregar
     * @param telefono Telefono de la Persona a Agregar
     * @param nacionalidad Nacionalidad de la Persona a Agregar
     * @param annoNacimiento Anno Nacimiento de la Persona a Agregar
     * @param mesNacimiento Mes Nacimiento de la Persona a Agregar
     * @param diaNacimiento Dia Nacimiento de la Persona a Agregar
     * @param estadoCivil Estado civil de la Persona a Agregar
     * @param genero Genero de la Persona a Agregar
     */

    public void agregarPersona(String nombre , String apellidos, String rut, String ciudad ,
                               String direccion , String correoElectronico,
                               String telefono, String nacionalidad, int annoNacimiento,
                               int mesNacimiento , int diaNacimiento, String estadoCivil, String genero){
        Persona nuevaPersona = new Persona(nombre,apellidos,rut,ciudad,direccion,correoElectronico,telefono,
                nacionalidad,annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil,genero);
        mapaPersonas.put(rut,nuevaPersona);
    }


    /**
     * Sobrecarga del metood anterior, que agrega una persona la mapa de personas
     * @param p Persona a agregar
     */

    public void agregarPersona(Persona p){
        mapaPersonas.put(p.getRut(),p);
    }

    /**
     * Elimina a la Persona del Mapa de Personas, en caso de existir
     * @param rut Rut de la Persona a Eliminar
     */

    public void eliminarPersona(String rut){
        mapaPersonas.remove(rut);
    }


    /**
     *
     * @param rut Rut de la Persona a buscar
     * @return Retorna true si existe la persona, false en caso contrario
     */

    public boolean existePersona(String rut){
        return mapaPersonas.get(rut) != null;
    }


    /**
     * Retorna a la Persona en caso de ser encontrada en el Mapa de Personas
     * @param rut Rut de la persona
     * @return Retorna el objeto Persona si fue encontrado
     */

    public Persona obtenerPersona(String rut){
        return mapaPersonas.get(rut);
    }



    /**
     * Retorna un arreglo con las personas que contiene el mapa
     * Es necesario, para realizar ciertas funciones del banco
     * @return Arreglo con personas
     */

    public Collection<Persona> values(){
        return mapaPersonas.values();
   }


    /**
     * Elimina la Cuenta Administrador de la Persona pasada por rut
     * @param rut Rut de la persona a quie se desea eliminar la cuenta
     * No retorna nada
     */

    public void eliminarCuentaAdministrador(String rut){
        if(existePersona(rut)){
            mapaPersonas.get(rut).setCuentaAdministrador(null);
        }
    }



    /**
     * Elimina la Cuenta Ejecutivo de la Persona pasada por rut
     * @param rut Rut de la persona a quie se desea eliminar la cuenta
     * No retorna nada
     */

    public void eliminarCuentaEjecutivo(String rut){
        if(existePersona(rut)){
            mapaPersonas.get(rut).setCuentaEjecutivo(null);
        }
    }



    /**
     * Elimina la Cuenta Usuario de la Persona pasada por rut
     * @param rut Rut de la persona a quie se desea eliminar la cuenta
     * No retorna nada
     */

    public void eliminarCuentaUsuario(String rut){
        if(existePersona(rut)){
            mapaPersonas.get(rut).setCuentaUsuario(null);
        }
    }

    /**
     * Obtiene la cantidad de hombres almacenados en este mapa de personas
     * @return  - Retorna la cantidad de hombres
     */

    public int obtenerNumeroHombres(){
        int cantidad = 0;
        for(Persona p : mapaPersonas.values()){
            if(p.getGenero().equals("Hombre"))
                cantidad++;
        }
        return cantidad;
    }

    /**
     * Obtiene la cantidad de hombres almacenados en este mapa de personas
     * @return  - Retorna la cantidad de hombres
     */

    public int obtenerNumeroMujeres(){
        int cantidad = 0;
        for(Persona p : mapaPersonas.values()){
            if(p.getGenero().equals("Mujer"))
                cantidad++;
        }
        return cantidad;
    }

    /**
     * Obtiene la cantidad de personas que tienen permisos de ejecutivos
     * @return  - Retorna la cantidad de ejecutivos
     */

    public int obtenerNumeroEjecutivos(){
        int cantidad = 0;
        for(Persona p : mapaPersonas.values()){
            if(p.getCuentaEjecutivo()!=null)
                cantidad++;
        }
        return cantidad;
    }

    /**
     * Obtiene la cantidad de personas totales en el mapa de personas
     * @return  - Retorna la cantidad de personas totales en el mapa de personas
     */

    public int obtenerNumeroPersonas(){
        return mapaPersonas.values().size();
    }

     /**
     * @return  - Retorna la cantidad de dinero total de las personas del mapa
     */

    public int obtenerDineroTotal(){
        int cantidad = 0;
        CuentaUsuario cu;
        for(Persona p : mapaPersonas.values()){
            cu = p.getCuentaUsuario();
            if(cu != null){
                cantidad += cu.obtenerDineroTotal();
            }
        }
        return cantidad;
    }

    /**
     * Actualiza el contenido de la tabla ingresada como parametros con el contenido del
     * mapa actual.
     * @param tabla         - Tabla que se quiere actualizar
     * @param reportLines   - ArrayList en el que se alacenaran datos en fomato csv
     */

    public void actualizarTabla(JFXTreeTableView tabla, ArrayList<String> reportLines){
        ObservableList data = FXCollections.observableArrayList();
        for(Persona p : mapaPersonas.values()){
            if(p.getCuentaUsuario() == null)
                continue;

            // actualizando la data de la tabla
            data.add(new PersonaTreeTableView(
                    p.getRut(), p.getNombres() + " " + p.getApellidos(),
                    String.valueOf(p.getCuentaUsuario().obtenerDineroTotal()), String.valueOf(p.getCuentaUsuario().getNumeroCuentas())
            ));

            // agregando una nueva linea al reporte por archivo
            reportLines.add(p.getRut() + "," + p.getNombres() + " " + p.getApellidos() + "," +
                    String.valueOf(p.getCuentaUsuario().obtenerDineroTotal()) + "," + String.valueOf(p.getCuentaUsuario().getNumeroCuentas()));
        }

        TreeTableColumn c1 = (TreeTableColumn) tabla.getColumns().get(0);
        TreeTableColumn c2 = (TreeTableColumn) tabla.getColumns().get(1);
        TreeTableColumn c3 = (TreeTableColumn) tabla.getColumns().get(2);
        TreeTableColumn c4 = (TreeTableColumn) tabla.getColumns().get(3);

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
        tabla.setRoot(root);

        tabla.setShowRoot(false);
    }

    /**
     * Actualiza el contenido del grafico ingresado como parametro con el contenido del mapa
     * actual. Este grafico tiene que ser Personas (x) vs Dinero total de sus cuentas (y).
     * @param grafico   - Grafico que se quiere actualizar.
     */

    public void actualizarGrafico(ScatterChart grafico){
        // Limpiando data del grafico
        grafico.getData().removeAll(grafico.getData());

        XYChart.Series series = new XYChart.Series();

        for(Persona p : mapaPersonas.values()){
            if(p.getCuentaUsuario() == null)
                continue;
            series.getData().add(new XYChart.Data(p.getRut(), p.getCuentaUsuario().obtenerDineroTotal()));
        }

        grafico.getData().add(series);
    }

}
