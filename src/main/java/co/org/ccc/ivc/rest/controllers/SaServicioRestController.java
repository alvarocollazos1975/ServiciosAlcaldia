package co.org.ccc.ivc.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.org.ccc.ivc.logic.EntidadLogic;
import co.org.ccc.ivc.logic.HistoricoLogic;
import co.org.ccc.ivc.logic.MetadataLogic;
import co.org.ccc.ivc.logic.ParametroLogic;
import co.org.ccc.ivc.logic.TrazabilidadLogic;
import co.org.ccc.ivc.modelo.dto.AlcaldiaDTO;
import co.org.ccc.ivc.modelo.dto.EntidadDTO;
import co.org.ccc.ivc.modelo.dto.IvcMetadataDTO;
import co.org.ccc.ivc.modelo.dto.RespuestaDTO;
import co.org.ccc.ivc.modelo.dto.TokenDTO;
import co.org.ccc.ivc.utility.Constantes;
import co.org.ccc.ivc.utility.Utilities;
import co.org.ccc.ivc.utility.ValidationException;

@RestController
@RequestMapping("/serviciosAlcaldia")
public class SaServicioRestController {

	private static final Logger logger = LoggerFactory.getLogger(SaServicioRestController.class);

	@Autowired
	private ParametroLogic parametroLogic;
	
	@Autowired
	private HistoricoLogic historicoLogic;
	
	@Autowired
	private MetadataLogic metadataLogic;
	
	@Autowired
	private TrazabilidadLogic trazabilidadLogic;
	
	@Autowired
	private EntidadLogic entidadLogic;
	
	private CloseableHttpClient CLIENT = HttpClients.createDefault();

