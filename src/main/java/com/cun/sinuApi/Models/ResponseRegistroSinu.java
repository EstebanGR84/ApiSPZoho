package com.cun.sinuApi.Models;

public class ResponseRegistroSinu {
    private String noIdRegistrado;
    private String noFormulario;
    private String status;
    public ResponseRegistroSinu() {}
    public String getNoIdRegistrado() { return noIdRegistrado; }
    public void setNoIdRegistrado(String noIdRegistrado) { this.noIdRegistrado = noIdRegistrado; }
    public String getNoFormulario() { return noFormulario; }
    public void setNoFormulario(String noFormulario) { this.noFormulario = noFormulario;}
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
