package co.org.ccc.ivc.dao;

import java.util.List;

import co.org.ccc.ivc.modelo.dto.IvcMetadataDTO;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

public interface MetadataDao {

	public List<IvcMetadataDTO> consultarMetadataPorGrupo(Integer codCamara, String idHistorico, String grupo)
			throws Exception;

	public RespuestaDTO eliminarRegistrosMetadata(Integer codCamara, String idHistorico) throws Exception;
	
}