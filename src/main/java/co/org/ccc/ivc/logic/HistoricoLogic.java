package co.org.ccc.ivc.logic;

import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

public interface HistoricoLogic {

	public RespuestaDTO actualizarEstadoHistorico(Integer codCamara, String idHistorico, String estado)
			throws Exception;

}