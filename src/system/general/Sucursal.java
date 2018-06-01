package system.general;

import system.clasesColecciones.MapaPersonas;
import system.interfaces.Reportable;

import java.util.HashMap;


/*
 *  Esta clase sera implementada en la Entrega B
 * */
public class Sucursal implements Reportable {

    private MapaPersonas mapaPersonasAsociadas;

    private String nombreSucursal;

    private long codigoSucursal;

    private String direccionSucursal;

    public Sucursal(String nombre , String direccionSucursal , long codigoSucursal){
        this.mapaPersonasAsociadas = new MapaPersonas();
        this.nombreSucursal = nombre;
        this.codigoSucursal = codigoSucursal;
        this.direccionSucursal = direccionSucursal;
    }

    public void agregarPersonaSucursal(Persona persona){
        mapaPersonasAsociadas.agregarPersona(persona);
    }

    public boolean existePersonaSucursal(Persona persona){
        return mapaPersonasAsociadas.existePersona(persona.getRut());
    }

    public Persona obtenerPersonaSucursal(Persona persona){
        return mapaPersonasAsociadas.obtenerPersona(persona.getRut());
    }

    @Override
    public void generarReporte() {
        // TODO: generar reporte de la sucursal
    }


    /*
    *   Setters y Getters
    **/
}
