package co.org.ccc.ivc.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.org.ccc.ivc.dao.EntidadDao;
import co.org.ccc.ivc.modelo.dto.EntidadDTO;
import co.org.ccc.ivc.utility.Utilities;
import oracle.jdbc.OracleTypes;

@Repository
@Scope("singleton")
public class EntidadDaoImpl implements EntidadDao {

	@Autowired
	private ApplicationContext appContext;

	private static final Logger logger = LoggerFactory.getLogger(EntidadDao.class);

	@Override
	public EntidadDTO obtenerCredenciales(Integer codCamara, Integer codEntidad) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Obteniendo Credenciales: " + codCamara + " " + codEntidad, null, false);
		
		EntidadDTO entidadDTO = new EntidadDTO();
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ ? = call PkgInfoEntidadCae.fcu_Obtiene_Usuario (?,?) }");
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setInt(2, codCamara);
			cstmt.setInt(3, codEntidad);
			
			cstmt.execute();
			
			rs = (ResultSet) cstmt.getObject(1);
			
			while (rs.next()) {
				entidadDTO.setLogin(rs.getString("LOGIN"));
				entidadDTO.setPassword(rs.getString("PASS"));
			}
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion obteniendo las credenciales: " + e.getMessage(), null, false);
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
		return entidadDTO;
	}
	
	@Override
	public EntidadDTO obtenerCredencialesToken(Integer codCamara, Integer codEntidad) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Obteniendo Credenciales Token: " + codCamara + " " + codEntidad, null, false);
		
		EntidadDTO entidadDTO = new EntidadDTO();
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ ? = call PKG_CAE_USUARIOS.fcu_Obtiene_Info_Usuario (?,?) }");
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setInt(2, codCamara);
			cstmt.setInt(3, codEntidad);
			
			cstmt.execute();
			
			rs = (ResultSet) cstmt.getObject(1);
			
			while (rs.next()) {
				entidadDTO.setLogin(rs.getString("LOGIN"));
				entidadDTO.setPassword(rs.getString("PASS"));
				entidadDTO.setToken(rs.getString("TOKEN"));
			}
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion obteniendo las credenciales Token: " + e.getMessage(), null, false);
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
		return entidadDTO;
	}

	@Override
	public Integer validarExpToken(Integer codCamara, Integer codEntidad) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Validar expiracion token: " + codCamara + " " + codEntidad, null, false);
		
		Connection connection = null;
		CallableStatement cstmt = null;
		Integer result = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ ? = call PKG_CAE_USUARIOS.fcu_Valida_Tiempo_Token (?,?) }");
			
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setInt(2, codCamara);
			cstmt.setInt(3, codEntidad);
			
			cstmt.execute();
			result = cstmt.getInt(1);
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion validando expiracion token: " + e.getMessage(), null, false);
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
		return result;
	}
	
	@Override
	public Integer actualizaToken(Integer codCamara, String Token) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Actualizando token: " + codCamara + " " + Token, null, false);
		
		Connection connection = null;
		CallableStatement cstmt = null;
		Integer result = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ ? = call PKG_CAE_USUARIOS.fcu_Actualiza_Token (?,?) }");
			
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setInt(2, codCamara);
			cstmt.setString(3, Token);
			
			cstmt.execute();
			result = cstmt.getInt(1);
			
			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Actualizando token: " + e.getMessage(), null, false);
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
		return result;
	}
	
}