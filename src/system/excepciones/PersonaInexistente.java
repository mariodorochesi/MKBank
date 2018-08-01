package system.excepciones;

public class PersonaInexistente extends Exception {

    
    public PersonaInexistente(String message){
        super(message);
    }

    public PersonaInexistente(String message , Throwable cause){
        super(message,cause);
    }

    public PersonaInexistente(){
        super("Persona no encontrada en el sistema");
    }
}
