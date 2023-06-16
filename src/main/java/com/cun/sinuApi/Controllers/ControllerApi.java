package com.cun.sinuApi.Controllers;

import com.cun.sinuApi.Models.Estudiante;
import com.cun.sinuApi.Services.OracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ControllerApi {
    @Autowired
    private final OracleService oracleService;

    public ControllerApi(OracleService oracleService) {
        this.oracleService = oracleService;
    }

    @PostMapping("/")
    public ResponseEntity<?> ejecutarProcedure(@RequestBody Estudiante estudiante){
        return ResponseEntity.ok().body(oracleService.crearOportunidadZoho(estudiante));
    }
}
