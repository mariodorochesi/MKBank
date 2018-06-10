package system.clasesColecciones;

import system.general.Sucursal;
import system.general.SucursalTreeTableView;
import system.interfaces.Identificador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapaSucursales implements Identificador{

    // NOTA: La key de la sucursal es el nombre
    private HashMap<String , Sucursal> mapaSucursales;
    // Generador de Identificador de Sucursales
    private Random generadorIdentificadores;
    // Ultimo error generado
    private String lastError;

    public MapaSucursales(){
        mapaSucursales = new HashMap<>();
        generadorIdentificadores = new Random();
    }

    /**
     * Agrega una sucursal al mapa con los parametros ingresados
     * @param nombre    - Nombre de la nueva sucursal.
     * @param direccion - Direccion de la nueva sucursal.
     * @param codigo    - Codigo (Generado con generarIdentificador).
     */

    public void  agregarSucursal(String nombre , String direccion, long codigo){
        if(mapaSucursales.get(nombre) == null) {
            Sucursal sucursalAgregar = new Sucursal(nombre , direccion , codigo);
            mapaSucursales.put(nombre,sucursalAgregar);
            lastError = "La sucursal " + nombre + "fue agregada correctamente";
        }
        else{
            lastError = "La sucursal " + nombre + " ya existe";
        }
    }

    /**
     * Obtiene la sucursal que contenga el nombre ingresado como parametro.
     * @param nombre    - Nombre de la sucursal que se quiere buscar.
     * @return          - Retorna la sucursal que contenga ese noombre.
     */

    public Sucursal obtenerSucursal(String nombre){
        return mapaSucursales.get(nombre);
    }

    /**
     * Elimina una sucursal siempre que existe y que no tenga personas asociadas.
     * @param nombre    - Nombre de la sucursal a eliminar
     */

    public boolean eliminarSucursal(String nombre) {
        if(mapaSucursales.get(nombre) == null) {
            lastError = "La sucursal no existe";
            return false;
        }

        if(mapaSucursales.get(nombre).getCantidadPersonasAsociadas() > 0){
            lastError = "No se puede eliminar la sucursal, ya que tiene personas asociadas";
            return false;
        }

        mapaSucursales.remove(nombre);
        lastError = "Sucursal eliminada correctamente";
        return true;
    }

    /**
     * Busca la sucursal con el nombre ingresado como parametro dentro del mapa.
     * @param nombre    - Nombre de la sucursal que se quiere comprobar si existe.
     * @return          - Retorna true si existe la sucursal y false en caso contrario.
     */

    public boolean existeSucursal(String nombre){
        return mapaSucursales.get(nombre) != null;
    }

    /**
     * Genera un identificador unico de 9 digitos para la sucursal, implementando la interfaz identificador
     * @return Identificador unico
     */

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

    /**
     * Metodo para obtener todos los nombres de las sucursales
     * @return Retorna un arreglo de String, con los nombres de las sucursales
     */
    public String[] obtenerNombresSucursales(){
        ArrayList<String> nombres = new ArrayList<>();

        for(Sucursal s : mapaSucursales.values())
            nombres.add(s.getNombreSucursal());

        String[] caster = new String[nombres.size()];
        return nombres.toArray(caster);
    }

    /**
     * Metodo que retorna un arreglo de SucursalTreeTableView. necesario para generar
     * la seccion de sucursales, en la interfaz del Administrador
     * @return arreglo de SucursalTreeTableView
     */

    public SucursalTreeTableView[] getSucursalesForTable(){
        ArrayList<SucursalTreeTableView> sucursalesOnTree = new ArrayList<>();

        for(Sucursal s : mapaSucursales.values()){
            sucursalesOnTree.add(
                    new SucursalTreeTableView(
                            s.getNombreSucursal(),
                            s.getDireccionSucursal(),
                            String.valueOf(s.getCodigoSucursal())));
        }

        SucursalTreeTableView[] caster = new SucursalTreeTableView[sucursalesOnTree.size()];
        return sucursalesOnTree.toArray(caster);
    }

    /**
     *  Metodo que retorna el ultimo error generado
     * @return String con el ultimo error
     */

    public String getLastError(){
        return lastError;
    }
}
