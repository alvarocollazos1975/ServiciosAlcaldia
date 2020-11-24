package co.org.ccc.ivc.dao;

import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

public interface HistoricoDao {

	public RespuestaDTO actualizarEstadoHistorico(Integer codCamara, String idHistorico, String estado)
			throws Exception;

}