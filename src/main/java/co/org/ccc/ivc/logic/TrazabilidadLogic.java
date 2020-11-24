package co.org.ccc.ivc.logic;

import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

public interface TrazabilidadLogic {

	public RespuestaDTO agregarJsonEnvio(Integer codCamara, String idHistorico, String jsonEnvio) throws Exception;

	public RespuestaDTO agregarJsonRespuesta(Integer codCamara, String idHistorico, String jsonRespuesta,
			String codigoError, String mensajeError) throws Exception;

}