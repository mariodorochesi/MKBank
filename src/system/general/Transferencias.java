package system.general;

import system.systemAccounts.Cuenta;
import system.systemAccounts.CuentaBancaria;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.sql.*;

public class Transferencias {

    private String nombreDestinatario;
    private String nombreOriginario;
    private String rutDestinatario;
    private String rutOriginario;
    private String bancoDestinatario;
    private String tipoCuentaDestinatario;
    private String tipocuentaOrigen;
    private String mailDestinatario;
    private String comentarioTransferencia;
    private int annoTransferencia;
    private int mesTransferencia;
    private int diaTransferencia;
    private int montoTransferencia;
    private long numeroCuentaDestinatario;
    private long numeroCuentaOrigen;
    private long numeroTransferencia;

    /**
     * Constructor Transferencia
     *
     * @param cuentaOrigen  Cuenta que hace el envio del dinero
     * @param cuentaDestino Cuenta que recibe el dinero
     */
    public Transferencias(CuentaBancaria cuentaOrigen,
                          CuentaBancaria cuentaDestino,
                          long identificador,
                          int monto,
                          String comentario) {
        this.tipocuentaOrigen = cuentaOrigen.getTipoCuenta();
        this.numeroCuentaOrigen = cuentaOrigen.getIdentificador();
        this.rutDestinatario = cuentaDestino.getPersona().getRut();
        this.rutOriginario = cuentaOrigen.getPersona().getRut();
        this.bancoDestinatario = "MKBank";
        this.numeroCuentaDestinatario = cuentaDestino.getIdentificador();
        this.nombreDestinatario = cuentaDestino.getPersona().getNombres() + " " + cuentaDestino.getPersona().getApellidos();
        this.nombreOriginario = cuentaOrigen.getPersona().getNombres() + " " + cuentaOrigen.getPersona().getApellidos();
        this.tipoCuentaDestinatario = cuentaDestino.getTipoCuenta();
        this.mailDestinatario = cuentaDestino.getPersona().getCorreoElectronico();
        this.comentarioTransferencia = comentario;
        this.annoTransferencia = LocalDate.now().getYear();
        this.mesTransferencia = LocalDate.now().getMonthValue();
        this.diaTransferencia = LocalDate.now().getDayOfMonth();
        this.montoTransferencia = monto;
        this.numeroTransferencia = identificador;
    }

    /**
     * Constructor sobrecargado de Transferencias
     * Utilizado para la carga de Archivos.
     *
     * Queda inutilizable una vez implementada la base de datos
     * @param nombreOriginario
     * @param rutOriginario
     * @param tipocuentaOrigen
     * @param numeroCuentaOrigen
     * @param nombreDestinatario
     * @param rutDestinatario
     * @param tipoCuentaDestinatario
     * @param numeroCuentaDestinatario
     * @param correoDestinatario
     * @param numeroTransferencia
     * @param montoTransferencia
     * @param annosTransferencia
     * @param mesTransferencia
     * @param diaTransferencia
     * @param comentarioTransferencia
     */
    public Transferencias(String nombreOriginario,
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
                          String comentarioTransferencia) {

        this.nombreOriginario = nombreOriginario;
        this.rutOriginario = rutOriginario;
        this.tipocuentaOrigen = tipocuentaOrigen;
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.nombreDestinatario = nombreDestinatario;
        this.rutDestinatario = rutDestinatario;
        this.tipoCuentaDestinatario = tipoCuentaDestinatario;
        this.numeroCuentaDestinatario = numeroCuentaDestinatario;
        this.mailDestinatario = correoDestinatario;
        this.numeroTransferencia = numeroTransferencia;
        this.annoTransferencia = annosTransferencia;
        this.mesTransferencia = mesTransferencia;
        this.diaTransferencia = diaTransferencia;
        this.montoTransferencia = montoTransferencia;
        this.comentarioTransferencia = comentarioTransferencia;


    }

    /*
    *   Setters y Getters
    * */

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public String getNombreOriginario() {
        return nombreOriginario;
    }

    public void setNombreOriginario(String nombreOriginario) {
        this.nombreOriginario = nombreOriginario;
    }

    public String getRutDestinatario() {
        return rutDestinatario;
    }

    public void setRutDestinatario(String rutDestinatario) {
        this.rutDestinatario = rutDestinatario;
    }

    public String getRutOriginario() {
        return rutOriginario;
    }

    public void setRutOriginario(String rutOriginario) {
        this.rutOriginario = rutOriginario;
    }

    public String getBancoDestinatario() {
        return bancoDestinatario;
    }

    public void setBancoDestinatario(String bancoDestinatario) {
        this.bancoDestinatario = bancoDestinatario;
    }

    public String getTipoCuentaDestinatario() {
        return tipoCuentaDestinatario;
    }

    public void setTipoCuentaDestinatario(String tipoCuentaDestinatario) {
        this.tipoCuentaDestinatario = tipoCuentaDestinatario;
    }

    public String getTipocuentaOrigen() {
        return tipocuentaOrigen;
    }

    public void setTipocuentaOrigen(String tipocuentaOrigen) {
        this.tipocuentaOrigen = tipocuentaOrigen;
    }

    public String getMailDestinatario() {
        return mailDestinatario;
    }

    public void setMailDestinatario(String mailDestinatario) {
        this.mailDestinatario = mailDestinatario;
    }

    public String getComentarioTransferencia() {
        return comentarioTransferencia;
    }

    public void setComentarioTransferencia(String comentarioTransferencia) {
        this.comentarioTransferencia = comentarioTransferencia;
    }

    public int getAnnoTransferencia() {
        return annoTransferencia;
    }

    public void setAnnoTransferencia(int annoTransferencia) {
        this.annoTransferencia = annoTransferencia;
    }

    public int getMesTransferencia() {
        return mesTransferencia;
    }

    public void setMesTransferencia(int mesTransferencia) {
        this.mesTransferencia = mesTransferencia;
    }

    public int getDiaTransferencia() {
        return diaTransferencia;
    }

    public void setDiaTransferencia(int diaTransferencia) {
        this.diaTransferencia = diaTransferencia;
    }

    public int getMontoTransferencia() {
        return montoTransferencia;
    }

    public void setMontoTransferencia(int montoTransferencia) {
        this.montoTransferencia = montoTransferencia;
    }

    public long getNumeroCuentaDestinatario() {
        return numeroCuentaDestinatario;
    }

    public void setNumeroCuentaDestinatario(long numeroCuentaDestinatario) {
        this.numeroCuentaDestinatario = numeroCuentaDestinatario;
    }

    public long getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    public void setNumeroCuentaOrigen(long numeroCuentaOrigen) {
        this.numeroCuentaOrigen = numeroCuentaOrigen;
    }

    public long getNumeroTransferencia() {
        return numeroTransferencia;
    }

    public void setNumeroTransferencia(long numeroTransferencia) {
        this.numeroTransferencia = numeroTransferencia;
    }
}