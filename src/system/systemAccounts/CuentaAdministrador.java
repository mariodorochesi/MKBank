package system.systemAccounts;

import system.general.Persona;


import java.lang.annotation.Documented;

public class CuentaAdministrador extends Cuenta{

    private boolean cuentaBloqueada;

    public CuentaAdministrador(Persona persona){
        super(persona);
        this.cuentaBloqueada =  false;
    }

    /**
     * Crea y retorna una Persona nueva, inicializada con los valorees pasados por parametros.
     * Ademas deja en el log un mensaje referente a quien fue el Administrador que creo a la Persona
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
     * @return
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
        System.out.println("El Administrador " + persona.getNombres() + " " + persona.getApellidos() + " ha creado a la Persona "
        + nuevaPersona.getNombres() + " " + nuevaPersona.getApellidos());
        lastError = "Has creado exitosamente a la Persona " + nuevaPersona.getNombres() + " " + nuevaPersona.getApellidos() + ".";
        return nuevaPersona;
    }

    /*
        SECCION BLOQUEOS / DESBLOQUEOS DE CUENTAS
     */

    /**
     * Bloquea a un ejecutivo si y solo si este no se encuentra bloqueado
     * En caso de que ya se encuentre bloqueado, lo desbloquea
     * @param cuentaEjecutivo Ejecutivo a bloquear/desbloquear
     */
    public void bloquearDesbloquear(CuentaEjecutivo cuentaEjecutivo){
        if(cuentaEjecutivo.isCuentaBloqueada()){
            System.out.println("El ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " " +
                    cuentaEjecutivo.getPersona().getApellidos()+ "ha sido desbloqueado por " + persona.getNombres() + " "
                    + persona.getApellidos() + ".");
            lastError = "El ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " " + cuentaEjecutivo.getPersona().getApellidos() +
                    " ha sido desbloqueado";
            cuentaEjecutivo.setCuentaBloqueada(false);
        }
        else{
            System.out.println("El ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " " +
                    cuentaEjecutivo.getPersona().getApellidos() + "ha sido bloqueado por " + persona.getNombres() + " "
                    + persona.getApellidos() + ".");
            lastError = "El ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " " + cuentaEjecutivo.getPersona().getApellidos() +
                    " ha sido bloqueado";
            cuentaEjecutivo.setCuentaBloqueada(true);
        }
    }
    /**
     * Bloquea a un usuario si y solo si este no se encuentra bloqueado
     * En caso de que ya se encuentre bloqueado, lo desbloquea
     * @param cuentaUsuario Usuario a bloquear
     */
    public void bloquearDesbloquar(CuentaUsuario cuentaUsuario){
        if(cuentaUsuario.getCuentaBloqueada()){
            System.out.println("El usuario " + cuentaUsuario.getPersona().getNombres() +
                    " " + cuentaUsuario.getPersona().getApellidos() + " ha sido desbloqueado por " + persona.getNombres()
                    + " " + persona.getApellidos() + ".");
            lastError = "El usuario " + cuentaUsuario.getPersona().getNombres() +
                    " " + cuentaUsuario.getPersona().getApellidos() + " ha sido desbloqueado";

            cuentaUsuario.setCuentaBloqueada(false);
        }
        else {
            System.out.println("El usuario " + cuentaUsuario.getPersona().getNombres() + " "
                    + cuentaUsuario.getPersona().getApellidos() + " ha sido bloqueado por " + persona.getNombres()
                    + " " + persona.getApellidos() + ".");
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
            System.out.println("La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " perteneciente a " + cuentaBancaria.getPersona().getNombres() +
                    " " + cuentaBancaria.getPersona().getApellidos() + " ha sido desbloqueada por " + persona.getNombres()
                    + " " + persona.getApellidos() + ".");

            lastError = "La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " ha sido desbloqueada.";
            cuentaBancaria.setCuentaBloqueada(false);
        }
        else {
            System.out.println("La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " perteneciente a " + cuentaBancaria.getPersona().getNombres() +
                    " " + cuentaBancaria.getPersona().getApellidos() +  " ha sido bloqueada por " + persona.getNombres()
                    + " " + persona.getApellidos() + ".");

            lastError = "La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " ha sido bloqueada.";
            cuentaBancaria.setCuentaBloqueada(true);
        }
    }

