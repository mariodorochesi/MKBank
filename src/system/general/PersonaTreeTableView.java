package system.general;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class PersonaTreeTableView extends RecursiveTreeObject{

    private SimpleStringProperty rutCliente;    // Rut Cliente
    private SimpleStringProperty nomCliente;    // Nombre + Apellido del cliente
    private SimpleStringProperty dineroTotal;   // Dinero total de todas las cuentas del cliente
    private SimpleStringProperty numeroCuent;   // Numero total de cuentas bancarias del cliente

    public PersonaTreeTableView(String rutCliente, String nomCliente, String dineroTotal, String numeroCuent) {
        this.rutCliente = new SimpleStringProperty(rutCliente);
        this.nomCliente = new SimpleStringProperty(nomCliente);
        this.dineroTotal = new SimpleStringProperty(dineroTotal);
        this.numeroCuent = new SimpleStringProperty(numeroCuent);
    }

    // Getter y setters:

    public String getRutCliente() {
        return rutCliente.get();
    }

    public SimpleStringProperty rutClienteProperty() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente.set(rutCliente);
    }

    public String getNomCliente() {
        return nomCliente.get();
    }

    public SimpleStringProperty nomClienteProperty() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente.set(nomCliente);
    }

    public String getDineroTotal() {
        return dineroTotal.get();
    }

    public SimpleStringProperty dineroTotalProperty() {
        return dineroTotal;
    }

    public void setDineroTotal(String dineroTotal) {
        this.dineroTotal.set(dineroTotal);
    }

    public String getNumeroCuent() {
        return numeroCuent.get();
    }

    public SimpleStringProperty numeroCuentProperty() {
        return numeroCuent;
    }

    public void setNumeroCuent(String numeroCuent) {
        this.numeroCuent.set(numeroCuent);
    }
}
