package system.systemAccounts;


import system.clasesColecciones.MapaCuentaBancarias;
import system.general.Persona;
import system.general.Transferencias;
import system.clasesColecciones.ListaTransferencias;

import java.util.ArrayList;
import java.util.HashMap;

public  class CuentaUsuario extends Cuenta {

    private MapaCuentaBancarias mapaCuentasBancarias; // Mapa con las cuentas Bancarias del Usuario
    private ListaTransferencias listaTransferencias; // Lista de Transferencias Bancarias del Usuario
    //private ArrayList<PagosAutomatico> listaPagosAutomaticos;
    private boolean cuentaBloqueada; //Booleano para ver si el usuario esta o no bloqueado
    private short numeroCuentas; //Cantidad de cuentas que tiene el usuasrio

    public CuentaUsuario(Persona persona){
        super(persona);
        this.cuentaBloqueada = false;
        this.mapaCuentasBancarias = new MapaCuentaBancarias();
        this.listaTransferencias = new ListaTransferencias();
        this.numeroCuentas = 0;
    }


    public CuentaBancaria isCuentaInCuentasBancarias(long identificador){
        return mapaCuentasBancarias.obtenerCuentaBancaria(identificador);
    }

    /**
     * Funcion que bloquea una cuenta bancaria si esta no se encuentra bloqueada,
     * o bien desbloquea una cuenta bancaria si esta se encontraba bloqueada.
     *
     * @param cuentaBancaria Cuenta a bloquear-desbloquear
     */
    public void bloquearDesbloquearCuentaBancaria(CuentaBancaria cuentaBancaria){
        if(cuentaBancaria.isCuentaBloqueada()){
            System.out.println("La cuenta ha sido desbloqueada");
            lastError = "La cuenta " + cuentaBancaria.getIdentificador() + " ha sido bloqueada";
            cuentaBancaria.setCuentaBloqueada(false);
        }
        else{
            System.out.println("La cuenta ha sido bloqueada");
            lastError = "La cuenta " + cuentaBancaria.getIdentificador() + " ha sido desbloqueada";
            cuentaBancaria.setCuentaBloqueada(true);
        }
    }

    /**
     * Se crea una cuenta Bancaria y se añade en la Lista de Cuentas Bancarias del Usuario
     * La nueva cuenta creada comienza con el monto ingresado por el usuario
     */
    public boolean crearCuentaBancaria(String tipoCuentaBancaria, long identificador){
        final int MAXCUENTA = 5;
        if(numeroCuentas >= MAXCUENTA ){
            System.out.println("El ususario " + persona.getNombres() + " " + persona.getApellidos() +" ha alcanzado el limite de cuentas.");
            lastError = "El usuario " + persona.getNombres() + " " + persona.getApellidos() + " ha alcanzado el limite de cuentas";
            return false;
        }
        CuentaBancaria agregar = new CuentaBancaria(persona , tipoCuentaBancaria, identificador);
        System.out.println("La cuenta bancaria " + identificador + " ha sido agregada exitosamente a la lista de cuentas de "
                + persona.getNombres() + " " + persona.getApellidos() +".");
        lastError = "La cuenta " + identificador + " ha sido agregada exitosamente";
        mapaCuentasBancarias.agregarCuentaBancaria(identificador,agregar);
        numeroCuentas++;
        return true;
    }

    public boolean eliminarCuentaBancaria(long identificador){
        if(numeroCuentas == 0){
            System.out.println("El usuario " + persona.getNombres() + " " + persona.getApellidos() + " no posee ninguna cuenta Bancaria");
            lastError = "El usuario " + persona.getNombres() + " " + persona.getApellidos() + " no posee ninguna cuenta Bancaria";
            return false;
        }
        if(isCuentaInCuentasBancarias(identificador) == null){
            System.out.println("El usuario " + persona.getNombres() + " " + persona.getApellidos() + " no tiene la cuenta " +
            identificador + " asociada a su lista de cuentas");
            lastError = "La cuenta " + identificador + " no se encuentra asociada al usuario " + persona.getNombres() + " " + persona.getApellidos();
            return false;
        }
        else{
            if(isCuentaInCuentasBancarias(identificador).getMonto() == 0 ) {
                mapaCuentasBancarias.eliminarCuentaBancaria(identificador);
                numeroCuentas--;
                System.out.println("La cuenta " + identificador + " ha sido eliminada del Usuario " + persona.getNombres() + " "
                        + persona.getApellidos());
                lastError = "La cuenta " + identificador + " ha sido eliminada exitosamente";
                return true;
            }
            else {
                System.out.println("No se pueden eliminar Cuentas Bancarias con dinero");
                lastError = "No se pueden eliminar Cuentas Bancarias con dinero. ";
                return false;
            }
        }
    }

