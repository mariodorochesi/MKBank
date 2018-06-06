package system.general;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class SucursalTreeTableView extends RecursiveTreeObject {

    private SimpleStringProperty nombre;
    private SimpleStringProperty direccion;
    private SimpleStringProperty codigo;

    public SucursalTreeTableView(String nombre, String direccion, String codigo){
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.codigo = new SimpleStringProperty(codigo);
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public SimpleStringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getCodigo() {
        return codigo.get();
    }

    public SimpleStringProperty codigoProperty() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }
}
