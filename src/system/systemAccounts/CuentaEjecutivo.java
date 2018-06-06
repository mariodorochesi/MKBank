package system.systemAccounts;


import system.general.Persona;
import system.interfaces.Reportable;

public class CuentaEjecutivo extends Cuenta{

    private boolean cuentaBloqueada;

    public CuentaEjecutivo(Persona persona){
        super(persona);
        this.cuentaBloqueada = false;
    }

    /*
    *   SECCION BLOQUEOS/DESBLOQUEOS DE CUENTAS
    * */

    /**
     * Bloquea a un usuario si y solo si este no se encuentra bloqueado
     * En caso de que ya se encuentre bloqueado, lo desbloquea
     * @param cuentaUsuario Usuario a bloquear
     */
    public void bloquearDesbloquar(CuentaUsuario cuentaUsuario){
        if(cuentaUsuario.getCuentaBloqueada()){
            System.out.println("El usuario " + cuentaUsuario.getPersona().getNombres() + " ha sido desbloqueado");
            lastError = "El usuario " + cuentaUsuario.getPersona().getNombres() + " ha sido desbloqueado";
            cuentaUsuario.setCuentaBloqueada(false);
        }
        else {
            System.out.println("El usuario " + cuentaUsuario.getPersona().getNombres() + " ha sido bloqueado");
            lastError = "El usuario " + cuentaUsuario.getPersona().getNombres() + " ha sido bloqueado";
            cuentaUsuario.setCuentaBloqueada(true);
        }
    }

    /**
     * Bloquea una cuenta bancaria si y solo si esta no se encuentra bloqueada
     * En caos de que ya se encuentre bloqueada, la desbloquea
     * @param cuentaBancaria Cuenta a desbloquear
     */
    public void bloquearDesbloquear(CuentaBancaria cuentaBancaria){
        if(cuentaBancaria.isCuentaBloqueada()){
            System.out.println("La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " perteneciente a " + cuentaBancaria.getPersona().getNombres() + " ha sido desbloqueada.");
            lastError = "La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " ha sido desbloqueada.";
            cuentaBancaria.setCuentaBloqueada(false);
        }
        else {
            System.out.println("La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " perteneciente a " + cuentaBancaria.getPersona().getNombres() + " ha sido bloqueada.");
            lastError = "La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " ha sido bloqueada.";
            cuentaBancaria.setCuentaBloqueada(true);
        }
    }

    /**
     * Crea una nueva Persona, genera un log de creacion de Persona y retorna la nueva Persona creada
     * @param nombres Nombres nueva persona
     * @param apellidos Apellidos nueva persona
     * @param rut Rut nueva persona
     * @param ciudad Ciudad nueva persona
     * @param direccion Direccion nueva persona
     * @param correoElectronico correoElectronico nueva persona
     * @param telefono telefono nueva persona
     * @param nacionalidad nacionalidad nueva persona
     * @param annoNacimiento annoNacimiento nueva persona
     * @param mesNacimiento mesNacimiento nueva persona
     * @param diaNacimiento diaNacimiento nueva persona
     * @param estadoCivil estadoCivil nueva persona
     * @param genero genero nueva persona
     * @return Objeto del Tipo Persona
     */
    public Persona crearPersona(String nombres,
                                String apellidos,
                                String rut,
                                String ciudad,
                                String direccion,
                                String correoElectronico,
                                String telefono,
                                String nacionalidad,
                                int annoNacimiento,
                                int mesNacimiento,
                                int diaNacimiento,
                                String estadoCivil,
                                String genero)
    {
        Persona nuevaPersona = new Persona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,telefono,nacionalidad,
                annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil,genero);
        System.out.println("El ejecutivo " + persona.getNombres() + " " + persona.getApellidos() + " ha creado a la Persona " + nuevaPersona.getNombres() + " " + nuevaPersona.getApellidos()+".");
        lastError = "Has creado exitosamente a la Persona " + nuevaPersona.getNombres() + " " + nuevaPersona.getApellidos() + ".";
        return nuevaPersona;
    }

    /*
    *   SECCION CREACION/ELIMINACION DE CUENTAS
    * */

    /**
     * Asigna una nueva CuentaUsuario a la Persona si y solo si no ha sido anteriormente instanciado como tal
     * @param persona Persona a quien se le asignara una nueva cuentaUsuario
     * @return True si fue asignada una CuentaUsuario/ False en caso contrario
     */
    public boolean crearCuentaUsuario(Persona persona){
        if(persona.getCuentaUsuario() != null){
            System.out.println("La persona " + persona.getNombres() + " ya tiene una cuenta Usuario asociada.");
            lastError = "Error! ,la persona " + persona.getNombres() + " " + persona.getApellidos() + " ya tiene una cuenta Usuario asociada.";
            return false;
        }
        else{
            System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " ha sido asignada como Usuario.");
            lastError = "La persona " + persona.getNombres() + " " + persona.getApellidos() + " ha sido asignada como Usuario.";
            persona.setCuentaUsuario(new CuentaUsuario(persona));
            return true;
        }
    }

