package system.clasesColecciones;

import system.interfaces.Identificador;
import system.general.Transferencias;

import java.util.HashMap;
import java.util.Random;

public class MapaTransferencias implements Identificador {

    //HashMap con las Transferencias
    private HashMap<Long, Transferencias> mapaTransferencias;
    //Generador de identificador
    private Random generadorIdentificadores;

    /**
     * Constructor de Mapa Transfernecias
     */

    public MapaTransferencias(){
        mapaTransferencias = new HashMap<>();
        generadorIdentificadores = new Random();
    }

    /**
     * Metood que agrega una Transferencia al mapa
     * @param identificador Identificador de la Transferencia
     * @param transferencia Transferencia a agregar
     */

    public void agregarTranferencia(long identificador,Transferencias transferencia){
        mapaTransferencias.put(identificador,transferencia);
    }

    /**
     * Metodo que verifica si existe la transferencia en el mapa
     * @param identificador Identificador de la Transferencia
     * @return True si pudo encontra la transferencia false en caso contrario
     */

    public boolean existeTransferencia(long identificador){
        return mapaTransferencias.get(identificador) != null;
    }

    /**
     * Metodo que elimina una Transferencia
     * @param identificador Identificador de la Transferencia
     * @return True si pudo eliminarla, False en caso contrario
     */

    public boolean eliminarCuentaBancaria(long identificador){
        return mapaTransferencias.remove(identificador) != null;
    }

    /**
     * Metodo que retorna una Transferencia en caso de existir
     * @param identificador Identificador de la Transferencia
     * @return La transferencia en caso de que exista, False en caso contrario
     */

    public Transferencias obtenerTransferencia(long identificador){
        return mapaTransferencias.get(identificador);
    }

    /**
     * Metodo que genera un identificador unico de 9 digitos para la transferencia
     * @return El identificador de 9 digitos
     */

    @Override
    public long generarIdentificador(){
        long identificador;
        do{
            identificador = generadorIdentificadores.nextInt(10) * 100000000 + generadorIdentificadores.nextInt(10) * 10000000 + generadorIdentificadores.nextInt(10) * 1000000
                    + generadorIdentificadores.nextInt(10) * 100000 + generadorIdentificadores.nextInt(10) * 10000 +
                    generadorIdentificadores.nextInt(10) * 1000 + generadorIdentificadores.nextInt(10) * 100 +
                    generadorIdentificadores.nextInt(10) * 10 + generadorIdentificadores.nextInt(10);
        } while (mapaTransferencias.get(identificador) != null);

        return identificador;
    }
}
