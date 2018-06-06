package system.general;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import system.clasesColecciones.MapaPersonas;
import system.interfaces.Reportable;

import java.util.ArrayList;

/*
 *  Esta clase sera implementada en la Entrega B
 * */
public class Sucursal implements Reportable {

    private MapaPersonas mapaPersonasAsociadas;
    private int cantidadPersonasAsociadas;

    private String nombreSucursal;

    private long codigoSucursal;

    private String direccionSucursal;


    public Sucursal(String nombre , String direccionSucursal , long codigoSucursal){
        this.mapaPersonasAsociadas = new MapaPersonas();
        this.nombreSucursal = nombre;
        this.codigoSucursal = codigoSucursal;
        this.direccionSucursal = direccionSucursal;
        this.cantidadPersonasAsociadas = 0;
    }

    public void agregarPersonaSucursal(Persona persona){
        System.out.println("Agregando a " + persona.getNombres() + " a la sucursal " + nombreSucursal);
        mapaPersonasAsociadas.agregarPersona(persona);
        cantidadPersonasAsociadas++;
    }

    public void eliminarPersonaSucursal(Persona persona){
        System.out.println("Eliminando a " + persona.getNombres() + " de la sucursal " + nombreSucursal);
        mapaPersonasAsociadas.eliminarPersona(persona.getRut());
        cantidadPersonasAsociadas--;
    }

    public boolean existePersonaSucursal(Persona persona){
        return mapaPersonasAsociadas.existePersona(persona.getRut());
    }

    public Persona obtenerPersonaSucursal(Persona persona){
        return mapaPersonasAsociadas.obtenerPersona(persona.getRut());
    }

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

    @Override
    public void generarReporte(ArrayList<String> reportLines, ScatterChart grafico,
                               JFXTreeTableView tabla, Label lb1, Label lb2, Label lb3, Label lb4, Label lb5) {

    }
}
