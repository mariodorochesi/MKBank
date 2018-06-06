package system.general;

import system.systemAccounts.CuentaAdministrador;
import system.systemAccounts.CuentaEjecutivo;
import system.systemAccounts.CuentaSuperAdministrador;
import system.systemAccounts.CuentaUsuario;

import java.time.LocalDate;

public class Persona{

    private String nombres;
    private String apellidos;
    private String rut;
    private String ciudad;
    private String direccion;
    private String correoElectronico;
    private String telefono;
    private String nacionalidad;
    private String estadoCivil;
    private String contrasena;
    private String genero;
    private String sucursalAsociada;

    private LocalDate fechaNacimiento;

    private CuentaSuperAdministrador cuentaSuperAdministrador; // 3
    private CuentaAdministrador cuentaAdministrador; //2
    private CuentaEjecutivo cuentaEjecutivo; //1
    private CuentaUsuario cuentaUsuario;


    /**
     * Constructor Persona
     * @param nombre Nombre de la Persona
     * @param rut    Rut de la Persona
     * @param ciudad Ciudad de la Persona
     * @param direccion Direccion de la Persona
     * @param correoElectronico Correo Electronico de la Persnoa
     * @param telefono Telefono de la Persona
     * @param nacionalidad Nacionalidad de la Persona
     * @param annoNacimiento Numero del año de nacimiento de la Persona
     * @param mesNacimiento Numero del mes de nacimiento de la Persona
     * @param diaNacimiento Numero del dia de nacimiento de la Persona
     * @param estadoCivil Estado Civil de la Persona
     */
    public Persona(String nombre , String apellidos, String rut, String ciudad ,
                   String direccion , String correoElectronico,
                   String telefono, String nacionalidad, int annoNacimiento,
                   int mesNacimiento , int diaNacimiento, String estadoCivil, String genero){
        this.nombres = nombre;
        this.apellidos = apellidos;
        this.rut = rut;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = LocalDate.of(annoNacimiento,mesNacimiento,diaNacimiento);
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.contrasena = rut.substring(0,rut.indexOf('-'));
        this.cuentaSuperAdministrador = null;
        this.cuentaAdministrador = null;
        this.cuentaEjecutivo = null;
        this.cuentaUsuario = null;
    }

    public Persona(String nombre , String apellidos, String rut, String ciudad ,
                   String direccion , String correoElectronico,
                   String telefono, String nacionalidad, int annoNacimiento,
                   int mesNacimiento , int diaNacimiento, String estadoCivil, String genero, String contrasena){
        this.nombres = nombre;
        this.apellidos = apellidos;
        this.rut = rut;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = LocalDate.of(annoNacimiento,mesNacimiento,diaNacimiento);
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.contrasena = contrasena;
        this.cuentaSuperAdministrador = null;
        this.cuentaAdministrador = null;
        this.cuentaEjecutivo = null;
        this.cuentaUsuario = null;
    }


    /*
    *   Setters y Getters
    * */

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /*
    public String getContrasena() {
        return contrasena;
    }
    */

    public boolean contrasenaEquals(String contrasena){
        return this.contrasena.equals(contrasena);
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public CuentaSuperAdministrador getCuentaSuperAdministrador() {
        return cuentaSuperAdministrador;
    }

    public void setCuentaSuperAdministrador(CuentaSuperAdministrador cuentaSuperAdministrador) {
        this.cuentaSuperAdministrador = cuentaSuperAdministrador;
    }

    public String getSucursalAsociada() {
        return sucursalAsociada;
    }

    public void setSucursalAsociada(String sucursalAsociada) {
        this.sucursalAsociada = sucursalAsociada;
    }



    public CuentaAdministrador getCuentaAdministrador() {
        return cuentaAdministrador;
    }

    public void setCuentaAdministrador(CuentaAdministrador cuentaAdministrador) {
        this.cuentaAdministrador = cuentaAdministrador;
    }

    public CuentaEjecutivo getCuentaEjecutivo() {
        return cuentaEjecutivo;
    }

    public void setCuentaEjecutivo(CuentaEjecutivo cuentaEjecutivo) {
        this.cuentaEjecutivo = cuentaEjecutivo;
    }

    public CuentaUsuario getCuentaUsuario() {
        return cuentaUsuario;
    }

    public void setCuentaUsuario(CuentaUsuario cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }
    public String getContrasena() {
        return contrasena;
    }


}
