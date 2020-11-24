package co.org.ccc.ivc.dao;

import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

public interface TrazabilidadDao {

	public RespuestaDTO agregarJsonEnvio(Integer codCamara, String idHistorico, String jsonEnvio) throws Exception;

	public RespuestaDTO agregarJsonRespuesta(Integer codCamara, String idHistorico, String jsonRespuesta,
			String codigoError, String mensajeError) throws Exception;

}