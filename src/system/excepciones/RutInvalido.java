package system.excepciones;

public class RutInvalido extends Exception {

    public RutInvalido(String message){
        super(message);
    }

    public RutInvalido(String message , Throwable cause){
        super(message,cause);
    }

    public RutInvalido(){
        super();
    }
}
