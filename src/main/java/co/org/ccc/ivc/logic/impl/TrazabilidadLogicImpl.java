package co.org.ccc.ivc.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.org.ccc.ivc.dao.TrazabilidadDao;
import co.org.ccc.ivc.logic.TrazabilidadLogic;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

@Service("TrazabilidadLogicImpl")
public class TrazabilidadLogicImpl implements TrazabilidadLogic {

	@Autowired
	private TrazabilidadDao trazabilidadDao;

	@Override
	public RespuestaDTO agregarJsonEnvio(Integer codCamara, String idHistorico, String jsonEnvio) throws Exception {
		return trazabilidadDao.agregarJsonEnvio(codCamara, idHistorico, jsonEnvio);
	}

	@Override
	public RespuestaDTO agregarJsonRespuesta(Integer codCamara, String idHistorico, String jsonRespuesta,
			String codigoError, String mensajeError) throws Exception {
		return trazabilidadDao.agregarJsonRespuesta(codCamara, idHistorico, jsonRespuesta, codigoError, mensajeError);
	}
	
}