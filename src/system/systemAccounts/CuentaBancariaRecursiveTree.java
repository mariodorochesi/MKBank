package system.systemAccounts;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class CuentaBancariaRecursiveTree extends RecursiveTreeObject {

    private SimpleStringProperty monto;
    private SimpleStringProperty habilitado;
    private SimpleStringProperty identificador;
    private SimpleStringProperty tipodeCuenta;

    /**
     * Esta clase funciona como intermediario para la utilizacion de
     * TreeViews en java FX
     */
    public CuentaBancariaRecursiveTree(String habilitado, String identificador, String tipodeCuenta, String monto){
        this.habilitado = new SimpleStringProperty(habilitado);
        this.identificador = new SimpleStringProperty(identificador);
        this.tipodeCuenta = new SimpleStringProperty(tipodeCuenta);
        this.monto = new SimpleStringProperty(monto);
    }

    public String getHabilitado() {
        return habilitado.get();
    }

    public SimpleStringProperty habilitadoProperty() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado.set(habilitado);
    }

    public String getIdentificador() {
        return identificador.get();
    }

    public SimpleStringProperty identificadorProperty() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador.set(identificador);
    }

    public String getTipodeCuenta() {
        return tipodeCuenta.get();
    }

    public SimpleStringProperty tipodeCuentaProperty() {
        return tipodeCuenta;
    }

    public void setTipodeCuenta(String tipodeCuenta) {
        this.tipodeCuenta.set(tipodeCuenta);
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
