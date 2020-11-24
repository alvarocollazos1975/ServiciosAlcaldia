package co.org.ccc.ivc.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.org.ccc.ivc.dao.HistoricoDao;
import co.org.ccc.ivc.logic.HistoricoLogic;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

@Service("HistoricoLogicImpl")
public class HistoricoLogicImpl implements HistoricoLogic {

	@Autowired
	private HistoricoDao historicoDao;

	@Override
	public RespuestaDTO actualizarEstadoHistorico(Integer codCamara, String idHistorico, String estado)
			throws Exception {
		return historicoDao.actualizarEstadoHistorico(codCamara, idHistorico, estado);
	}

}