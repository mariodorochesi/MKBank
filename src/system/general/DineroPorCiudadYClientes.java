package system.general;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class DineroPorCiudadYClientes extends RecursiveTreeObject{

    private SimpleStringProperty ciudad;
    private SimpleStringProperty monto;
    private SimpleStringProperty cclientes;

    public DineroPorCiudadYClientes(String ciudad, String monto, String cclientes) {
        this.ciudad = new SimpleStringProperty(ciudad);
        this.monto = new SimpleStringProperty(monto);
        this.cclientes = new SimpleStringProperty(cclientes);
    }

    /*
    *   Setters y Getters
    * */

    public String getCiudad() {
        return ciudad.get();
    }

    public SimpleStringProperty ciudadProperty() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
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

    public String getCclientes() {
        return cclientes.get();
    }

    public SimpleStringProperty cclientesProperty() {
        return cclientes;
    }

    public void setCclientes(String cclientes) {
        this.cclientes.set(cclientes);
    }
}
