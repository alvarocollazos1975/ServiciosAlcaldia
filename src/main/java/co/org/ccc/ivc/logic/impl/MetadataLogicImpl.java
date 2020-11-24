package co.org.ccc.ivc.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.org.ccc.ivc.dao.MetadataDao;
import co.org.ccc.ivc.logic.MetadataLogic;
import co.org.ccc.ivc.modelo.dto.IvcMetadataDTO;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

@Service("MetadataLogicImpl")
public class MetadataLogicImpl implements MetadataLogic {

	@Autowired
	private MetadataDao metadataDao;

	@Override
	public List<IvcMetadataDTO> consultarMetadataPorGrupo(Integer codCamara, String idHistorico, String grupo)
			throws Exception {
		return metadataDao.consultarMetadataPorGrupo(codCamara, idHistorico, grupo);
	}

	@Override
	public RespuestaDTO eliminarRegistrosMetadata(Integer codCamara, String idHistorico) throws Exception {
		return metadataDao.eliminarRegistrosMetadata(codCamara, idHistorico);
	}

}