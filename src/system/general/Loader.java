package system.general;

import system.SQL.ConexionSQL;
import system.general.Banco;

public class Loader {

    //Banco donde cargar los Datos
    private Banco banco;
    //Conexion SQL desde donde obtener los datos
    private ConexionSQL conexionSQL;


    /**
     * Constructor del objeto Loader
     * @param banco Banco a donde se desean cargar los datos
     * @param conexionSQL ConexionSQL desde donde se desean obtener los datos
     */

    public Loader(Banco banco, ConexionSQL conexionSQL){
        this.banco = banco;
        this.conexionSQL = conexionSQL;
    }


    /**
     * Metodo que se encarga de cargar al Banco todos los datos obtenido de la base SQL, mediante llamadas a metodos
     * propios de la clase conexionSQL
     */

    public void loadFromSQL(){
        conexionSQL.cargarSucursales(banco);
        conexionSQL.cargarPersonas(banco);
        conexionSQL.cargarCuentas(banco);
        conexionSQL.cargarTransferencias(banco);
    }



}
