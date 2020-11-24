package co.org.ccc.ivc.modelo.dto;

import java.io.Serializable;

public class EntidadDTO implements Serializable {

	private static final long serialVersionUID = 2979936655242702484L;

	private String login;
	
	private String password;
	
	private String token;
	
	public EntidadDTO() {

	}

	public EntidadDTO(String login, String password, String token) {
		this.login = login;
		this.password = password;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}