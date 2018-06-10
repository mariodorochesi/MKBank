package system.clasesColecciones;

import system.interfaces.Identificador;
import system.systemAccounts.CuentaBancaria;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class MapaCuentaBancarias implements Identificador {

    //Mapa de Cuentas Bancarias
    private HashMap<Long , CuentaBancaria> mapaCuentasBancarias;

    //Generador de identificadores unicos
    Random generadorIdentificadores;


    /**
     * Constructor de Mapa de Cuentas Bancarias
     */
    public MapaCuentaBancarias(){
        generadorIdentificadores = new Random();
        mapaCuentasBancarias = new HashMap<>();
    }

    /**
     * Agrega una Cuenta Bancaria al mapa de Cuentas
     * @param identificador Identificador de la Cuenta
     * @param cuentaBancaria Cuenta a agregar
     */
    public void agregarCuentaBancaria(long identificador,CuentaBancaria cuentaBancaria){
        mapaCuentasBancarias.put(identificador,cuentaBancaria);
    }

    /**
     * Metodo que verifica si existe una cuenta bancaria
     * @param identificador Identificador de la cuenta
     * @return True si existe / False en caso contrario
     */

    public boolean existeCuentaBancaria(long identificador){
        return mapaCuentasBancarias.get(identificador) != null;
    }

    /**
     * Elimina una cuenta bancaria del mapa de cuentas bancarias
     * @param identificador Identificador de la cuenta a eliminar
     * @return True si la pudo eliminar false en caso contrario
     */

    public boolean eliminarCuentaBancaria(long identificador){
        return mapaCuentasBancarias.remove(identificador) != null;
    }

    /**
     * Metodo que retorna una cuenta bancaria
     * @param identificador Identificador de la cuenta bancaria
     * @return Retorna la cuenta, si es que la encontro, NULL en caso contrario
     */

    public CuentaBancaria obtenerCuentaBancaria(long identificador){
        return mapaCuentasBancarias.get(identificador);
    }


    /**
     * Retorna un arreglo con las cuentas bancarias
     * Es de NECESIDAD, para poder generar la parte gráfica del reporte bancario
     * @return Arreglo de cuentas bancarias
     */

    public Collection<CuentaBancaria> values(){
        return mapaCuentasBancarias.values();
    }

    /**
     * Limpia el mapa de cuentas bancarias, dejandolo vacio
     */
    public void clear(){
        mapaCuentasBancarias.clear();
    }


    /**
     * Genera un Identificador Unico de 9 digitos para una cuenta Bancaria, y comprueba que el Identificador Generado
     * no este asociado a otra cuenta.
     * Si ya esta asociado a otra cuenta, se genera un nuevo Identificador
     * @return Identificador
     */
    @Override
    public long generarIdentificador(){
        long identificador;
        do{
            identificador = generadorIdentificadores.nextInt(10) * 100000000 + generadorIdentificadores.nextInt(10) * 10000000 + generadorIdentificadores.nextInt(10) * 1000000
                    + generadorIdentificadores.nextInt(10) * 100000 + generadorIdentificadores.nextInt(10) * 10000 +
                    generadorIdentificadores.nextInt(10) * 1000 + generadorIdentificadores.nextInt(10) * 100 +
                    generadorIdentificadores.nextInt(10) * 10 + generadorIdentificadores.nextInt(10);
        }while (existeCuentaBancaria(identificador));
        return identificador;
    }

    /**
     * Metodo para obtener el dinero total que maneja el banco
     * @return Retorna la suma del dinero de todas las cuentas bancarias
     */
    public long obtenerDineroTotal(){
        long total = 0;
        for(CuentaBancaria cb : mapaCuentasBancarias.values()){
            total += cb.getMonto();
        }
        return total;
    }
}
