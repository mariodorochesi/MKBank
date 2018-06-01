package system.general;

import system.SQL.ConexionSQL;
import system.general.Banco;

public class Loader {

    private Banco banco;
    private ConexionSQL conexionSQL;


    public Loader(Banco banco, ConexionSQL conexionSQL){
        this.banco = banco;
        this.conexionSQL = conexionSQL;
    }


    public void loadFromSQL(){
        conexionSQL.cargarPersonas(banco);
        conexionSQL.cargarCuentas(banco);
        conexionSQL.cargarTransferencias(banco);
    }



}
