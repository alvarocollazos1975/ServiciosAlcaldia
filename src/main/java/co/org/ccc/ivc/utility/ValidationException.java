package co.org.ccc.ivc.utility;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 5742399686929666068L;

	private String codigoError;
	private String mensajeError;

	public ValidationException(String codigoError, String mensajeError) {
		this.codigoError = codigoError;
		this.mensajeError = mensajeError;
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

}
