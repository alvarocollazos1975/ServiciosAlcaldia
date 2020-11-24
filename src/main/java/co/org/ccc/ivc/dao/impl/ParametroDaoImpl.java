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

import co.org.ccc.ivc.dao.ParametroDao;
import co.org.ccc.ivc.utility.Utilities;
import oracle.jdbc.OracleTypes;

@Repository
@Scope("singleton")
public class ParametroDaoImpl implements ParametroDao {

	@Autowired
	private ApplicationContext appContext;

	private static final Logger logger = LoggerFactory.getLogger(ParametroDao.class);

	@Override
	public String obtenerValorParametro(Integer codCamara, String nomTabla, String codTabla) throws Exception {
		Utilities.addMessage(logger, "INFO",
				"Obtener valor del parametro: " + codCamara + " " + nomTabla + " " + codTabla, null, false);

		Connection connection = null;
		CallableStatement cstmt = null;
		String result = null;
		
		try {
			DataSource ds = (DataSource) appContext.getBean("dataSource");
			connection = ds.getConnection();
			
			cstmt = connection.prepareCall("{ ? = call PKG_RP_GENERICA.fvGetValorObservacion (?,?,?) }");
			cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
			cstmt.setInt(2, codCamara);
			cstmt.setString(3, nomTabla);
			cstmt.setString(4, codTabla);
			
			cstmt.execute();
			result = cstmt.getString(1);

			cstmt.clearParameters();
			cstmt.close();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion obteniendo el valor del parametro: " + e.getMessage(), null, false);
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