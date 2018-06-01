package system.clasesColecciones;

import system.interfaces.Identificador;
import system.general.Transferencias;

import java.util.HashMap;
import java.util.Random;

public class MapaTransferencias implements Identificador {

    private HashMap<Long ,Transferencias> mapaTransferencias;
    private Random generadorIdentificadores;

    public MapaTransferencias(){
        mapaTransferencias = new HashMap<>();
        generadorIdentificadores = new Random();
    }

    public void agregarTranferencia(long identificador,Transferencias transferencia){
        mapaTransferencias.put(identificador,transferencia);
    }

    public void agregarTransferencia(long id,Transferencias t){
        mapaTransferencias.put(id,t);
    }

    public boolean existeTransferencia(long identificador){
        return mapaTransferencias.get(identificador) != null;
    }

    public boolean eliminarCuentaBancaria(long identificador){
        return mapaTransferencias.remove(identificador) != null;
    }

    public Transferencias obtenerTransferencia(long identificador){
        return mapaTransferencias.get(identificador);
    }

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