    /*
    *   SECCION CREACION/ELIMINACION DE CUENTAS
    * */

    /**
     * Asigna una CuentaEjecutivo a la Persona si y solo si no ha sido anteriormente asignado como ejecutivo.
     * Ademas revisa que la persona no tenga privilegios superiores a los de un Ejecutivo, en caso de que tenga
     * privilegios superiores no se crea una cuentaEjecutivo
     * @param persona  Persona a quien se le quieren asignar permisos de Ejecutivo
     * @return
     */
    public boolean crearCuentaEjecutivo(Persona persona){
        if(persona.getCuentaEjecutivo() != null) {
            System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " ya tiene una cuenta de Ejecutivo asociada.");
            lastError = "La persona " + persona.getNombres() + " " + persona.getApellidos() + " ya es Ejecutivo!";
            return false;
        }
        else {
            if(persona.getCuentaSuperAdministrador() != null || persona.getCuentaAdministrador() != null){
                System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos()
                        + "tiene privilegios superiores a los de un ejecutivo.");
                lastError = "La persona " + persona.getNombres() + " " + persona.getApellidos()
                        + "tiene privilegios superiores a los de un ejecutivo.";
                return false;
            }
            else {
                System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " ha sido asignada como Ejecutivo por "
                        + getPersona().getNombres() + " " + getPersona().getApellidos() + ".");
                lastError = "Has asignado correctamente a " + persona.getNombres() + " " + persona.getApellidos() + "como Ejecutivo";
                persona.setCuentaEjecutivo(new CuentaEjecutivo(persona));
                return true;
            }
        }
    }

    /**
     * Asigna una nueva CuentaUsuario a la Persona si y solo si no ha sido anteriormente instanciado como tal
     * @param persona Persona a quien se le asignara una nueva cuentaUsuario
     * @return True si fue asignada una CuentaUsuario/ False en caso contrario
     */
    public boolean crearCuentaUsuario(Persona persona){
        if(persona.getCuentaUsuario() != null){
            System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " ya tiene una cuenta Usuario asociada.");
            lastError = "Error! ,la persona " + persona.getNombres() + " " + persona.getApellidos() + " ya tiene una cuenta Usuario asociada.";

            return false;
        }
        else{
            System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " ha sido asignada como Usuario por "
                    + getPersona().getNombres() + " " + getPersona().getApellidos() + ".");
            lastError = "La persona " + persona.getNombres() + " " + persona.getApellidos() + " ha sido asignada como Usuario.";

            persona.setCuentaUsuario(new CuentaUsuario(persona));
            return true;
        }
    }

    /**
     * Crea una nueva Cuenta Bancaria al usuario. La nueva cuenta tiene saldo 0
     * @param cuentaUsuario Usuario a quien se le añade una cuenta bancaria
     */
    public boolean crearCuentaBancaria(CuentaUsuario cuentaUsuario,String tipoCuentaBancaria, long identificador){
        final boolean resultado = cuentaUsuario.crearCuentaBancaria(tipoCuentaBancaria,identificador);
        lastError = cuentaUsuario.getLastError();
        return resultado;
    }

    public boolean eliminarCuentaBancaria(CuentaUsuario cuentaUsuario , long identificador){
        final boolean resulado = cuentaUsuario.eliminarCuentaBancaria(identificador);
        lastError = cuentaUsuario.getLastError();
        return resulado;
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

    public short obtenerPermisos(){
        return 3;
    }



    /*
     *  Setters y Getters
     */

    public boolean isCuentaBloqueada() {
        return cuentaBloqueada;
    }

    public void setCuentaBloqueada(boolean cuentaBloqueada) {
        this.cuentaBloqueada = cuentaBloqueada;
    }


}