    /**
     * Crea una nueva Cuenta Bancaria al usuario. La nueva cuenta tiene saldo 0
     * @param cuentaUsuario Usuario a quien se le añade una cuenta bancaria
     */
    public boolean crearCuentaBancaria(CuentaUsuario cuentaUsuario , String tipoCuentaBancaria, long identificador){
        final boolean resultado = cuentaUsuario.crearCuentaBancaria(tipoCuentaBancaria,identificador);
        lastError = cuentaUsuario.getLastError();
        return resultado;
    }

    /**
     * Elimina la cuenta bancaria asociada al identificador de la cuenta Usuario
     * @param cuentaUsuario Usuario
     * @param identificador Identificador de cuenta a eliminar
     * @return True/False
     */

    public boolean eliminarCuentaBancaria(CuentaUsuario cuentaUsuario, long identificador){
        final boolean resultado = cuentaUsuario.eliminarCuentaBancaria(identificador);
        lastError = cuentaUsuario.getLastError();
        return resultado;
    }

    /**
     * Deposita en la Cuenta asociada al identificador la cantidad monto
     * @param cuentaUsuario Cuenta Usuario
     * @param identificador Identificador de la Cuenta
     * @param monto Monto a depositar
     * @return True/False
     */

    public boolean depositarCuentaBancaria(CuentaUsuario cuentaUsuario,long identificador, int monto){
        if(cuentaUsuario.isCuentaInCuentasBancarias(identificador) == null){
            lastError = "La cuenta bancaria no esta asociada al Usuario";
            return false;
        }
        else{
            final boolean resultado = cuentaUsuario.isCuentaInCuentasBancarias(identificador).depositar(monto);
            lastError = cuentaUsuario.isCuentaInCuentasBancarias(identificador).getLastError();
            return resultado;
        }
    }

    /**
     * Retira de la cuentaBancaria identificador asociada a la cuenta Usuario, la cantidad pasada por
     * monto
     * @param cuentaUsuario Cuenta Usuario
     * @param identificador Identificador Cuenta Bancaria
     * @param monto Monto a retirar
     * @return
     */

    public boolean retirarCuentaBancaria(CuentaUsuario cuentaUsuario, long identificador, int monto){
        if(cuentaUsuario.isCuentaInCuentasBancarias(identificador) == null){
            lastError = "La cuenta bancaria no esta asociada la Usuario";
            return false;
        }
        else{
            final boolean resultado = cuentaUsuario.isCuentaInCuentasBancarias(identificador).transferir(monto);
            lastError = cuentaUsuario.isCuentaInCuentasBancarias(identificador).getLastError();
            return resultado;
        }

    }
    /*
        Setters y Getters
     */

    public boolean isCuentaBloqueada() {
        return cuentaBloqueada;
    }

    public void setCuentaBloqueada(boolean cuentaBloqueada) {
        this.cuentaBloqueada = cuentaBloqueada;
    }

    public Persona getPersona(){
        return  persona;
    }
}
