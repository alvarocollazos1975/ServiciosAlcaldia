package co.org.ccc.ivc.modelo.dto;

import java.io.Serializable;

public class RespuestaDTO implements Serializable {
	
	private static final long serialVersionUID = 4329118104199292733L;

	private Integer codigoError;
	
	private String mensajeError;
	
	public RespuestaDTO() {
		
	}

	public RespuestaDTO(Integer codigoError, String mensajeError) {
		this.codigoError = codigoError;
		this.mensajeError = mensajeError;
	}

	public Integer getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(Integer codigoError) {
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