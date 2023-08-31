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
    public boolean validarExistencia(Aspirante estudiante){
        try{
            String sqlValidacion = "SELECT NUM_IDENTIFICACION FROM SINU.BAS_TERCERO bt WHERE bt.NUM_IDENTIFICACION = ?";
            List<Map<String, Object>> usuario = jdbcTemplate.queryForList(sqlValidacion, estudiante.getNumeroDocumento());
            if(usuario.size() != 0){
                return true;
            }
            return false;
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    public void actualizarBasTercero(Aspirante estudiante, String segundoApellido, String segundoNombre, String ciudadExpedicion) {
        String tipoDoc;

        if(estudiante.getTipoid() != null){
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
        }
        else {
            tipoDoc = "C";
        }
        if(estudiante.getCiudadnacimiento() == null || estudiante.getCiudadnacimiento().equals("2390")){
            estudiante.setCiudadnacimiento("2459");
        }
        if(estudiante.getCiudadresidencia() == null || estudiante.getCiudadresidencia().equals("2390")){
            estudiante.setCiudadresidencia("2459");
        }
        if(estudiante.getCiudadresidencia().equals("33")){
            estudiante.setCiudadresidencia("926");
        }
        if(estudiante.getCiudadnacimiento().equals("33")){
            estudiante.setCiudadnacimiento("926");
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
                }
                else if(ciudadExpedicion.equals("33")){
                    callableStatement.setString(6,"926");
                }
                else {
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
            String sql = "SELECT COD_PERIODO FROM SRC_ACT_ACADEMICA WHERE TO_CHAR(SYSDATE,'dd/mm/yyyy') <= FEC_FIN AND VAL_ACTIVIDAD = 1 GROUP BY COD_PERIODO";
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
            String getProgramByPeriod = "SELECT UNIQUE (A.COD_UNIDAD) " +
                    ",U.NOM_UNIDAD " +
                    ",P.COD_PENSUM " +
                    ",s.id_sede " +
                    ",S.NOM_SEDE " +
                    ",se.id_seccional " +
                    ",SE.Nom_Seccional " +
                    "FROM SRC_ACT_ACADEMICA A " +
                    "INNER JOIN SRC_PENSUM P ON P.COD_UNIDAD = A.COD_UNIDAD  " +
                    "INNER JOIN SRC_UNI_ACADEMICA U ON U.COD_UNIDAD = A.COD_UNIDAD " +
                    "INNER JOIN SRC_SEDE S ON S.ID_SEDE = U.ID_SEDE " +
                    "INNER JOIN SRC_SECCIONAL SE ON SE.ID_SECCIONAL = S.ID_SECCIONAL " +
                    "WHERE A.VAL_ACTIVIDAD = 1 " +
                    "AND P.EST_PENSUM =  1 " +
                    "AND P.IND_PEN_OFERTA = 1 " +
                    "AND TO_CHAR (A.FEC_FIN ,'DD/MM/YYYY') >= SYSDATE " +
                    "AND A.COD_PERIODO = ?";
            List<Map<String, Object>> programas = jdbcTemplate.queryForList(getProgramByPeriod, periodo);
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
    public  List<Map<String, Object>> consultarValorMatricula(String periodo, String programa, String ciclo, String tipoInscripcion){
        try{
            String sql = "SELECT DISTINCT VALOR_MATRICULA, VALOR_IDIOMAS, VALOR_SERVICIO " +
                    "FROM CUNT_PROMOCIONES_DISPONIBLES WHERE ESTADO = 'A' " +
                    "AND TIPO_PROMOCION = 'PAGO ANTICIPADO' AND PERIODO = ? " +
                    "AND PROGRAMA = ? AND CICLO = ? AND TIPO_INSCRIPCION = ? ";
            List<Map<String, Object>> valorMatricula = jdbcTemplate.queryForList(sql, periodo, programa, ciclo,tipoInscripcion);
            return valorMatricula;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public  List<Map<String, Object>> consultarDescuentoMatricula(String periodo){
        try{
            String sql = "SELECT GRUPO, (PORCENTAJE * -1) PORCENTAJE " +
                    "FROM VENCIMIENTO_PERIODO WHERE PERIODO = ? " +
                    "AND GRUPO > 100 AND FECHA_VENCIMIENTO > SYSDATE " +
                    "ORDER BY PORCENTAJE ";
            List<Map<String, Object>> descuento = jdbcTemplate.queryForList(sql, periodo);
            return descuento;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public  List<Map<String, Object>> consultarPensumCarrera(String codigoPrograma, String nivelIngreso){
        try{
            String sql = "SELECT * FROM SINU.SRC_MAT_PENSUM " +
                    "WHERE COD_UNIDAD = ? " +
                    "AND NUM_NIVEL < ? " +
                    "ORDER BY NUM_NIVEL ";
            List<Map<String, Object>> materiasData = jdbcTemplate.queryForList(sql, codigoPrograma, Integer.parseInt(nivelIngreso));
            return materiasData;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public List<Map<String, Object>> consultarProgramasEntradaReconocimentoTitulo(String nivel){
        try{
            String[] nivelEstudio = new String[2];
            nivelEstudio[0] =nivel.equals("TECNICO") ?  "Técnico Profesional" : "TECNÓLOGO";
            nivelEstudio[1] = nivel.equals("TECNICO") ?  "Tecnico" : "TECNOLOGO";;
            String getProgramTitleRecognition = "SELECT * FROM SINU.RECONOCIMIENTO r WHERE r.NIVEL_IES = UPPER(?) OR r.NIVEL_IES = UPPER(?)";
            List<Map<String, Object>> programas = jdbcTemplate.queryForList(getProgramTitleRecognition, nivelEstudio[0], nivelEstudio[1]);
            return programas;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }
    public List<Map<String, Object>> consultarProgramaSalidaReconocimentoTitulo(String nivel, String nombrePrograma){
        try{
            String[] nivelEstudio = new String[2];
            nivelEstudio[0] =nivel.equals("TECNICO") ?  "Técnico Profesional" : "TECNÓLOGO";
            nivelEstudio[1] = nivel.equals("TECNICO") ?  "Tecnico" : "TECNOLOGO";;
            String getProgramTitleRecognition = "SELECT * FROM SINU.RECONOCIMIENTO r " +
                    "WHERE PROGRAMA_IES = ? " +
                    "AND (r.NIVEL_IES = UPPER('?') OR r.NIVEL_IES = UPPER('?'))";
            List<Map<String, Object>> programas = jdbcTemplate.queryForList(getProgramTitleRecognition,nombrePrograma,nivelEstudio[0],nivelEstudio[1]);
            return programas;
        }
        catch (DataAccessException e){
            logger.error("Error: " + e.getMessage());
            return null;
        }
    }




}
