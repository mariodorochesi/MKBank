package system.systemAccounts;

import system.general.Persona;

public abstract class Cuenta {

    protected Persona persona;
    protected String lastError;

    public Cuenta(Persona persona){
        this.persona = persona;
    }

    public Persona getPersona(){
        return persona;
    }

    public String getLastError() {
        return lastError;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }
}
