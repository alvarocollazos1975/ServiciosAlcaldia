package co.org.ccc.ivc.dao.impl;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.org.ccc.ivc.dao.MetadataDao;
import co.org.ccc.ivc.modelo.dto.IvcMetadataDTO;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;
import co.org.ccc.ivc.utility.Utilities;
import oracle.jdbc.OracleTypes;

@Repository
@Scope("singleton")
public class MetadataDaoImpl implements MetadataDao {

	@Autowired
	private ApplicationContext appContext;

	private static final Logger logger = LoggerFactory.getLogger(MetadataDao.class);

	@Override
	public List<IvcMetadataDTO> consultarMetadataPorGrupo(Integer codCamara, String idHistorico, String grupo)
			throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Obteniendo Metadata: " + codCamara + " " + idHistorico + " " + grupo, null, false);
		
		List<IvcMetadataDTO> ivcMetadatas = new ArrayList<IvcMetadataDTO>();
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ ? = call PKG_IVC_METADATA.fun_ConsultaMetadata (?,?,?) }");
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setInt(2, codCamara);
			cstmt.setString(3, idHistorico);
			cstmt.setString(4, grupo);
			
			cstmt.execute();
			
			rs = (ResultSet) cstmt.getObject(1);
			
			while (rs.next()) {
				IvcMetadataDTO ivcMetadataDTO = new IvcMetadataDTO();
				ivcMetadataDTO.setIvcIdMetadata(rs.getString("IVC_ID_METADATA"));
				ivcMetadataDTO.setIvcGrupo(rs.getString("IVC_GRUPO"));
				Clob clob = rs.getClob("IVC_DATA");
				ivcMetadataDTO.setIvcData(Utilities.convertClobToString(clob).replace(":\"\"", ":\"").replace("\"\"", "\""));
				ivcMetadataDTO.setIvcNumConsecutivo(rs.getInt("IVC_NUM_CONSECUTIVO"));
				ivcMetadataDTO.setIvcNumPropietario(rs.getInt("IVC_NUM_PROPIETARIO"));
				ivcMetadatas.add(ivcMetadataDTO);
			}
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Obteniendo metadata: " + e.getMessage(), null, false);
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

		return ivcMetadatas;
	}
	
	@Override
	public RespuestaDTO eliminarRegistrosMetadata(Integer codCamara, String idHistorico) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Eliminar Metadata: " + codCamara + " " + idHistorico, null, false);
		
		RespuestaDTO respuestaDTO = new RespuestaDTO();

		Connection connection = null;
		CallableStatement cstmt = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ call PKG_IVC_METADATA.proEliminacionMetadata (?,?,?,?) }");
			cstmt.setInt(1, codCamara);
			cstmt.setString(2, idHistorico);
			
			cstmt.registerOutParameter(3, OracleTypes.INTEGER);
			cstmt.registerOutParameter(4, OracleTypes.VARCHAR);
			
			cstmt.execute();
			
			respuestaDTO.setCodigoError(cstmt.getInt(3));
			respuestaDTO.setMensajeError(cstmt.getString(4));
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Eliminando metadata: " + e.getMessage(), null, false);
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