    /**
     * Transfiere un monto desde una cuentaOrigen a una cuentaDestino corroborando que
     * la cuentaOrigen no se encuentre bloqueada y ademas que la cuentaOrigen tenga el
     * monto suficiente para transferir.
     * @param cuentaOrigen CuentaBancaria origen
     * @param cuentaDestino CuentaBancaria destino
     * @param monto Monto a transferir
     * @return True si pudo transferir, False en caso contrario
     */
    public boolean transferir(CuentaBancaria cuentaOrigen , CuentaBancaria cuentaDestino, int monto){
        if(cuentaOrigen.isCuentaBloqueada()){
            System.out.println("No puede transferir ya que la cuenta origen se encuentra bloqueada");
            lastError = "No se puede transferir dinero desde " + cuentaOrigen.getIdentificador()
            + ", ya que su cuenta se encuentra bloqueada";
            return false;
        }
        if(cuentaOrigen.transferir(monto)){
            if(cuentaDestino.depositar(monto)) {
                lastError = "Transaccion realizada correctamente!.";
                return true;
            }
            else{
                lastError = cuentaDestino.getLastError();
                return false;
            }
        }
        else{
            lastError = cuentaOrigen.getLastError();
            return false;
        }

    }

    public boolean agregarTransferencia(CuentaBancaria cuentaOrigen,
                                        CuentaBancaria cuentaDestino,
                                        long identificador,
                                        int monto,
                                        String comentario){
        return listaTransferencias.agregarTransferencia(cuentaOrigen,cuentaDestino,identificador,monto,comentario);
    }

    public boolean agregarTransferencia(Transferencias t){
        return listaTransferencias.agregarTransferencia(t);
    }

    public boolean agregarTransferencia(String nombreOriginario,
                                        String rutOriginario,
                                        String tipocuentaOrigen,
                                        long numeroCuentaOrigen,
                                        String nombreDestinatario,
                                        String rutDestinatario,
                                        String tipoCuentaDestinatario,
                                        long numeroCuentaDestinatario,
                                        String correoDestinatario,
                                        long numeroTransferencia,
                                        int montoTransferencia,
                                        int annosTransferencia,
                                        int mesTransferencia,
                                        int diaTransferencia,
                                        String comentarioTransferencia){
        return listaTransferencias.agregarTransferencia(nombreOriginario,rutOriginario,tipocuentaOrigen,numeroCuentaOrigen,
                nombreDestinatario,rutDestinatario,tipoCuentaDestinatario,numeroCuentaDestinatario,correoDestinatario,numeroTransferencia,
                montoTransferencia,annosTransferencia,mesTransferencia,diaTransferencia,comentarioTransferencia);
    }


    /**
     * Retorna un ArrayList con las N ultimas transacciones realizadas
     * por el usuario
     * @param cantidadMostrar //Numero de ultimas transacciones a mostrar
     * @return ArrayList con transacciones
     */
    public ArrayList<Transferencias> historialTransacciones(int cantidadMostrar){
        return listaTransferencias.historialTransacciones(cantidadMostrar);
    }


    /*
     *   Setters y Getters
     * */

    public void eliminarCuentasBancarias(){
        mapaCuentasBancarias.clear();
        mapaCuentasBancarias = null;
    }

    public ArrayList<CuentaBancaria> getCuentasBancarias(){
        ArrayList<CuentaBancaria> listaCuentasBancarias = new ArrayList<>();
        for(CuentaBancaria cuentaBancaria : mapaCuentasBancarias.values()){
            listaCuentasBancarias.add(cuentaBancaria);
        }
        return listaCuentasBancarias;
    }

    public long montoTotalUsuario(){
        long montoTotal = 0;
        for(CuentaBancaria cuentaBancaria : mapaCuentasBancarias.values()){
            montoTotal = montoTotal + cuentaBancaria.getMonto();
        }
        return montoTotal;
    }

    public void saveTransferencias(String rutaArchivo){
        listaTransferencias.saveTransferencias(rutaArchivo);
    }

    public boolean getCuentaBloqueada(){
        return cuentaBloqueada;
    }

    public void setCuentaBloqueada(boolean cuentaBloqueada){
        this.cuentaBloqueada = cuentaBloqueada;
    }

    public Persona getPersona(){
        return persona;
    }

    public boolean isCuentaBloqueada() {
        return cuentaBloqueada;
    }

    public short getNumeroCuentas() {
        return numeroCuentas;
    }

    public void setNumeroCuentas(short numeroCuentas) {
        this.numeroCuentas = numeroCuentas;
    }
}
