package co.org.ccc.ivc.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.org.ccc.ivc.dao.TrazabilidadDao;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;
import co.org.ccc.ivc.utility.Utilities;
import oracle.jdbc.OracleTypes;

@Repository
@Scope("singleton")
public class TrazabilidadDaoImpl implements TrazabilidadDao {

	@Autowired
	private ApplicationContext appContext;

	private static final Logger logger = LoggerFactory.getLogger(TrazabilidadDao.class);

	@Override
	public RespuestaDTO agregarJsonEnvio(Integer codCamara, String idHistorico, String jsonEnvio) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Agregar Json Envio: " + codCamara + " " + idHistorico + " " + jsonEnvio, null, false);
		
		RespuestaDTO respuestaDTO = new RespuestaDTO();

		Connection connection = null;
		CallableStatement cstmt = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ call PKG_IVC_TRAZABILIDAD.proInsertJsonInicial (?,?,?,?,?) }");
			cstmt.setInt(1, codCamara);
			cstmt.setString(2, idHistorico);
			cstmt.setObject(3, jsonEnvio);
			
			cstmt.registerOutParameter(4, OracleTypes.INTEGER);
			cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
			
			cstmt.execute();
			
			respuestaDTO.setCodigoError(cstmt.getInt(4));
			respuestaDTO.setMensajeError(cstmt.getString(5));
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Agregando JSON envio: " + e.getMessage(), null, false);
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null)
					cstmt.close();
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException x) {
				cstmt = null;
				Utilities.addMessage(logger, "ERROR", "Error intentando cerrar la conexion de BD: " + x.getMessage(), null, false);
			}
		}
		
		return respuestaDTO;
	}
	@Override
	public RespuestaDTO agregarJsonRespuesta(Integer codCamara, String idHistorico, String jsonRespuesta,
			String codigoError, String mensajeError) throws Exception {
		Utilities.addMessage(logger, "INFO", "Agregar Json Respuesta: " + codCamara + " " + idHistorico + " "
				+ jsonRespuesta + " " + codigoError + " " + mensajeError, null, false);
		
		RespuestaDTO respuestaDTO = new RespuestaDTO();

		Connection connection = null;
		CallableStatement cstmt = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ call PKG_IVC_TRAZABILIDAD.proInsertJsonRespuesta (?,?,?,?,?,?,?) }");
			cstmt.setInt(1, codCamara);
			cstmt.setString(2, idHistorico);
			cstmt.setObject(3, jsonRespuesta);
			cstmt.setString(4, codigoError);
			cstmt.setString(5, mensajeError);
			
			cstmt.registerOutParameter(6, OracleTypes.INTEGER);
			cstmt.registerOutParameter(7, OracleTypes.VARCHAR);
			
			cstmt.execute();
			
			respuestaDTO.setCodigoError(cstmt.getInt(6));
			respuestaDTO.setMensajeError(cstmt.getString(7));
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Agregando JSON respuesta: " + e.getMessage(), null, false);
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null)
					cstmt.close();
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException x) {
				cstmt = null;
				Utilities.addMessage(logger, "ERROR", "Error intentando cerrar la conexion de BD: " + x.getMessage(), null, false);
			}
		}
		
		return respuestaDTO;
	}
	
}