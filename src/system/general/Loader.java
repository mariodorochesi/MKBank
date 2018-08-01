package system.general;

import system.SQL.ConexionSQL;
import system.general.Banco;

import java.sql.SQLException;

public class Loader {

    //Banco donde cargar los Datos
    private Banco banco = Banco.getInstance();
    //Conexion SQL desde donde obtener los datos
    private ConexionSQL conexionSQL;


    /**
     * Constructor del objeto Loader
     * @param conexionSQL ConexionSQL desde donde se desean obtener los datos
     */

    public Loader(ConexionSQL conexionSQL){
        this.conexionSQL = conexionSQL;
    }


    /**
     * Metodo que se encarga de cargar al Banco todos los datos obtenido de la base SQL, mediante llamadas a metodos
     * propios de la clase conexionSQL
     */

    public void loadFromSQL() throws SQLException{
            conexionSQL.cargarSucursales(banco);
            conexionSQL.cargarSucursales(banco);
            conexionSQL.cargarPersonas(banco);
            conexionSQL.cargarCuentas(banco);
            conexionSQL.cargarTransferencias(banco);
    }



}
