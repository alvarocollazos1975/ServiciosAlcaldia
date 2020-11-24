package co.org.ccc.ivc.modelo.dto;

import java.io.Serializable;

public class IvcMetadataDTO implements Serializable {

	private static final long serialVersionUID = 2019354103004684654L;
	
	private Integer ivcCodCamara;
	
	private String ivcIdMetadata;
	
	private Integer ivcNumConsecutivo;
	
	private Integer ivcNumPropietario;
	
	private String ivcGrupo;
	
	private String ivcData;
	
	private String ivcIdHistorico;
	
	public IvcMetadataDTO() {

	}

	public IvcMetadataDTO(Integer ivcCodCamara, String ivcIdMetadata, Integer ivcNumConsecutivo, Integer ivcNumPropietario,
			String ivcGrupo, String ivcData, String ivcIdHistorico) {
		this.ivcCodCamara = ivcCodCamara;
		this.ivcIdMetadata = ivcIdMetadata;
		this.ivcNumConsecutivo = ivcNumConsecutivo;
		this.ivcNumPropietario = ivcNumPropietario;
		this.ivcGrupo = ivcGrupo;
		this.ivcData = ivcData;
		this.ivcIdHistorico = ivcIdHistorico;
	}

	public Integer getIvcCodCamara() {
		return ivcCodCamara;
	}
	
	public void setIvcCodCamara(Integer ivcCodCamara) {
		this.ivcCodCamara = ivcCodCamara;
	}
	
	public String getIvcIdMetadata() {
		return ivcIdMetadata;
	}
	
	public void setIvcIdMetadata(String ivcIdMetadata) {
		this.ivcIdMetadata = ivcIdMetadata;
	}
	
	public Integer getIvcNumConsecutivo() {
		return ivcNumConsecutivo;
	}
	
	public void setIvcNumConsecutivo(Integer ivcNumConsecutivo) {
		this.ivcNumConsecutivo = ivcNumConsecutivo;
	}
	
	public Integer getIvcNumPropietario() {
		return ivcNumPropietario;
	}
	
	public void setIvcNumPropietario(Integer ivcNumPropietario) {
		this.ivcNumPropietario = ivcNumPropietario;
	}
	
	public String getIvcGrupo() {
		return ivcGrupo;
	}
	
	public void setIvcGrupo(String ivcGrupo) {
		this.ivcGrupo = ivcGrupo;
	}
	
	public String getIvcData() {
		return ivcData;
	}
	
	public void setIvcData(String ivcData) {
		this.ivcData = ivcData;
	}
	
	public String getIvcIdHistorico() {
		return ivcIdHistorico;
	}
	
	public void setIvcIdHistorico(String ivcIdHistorico) {
		this.ivcIdHistorico = ivcIdHistorico;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}