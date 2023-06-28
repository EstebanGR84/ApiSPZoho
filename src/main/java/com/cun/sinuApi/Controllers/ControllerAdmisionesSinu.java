package com.cun.sinuApi.Controllers;

import com.cun.sinuApi.Models.Estudiante;
import com.cun.sinuApi.Models.ResponseRegistroSinu;
import com.cun.sinuApi.Services.OracleServiceAdmisiones;
import com.cun.sinuApi.Services.OracleServiceGeopolitica;
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
    public ControllerAdmisionesSinu(OracleServiceAdmisiones oracleServiceAdmisiones, OracleServiceGeopolitica oracleServiceGeopolitica) {
        this.oracleServiceAdmisiones = oracleServiceAdmisiones;
        this.oracleServiceGeopolitica = oracleServiceGeopolitica;
    }
    @PostMapping("/")
    public ResponseEntity<Object> ejecutarProcedure(@RequestBody Estudiante estudiante){
        String crearOportunidadResult = oracleServiceAdmisiones.crearOportunidadZoho(estudiante);
        if(crearOportunidadResult.equals("ok")){
            String numFormulario = oracleServiceAdmisiones.consultarNumFormulario(estudiante);
            String ciudadNacimiento = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadNacimiento());
            String ciudadRecidencia = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadRecidencia());
            String ciudadExpedicion = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getCiudadExpedicion());
            oracleServiceAdmisiones.actualizarBasTercero(estudiante, ciudadNacimiento, ciudadRecidencia, ciudadExpedicion);
            ResponseRegistroSinu response = new ResponseRegistroSinu();
            response.setNoFormulario(numFormulario);
            response.setNoIdRegistrado(String.valueOf(estudiante.getNumeroDocumento()));
            return ResponseEntity.ok().body(response);
        }
        else{
            return ResponseEntity.ok().body(crearOportunidadResult);
        }
    }



}
