package com.cun.sinuApi.Models;

public class Estudiante {
    public Estudiante() {
    }
    private String usuario;
    private String clave;
    private int numeroDocumento;
    private long idRegistro;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private int programa;
    private int prospecto;
    private String owner;
    private String direccion;
    private String genero;
    private String email;
    private String telefono;
    private String celular;
    private String fechaNacimiento;
    private String fechaExpedicion;
    private String grupoSanguineo;
    private String rhSanguineo;
    private String homologacion;
    private String gestorComercial;
    public String ciudadNacimiento;
    public String ciudadRecidencia;
    public String ciudadExpedicion;
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public int getNumeroDocumento() {
        return numeroDocumento;
    }
    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    public long getIdRegistro() {
        return idRegistro;
    }
    public void setIdRegistro(long idRegistro) {
        this.idRegistro = idRegistro;
    }
    public int getPrograma() {
        return programa;
    }
    public void setPrograma(int programa) {
        this.programa = programa;
    }
    public int getProspecto() {
        return prospecto;
    }
    public void setProspecto(int prospecto) {
        this.prospecto = prospecto;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getHomologacion() {
        return homologacion;
    }
    public void setHomologacion(String homologacion) {
        this.homologacion = homologacion;
    }
    public String getGestorComercial() {
        return gestorComercial;
    }
    public void setGestorComercial(String gestorComercial) {
        this.gestorComercial = gestorComercial;
    }
    public String getPrimerNombre() { return primerNombre;}
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre;}
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getFechaExpedicion() { return fechaExpedicion; }
    public void setFechaExpedicion(String fechaExpedicion) { this.fechaExpedicion = fechaExpedicion; }
    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }
    public String getRhSanguineo() { return rhSanguineo; }
    public void setRhSanguineo(String rhSanguineo) { this.rhSanguineo = rhSanguineo;}
    public String getCiudadNacimiento() { return ciudadNacimiento; }
    public void setCiudadNacimiento(String ciudadNacimiento) { this.ciudadNacimiento = ciudadNacimiento; }
    public String getCiudadRecidencia() { return ciudadRecidencia; }
    public void setCiudadRecidencia(String ciudadRecidencia) { this.ciudadRecidencia = ciudadRecidencia; }
    public String getCiudadExpedicion() { return ciudadExpedicion; }
    public void setCiudadExpedicion(String ciudadExpedicion) { this.ciudadExpedicion = ciudadExpedicion; }
}
