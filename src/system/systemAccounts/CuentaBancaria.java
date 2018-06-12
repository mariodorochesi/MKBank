package system.systemAccounts;

import system.general.Persona;

public class CuentaBancaria extends CuentaUsuario{

    private long monto;
    private String tipoCuenta;
    private long identificador;
    private boolean cuentaBloqueada;


    /**
     * Constructor por defecto de CuentaBancaria
     * Setea el monto en 0 de la cuenta, la inicializa como no bloqueada y hereda los valores de Persona
     * @param persona Persona dueña de la cuenta
     */
    public CuentaBancaria(Persona persona , String tipoCuentaBancaria, long identificador ){
        super(persona);
        this.identificador = identificador;
        this.tipoCuenta = tipoCuentaBancaria;
        this.monto = 0;
        this.cuentaBloqueada = false;
    }

    /**
     * Funcion que resta el dinero desde una cuenta origen.
     * Verifica que el monto ingresado no sea negativo y ademas que la cuenta
     * tenga los fondos suficientes para transferir el monto
     * @param monto Monto a transferir
     * @return True si pudo transferir, False en caso contrario
     */
    public boolean transferir(long monto){
        //Se comprueba que el monto transferido no sea negativo

        if(monto < 0){
            System.out.println("Error, no se pueden transferir cantidades negativas");
            lastError = "Error!, no se pueden transferir cantidades negativas";
            return false;
        }
        //Se comprueba que tenga los fondos suficientes
        if(this.monto >= monto){
            this.monto = this.monto - monto;
            lastError = "Operacion exitosa!. Se han descontado $"+monto +" pesos de la cuenta Bancaria " + identificador;
            return true;
        }
        else{
            System.out.println("Error, no hay suficientes fondos en su cuenta");
            lastError = "Error, no hay suficientes fondos en su cuenta";
            return false;
        }
    }

    /**
     * Funcion que agrega dinero hacia una cuenta destino.
     * Verifica que el monto ingresado no sea negativo
     * @param monto Monto a Transferir
     * @return True si pudo transferir, False en caso contrario
     */
    public boolean depositar(long monto){
        if(monto < 0){
            System.out.println("Error, no se pueden depositar montos negativos");
            lastError = "Error, no se pueden depositar montos negativos";
            return false;
        }
        this.monto = this.monto + monto;
        lastError = "Operacion exitosa!. Se han añadido $" + monto + " pesos a la cuenta Bancaria " + identificador;
        return true;
    }

    /*
    *   Setters y Getters
    * */

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public boolean isCuentaBloqueada() {
        return cuentaBloqueada;
    }

    public void setCuentaBloqueada(boolean cuentaBloqueada) {
        this.cuentaBloqueada = cuentaBloqueada;
    }

    public Persona getPersona(){
        return persona;
    }
}
