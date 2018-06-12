package system.systemAccounts;

import system.general.Persona;

public abstract class Cuenta {

    //Persona Asociada a la Cuenta
    protected Persona persona;
    //Posible ultimo error generado
    protected String lastError;

    /**
     * Constructor de la clase Cuenta
     * @param persona Persona de la cual se setean los datos
     */

    public Cuenta(Persona persona){
        this.persona = persona;
    }

    /**
     * Metodo abstracto que permite obtener el permiso de una determinada cuenta
     * para poder realizar acciones en base a sus permisos
     * @return Nivel de permiso de la Cuenta
     */

    public abstract short obtenerPermisos();

    /*
    *   Getters y Setters
    * */

    public Persona getPersona(){ return persona; }

    public String getLastError() { return lastError; }

    public void setPersona(Persona persona) { this.persona = persona; }

}
