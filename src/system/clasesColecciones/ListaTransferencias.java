package system.clasesColecciones;

import system.general.Transferencias;
import system.systemAccounts.CuentaBancaria;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ListaTransferencias {

    //Lista de Transferencias
    private ArrayList<Transferencias> listaTransferencias;

    /*
        Constructor de Lista Transferencias
    * */
    public ListaTransferencias(){
        listaTransferencias = new ArrayList<>();
    }

    /**
     * Agrega una Transferencia en la Lista de Transferencias
     * @return Verdadero si pudo agregar, Falso en caso contrario
     */

    public boolean agregarTransferencia(String nombreOriginario,
                                        String rutOriginario,
                                        String tipocuentaOrigen,
                                        long numeroCuentaOrigen,
                                        String nombreDestinatario,
                                        String rutDestinatario,
                                        String tipoCuentaDestinatario,
                                        long numeroCuentaDestinatario,
                                        String correoDestinatario,
                                        long numeroTransferencia,
                                        int montoTransferencia,
                                        int annosTransferencia,
                                        int mesTransferencia,
                                        int diaTransferencia,
                                        String comentarioTransferencia){
        Transferencias transferenciaAgregar = new Transferencias(nombreOriginario,rutOriginario,tipocuentaOrigen,numeroCuentaOrigen,
                nombreDestinatario,rutDestinatario,tipoCuentaDestinatario,numeroCuentaDestinatario,correoDestinatario,numeroTransferencia,
                montoTransferencia,annosTransferencia,mesTransferencia,diaTransferencia,comentarioTransferencia);

        return listaTransferencias.add(transferenciaAgregar);
    }

    /**
     * Sobrecarga del metodo anterior, agrega una transferencia a la lista de transferencias
     * @return Verdadero si pudo agregar, Falso en caso contrario
     */
    public boolean agregarTransferencia(CuentaBancaria cuentaOrigen,
                                     CuentaBancaria cuentaDestino,
                                     long identificador,
                                     int monto,
                                     String comentario){

        Transferencias transferenciaAgregar = new Transferencias(cuentaOrigen,cuentaDestino,identificador,monto,comentario);

        return listaTransferencias.add(transferenciaAgregar);

    }

    /**
     * Sobrecarga del metodo anterior, agrega una transferencia a la lista de transferencias
     * @param t Transferencia a agregar
     * @return Verdadero si pudo agregar o falso en caso contrario
     */

    public boolean agregarTransferencia(Transferencias t){
        return listaTransferencias.add(t);
    }


    /**
     * Retorna las ultimas N transferencias, para poder realizar un historial de transferencias del Usuario
     * @param cantidadMostrar Cantidad de N ultimas transacciones a mostrar
     * @return
     */

    public ArrayList<Transferencias> historialTransacciones(int cantidadMostrar){
        if(cantidadMostrar >= listaTransferencias.size() || cantidadMostrar == 0){
            return listaTransferencias;
        }
        ArrayList<Transferencias> historialMostrar = new ArrayList<>();
        for(int i = listaTransferencias.size()-cantidadMostrar;i<listaTransferencias.size();i++){
            historialMostrar.add(listaTransferencias.get(i));
        }
        return historialMostrar;
    }

    /**
     * Guarda todas las transferencias de un Usuario en el archivo indicado por parametro
     * @param rutaArchivo
     */

    public void saveTransferencias(String rutaArchivo){
        try{
            String line;
            final String sep = ",.,";
            PrintWriter writerTransferencias = new PrintWriter(rutaArchivo,"UTF-8");
            for(int i = 0; i < listaTransferencias.size(); i++){
                Transferencias transferencias = listaTransferencias.get(i);
                line = transferencias.getNombreOriginario() + sep +
                        transferencias.getRutOriginario() + sep +
                        transferencias.getTipocuentaOrigen() + sep +
                        transferencias.getNumeroCuentaOrigen() + sep +
                        transferencias.getNombreDestinatario() + sep  +
                        transferencias.getRutDestinatario() + sep +
                        transferencias.getTipoCuentaDestinatario() + sep +
                        transferencias.getNumeroCuentaDestinatario() + sep +
                        transferencias.getMailDestinatario() + sep +
                        transferencias.getNumeroTransferencia() + sep +
                        transferencias.getMontoTransferencia() + sep +
                        transferencias.getAnnoTransferencia() + sep +
                        transferencias.getMesTransferencia() + sep +
                        transferencias.getDiaTransferencia() + sep +
                        transferencias.getComentarioTransferencia();
                writerTransferencias.println(line);
            }
            writerTransferencias.close();
        }
        catch (Exception ignored){ }
    }
}
