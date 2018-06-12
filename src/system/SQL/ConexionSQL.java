package system.SQL;

import system.general.Banco;
import system.general.Persona;
import system.general.Sucursal;
import system.general.Transferencias;
import system.systemAccounts.CuentaBancaria;

import javax.xml.crypto.dsig.TransformService;
import java.lang.reflect.Executable;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConexionSQL {

    //Conexion que se establece
    private  Connection myConn;

    //Statement a generar
    private  Statement myStmt;

    /**
     * Constructor de la clase ConexionSQL
     */

    public ConexionSQL() throws Exception {
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://den1.mysql2.gear.host/mkbank?autoReconnect=true&useSSL=false","mkbank","mkb.-123");
            myStmt = myConn.createStatement();
            System.out.println("Conexion Exitosa a la base de datos");
        }
        catch (Exception e){
            System.out.println("Conexion Fallida a la base de datos");
            throw new Exception();
        }
    }

    /*
    *   METODOS DE CARGA DE DATOS DESDE LA BASE
    *   SQL
    * */

    /**
     * Metodo que carga a todas las Personas de la Base de Datos
     * y las almacena en el mapa de Personas del banco
     */
    public void cargarPersonas(Banco banco){

        try{
            ResultSet myRs = myStmt.executeQuery("SELECT * from personas");
            while(myRs.next()){
                String nombre = myRs.getString("nombres");
                String apellido = myRs.getString("apellidos");
                String rut = myRs.getString("rut");
                String ciudad = myRs.getString("ciudad");
                String direccion = myRs.getString("direccion");
                String correoElectronico = myRs.getString("email");
                String telefono = myRs.getString("telefono");
                String contrasena = myRs.getString("contrasena");
                String nacionalidad = myRs.getString("nacionalidad");
                Date mydate = myRs.getDate("fechaNacimiento");
                int annoNacimiento = Integer.parseInt(mydate.toString().substring(0,4));
                int mesNacimiento = Integer.parseInt(mydate.toString().substring(5,7));
                int diaNacimiento = Integer.parseInt(mydate.toString().substring(8,10));
                String estadoCivil = myRs.getString("estadoCivil");
                String genero = myRs.getString("genero");
                int permisosSuperiores = myRs.getInt("permisosSuperiores");
                int permisosUsuario = myRs.getInt("permisosUsuario");
                String sucursalAsociada = myRs.getString("sucursales");

                banco.agregarPersona(nombre,apellido,rut,ciudad,direccion,correoElectronico,telefono,nacionalidad,
                        annoNacimiento,mesNacimiento,diaNacimiento,estadoCivil,genero,contrasena, sucursalAsociada);

                if(permisosUsuario == 1)
                    banco.otorgarPermisosUsuarioCarga(rut);
                if(permisosSuperiores != 0)
                    banco.otorgarPermisosSuperioresCarga(permisosSuperiores,rut);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo que carga las sucursales de la base de datos y las
     * almacena en el mapa de sucursales.
     */
    public void cargarSucursales(Banco banco){
        try{
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM sucursales");
            while(myRs.next()){
                String nombre = myRs.getString("nombreSucursal");
                String direccion = myRs.getString("direccionSucursal");
                int codigo = myRs.getInt("numeroSucursal");

                banco.agregarSucursal(nombre, direccion, codigo);
                System.out.println("Sucursal cargada: \n" +
                        "Codigo: " + codigo + "\n" +
                        "Direccion: " + direccion + "\n" +
                        "Nombre: " + nombre + "\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo que carga las cuentas de la base de datos
     * y las almacena en el mapa de cuentas bancarias y ademas
     * se las asigna a cada uno de las personas las cuentas respectivas
     */
    public void cargarCuentas(Banco banco){

        try{
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM cuentasbancarias");

            while(myRs.next()){

                long identificador = Long.parseLong(myRs.getString("numeroCuenta"));
                long monto = Long.parseLong(myRs.getString("montoCuenta"));
                String titularCuenta = myRs.getString("titularCuenta");
                int tipoCuenta = myRs.getInt("tipoCuenta");

                String tipoCuentaBancaria;

                if(tipoCuenta == 0)
                    tipoCuentaBancaria = "Cuenta Vista";
                else if(tipoCuenta == 1)
                    tipoCuentaBancaria = "Cuenta Corriente";
                else
                    tipoCuentaBancaria = "Cuenta Ahorro";

                banco.agregarCuentaBancaria(banco.isSuperAdministradorOnBanco("9897123"),banco.isUsuarioOnBanco(titularCuenta),tipoCuentaBancaria,identificador,monto);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo que carga todas las transferencias de la base de datos
     * y los agrega al mapa de transferencias y ademas lo agrega a las transferencias de las personas
     */
    public void cargarTransferencias(Banco banco){

        try{
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM transferencias");

            while(myRs.next()){

                String nombreOriginario = myRs.getString("nombreOriginario");
                String rutOriginario = myRs.getString("rutOriginario");
                int tipoCuentaOrigen = myRs.getInt("tipoCuentaOrigen");
                long numeroCuentaOrigen = myRs.getLong("numeroCuentaOrigen");
                String nombreDestinatario = myRs.getString("nombreDestinatario");
                String rutDestinatario = myRs.getString("rutDestinatario");
                int tipoCuentaDestinatario = myRs.getInt("tipoCuentaDestinatario");
                String correoDestinatario = myRs.getString("correoDestinatario");
                long numeroTransferencia = myRs.getLong("numeroTransferencia");
                long numeroCuentaDestinatario = myRs.getLong("numeroCuentaDestinatario");
                int montoTransferencia = myRs.getInt("montoTransferencia");
                Date mydate = myRs.getDate("fechaTransferencia");
                int annoNacimiento = Integer.parseInt(mydate.toString().substring(0,4));
                int mesNacimiento = Integer.parseInt(mydate.toString().substring(5,7));
                int diaNacimiento = Integer.parseInt(mydate.toString().substring(8,10));
                String comentario = myRs.getString("comentarioTransferencia");
                String tipoCuentaOrigenn, tipoCuentaDestinatarioo;
                if(tipoCuentaOrigen == 0)
                    tipoCuentaOrigenn = "Cuenta Vista";

                else if(tipoCuentaDestinatario == 1)
                    tipoCuentaOrigenn = "Cuenta Corriente";
                else
                    tipoCuentaOrigenn = "Cuenta de Ahorro";

                if(tipoCuentaDestinatario == 0)
                    tipoCuentaDestinatarioo = "Cuenta Vista";
                else if(tipoCuentaDestinatario == 1)
                    tipoCuentaDestinatarioo = "Cuenta Corriente";
                else
                    tipoCuentaDestinatarioo = "Cuenta de Ahorro";

                Transferencias t = new Transferencias(nombreOriginario,rutOriginario,tipoCuentaOrigenn,numeroCuentaOrigen,
                        nombreDestinatario,rutDestinatario,tipoCuentaDestinatarioo,numeroCuentaDestinatario,correoDestinatario,
                        numeroTransferencia,montoTransferencia,annoNacimiento,mesNacimiento,diaNacimiento,comentario);

                banco.agregarTransferencia(numeroTransferencia,t);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    *   METODOS PARA AGREGAR A LA BASE DE DATOS SQL
    * */

    public boolean agregarPersonaSQL(Persona p, int permisos){

        int permisosAdmin = 0;
        int permisosUser = 0;

        if(permisos > 1)
            permisosAdmin = permisos;
        if(p.getCuentaUsuario() != null)
            permisosUser = 1;

        try{
            System.out.println("Agregando a la Persona a SQL");
            myStmt.executeUpdate("INSERT INTO personas VALUES('"+ p.getNombres()+"','"+p.getApellidos()+"','"+p.getRut()+"','"+
                    p.getCiudad()+"','"+p.getDireccion()+"','"+p.getCorreoElectronico()+"','"+p.getTelefono()+"','"+p.getNacionalidad()+"','"+
                    p.getFechaNacimiento()+"','"+p.getEstadoCivil()+"','"+p.getContrasena()+"','"+permisosAdmin+"','"+permisosUser+"','"+
                    p.getGenero()+"','"+p.getSucursalAsociada()+"')");
            if(permisosUser==1){
                ArrayList<CuentaBancaria> a = p.getCuentaUsuario().getCuentasBancarias();
                for(int i = 0; i < a.size(); i++){
                    agregarCuentaBancariaSQL(a.get(i));
                }
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean agregarSucursalSQL(Sucursal sucursal){
        try{
            myStmt.executeUpdate("INSERT INTO sucursales VALUES("+sucursal.getCodigoSucursal()+", '" +
                    sucursal.getNombreSucursal() + "', '" + sucursal.getDireccionSucursal() + "')");
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean agregarCuentaBancariaSQL(CuentaBancaria cuentaBancaria){
        int block=0;
        int tipo;
        System.out.println("Agregando la cuenta a SQL");

        if(cuentaBancaria.getTipoCuenta().equals("Cuenta Vista"))
            tipo = 0;
        else if(cuentaBancaria.getTipoCuenta().equals("Cuenta Corriente"))
            tipo = 1;
        else
            tipo = 2;

        if(!cuentaBancaria.isCuentaBloqueada())
            block=1;

        try{
            myStmt.executeUpdate("INSERT INTO cuentasbancarias VALUES('"+cuentaBancaria.getIdentificador()+"','"+cuentaBancaria.getMonto()+"','"+
                    cuentaBancaria.getPersona().getRut()+"','"+block+"','"+tipo+"')");
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean agregarTransferenciaSQL(Transferencias t){

        int tipocuentaOrigen = 0;
        int tipoCuentaDestino = 0;

        if(t.getTipocuentaOrigen().equals("Cuenta Corriente"))
            tipocuentaOrigen = 1;
        else if(t.getTipocuentaOrigen().equals("Cuenta Ahorro"))
            tipocuentaOrigen = 2;

        if(t.getTipoCuentaDestinatario().equals("Cuenta Corriente"))
            tipoCuentaDestino = 1;
        else if(t.getTipoCuentaDestinatario().equals("Cuenta Ahorro"))
            tipoCuentaDestino = 2;

        try{

            myStmt.executeUpdate("INSERT INTO transferencias VALUES('"+t.getNombreOriginario()+"','"+t.getRutOriginario()+"','"+
                    tipocuentaOrigen+"','"+t.getNumeroCuentaOrigen()+"','"+t.getNombreDestinatario()+"','"+t.getRutDestinatario()+"','"+
                    tipoCuentaDestino+"','"+t.getNumeroCuentaDestinatario()+"','"+t.getMailDestinatario()+"','"+t.getNumeroTransferencia()+"','"+
                    t.getMontoTransferencia()+"','"+ LocalDate.of(t.getAnnoTransferencia(),t.getMesTransferencia(),t.getDiaTransferencia()).toString()+"','"+
                    t.getComentarioTransferencia()+"')");

            myStmt.executeUpdate("INSERT INTO transferenciaspendientes VALUES('"+t.getNombreOriginario()+"','"+t.getRutOriginario()+"','"+
                    tipocuentaOrigen+"','"+t.getNumeroCuentaOrigen()+"','"+t.getNombreDestinatario()+"','"+t.getRutDestinatario()+"','"+
                    tipoCuentaDestino+"','"+t.getNumeroCuentaDestinatario()+"','"+t.getMailDestinatario()+"','"+t.getNumeroTransferencia()+"','"+
                    t.getMontoTransferencia()+"','"+ LocalDate.of(t.getAnnoTransferencia(),t.getMesTransferencia(),t.getDiaTransferencia()).toString()+"','"+
                    t.getComentarioTransferencia()+"')");

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /*
    *   METODOS PARA HACER MODIFICACIONES EN LA BASE
    *   DE DATOS
    * */


    public boolean modificarCuentaSQLSuperior(Persona persona, int permisos){
        try{
            myStmt.executeUpdate("UPDATE personas SET permisosSuperiores = " + permisos + " WHERE rut = '" + persona.getRut() + "'");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;

    }

    public boolean modificarCuentaSQLUsuario(Persona persona, int permisos){

        try{
            System.out.println("Modificando permisos para " + persona.getNombres());
            myStmt.executeUpdate("UPDATE personas SET permisosUsuario = " + permisos + " WHERE rut = '" + persona.getRut() + "'");
        }
        catch (Exception e){
            System.out.println("No se pudieron modificar los permisos de Usuario para " + persona.getNombres());
            e.printStackTrace();
        }
        return true;
    }

    public boolean depositarCuentaSQL(CuentaBancaria cuentaBancaria, int monto){
        long montoCuenta=0;
        try{
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM cuentasbancarias WHERE numeroCuenta = "+cuentaBancaria.getIdentificador());
            while(myRs.next()){
                montoCuenta = Long.parseLong(myRs.getString("montoCuenta"));
            }

            try{
                myStmt.executeUpdate("UPDATE cuentasbancarias SET montoCuenta = " +(montoCuenta+monto)+ " WHERE numeroCuenta = " +cuentaBancaria.getIdentificador());
            }
            catch (Exception e){
                System.out.println("No se ha podido depositar en la cuenta");
            }

        }catch(Exception e){}
        return false;
    }

    public boolean transferirCuentaSQL(CuentaBancaria cuentaBancaria, int monto){
        long montoCuenta=0;
        try{
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM cuentasbancarias WHERE numeroCuenta = "+cuentaBancaria.getIdentificador());
            while(myRs.next()){
                montoCuenta = Long.parseLong(myRs.getString("montoCuenta"));
            }

            try{
                myStmt.executeUpdate("UPDATE cuentasbancarias SET montoCuenta = " +(montoCuenta-monto)+ " WHERE numeroCuenta = " +cuentaBancaria.getIdentificador());
            }
            catch (Exception e){
                System.out.println("No se ha podido depositar en la cuenta");
            }

        }catch(Exception ignored){}
        return false;
    }

    public void editarPersonaSQL(Persona persona){
        try{
            myStmt.executeUpdate("UPDATE personas SET ciudad = '" + persona.getCiudad() + "', " +
                    "direccion = '" + persona.getDireccion() + "', " +
                    "email = '" + persona.getCorreoElectronico() + "', " +
                    "telefono = " + persona.getTelefono() + ", " +
                    "estadoCivil = '" + persona.getEstadoCivil() + "', " +
                    "nacionalidad = '" + persona.getNacionalidad() + "', " +
                    "sucursales = '" + persona.getSucursalAsociada() + "' WHERE rut = '" + persona.getRut() + "'");
        }catch(Exception ignored){}
    }

    public void eliminarCuentaBancariaSQL(long identificador){
        try{
            myStmt.executeUpdate("DELETE FROM cuentasBancarias\n" +
                    "WHERE numeroCuenta = '" + identificador + "'");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void eliminarPersona(String rut){
        try{
            myStmt.executeUpdate("DELETE FROM personas\n" +
                    "WHERE rut = '" + rut + "'");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void eliminarSucursalSQL(String nombre){
        try{
            myStmt.executeUpdate("DELETE FROM sucursales\n"+
                    "WHERE nombreSucursal = '" + nombre + "'");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
