package co.org.ccc.ivc.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.org.ccc.ivc.dao.EntidadDao;
import co.org.ccc.ivc.logic.EntidadLogic;
import co.org.ccc.ivc.modelo.dto.EntidadDTO;

@Service("EntidadLogicImpl")
public class EntidadLogicImpl implements EntidadLogic {

	@Autowired
	private EntidadDao entidadDao;

	@Override
	public EntidadDTO obtenerCredenciales(Integer codCamara, Integer codEntidad) throws Exception {
		return entidadDao.obtenerCredenciales(codCamara, codEntidad);
	}
	
	@Override
	public EntidadDTO obtenerCredencialesToken(Integer codCamara, Integer codEntidad) throws Exception {
		return entidadDao.obtenerCredencialesToken(codCamara, codEntidad);
	}
		
	@Override
	public Integer validarExpToken(Integer codCamara, Integer codEntidad) throws Exception {
		return entidadDao.validarExpToken(codCamara, codEntidad);
	}
	
	@Override
	public Integer actualizaToken(Integer codEntidad, String Token) throws Exception {
		return entidadDao.actualizaToken(codEntidad, Token);
	}

}