package system.general;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class TransferenciasTreeTableView extends RecursiveTreeObject{

    private SimpleStringProperty nTransferencia;
    private SimpleStringProperty nCuenta;
    private SimpleStringProperty tipoCuentaDestino;
    private SimpleStringProperty numeroCuentaDestino;
    private SimpleStringProperty comentario;
    private SimpleStringProperty fecha;
    private SimpleStringProperty monto;

    /**
     * Esta clase funciona como intermediario para la utilizacion de
     * TreeViews en java FX
     */
    public TransferenciasTreeTableView(String nTransferencia, String nCuenta,
                                       String tipoCuentaDestino, String numeroCuentaDestino,
                                       String comentario, String fecha,
                                       String monto) {
        this.nTransferencia = new SimpleStringProperty(nTransferencia);
        this.nCuenta = new SimpleStringProperty(nCuenta);
        this.tipoCuentaDestino = new SimpleStringProperty(tipoCuentaDestino);
        this.numeroCuentaDestino = new SimpleStringProperty(numeroCuentaDestino);
        this.comentario = new SimpleStringProperty(comentario);
        this.fecha = new SimpleStringProperty(fecha);
        this.monto = new SimpleStringProperty(monto);
    }

    public String getnTransferencia() {
        return nTransferencia.get();
    }

    public SimpleStringProperty nTransferenciaProperty() {
        return nTransferencia;
    }

    public void setnTransferencia(String nTransferencia) {
        this.nTransferencia.set(nTransferencia);
    }

    public String getnCuenta() {
        return nCuenta.get();
    }

    public SimpleStringProperty nCuentaProperty() {
        return nCuenta;
    }

    public void setnCuenta(String nCuenta) {
        this.nCuenta.set(nCuenta);
    }

    public String getTipoCuentaDestino() {
        return tipoCuentaDestino.get();
    }

    public SimpleStringProperty tipoCuentaDestinoProperty() {
        return tipoCuentaDestino;
    }

    public void setTipoCuentaDestino(String tipoCuentaDestino) {
        this.tipoCuentaDestino.set(tipoCuentaDestino);
    }

    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino.get();
    }

    public SimpleStringProperty numeroCuentaDestinoProperty() {
        return numeroCuentaDestino;
    }

    public void setNumeroCuentaDestino(String numeroCuentaDestino) {
        this.numeroCuentaDestino.set(numeroCuentaDestino);
    }

    public String getComentario() {
        return comentario.get();
    }

    public SimpleStringProperty comentarioProperty() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario.set(comentario);
    }

    public String getFecha() {
        return fecha.get();
    }

    public SimpleStringProperty fechaProperty() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }

    public String getMonto() {
        return monto.get();
    }

    public SimpleStringProperty montoProperty() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto.set(monto);
    }




}
