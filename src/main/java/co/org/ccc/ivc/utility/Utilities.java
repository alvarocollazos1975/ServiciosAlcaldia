package co.org.ccc.ivc.utility;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.config.RequestConfig;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.slf4j.Logger;

public class Utilities implements Serializable {

	private static final long serialVersionUID = 1209205298026504955L;

	public static String convierteFecha(String fecha) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat sdfOg = new SimpleDateFormat("yyyy-MM-dd");
		Date dfecha = sdfOg.parse(fecha);
		return sdf.format(dfecha);
	}

	public static String convierteFechaTiempos(String fecha) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mi");
		SimpleDateFormat sdfOg = new SimpleDateFormat("yyyy-MM-dd HH:mi");
		Date dfecha = sdfOg.parse(fecha);
		return sdf.format(dfecha);
	}

	public static String convierteFechaHora(String fecha) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		Date dfecha = dateFormat.parse(fecha);
		return sdf.format(dfecha);
	}

	public static boolean isNull(Object data) {
		return ((data == null) || (data.equals(Integer.valueOf(0))));
	}

	public static String obtenerString(Object object) {
		String salida = null;
		if (object != null) {
			salida = object.toString();
		}
		return salida;
	}

	public static void addMessage(Logger logger, String type, String msg, Exception e, boolean context) {
		addMessage(logger, type, msg, e, context, null);
	}

	public static void addMessage(Logger logger, String type, String msg, Exception e, boolean context,
			String clientId) {
		if (type.equals("INFO")) {
			logger.info(msg);
		} else if (type.equals("ERROR")) {
			logger.error(msg);
		} else if (type.equals("WARN")) {
			logger.warn(msg);
		} else if (type.equals("DEBUG")) {
			logger.debug(msg);
		} else if (type.equals("TRACE")) {
			logger.trace(msg);
		}
		if (e != null) {
			e.printStackTrace();
		}
	}

	public static void getColumnNames(ResultSet cur) throws SQLException {
		String ret = "";
		ResultSetMetaData curMeta = cur.getMetaData();
		int colCount = curMeta.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			ret = ret + "Parametro " + i + ": " + "Nombre: " + curMeta.getColumnName(i) + " Tipo: "
					+ curMeta.getColumnClassName(i) + "\n";
		}
		System.out.println(ret);
	}

	public static String convertClobToString(Clob clob) {
		String resultado = "";
		try {
			Reader r = clob.getCharacterStream();
			StringBuffer buffer = new StringBuffer();
			int ch;
			while ((ch = r.read()) != -1) {
				buffer.append("" + (char) ch);
			}
			resultado = buffer.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public static RequestConfig getConfigCon(int timeout) {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		return config;
	}

	public static boolean validateJson(String objectJson) {
		boolean flag = true;
		try {
	        JSONObject json = new JSONObject(objectJson);
	        if(json != null) {
	        	flag = true;
	        }
	    } catch (JSONException e) {
	    	flag = false;
	    }
		return flag;
	}

}