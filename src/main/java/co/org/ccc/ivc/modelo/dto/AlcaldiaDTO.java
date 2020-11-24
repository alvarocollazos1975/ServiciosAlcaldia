package co.org.ccc.ivc.modelo.dto;

import java.io.Serializable;

public class AlcaldiaDTO implements Serializable {
	
	private static final long serialVersionUID = -6199978003419959803L;

	private String jsonRespuesta;
	
	private String codigoError;
	
	private String mensajeError;
	
	public AlcaldiaDTO() {
		
	}

	public AlcaldiaDTO(String jsonRespuesta, String codigoError, String mensajeError) {
		this.jsonRespuesta = jsonRespuesta;
		this.codigoError = codigoError;
		this.mensajeError = mensajeError;
	}

	public String getJsonRespuesta() {
		return jsonRespuesta;
	}

	public void setJsonRespuesta(String jsonRespuesta) {
		this.jsonRespuesta = jsonRespuesta;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}