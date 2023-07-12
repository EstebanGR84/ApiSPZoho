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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
                callableStatement.setString(3, estudiante.getNumeroDocumento());
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
                callableStatement.setString(16, String.valueOf(estudiante.getGenero().charAt(0)));
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
            //String delete = "DELETE FROM ZOHO_CUN.CUNT_ZOHO_CONTROL_CONEXION sf WHERE sf.LLAVE_UNICA_OPORTUNIDAD = '"+estudiante.getIdRegistro()+"'";
            //jdbcTemplate.execute(delete);
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
            try{
                String sql = "SELECT TEXTO_ESTADO FROM ZOHO_CUN.CUNT_ZOHO_CONTROL_CONEXION sf WHERE sf.LLAVE_UNICA_OPORTUNIDAD = ?";
                String MessageError = jdbcTemplate.queryForObject(sql, String.class, String.valueOf(estudiante.getIdRegistro()));
                String delete = "DELETE FROM ZOHO_CUN.CUNT_ZOHO_CONTROL_CONEXION sf WHERE sf.LLAVE_UNICA_OPORTUNIDAD = '"+estudiante.getIdRegistro()+"'";
                jdbcTemplate.execute(delete);
                return MessageError;
            }
            catch(Exception ex){
                return ex.getMessage();
            }
        }
    }
    public void actualizarBasTercero(Aspirante estudiante, String segundoApellido, String segundoNombre, String ciudadExpedicion) {
        String tipoDoc;
        if(estudiante.getTipoid().contains("Identidad")){
            tipoDoc = "T";
        }
        else if(estudiante.getTipoid().contains("Ciudadanía")){
            tipoDoc = "C";
        }
        else if(estudiante.getTipoid().contains("Pasaporte")){
            tipoDoc = "P";
        }
        else if(estudiante.getTipoid().contains("Extranjería")){
            tipoDoc = "E";
        }
        else {
            tipoDoc = "C";
        }
            if(estudiante.getCiudadnacimiento().equals("2390")){
            estudiante.setCiudadnacimiento("2459");
        }
        if(estudiante.getCiudadresidencia().equals("2390")){
            estudiante.setCiudadresidencia("2459");
        }
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaUtil = dateFormat.parse(estudiante.getFechaexpedicion());
            java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
            String sql = "UPDATE SINU.BAS_TERCERO bt SET bt.SEG_APELLIDO = ?,"+
                    "bt.SEG_NOMBRE = ?, bt.FEC_EXP_DOCUMENTO = ?,"+
                    "bt.ID_UBI_RES = ?, bt.ID_UBI_NAC = ?, ID_UBI_DOCUMENTO = ?, TIP_IDENTIFICACION = ?"+
                    "WHERE bt.NUM_IDENTIFICACION = ?";
            String respuesta = jdbcTemplate.execute((ConnectionCallback<String>) con -> {
                    CallableStatement callableStatement = con.prepareCall(sql);
                callableStatement.setString(1, segundoApellido);
                callableStatement.setString(2, segundoNombre);
                callableStatement.setDate(3, fechaSql);
                callableStatement.setString(4, estudiante.getCiudadresidencia());
                callableStatement.setString(5, estudiante.getCiudadnacimiento());
                if(ciudadExpedicion.equals("2390")){
                    callableStatement.setString(6,"2459");
                }else {
                    callableStatement.setString(6,ciudadExpedicion);
                }
                callableStatement.setString(7, tipoDoc);
                callableStatement.setString(8, String.valueOf(estudiante.getNumeroDocumento()));
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
    public List<Map<String, Object>> consultarPeriodoDeIngreso(){
        try{
            String sql = "SELECT DISTINCT PERIODO FROM CUNT_PROMOCIONES_DISPONIBLES WHERE ESTADO = 'A' AND TIPO_PROMOCION = 'PAGO ANTICIPADO'";
            List<Map<String, Object>> periodos = jdbcTemplate.queryForList(sql);
            return periodos;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public  List<Map<String, Object>> consultarRegional(){
        try{
            String sql = "SELECT ID_SECCIONAL, NOM_SECCIONAL FROM sinu.src_seccional WHERE nom_seccional LIKE '%Regional%'";
            List<Map<String, Object>> regionales = jdbcTemplate.queryForList(sql);
            return regionales;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public List<Map<String, Object>> consultarProgramas(String periodo){
        try{
            String sql = "SELECT DISTINCT PROGRAMA FROM CUNT_PROMOCIONES_DISPONIBLES WHERE ESTADO = 'A' AND TIPO_PROMOCION = 'PAGO ANTICIPADO' AND PERIODO = ?";
            List<Map<String, Object>> programas = jdbcTemplate.queryForList(sql, periodo);
            return programas;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public List<Map<String, Object>> consultarCiclos(String periodo, String programa){
        try{
            String sql = "SELECT DISTINCT CICLO FROM CUNT_PROMOCIONES_DISPONIBLES WHERE ESTADO = 'A' " +
                         "AND TIPO_PROMOCION = 'PAGO ANTICIPADO' " +
                         "AND PERIODO = ? AND PROGRAMA = ? ORDER BY CICLO DESC";
            List<Map<String, Object>> ciclos = jdbcTemplate.queryForList(sql, periodo, programa);
            return ciclos;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public List<Map<String, Object>> consultarTiposInscripcion(String periodo, String programa, String ciclo){
        try{
            String sql = "SELECT DISTINCT TIPO_INSCRIPCION FROM CUNT_PROMOCIONES_DISPONIBLES WHERE ESTADO = 'A' " +
                    "AND TIPO_PROMOCION = 'PAGO ANTICIPADO' AND PERIODO = ? " +
                    "AND PROGRAMA = ? AND CICLO = ?";
            List<Map<String, Object>> TipoI = jdbcTemplate.queryForList(sql, periodo, programa, ciclo);
            return TipoI;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
}
