package system.clasesColecciones;

import system.general.Persona;

import java.util.Collection;
import java.util.HashMap;

public class MapaPersonas {

    //Mapa de Personas
    private HashMap<String, Persona> mapaPersonas;


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
     * REHACER ESTO, ROMPE EL ENCAPSULAMIENTO
     * @return
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

}
