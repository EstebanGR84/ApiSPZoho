package com.cun.sinuApi.Services;

import com.cun.sinuApi.Models.Estudiante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OracleServiceGeopolitica {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OracleServiceAdmisiones.class);
    @Autowired
    public OracleServiceGeopolitica(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public String consultarGeopolitica(String nomCiudad) {
        try{
            String sql = "SELECT ID_GEOPOLITICA FROM SINU.BAS_GEOPOLITICA bg WHERE UPPER(NOM_DIV_GEOPOLITICA) LIKE ?";
            String formato = "%"+nomCiudad.toUpperCase()+"%";
            String idGeo = jdbcTemplate.queryForObject(sql, String.class, formato);
            return idGeo;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return "";
        }
    }
}
