package com.cun.sinuApi.Controllers;

import com.cun.sinuApi.Models.Aspirante;
import com.cun.sinuApi.Models.Estudiante;
import com.cun.sinuApi.Models.JsonRequest;
import com.cun.sinuApi.Models.ResponseRegistroSinu;
import com.cun.sinuApi.Services.OracleServiceAdmisiones;
import com.cun.sinuApi.Services.OracleServiceGeopolitica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "*")
public class ControllerAdmisionesSinu {
    @Autowired
    private final OracleServiceAdmisiones oracleServiceAdmisiones;
    @Autowired
    private final OracleServiceGeopolitica oracleServiceGeopolitica;
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdmisionesSinu.class);
    public ControllerAdmisionesSinu(OracleServiceAdmisiones oracleServiceAdmisiones, OracleServiceGeopolitica oracleServiceGeopolitica) {
        this.oracleServiceAdmisiones = oracleServiceAdmisiones;
        this.oracleServiceGeopolitica = oracleServiceGeopolitica;
    }
    @PostMapping("/")
    public ResponseEntity<Object> ejecutarProcedure(@RequestBody Aspirante estudiante){
        logger.error("Estudiante: " + estudiante);
        String primerNombre = estudiante.getNombres();
        String primerApellido = estudiante.getApellidos();
        String segundoNombre = "";
        String segundoApellido = "";
        String[] nombres = estudiante.getNombres().split(" ");
        String[] apellidos = estudiante.getApellidos().split(" ");
        if(nombres.length > 1){
            primerNombre = nombres[0];
            segundoNombre = nombres[1];
        }
        if(apellidos.length > 1) {
            primerApellido = apellidos[0];
            segundoApellido = apellidos[1];
        }
        estudiante.setApellidos(primerApellido);
        estudiante.setNombres(primerNombre);
        String crearOportunidadResult = oracleServiceAdmisiones.crearOportunidadZoho(estudiante);
        if(crearOportunidadResult.equals("ok")){
            String numFormulario = oracleServiceAdmisiones.consultarNumFormulario(estudiante);
            // String ciudadNacimiento = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadNacimiento());
            //String ciudadRecidencia = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadRecidencia());
            //String ciudadExpedicion = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadExpedicion());
            //oracleServiceAdmisiones.actualizarBasTercero(estudiante, ciudadNacimiento, ciudadRecidencia, ciudadExpedicion);
            oracleServiceAdmisiones.actualizarBasTercero(estudiante, segundoApellido, segundoApellido);
            ResponseRegistroSinu response = new ResponseRegistroSinu();
            response.setNoFormulario(numFormulario);
            response.setNoIdRegistrado(String.valueOf(estudiante.getNumeroDocumento()));
            return ResponseEntity.ok().body(response);
        }
        else{
              return ResponseEntity.ok().body(crearOportunidadResult);
        }
        //String numFormulario = oracleServiceAdmisiones.consultarNumFormulario(estudiante);
        //oracleServiceAdmisiones.actualizarBasTercero(estudiante, segundoApellido, segundoApellido);
        //ResponseRegistroSinu response = new ResponseRegistroSinu();
        //response.setNoFormulario(numFormulario);
        //response.setNoIdRegistrado(String.valueOf(estudiante.getNumeroDocumento()));
        //return ResponseEntity.ok().body(response);
        //if(estudiante.getCiudadNacimiento() == null || estudiante.getCiudadNacimiento() == ""){
          //  return ResponseEntity.badRequest().body("parametro ciudad de nacimiento erroneo");
        //}if(estudiante.getCiudadExpedicion() == null || estudiante.getCiudadExpedicion() == ""){
          //  return ResponseEntity.badRequest().body("parametro ciudad de expedicion erroneo");
        //}if(estudiante.getCiudadRecidencia() == null || estudiante.getCiudadRecidencia() == ""){
          //  return ResponseEntity.badRequest().body("parametro ciudad de recidencia erroneo");
        //}
        //String crearOportunidadResult = oracleServiceAdmisiones.crearOportunidadZoho(estudiante);
        //if(crearOportunidadResult.equals("ok")){
          //  String numFormulario = oracleServiceAdmisiones.consultarNumFormulario(estudiante);
           // String ciudadNacimiento = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadNacimiento());
            //String ciudadRecidencia = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadRecidencia());
            //String ciudadExpedicion = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadExpedicion());
            //oracleServiceAdmisiones.actualizarBasTercero(estudiante, ciudadNacimiento, ciudadRecidencia, ciudadExpedicion);
            //ResponseRegistroSinu response = new ResponseRegistroSinu();
            //response.setNoFormulario(numFormulario);
            //response.setNoIdRegistrado(String.valueOf(estudiante.getNumeroDocumento()));
            //return ResponseEntity.ok().body(response);
        //}
        //else{
          //  return ResponseEntity.ok().body(crearOportunidadResult);
        //}
    }



}
