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

import co.org.ccc.ivc.dao.HistoricoDao;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;
import co.org.ccc.ivc.utility.Utilities;
import oracle.jdbc.OracleTypes;

@Repository
@Scope("singleton")
public class HistoricoDaoImpl implements HistoricoDao {

	@Autowired
	private ApplicationContext appContext;

	private static final Logger logger = LoggerFactory.getLogger(HistoricoDao.class);

	@Override
	public RespuestaDTO actualizarEstadoHistorico(Integer codCamara, String idHistorico, String estado) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Actualizar estado historico: " + codCamara + " " + idHistorico + " " + estado, null, false);
		
		RespuestaDTO respuestaDTO = new RespuestaDTO();
		
		Connection connection = null;
		CallableStatement cstmt = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ call PKG_IVC_HISTORICO.pro_ActEstadoHistorico (?,?,?,?,?) }");
			cstmt.setInt(1, codCamara);
			cstmt.setString(2, idHistorico);
			cstmt.setString(3, estado);
			
			cstmt.registerOutParameter(4, OracleTypes.INTEGER);
			cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
			
			cstmt.execute();
			
			respuestaDTO.setCodigoError(cstmt.getInt(4));
			respuestaDTO.setMensajeError(cstmt.getString(5));
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Actualizando el estado historico: " + e.getMessage(), null, false);
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