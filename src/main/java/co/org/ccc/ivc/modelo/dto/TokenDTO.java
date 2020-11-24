package co.org.ccc.ivc.modelo.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable {

	private static final long serialVersionUID = -1352697821595544637L;
	
	private Boolean success;
	
	private String msg;
	
	private String token;
	
	public TokenDTO() {
		
	}

	public TokenDTO(Boolean success, String msg, String token) {
		this.success = success;
		this.msg = msg;
		this.token = token;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}