package co.org.ccc.ivc.logic;

import co.org.ccc.ivc.modelo.dto.EntidadDTO;

public interface EntidadLogic {

	public EntidadDTO obtenerCredenciales(Integer codCamara, Integer codEntidad) throws Exception;

	public EntidadDTO obtenerCredencialesToken(Integer codCamara, Integer codEntidad) throws Exception;
	
	public Integer validarExpToken(Integer codCamara, Integer codEntidad) throws Exception;
	
	public Integer actualizaToken(Integer codEntidad, String Token) throws Exception;
	
}