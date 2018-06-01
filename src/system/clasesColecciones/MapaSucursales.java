package system.clasesColecciones;

import system.general.Sucursal;
import system.interfaces.Identificador;

import java.util.HashMap;
import java.util.Random;

public class MapaSucursales implements Identificador{

    private HashMap<Long , Sucursal> mapaSucursales;
    private Random generadorIdentificadores;

    public MapaSucursales(){
        mapaSucursales = new HashMap<>();
        generadorIdentificadores = new Random();
    }

    public void  agregarSucursal(String nombre , String direccion){
        long codigo = generarIdentificador();
        Sucursal sucursalAgregar = new Sucursal(nombre , direccion , codigo);
        mapaSucursales.put(codigo,sucursalAgregar);
    }

    public Sucursal obtenerSucursal(long identificador){
        return mapaSucursales.get(identificador);
    }

    public boolean existeSucursal(long identificador){
        return mapaSucursales.get(identificador) != null;
    }

    @Override
    public long generarIdentificador(){
        long identificador;
        do{
            identificador = generadorIdentificadores.nextInt(10) * 100000000 + generadorIdentificadores.nextInt(10) * 10000000 + generadorIdentificadores.nextInt(10) * 1000000
                    + generadorIdentificadores.nextInt(10) * 100000 + generadorIdentificadores.nextInt(10) * 10000 +
                    generadorIdentificadores.nextInt(10) * 1000 + generadorIdentificadores.nextInt(10) * 100 +
                    generadorIdentificadores.nextInt(10) * 10 + generadorIdentificadores.nextInt(10);
        } while (mapaSucursales.get(identificador) != null);
        return identificador;
    }


}
