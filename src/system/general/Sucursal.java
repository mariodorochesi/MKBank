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
import system.clasesColecciones.MapaPersonas;
import system.interfaces.Reportable;
import system.systemAccounts.CuentaBancariaRecursiveTree;

import java.util.ArrayList;
import java.util.HashMap;

public class Sucursal implements Reportable {

    //Mapa de Personas Asociadas a la Sucursal
    private MapaPersonas mapaPersonasAsociadas;
    //Cantidad de Personas que posee la Sucursal
    private int cantidadPersonasAsociadas;

    //Nombre de la Sucursal
    private String nombreSucursal;

    //Codigo de la Sucursal
    private long codigoSucursal;

    //Direccion de la Sucursal
    private String direccionSucursal;

    /**
     * Constructor de la Sucursañ
     * @param nombre Nombre
     * @param direccionSucursal Direccion de la Sucursal
     * @param codigoSucursal Codigo de la Sucursal
     */

    public Sucursal(String nombre , String direccionSucursal , long codigoSucursal){
        this.mapaPersonasAsociadas = new MapaPersonas();
        this.nombreSucursal = nombre;
        this.codigoSucursal = codigoSucursal;
        this.direccionSucursal = direccionSucursal;
        this.cantidadPersonasAsociadas = 0;
    }

    /**
     * Metood para Agregar a una Persona a la Sucursal
     * @param persona Persona a agregar
     */

    public void agregarPersonaSucursal(Persona persona){
        System.out.println("Agregando a " + persona.getNombres() + " a la sucursal " + nombreSucursal);
        mapaPersonasAsociadas.agregarPersona(persona);
        cantidadPersonasAsociadas++;
    }

    /**
     * Metodo para Eliminar una Persona de la Sucursal
     * @param persona Persona a Eliminar de la Sucursal
     */

    public void eliminarPersonaSucursal(Persona persona){
        System.out.println("Eliminando a " + persona.getNombres() + " de la sucursal " + nombreSucursal);
        mapaPersonasAsociadas.eliminarPersona(persona.getRut());
        cantidadPersonasAsociadas--;
    }

    /**
     * Metood para verificar si existe una Persona en la Sucursal
     * @param persona Persona a verificar
     * @return True si exite / False en caso contrario
     */

    public boolean existePersonaSucursal(Persona persona){
        return mapaPersonasAsociadas.existePersona(persona.getRut());
    }

    /*
     *  SECCION DE SETTERS Y GETTERS
      * */

    public String getNombreSucursal(){
        return nombreSucursal;
    }
    public String getDireccionSucursal(){
        return direccionSucursal;
    }

    public long getCodigoSucursal(){
        return codigoSucursal;
    }

    public int getCantidadPersonasAsociadas() {
        return cantidadPersonasAsociadas;
    }


    /**
     * Metodo que sobreescribe el metodo generarReporte implementado de la interfaz Reportable, Genera un Grafico con
     * toda la gente que pertenece a la Sucursal
     */

    @Override
    public void generarReporte(ArrayList<String> reportLines, ScatterChart grafico,
                               JFXTreeTableView tabla, Label lb1, Label lb2, Label lb3, Label lb4, Label lb5) {
        reportLines.removeAll(reportLines);
        int nHombres = mapaPersonasAsociadas.obtenerNumeroHombres();
        int nMujeres = mapaPersonasAsociadas.obtenerNumeroMujeres();
        int nEjecutivos = mapaPersonasAsociadas.obtenerNumeroEjecutivos();
        int nPersonas = mapaPersonasAsociadas.obtenerNumeroPersonas();
        int dineroTotal = mapaPersonasAsociadas.obtenerDineroTotal();

        // Data de la tabla
        ObservableList data = FXCollections.observableArrayList();

        // Limpiando data
        grafico.getData().removeAll(grafico.getData());

        reportLines.add("Reporte de la sucursal " + getNombreSucursal());
        reportLines.add("Cliente,Nombre,Dinero Total, N° de cuentas bancarias");

        mapaPersonasAsociadas.actualizarTabla(tabla, reportLines);

        // TODO: Actualizar grafico
        mapaPersonasAsociadas.actualizarGrafico(grafico);

        lb1.setText("C. Hombres:\t\t" + nHombres);
        lb2.setText("C. Mujeres:\t\t" + nMujeres);
        lb3.setText("C. Ejecutivos:\t\t\t" + nEjecutivos);
        lb4.setText("C. De Personas:\t\t" + nPersonas);
        lb5.setText("D. Total S.:\t" + dineroTotal);

        reportLines.add("C. Hombres:\t\t" + nHombres);
        reportLines.add("C. Mujeres:\t\t" + nMujeres);
        reportLines.add("C. Ejecutivos:\t\t\t" + nEjecutivos);
        reportLines.add("C. De Personas:\t\t" + nPersonas);
        reportLines.add("D. Total S.:\t" + dineroTotal);

    }
}