	@RequestMapping(value = "/consumoAlcaldia", method = { RequestMethod.GET, RequestMethod.POST })
	public void consumoAlcaldia(@RequestParam("historico") String idHistorico, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RespuestaDTO respuestaDTO = new RespuestaDTO();
		String jsonRespuesta = "";
		String codigoError = "";
		String mensajeError = "";
		try {
			String jsonEnvio = "{";
			jsonEnvio = jsonEnvio + "\"inscrito\": {";
			
			List<IvcMetadataDTO> inscritoMetadata = this.metadataLogic
					.consultarMetadataPorGrupo(Constantes.CODIGO_CAMARA, idHistorico, Constantes.GRUPO_INSCRITO);
			if(inscritoMetadata != null && !inscritoMetadata.isEmpty()) {
				for (IvcMetadataDTO ivcMetadataDTO : inscritoMetadata) {
					jsonEnvio = jsonEnvio + ivcMetadataDTO.getIvcData() + ",";
					break;
				}
			}
			
			String jsonEstbl = "";
			jsonEstbl = jsonEstbl + "\"establecimientos\": [";
			List<IvcMetadataDTO> establMetadata = this.metadataLogic
					.consultarMetadataPorGrupo(Constantes.CODIGO_CAMARA, idHistorico, Constantes.GRUPO_ESTABLECIMIENTO);
			if(establMetadata != null && !establMetadata.isEmpty()) {
				int i = 1;
				int sizeEtbl = establMetadata.size();
				for (IvcMetadataDTO ivcMetadataDTO : establMetadata) {
					if(i < sizeEtbl) {
						jsonEstbl = jsonEstbl + ivcMetadataDTO.getIvcData() + ",";
					}else {
						jsonEstbl = jsonEstbl + ivcMetadataDTO.getIvcData();
					}
					i++;
				}
			}
			jsonEstbl = jsonEstbl + "]";
			jsonEnvio = jsonEnvio + jsonEstbl + ",";
			
			String jsonVinculos = "";
			jsonVinculos = jsonVinculos + "\"vinculos\": [";
			List<IvcMetadataDTO> vinculoMetadata = this.metadataLogic
					.consultarMetadataPorGrupo(Constantes.CODIGO_CAMARA, idHistorico, Constantes.GRUPO_VINCULOS);
			if(vinculoMetadata != null && !vinculoMetadata.isEmpty()) {
				for (IvcMetadataDTO ivcMetadataDTO : vinculoMetadata) {
					jsonVinculos = jsonVinculos + ivcMetadataDTO.getIvcData();
					break;
				}
			}
			jsonVinculos = jsonVinculos + "]";
			jsonEnvio = jsonEnvio + jsonVinculos;
			
			jsonEnvio = jsonEnvio + "}}";
			
			boolean flagJson = Utilities.validateJson(jsonEnvio);
			if(flagJson) {
				//Creo el registro de trazabilidad con el JSON a enviar
				respuestaDTO = this.trazabilidadLogic.agregarJsonEnvio(Constantes.CODIGO_CAMARA, idHistorico, jsonEnvio);
				if(respuestaDTO.getCodigoError().equals(Constantes.CODIGO_ERROR)) {
					throw new ValidationException(respuestaDTO.getCodigoError() + "", respuestaDTO.getMensajeError());
				}
				
				//Ejecucion servicio alcaldia
				AlcaldiaDTO alcaldiaDTO = servicioAlcaldia(jsonEnvio);
				
				//AlcaldiaDTO alcaldiaDTO = new AlcaldiaDTO();
				//alcaldiaDTO.setJsonRespuesta("Soy una respuesta");
				
				jsonRespuesta = alcaldiaDTO.getJsonRespuesta();
				codigoError = alcaldiaDTO.getCodigoError();
				mensajeError = alcaldiaDTO.getMensajeError();
				
				//Actualizo la trazabilidad con la respuesta de la alcaldia
				respuestaDTO = this.trazabilidadLogic.agregarJsonRespuesta(Constantes.CODIGO_CAMARA, idHistorico, jsonRespuesta, codigoError, mensajeError);
				if(respuestaDTO.getCodigoError().equals(Constantes.CODIGO_ERROR)) {
					throw new ValidationException(respuestaDTO.getCodigoError() + "", respuestaDTO.getMensajeError());
				}
				
				//Elimino los registros de metadata
				respuestaDTO = this.metadataLogic.eliminarRegistrosMetadata(Constantes.CODIGO_CAMARA, idHistorico);
				if(respuestaDTO.getCodigoError().equals(Constantes.CODIGO_ERROR)) {
					throw new ValidationException(respuestaDTO.getCodigoError() + "", respuestaDTO.getMensajeError());
				}
				
				//Actualizo los historicos a enviado
				respuestaDTO = this.historicoLogic.actualizarEstadoHistorico(Constantes.CODIGO_CAMARA, idHistorico, Constantes.ESTADO_ENVIADO);
				if(respuestaDTO.getCodigoError().equals(Constantes.CODIGO_ERROR)) {
					throw new ValidationException(respuestaDTO.getCodigoError() + "", respuestaDTO.getMensajeError());
				}
			}else {
				jsonRespuesta = jsonEnvio;
				codigoError = Constantes.COD_ERROR_JSON;
				mensajeError = Constantes.MSJ_ERROR_JSON;
				
				//Actualizo la trazabilidad con la respuesta de la alcaldia
				respuestaDTO = this.trazabilidadLogic.agregarJsonRespuesta(Constantes.CODIGO_CAMARA, idHistorico, jsonRespuesta, codigoError, mensajeError);
				if(respuestaDTO.getCodigoError().equals(Constantes.CODIGO_ERROR)) {
					throw new ValidationException(respuestaDTO.getCodigoError() + "", respuestaDTO.getMensajeError());
				}
				
				//Elimino los registros de metadata
				respuestaDTO = this.metadataLogic.eliminarRegistrosMetadata(Constantes.CODIGO_CAMARA, idHistorico);
				if(respuestaDTO.getCodigoError().equals(Constantes.CODIGO_ERROR)) {
					throw new ValidationException(respuestaDTO.getCodigoError() + "", respuestaDTO.getMensajeError());
				}
				
				//Actualizo los historicos a enviado
				respuestaDTO = this.historicoLogic.actualizarEstadoHistorico(Constantes.CODIGO_CAMARA, idHistorico, Constantes.ESTADO_ENVIADO);
				if(respuestaDTO.getCodigoError().equals(Constantes.CODIGO_ERROR)) {
					throw new ValidationException(respuestaDTO.getCodigoError() + "", respuestaDTO.getMensajeError());
				}
			}
		} catch (ValidationException e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Servicio alcaldia: " + "Codigo: " + e.getCodigoError()
					+ " Mensaje: " + e.getMensajeError(), null, false);
			e.printStackTrace();
		} catch (Exception e) {
			Utilities.addMessage(logger, "ERROR", "Excepcion Servicio alcaldia: " + e.getMessage(), null, false);
			e.printStackTrace();
		}
	}

	public TokenDTO obtenerToken(String usuario, String password) throws Exception {
		TokenDTO tokenDTO = new TokenDTO();
		
		String urlServicio = this.parametroLogic.obtenerValorParametro(Constantes.CODIGO_CAMARA,
				Constantes.URL_ALC_TOKEN, Constantes.COD_TABLA_ALCALDIA);
		
		HttpPost request = new HttpPost(urlServicio);
		
		// add header
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("usuario", usuario));
        urlParameters.add(new BasicNameValuePair("contrasena", password));

        request.setEntity(new UrlEncodedFormEntity(urlParameters));

		RequestConfig config = Utilities.getConfigCon(1000);
		
		CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		
		//Respuesta
		HttpResponse response = (HttpResponse) CLIENT.execute(request);
		HttpEntity entity = response.getEntity();

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		if (Constantes.STATUS_HTTP.intValue() == response.getStatusLine().getStatusCode()) {
			String jsonString = EntityUtils.toString(entity);
			
			JSONObject jsonRespuesta = new JSONObject(jsonString);
			
			tokenDTO.setSuccess(jsonRespuesta.getBoolean("success"));
			tokenDTO.setMsg(jsonRespuesta.getString("msg"));
			tokenDTO.setToken(jsonRespuesta.getString("token"));
		}
		return tokenDTO;
	}
	
	public AlcaldiaDTO servicioAlcaldia(String jsonEnvio) throws Exception {
		AlcaldiaDTO alcaldiaDTO = new AlcaldiaDTO();
		
		String urlServicio = this.parametroLogic.obtenerValorParametro(Constantes.CODIGO_CAMARA,
				Constantes.URL_ALCALDIA, Constantes.COD_TABLA_ALCALDIA);
		
		EntidadDTO entidadDTO = this.entidadLogic.obtenerCredencialesToken(Constantes.CODIGO_CAMARA, Constantes.COD_ENTIDAD);
		
		if(entidadDTO == null) {
			throw new ValidationException(Constantes.CODIGO_ERROR + "", "Entidad es Null (PKG_CAE_USUARIOS.fcu_Obtiene_Info_Usuario)");
		}
		
		if(entidadDTO.getLogin() == null || entidadDTO.getLogin().equalsIgnoreCase("")) {
			throw new ValidationException(Constantes.CODIGO_ERROR + "", "Usuario entidad es Null (PKG_CAE_USUARIOS.fcu_Obtiene_Info_Usuario)");
		}
		if(entidadDTO.getPassword() == null || entidadDTO.getPassword().equalsIgnoreCase("")) {
			throw new ValidationException(Constantes.CODIGO_ERROR + "", "Password entidad es Null (PKG_CAE_USUARIOS.fcu_Obtiene_Info_Usuario)");
		}
		if(entidadDTO.getToken() == null || entidadDTO.getToken().equalsIgnoreCase("")) {
			TokenDTO tokenDTO = obtenerToken(entidadDTO.getLogin(), entidadDTO.getPassword());
			if(tokenDTO != null) {
				if(tokenDTO.getToken() != null && !tokenDTO.getToken().equalsIgnoreCase("")) {
					this.entidadLogic.actualizaToken(Constantes.COD_ENTIDAD, tokenDTO.getToken());
					entidadDTO.setToken(tokenDTO.getToken());
				}
			}
		}else {
			Integer expiracionToken = this.entidadLogic.validarExpToken(Constantes.CODIGO_CAMARA, Constantes.COD_ENTIDAD);
			if(expiracionToken != null && expiracionToken.intValue() == 1) {
				TokenDTO tokenDTO = obtenerToken(entidadDTO.getLogin(), entidadDTO.getPassword());
				if(tokenDTO != null) {
					if(tokenDTO.getToken() != null && !tokenDTO.getToken().equalsIgnoreCase("")) {
						this.entidadLogic.actualizaToken(Constantes.COD_ENTIDAD, tokenDTO.getToken());
						entidadDTO.setToken(tokenDTO.getToken());
					}
				}
			}
		}
		
		HttpPost request = new HttpPost(urlServicio);
		request.addHeader("content-type", "application/json;charset=UTF-8");
		request.addHeader("charset", "UTF-8");
		String authValue = "Bearer " + entidadDTO.getToken();
		request.setHeader(HttpHeaders.AUTHORIZATION, authValue);
		
		StringEntity params = new StringEntity(jsonEnvio, "UTF-8");
		request.setEntity(params);

		RequestConfig config = Utilities.getConfigCon(1000);
		
		CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		
		//Respuesta
		HttpResponse response = (HttpResponse) CLIENT.execute(request);
		HttpEntity entity = response.getEntity();

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		String jsonString = EntityUtils.toString(entity);
		if (Constantes.STATUS_HTTP.intValue() == response.getStatusLine().getStatusCode()) {
			JSONObject jsonRespuesta = new JSONObject(jsonString);
			alcaldiaDTO.setCodigoError(jsonRespuesta.getString("codigoRespuesta"));
			alcaldiaDTO.setMensajeError(jsonRespuesta.getString("mensajeRespuesta"));
			alcaldiaDTO.setJsonRespuesta(jsonString);
		}else {
			alcaldiaDTO.setCodigoError(response.getStatusLine().getStatusCode() + "");
			alcaldiaDTO.setMensajeError(Constantes.MENSAJE_ERROR_ALCALDIA);
			alcaldiaDTO.setJsonRespuesta(jsonString);
		}
		return alcaldiaDTO;
	}
	
}