package co.org.ccc.ivc.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.org.ccc.ivc.dao.ParametroDao;
import co.org.ccc.ivc.logic.ParametroLogic;

@Service("ParametroLogicImpl")
public class ParametroLogicImpl implements ParametroLogic {
	
	@Autowired
	private ParametroDao parametroDao;
	
	@Override
	public String obtenerValorParametro(Integer codCamara, String nomTabla, String codTabla) throws Exception {
		return parametroDao.obtenerValorParametro(codCamara, nomTabla, codTabla);
	}

}