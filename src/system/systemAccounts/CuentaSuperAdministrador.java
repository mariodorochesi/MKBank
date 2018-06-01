package system.systemAccounts;

import system.general.Persona;

public class CuentaSuperAdministrador extends Cuenta {

    private boolean cuentaBloqueada;


    public CuentaSuperAdministrador(Persona persona){
        super(persona);
        this.cuentaBloqueada = false;
    }

    /**
     *  Crea y retorna una Persona nueva, inicializada con los valorees pasados por parametros.
     * Ademas deja en el log un mensaje referente a quien fue el SuperAdministrador que creo a la Persona
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
        //Se crea una nuevaPersona
        Persona nuevaPersona = new Persona(nombres,apellidos,rut,ciudad,direccion,correoElectronico,telefono,nacionalidad,
                annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil,genero);

        System.out.println("El SuperAdministrador " + persona.getNombres() + " " + persona.getApellidos() + "ha creado a la Persona "
                + nuevaPersona.getNombres() + " " + nuevaPersona.getApellidos());
        lastError = "Has creado exitosamente a la Persona " + nuevaPersona.getNombres() + " " + nuevaPersona.getApellidos() + ".";
        return nuevaPersona;
    }


    /*
    *   SECCION BLOQUEOS / DESBLOQUEOS DE CUENTAS
    * */

    /**
     * Bloquea a un administrador si y solo si este no se encuentra bloqueado
     * En caso de que ya se encuentre bloqueado,lo desbloquea
     * @param cuentaAdministrador Administrador a bloquear/desbloquear
     */
    public void bloquearDesbloquear(CuentaAdministrador cuentaAdministrador){
        if(cuentaAdministrador.isCuentaBloqueada()){
            System.out.println("El administrador " + cuentaAdministrador.getPersona().getNombres() + " " +
                    cuentaAdministrador.getPersona().getApellidos() + " ha sido desbloqueado.");
            lastError = "El administrador " + cuentaAdministrador.getPersona().getNombres() +
                    " " + cuentaAdministrador.getPersona().getApellidos() + " ha sido desbloqueado";
            cuentaAdministrador.setCuentaBloqueada(false);
        }
        else {
            System.out.println("El administrador " + cuentaAdministrador.getPersona().getNombres() + " " +
                    cuentaAdministrador.getPersona().getApellidos() + " ha sido bloqueado.");
            lastError = "El administrador " + cuentaAdministrador.getPersona().getNombres() +
                    " " + cuentaAdministrador.getPersona().getApellidos() + " ha sido bloqueado";
            cuentaAdministrador.setCuentaBloqueada(true);
        }
    }

    /**
     * Bloquea a un ejecutivo si y solo si este no se encuentra bloqueado
     * En caso de que ya se encuentre bloqueado, lo desbloquea
     * @param cuentaEjecutivo Ejecutivo a bloquear/desbloquear
     */
    public void bloquearDesbloquear(CuentaEjecutivo cuentaEjecutivo){
        if(cuentaEjecutivo.isCuentaBloqueada()){
            System.out.println("El ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " "
                    + cuentaEjecutivo.getPersona().getApellidos()+" ha sido desbloqueado.");
            cuentaEjecutivo.setCuentaBloqueada(false);
            lastError = "El ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " " + cuentaEjecutivo.getPersona().getApellidos() +
                    " ha sido desbloqueado";
        }
        else{
            System.out.println("El ejecutivo " + cuentaEjecutivo.getPersona().getNombres() + " "
                    + cuentaEjecutivo.getPersona().getApellidos() + "ha sido bloqueado.");
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
            System.out.println("El usuario " + cuentaUsuario.getPersona().getNombres() + " "
                    + cuentaUsuario.getPersona().getApellidos() + " ha sido desbloqueado.");
            cuentaUsuario.setCuentaBloqueada(false);
            lastError = "El usuario " + cuentaUsuario.getPersona().getNombres() +
                    " " + cuentaUsuario.getPersona().getApellidos() + " ha sido desbloqueado";
        }
        else {
            System.out.println("El usuario " + cuentaUsuario.getPersona().getNombres() +
                    " " + cuentaUsuario.getPersona().getApellidos() + " ha sido bloqueado.");
            lastError = "El usuario " + cuentaUsuario.getPersona().getNombres() +
                    " " + cuentaUsuario.getPersona().getApellidos() + " ha sido bloqueado";
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
            System.out.println("La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " perteneciente a " +
                    cuentaBancaria.getPersona().getNombres() + " " + cuentaBancaria.getPersona().getApellidos() + " ha sido desbloqueada.");
            lastError = "La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " ha sido desbloqueada.";
            cuentaBancaria.setCuentaBloqueada(false);
        }
        else {
            System.out.println("La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " perteneciente a " +
                    cuentaBancaria.getPersona().getNombres() + " " + cuentaBancaria.getPersona().getApellidos() + " ha sido bloqueada.");
            lastError = "La cuenta bancaria numero " + cuentaBancaria.getIdentificador() + " ha sido bloqueada.";
            cuentaBancaria.setCuentaBloqueada(true);
        }
    }

