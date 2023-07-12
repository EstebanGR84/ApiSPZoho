package com.cun.sinuApi.Controllers;

import com.cun.sinuApi.Models.Aspirante;
import com.cun.sinuApi.Models.ResponseChatGPT;
import com.cun.sinuApi.Models.ResponseRegistroSinu;
import com.cun.sinuApi.Services.ChatGPTService;
import com.cun.sinuApi.Services.EmailService;
import com.cun.sinuApi.Services.OracleServiceAdmisiones;
import com.cun.sinuApi.Services.OracleServiceGeopolitica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ControllerAdmisionesSinu {
    @Autowired
    private final OracleServiceAdmisiones oracleServiceAdmisiones;
    @Autowired
    private final OracleServiceGeopolitica oracleServiceGeopolitica;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final ChatGPTService chatGpt;
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdmisionesSinu.class);
    public ControllerAdmisionesSinu(OracleServiceAdmisiones oracleServiceAdmisiones, OracleServiceGeopolitica oracleServiceGeopolitica, EmailService emailService, ChatGPTService chatGpt) {
        this.oracleServiceAdmisiones = oracleServiceAdmisiones;
        this.oracleServiceGeopolitica = oracleServiceGeopolitica;
        this.emailService = emailService;
        this.chatGpt = chatGpt;
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
            primerNombre = nombres[0].toUpperCase();;
            segundoNombre = nombres[1].toUpperCase();;
        }
        if(apellidos.length > 1) {
            primerApellido = apellidos[0].toUpperCase();;
            segundoApellido = apellidos[1].toUpperCase();;
        }
        if(estudiante.getLugarexpedicion().equals("") || estudiante.getLugarexpedicion() == null ){
            estudiante.setLugarexpedicion("bogota");
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
            if(!numFormulario.matches("\\d+")){
                response.setStatus("Error");
            }
            else{
                response.setStatus("Success");
            }
            response.setNoFormulario(numFormulario);
            response.setNoIdRegistrado(String.valueOf(estudiante.getNumeroDocumento()));
            return ResponseEntity.ok().body(response);
        }
        else{
            ResponseRegistroSinu response = new ResponseRegistroSinu();
            response.setStatus("Error");
            response.setNoFormulario(crearOportunidadResult);
            response.setNoIdRegistrado(String.valueOf(estudiante.getNumeroDocumento()));
            return ResponseEntity.ok().body(response);
        }
    }
    @PostMapping("/periodos")
    public ResponseEntity<List<Map<String, Object>>> buscarPeriodos(){
        return ResponseEntity.ok(this.oracleServiceAdmisiones.consultarPeriodoDeIngreso());
    }
    @PostMapping("/regionales")
    public ResponseEntity<List<Map<String, Object>>> buscarRegionales(){
        return ResponseEntity.ok(this.oracleServiceAdmisiones.consultarRegional());
    }
    @PostMapping("/programas")
    public ResponseEntity<List<Map<String, Object>>> buscarProgramas(@RequestBody JsonNode datos){
        return ResponseEntity.ok(this.oracleServiceAdmisiones.consultarProgramas(datos.get("periodo").asText()));
    }
    @PostMapping("/ciclos")
    public ResponseEntity <List<Map<String, Object>>> buscarCiclos(@RequestBody JsonNode datos){
        return ResponseEntity.ok(this.oracleServiceAdmisiones.consultarCiclos(datos.get("periodo").asText(), datos.get("programa").asText()));
    }
    @PostMapping("/tipoInscripcion")
    public ResponseEntity <List<Map<String, Object>>> buscarTipo(@RequestBody JsonNode datos){
        return ResponseEntity.ok(
                this.oracleServiceAdmisiones.consultarTiposInscripcion(datos.get("periodo").asText(),
                                                                       datos.get("programa").asText(),
                                                                       datos.get("ciclo").asText())
                );
    }
    @PostMapping("/ciudad")
    public ResponseEntity<String> buscarCiudad(){

        return ResponseEntity.ok( chatGpt.sendMessageChatGPT("puedes de este texto escribirme solo la ciudad que corresponde sin ningun tipo de descripcion y " +
                "sin ningun otra palabra Montelibano-Cordoba"));
    }
}
