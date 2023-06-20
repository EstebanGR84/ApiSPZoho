package com.cun.sinuApi.Services;

import com.cun.sinuApi.Models.Estudiante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OracleService {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OracleService.class);
    @Autowired
    public OracleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public String crearOportunidadZoho(Estudiante estudiante) {
        String sql = "BEGIN " +
                "  cunp_zoho_control_conexion.crear_oportunidad_zoho(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); " +
                "END;";
        try {
            String respuesta = jdbcTemplate.execute((ConnectionCallback<String>) con -> {
                CallableStatement callableStatement = con.prepareCall(sql);
                callableStatement.setString(1, estudiante.getUsuario());
                callableStatement.setString(2, estudiante.getClave());
                callableStatement.setInt(3, estudiante.getNumeroDocumento());
                callableStatement.setInt(4, estudiante.getIdRegistro());
                callableStatement.setString(5, estudiante.getApellidos());
                callableStatement.setString(6, estudiante.getNombres());
                callableStatement.setInt(7, estudiante.getPrograma());
                callableStatement.setInt(8, estudiante.getProspecto());
                callableStatement.setString(9, estudiante.getOwner());
                callableStatement.setString(10, estudiante.getDireccion());
                callableStatement.setString(11, estudiante.getEmail());
                callableStatement.setString(12, estudiante.getTelefono());
                callableStatement.setString(13, estudiante.getCelular());
                callableStatement.setString(14, estudiante.getFechaNacimiento());
                callableStatement.setString(15, null);
                callableStatement.setString(16, null);
                callableStatement.setString(17, null);
                callableStatement.setString(18, null);
                callableStatement.setString(19, null);
                callableStatement.setString(20, null);
                callableStatement.setString(21, null);
                callableStatement.setString(22, estudiante.getHomologacion());
                callableStatement.setString(23, null);
                callableStatement.setString(24, estudiante.getGestorComercial());
                callableStatement.setString(25, null);
                callableStatement.setString(26, null);
                callableStatement.setString(27, null);
                callableStatement.setString(28, null);
                callableStatement.setString(29, null);
                callableStatement.setString(30, "ZOHO-NUEVO");
                callableStatement.setString(31, null);
                callableStatement.executeUpdate();
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet != null && resultSet.next()) {
                    return resultSet.getString(1);
                } else {
                    return null;
                }
            });
            if (respuesta != null) {
                return respuesta;
            } else {
                return "Registro Ok";
            }
        } catch (DataAccessException e) {
            logger.error("Error: " + e.getMessage());
            return e.getMessage();
        }
    }
}
