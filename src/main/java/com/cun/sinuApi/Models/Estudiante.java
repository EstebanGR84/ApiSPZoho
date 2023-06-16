package com.cun.sinuApi.Models;

public class Estudiante {
    public Estudiante() {
    }
    private String usuario;
    private String clave;
    private int numeroDocumento;
    private int idRegistro;
    private String nombres;
    private String apellidos;
    private int programa;
    private int prospecto;
    private String owner;
    private String direccion;
    private String genero;
    private String email;
    private String telefono;
    private String celular;
    private String fechaNacimiento;
    private String homologacion;
    private String gestorComercial;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    public int getNumeroDocumento() {
        return numeroDocumento;
    }
    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    public int getIdRegistro() {
        return idRegistro;
    }
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }
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
}