    /*
    *   Seccion Creacion Cuentas
    * */



    /**
     * Asigna una CuentaAdministrador a una Persona si y solo si no ha sido anteriormente asignado como Administrador.
     * Ademas revisa que no tenga permisos superiores a los de Administrador, en caso que tenga privilegios superiores
     * no se crea la cuentaAdministrador.
     * Si tenia permisos inferiores, como permisos de Ejecutivo, se le revocan los permisos de Ejecutivo y se le otorgan
     * los de administrador.
     * @param persona
     * @return
     */
    public boolean crearCuentaAdministrador(Persona persona){
        if(persona.getCuentaAdministrador() != null){
            System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " ya tiene una cuenta de Administrador asociada.");
            lastError = "La persona " + persona.getNombres() + " " + persona.getApellidos() + " ya es Administrador!";
            return false;
        }
        else {
            if(persona.getCuentaEjecutivo() != null){
                System.out.println("Se han revocado los permisos de Ejecutivo a " + persona.getNombres() + " " + persona.getApellidos() + " y se le han otorgado permisos de Administrador.");
                lastError = "Se han revocado los permisos de Ejecutivo a " + persona.getNombres() + " " + persona.getApellidos() + " y se le han otorgado los permisos de Administrador";
                persona.setCuentaEjecutivo(null);
                persona.setCuentaAdministrador(new CuentaAdministrador(persona));
                return true;
            }
            else if(persona.getCuentaSuperAdministrador() != null){
                System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " tiene privilegios superiores a los de un Administrador");
                lastError ="La persona " + persona.getNombres() + " " + persona.getApellidos() + " ya tiene privilegios superiores a los de un Administrador";

                return false;
            }
            else{
                System.out.println("Se le han otorgado los permisos de Administrador a " + persona.getNombres() + " " + persona.getApellidos()
                +" . El Superadministrador  " + getPersona().getNombres() + " " + getPersona().getApellidos() + " le ha otorgado los permisos");
                persona.setCuentaAdministrador(new CuentaAdministrador(persona));
                lastError = "Has asignado correctamente a " + persona.getNombres() + " " + persona.getApellidos() + " como Administrador";
                return true;
            }
        }
    }

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
                System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + "tiene privilegios superiores a los de un ejecutivo.");
                lastError = "La persona " + persona.getNombres() + " " + persona.getApellidos() + "tiene privilegios superiores a los de un ejecutivo.";
                return false;
            }
            else {
                System.out.println("La persona " + persona.getNombres() + " " + persona.getApellidos() + " ha sido asignada como Ejecutivo.");
                persona.setCuentaEjecutivo(new CuentaEjecutivo(persona));
                lastError = "Has asignado correctamente a " + persona.getNombres() + " " + persona.getApellidos() + "como Ejecutivo";

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
    public boolean crearCuentaBancaria(CuentaUsuario cuentaUsuario, String tipoCuentaBancaria, long identificador){
        final boolean resultado  = cuentaUsuario.crearCuentaBancaria(tipoCuentaBancaria,identificador);
        lastError = cuentaUsuario.getLastError();
        return resultado;
    }

    public boolean eliminarCuentaBancaria(CuentaUsuario cuentaUsuario , long identificador){
        final boolean resultado = cuentaUsuario.eliminarCuentaBancaria(identificador);
        lastError = cuentaUsuario.getLastError();
        return resultado;
    }


    /*
    *   Setters y Getters
    * */

    public boolean isCuentaBloqueada() {
        return cuentaBloqueada;
    }

    public void setCuentaBloqueada(boolean cuentaBloqueada) {
        this.cuentaBloqueada = cuentaBloqueada;
    }

    public String getLastError() {
        return lastError;
    }

    public Persona getPersona(){
        return persona;
    }
}
