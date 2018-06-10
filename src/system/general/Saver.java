package system.general;

import system.SQL.ConexionSQL;
import system.systemAccounts.CuentaBancaria;

public class Saver {

    //Conexion SQL hacia donde se desean agregar los Datos
    private ConexionSQL conexionSQL;


    /**
     * Constructor del objeto Saver
     * @param conexionSQL ConexionSQL donde se desean guardar los datos
     */

    public Saver(ConexionSQL conexionSQL){
        this.conexionSQL = conexionSQL;
    }

    /**
     * Metodo para agregar una nueva Cuenta Bancaria en la Base de Datos
     * @param cuentaBancaria Cuenta a agregar
     */

    public void agregarCuentaBancariaSQL(CuentaBancaria cuentaBancaria){
        conexionSQL.agregarCuentaBancariaSQL(cuentaBancaria);
    }

    /**
     * Metodo para agregar una nueva Persona en la base de Datos
     * @param persona Persona a agregar
     * @param permisos Permisos superior que posee la perosna
     */

    public void agregarPersonaSQL(Persona persona , int permisos){
        conexionSQL.agregarPersonaSQL(persona,permisos);
    }

    /**
     * Metodo para modificar en la base de datos los permisos Superiores que posee una Persona
     * @param persona Persona a modificar
     * @param permisos Nuevos permisos a asignar
     */

    public void modificarPermisosSQLSuperior(Persona persona, int permisos){
        conexionSQL.modificarCuentaSQLSuperior(persona, permisos);
    }

    /**
     * Metodo para modificar en la base de datos los permisos de Usuario que posee una persona
     * @param persona Persona a modificar
     * @param permisos Permisos a asignar
     */

    public void modificarPermisoUsuarioSQL(Persona persona, int permisos){
        conexionSQL.modificarCuentaSQLUsuario(persona, permisos);
    }

    /**
     * Metodo para agregar una transferencia bancaria en la base de datos
     * @param t Transferencia a agregar
     */

    public void agregarTransferenciaSQL(Transferencias t){
        conexionSQL.agregarTransferenciaSQL(t);
    }

    /**
     * Metodo para depositar a una Cuenta Bancaria
     * @param cuentaBancaria Cuenta a depositar
     * @param monto Monto a depositar
     */

    public void depositarCuentaSQL(CuentaBancaria cuentaBancaria, int monto){
        conexionSQL.depositarCuentaSQL(cuentaBancaria,monto);
    }

    /**
     * Metodo para transferir a una Cuenta Bancaria
     * @param cuentaBancaria Cuenta a Transferir
     * @param monto Monto a transferir
     */

    public void transferirCuentaSQL(CuentaBancaria cuentaBancaria, int monto){
        conexionSQL.transferirCuentaSQL(cuentaBancaria,monto);
    }

    /**
     * Metodo para editar una Persona en la base de datos
     * @param persona Persona  editar
     */

    public void editarPersonaSQL(Persona persona){
        conexionSQL.editarPersonaSQL(persona);
    }

    /**
     * Metodo para eliminar una Persona de la Base de Datos
     * @param rut Rut de la Persona a eliminar
     */

    public void eliminarPersona(String rut){conexionSQL.eliminarPersona(rut);}

    /**
     * Metodo para eliminar una Cuenta Bancaira de la Base de Datos
     * @param identificador Identificador de la Cuenta e liminar
     */

    public void eliminarCuentaBancariaSQL(long identificador){
        conexionSQL.eliminarCuentaBancariaSQL(identificador);
    }

    /**
     * Metodo para agregar una Nueva Sucursal en la Base de Dats
     * @param sucursal Sucursal a Agregar
     */
    
    public void agregarSucursalSQL(Sucursal sucursal){
        conexionSQL.agregarSucursalSQL(sucursal);
    }

    /**
     * Metodo para eliminar una Sucursal de la Base de Datos
     * @param nombre Nombre de la Sucursal a Eliminar
     */

    public void eliminarSucursalSQL(String nombre){
        conexionSQL.eliminarSucursalSQL(nombre);
    }
}
