package co.org.ccc.ivc.logic;

import java.util.List;

import co.org.ccc.ivc.modelo.dto.IvcMetadataDTO;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;

public interface MetadataLogic {
	
	public List<IvcMetadataDTO> consultarMetadataPorGrupo(Integer codCamara, String idHistorico, String grupo) throws Exception;
	
	public RespuestaDTO eliminarRegistrosMetadata(Integer codCamara, String idHistorico) throws Exception;
	
}