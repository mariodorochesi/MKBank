package system.general;

import system.SQL.ConexionSQL;
import system.systemAccounts.CuentaBancaria;

public class Saver {

    private ConexionSQL conexionSQL;

    public Saver(Banco banco, ConexionSQL conexionSQL){
        this.conexionSQL = conexionSQL;
    }

    public void agregarCuentaBancariaSQL(CuentaBancaria cuentaBancaria){
        conexionSQL.agregarCuentaBancariaSQL(cuentaBancaria);
    }

    public void agregarPersonaSQL(Persona persona , int permisos){
        conexionSQL.agregarPersonaSQL(persona,permisos);
    }

    public void modificarPermisosSQLSuperior(Persona persona, int permisos){
        conexionSQL.modificarCuentaSQLSuperior(persona, permisos);
    }

    public void modificarPermisoUsuarioSQL(Persona persona, int permisos){
        conexionSQL.modificarCuentaSQLUsuario(persona, permisos);
    }

    public void agregarTransferenciaSQL(Transferencias t){
        conexionSQL.agregarTransferenciaSQL(t);
    }

    public void depositarCuentaSQL(CuentaBancaria cuentaBancaria, int monto){
        conexionSQL.depositarCuentaSQL(cuentaBancaria,monto);
    }

    public void transferirCuentaSQL(CuentaBancaria cuentaBancaria, int monto){
        conexionSQL.transferirCuentaSQL(cuentaBancaria,monto);
    }

    public void editarPersonaSQL(Persona persona){
        conexionSQL.editarPersonaSQL(persona);
    }

    public void eliminarPersona(String rut){conexionSQL.eliminarPersona(rut);}

    public void eliminarCuentaBancariaSQL(long identificador){
        conexionSQL.eliminarCuentaBancariaSQL(identificador);
    }

    
    public void agregarSucursalSQL(Sucursal sucursal){
        conexionSQL.agregarSucursalSQL(sucursal);
    }

    public void eliminarSucursalSQL(String nombre){
        conexionSQL.eliminarSucursalSQL(nombre);
    }
}
