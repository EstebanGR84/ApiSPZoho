package com.cun.sinuApi.Controllers;

import com.cun.sinuApi.Models.Aspirante;
import com.cun.sinuApi.Models.ResponseRegistroSinu;
import com.cun.sinuApi.Services.EmailService;
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
    @Autowired
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdmisionesSinu.class);
    public ControllerAdmisionesSinu(OracleServiceAdmisiones oracleServiceAdmisiones, OracleServiceGeopolitica oracleServiceGeopolitica, EmailService emailService) {
        this.oracleServiceAdmisiones = oracleServiceAdmisiones;
        this.oracleServiceGeopolitica = oracleServiceGeopolitica;
        this.emailService = emailService;
    }
    @PostMapping("/")
    public ResponseEntity<Object> ejecutarProcedure(@RequestBody Aspirante estudiante){
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
            if(estudiante.getLugarexpedicion().contains("D.C.") || estudiante.getLugarexpedicion().contains("d.c.") || estudiante.getLugarexpedicion().contains(" DC")){
                estudiante.setLugarexpedicion("bogota");
            }
            String ciudadExpedicion = oracleServiceGeopolitica.consultarGeopolitica(estudiante.getLugarexpedicion());
            if(ciudadExpedicion == null || ciudadExpedicion.equals("")){
                String destinatario = "jose_gomezre@cun.edu.co";
                String asunto = "Geopolitica sin encontrar";
                String contenido = "No Pudimos encontrar la ciudad "+ estudiante.getLugarexpedicion();
                emailService.enviarCorreo(destinatario, asunto, contenido);
            }
            oracleServiceAdmisiones.actualizarBasTercero(estudiante, segundoApellido, segundoNombre, ciudadExpedicion);
            ResponseRegistroSinu response = new ResponseRegistroSinu();
            response.setStatus("Success");
            response.setNoFormulario(numFormulario);
            response.setNoIdRegistrado(String.valueOf(estudiante.getNumeroDocumento()));
            return ResponseEntity.ok().body(response);
        }
        else{
              return ResponseEntity.ok().body(crearOportunidadResult);
        }
    }
}
