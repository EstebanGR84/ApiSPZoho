package com.cun.sinuApi.Services;
import com.cun.sinuApi.Models.Aspirante;
import com.cun.sinuApi.Models.Estudiante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class OracleServiceAdmisiones {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OracleServiceAdmisiones.class);
    @Autowired
    public OracleServiceAdmisiones(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String crearOportunidadZoho(Aspirante estudiante) {
        String sql = "BEGIN " +
                "  ZOHO_CUN.cunp_zoho_control_conexion.crear_oportunidad_zoho"+
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); " +
                "END;";
        try {
            String respuesta = jdbcTemplate.execute((ConnectionCallback<String>) con -> {
                CallableStatement callableStatement = con.prepareCall(sql);
                callableStatement.setString(1, estudiante.getIdCliente());
                callableStatement.setString(2, estudiante.getToken());
                callableStatement.setLong(3, Long.parseLong(estudiante.getNumeroDocumento()));
                callableStatement.setLong(4, Long.parseLong(estudiante.getIdRegistro()));
                callableStatement.setString(5, estudiante.getApellidos());
                callableStatement.setString(    6, estudiante.getNombres());
                callableStatement.setInt(7, Integer.parseInt(estudiante.getPrograma()));
                callableStatement.setLong(8, Long.parseLong(estudiante.getProspecto()));
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
                return "ok";
            });
            return "ok";
        } catch (DataAccessException e) {
            logger.error("Error: " + e.getMessage());
            return e.getMessage();
        }
    }
    public String consultarNumFormulario(Aspirante estudiante) {
        try{
            String sql = "SELECT NUM_FORMULARIO FROM SINU.SRC_FORMULARIO sf WHERE sf.NUM_IDENTIFICACION = ? AND sf.ID_CRM = ?";
            String numFormulario = jdbcTemplate.queryForObject(sql, String.class,estudiante.getNumeroDocumento(), String.valueOf(estudiante.getIdRegistro()));
            return numFormulario;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return "Ocurrio un problema con tu peticion puede que el documento ya se encuentre registrado";
        }
    }
    public void actualizarBasTercero(Aspirante estudiante, String segundoApellido, String segundoNombre) {
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaUtil = dateFormat.parse(estudiante.getFechaexpedicion());
            java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
            String sql = "UPDATE SINU.BAS_TERCERO bt SET bt.SEG_APELLIDO = ?,"+
                    "bt.SEG_NOMBRE = ?, bt.FEC_EXP_DOCUMENTO = ?,"+
                    "bt.ID_UBI_RES = ?, bt.ID_UBI_NAC = ?"+
                    "WHERE bt.NUM_IDENTIFICACION = ?";
            String respuesta = jdbcTemplate.execute((ConnectionCallback<String>) con -> {
                CallableStatement callableStatement = con.prepareCall(sql);
                callableStatement.setString(1, segundoApellido);
                callableStatement.setString(2, segundoNombre);
                callableStatement.setDate(3, fechaSql);
                callableStatement.setInt(4, Integer.parseInt(estudiante.getCiudadresidencia()));
                callableStatement.setInt(5, Integer.parseInt(estudiante.getCiudadnacimiento()));
                callableStatement.setString(6, String.valueOf(estudiante.getNumeroDocumento()));
                callableStatement.executeUpdate();
                return "ok";
    
            });
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
