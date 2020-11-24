package co.org.ccc.ivc.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	private static CloseableHttpClient CLIENT = HttpClients.createDefault();
	
	public static void main(String[] args) {

		String urlServicio = "https://devv5.certifactura.co/servicios/recepcionPrueba/rest/emitir";
		String user = "admin_camara_cali";
		String password = "123456";
		Object jsonEnvio = "{\r\n" + 
				"    \"tipoDocumento\": \"FE\",\r\n" + 
				"    \"versionDocumento\": \"1.0\",\r\n" + 
				"    \"registrar\": false,\r\n" + 
				"    \"control\": \"S\",\r\n" + 
				"    \"cvcc\": \"CVCC#ÁÉÍÓÚÜÑ&áéíóúúñ@¿¡!\",\r\n" + 
				"    \"formato\": \"JSON\",\r\n" + 
				"    \"codigoTipoDocumento\": \"01\",\r\n" + 
				"    \"tipoOperacion\": \"12\",\r\n" + 
				"    \"prefijoDocumento\": \"SETT\",\r\n" + 
				"    \"numeroDocumento\": \"3000270\",\r\n" + 
				"    \"fechaEmision\": \"2020-03-10\",\r\n" + 
				"    \"horaEmision\": \"02:32:35\",\r\n" + 
				"    \"periodoFacturacion\": {\r\n" + 
				"        \"fechaInicio\": \"\",\r\n" + 
				"        \"horaInicio\": \"\",\r\n" + 
				"        \"fechaFin\": \"\",\r\n" + 
				"        \"horaFin\": \"\"\r\n" + 
				"    },\r\n" + 
				"    \"numeroLineas\": \"10\",\r\n" + 
				"    \"subtotal\": \"130100\",\r\n" + 
				"    \"totalBaseImponible\": \"130100\",\r\n" + 
				"    \"subtotalMasTributos\": \"130100\",\r\n" + 
				"    \"totalDescuentos\": \"0\",\r\n" + 
				"    \"totalCargos\": \"0\",\r\n" + 
				"    \"totalAnticipos\": \"0\",\r\n" + 
				"    \"total\": \"130100\",\r\n" + 
				"    \"codigoMoneda\": \"COP\",\r\n" + 
				"    \"tasaCambio\": {\r\n" + 
				"        \"fechaCambio\": \"\",\r\n" + 
				"        \"codigoMonedaFacturado\": \"\",\r\n" + 
				"        \"codigoMonedaCambio\": \"\",\r\n" + 
				"        \"baseCambioFacturado\": \"\",\r\n" + 
				"        \"baseCambio\": \"\",\r\n" + 
				"        \"trm\": \"\"\r\n" + 
				"    },\r\n" + 
				"    \"pago\": {\r\n" + 
				"        \"id\": \"1\",\r\n" + 
				"        \"codigoMedioPago\": \"10\",\r\n" + 
				"        \"fechaVencimiento\": \"2020-03-10\",\r\n" + 
				"        \"listaIdentificadoresPago\": [\"1\"]\r\n" + 
				"    },\r\n" + 
				"    \"listaDescripciones\": [\"\"],\r\n" + 
				"    \"documentosAnexos\": [{\r\n" + 
				"            \"id\": \"\",\r\n" + 
				"            \"tipo\": \"OR\",\r\n" + 
				"            \"fechaEmision\": \"\"\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"gruposImpuestos\": [],\r\n" + 
				"    \"gruposDeducciones\": [{\r\n" + 
				"            \"codigo\": \"ZY\",\r\n" + 
				"            \"total\": \"0.00\",\r\n" + 
				"            \"listaDeducciones\": [{\r\n" + 
				"                    \"codigo\": \"ZY\",\r\n" + 
				"                    \"nombre\": \"No causa\",\r\n" + 
				"                    \"baseGravable\": \"0.00\",\r\n" + 
				"                    \"porcentaje\": \"0.00\",\r\n" + 
				"                    \"valor\": \"0.00\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"facturador\": {\r\n" + 
				"        \"razonSocial\": \"CÃ¡mara de Comercio de Cali\",\r\n" + 
				"        \"nombreRegistrado\": \"CÃ¡mara de Comercio de Cali\",\r\n" + 
				"        \"tipoIdentificacion\": \"31\",\r\n" + 
				"        \"identificacion\": \"890399001\",\r\n" + 
				"        \"digitoVerificacion\": \"1\",\r\n" + 
				"        \"naturaleza\": \"1\",\r\n" + 
				"        \"codigoRegimen\": \"48\",\r\n" + 
				"        \"responsabilidadFiscal\": \"O-15;O-13\",\r\n" + 
				"        \"codigoImpuesto\": \"1\",\r\n" + 
				"        \"nombreImpuesto\": \"IVA\",\r\n" + 
				"        \"telefono\": \"8861300\",\r\n" + 
				"        \"email\": \"facturacionelectronica@ccc.org.co\",\r\n" + 
				"        \"contacto\": {\r\n" + 
				"            \"nombre\": \"CAMARA DE COMERCIO DE CALI  \",\r\n" + 
				"            \"telefono\": \"8861300\",\r\n" + 
				"            \"fax\": \"8861300\",\r\n" + 
				"            \"email\": \"facturacionelectronica@ccc.org.co\",\r\n" + 
				"            \"observaciones\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"direccion\": {\r\n" + 
				"            \"codigoPais\": \"CO\",\r\n" + 
				"            \"nombrePais\": \"Colombia\",\r\n" + 
				"            \"codigoLenguajePais\": \"es\",\r\n" + 
				"            \"codigoDepartamento\": \"76\",\r\n" + 
				"            \"nombreDepartamento\": \"Valle del Cauca\",\r\n" + 
				"            \"codigoCiudad\": \"76001\",\r\n" + 
				"            \"nombreCiudad\": \"CALI\",\r\n" + 
				"            \"direccionFisica\": \"CL 8  NRO 3  -  14\",\r\n" + 
				"            \"codigoPostal\": \"76000\"\r\n" + 
				"        },\r\n" + 
				"        \"direccionFiscal\": {\r\n" + 
				"            \"codigoPais\": \"CO\",\r\n" + 
				"            \"nombrePais\": \"Colombia\",\r\n" + 
				"            \"codigoLenguajePais\": \"es\",\r\n" + 
				"            \"codigoDepartamento\": \"76\",\r\n" + 
				"            \"nombreDepartamento\": \"Valle del Cauca\",\r\n" + 
				"            \"codigoCiudad\": \"76001\",\r\n" + 
				"            \"nombreCiudad\": \"CALI\",\r\n" + 
				"            \"direccionFisica\": \"CL 8  NRO 3  -  14\",\r\n" + 
				"            \"codigoPostal\": \"76000\"\r\n" + 
				"        },\r\n" + 
				"        \"listaResponsabilidadesTributarias\": [{\r\n" + 
				"                \"codigo\": \"01\",\r\n" + 
				"                \"nombre\": \"IVA\",\r\n" + 
				"                \"descripcion\": \"Impuesto de Valor Agregado\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"codigoCIIU\": \"\",\r\n" + 
				"        \"sucursal\": {\r\n" + 
				"            \"id\": \"\",\r\n" + 
				"            \"numeroMatricula\": \"\",\r\n" + 
				"            \"razonSocial\": \"\",\r\n" + 
				"            \"prefijoFacturacion\": \"\",\r\n" + 
				"            \"direccion\": \"\",\r\n" + 
				"            \"telefono\": \"\",\r\n" + 
				"            \"email\": \"\"\r\n" + 
				"        }\r\n" + 
				"    },\r\n" + 
				"    \"adquiriente\": {\r\n" + 
				"        \"razonSocial\": \"ALBERT BELTRAN ORDOÃ?EZ\",\r\n" + 
				"        \"nombreRegistrado\": \"ALBERT BELTRAN ORDOÃ?EZ\",\r\n" + 
				"        \"tipoIdentificacion\": \"13\",\r\n" + 
				"        \"identificacion\": \"1107052777\",\r\n" + 
				"        \"digitoVerificacion\": \"6\",\r\n" + 
				"        \"naturaleza\": \"2\",\r\n" + 
				"        \"codigoRegimen\": \"49\",\r\n" + 
				"        \"responsabilidadFiscal\": \"O-37\",\r\n" + 
				"        \"codigoImpuesto\": \"01\",\r\n" + 
				"        \"nombreImpuesto\": \"IVA\",\r\n" + 
				"        \"telefono\": \"443 7989\",\r\n" + 
				"        \"email\": \"facturacionelectronica@ccc.org.co\",\r\n" + 
				"        \"contacto\": {\r\n" + 
				"            \"nombre\": \"ALBERT BELTRAN ORDOÃ?EZ\",\r\n" + 
				"            \"telefono\": \"443 7989\",\r\n" + 
				"            \"fax\": \"443 7989\",\r\n" + 
				"            \"email\": \"facturacionelectronica@ccc.org.co\",\r\n" + 
				"            \"observaciones\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"direccion\": {\r\n" + 
				"            \"codigoPais\": \"CO\",\r\n" + 
				"            \"nombrePais\": \"Colombia\",\r\n" + 
				"            \"codigoLenguajePais\": \"es\",\r\n" + 
				"            \"codigoDepartamento\": \"76\",\r\n" + 
				"            \"nombreDepartamento\": \"Valle del Cauca\",\r\n" + 
				"            \"codigoCiudad\": \"76001\",\r\n" + 
				"            \"nombreCiudad\": \"CALI\",\r\n" + 
				"            \"direccionFisica\": \"CRA 15A # 49 -63\",\r\n" + 
				"            \"codigoPostal\": \"760001\"\r\n" + 
				"        },\r\n" + 
				"        \"direccionFiscal\": {\r\n" + 
				"            \"codigoPais\": \"CO\",\r\n" + 
				"            \"nombrePais\": \"Colombia\",\r\n" + 
				"            \"codigoLenguajePais\": \"es\",\r\n" + 
				"            \"codigoDepartamento\": \"76\",\r\n" + 
				"            \"nombreDepartamento\": \"Valle del Cauca\",\r\n" + 
				"            \"codigoCiudad\": \"76001\",\r\n" + 
				"            \"nombreCiudad\": \"CALI\",\r\n" + 
				"            \"direccionFisica\": \"CRA 15A # 49 -63\",\r\n" + 
				"            \"codigoPostal\": \"760001\"\r\n" + 
				"        },\r\n" + 
				"        \"listaResponsabilidadesTributarias\": [{\r\n" + 
				"                \"codigo\": \"ZY\",\r\n" + 
				"                \"nombre\": \"No causa\",\r\n" + 
				"                \"descripcion\": \"\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"codigoCIIU\": \"\",\r\n" + 
				"        \"sucursal\": {\r\n" + 
				"            \"id\": \"\",\r\n" + 
				"            \"numeroMatricula\": \"\",\r\n" + 
				"            \"razonSocial\": \"\",\r\n" + 
				"            \"prefijoFacturacion\": \"\",\r\n" + 
				"            \"direccion\": \"\",\r\n" + 
				"            \"telefono\": \"\",\r\n" + 
				"            \"email\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"centroCosto\": {\r\n" + 
				"            \"id\": \"\",\r\n" + 
				"            \"nombre\": \"\",\r\n" + 
				"            \"direccion\": \"\",\r\n" + 
				"            \"telefono\": \"\",\r\n" + 
				"            \"email\": \"\"\r\n" + 
				"        }\r\n" + 
				"    },\r\n" + 
				"    \"autorizado\": {\r\n" + 
				"        \"razonSocial\": \"\",\r\n" + 
				"        \"tipoIdentificacion\": \"\",\r\n" + 
				"        \"identificacion\": \"\",\r\n" + 
				"        \"digitoVerificacion\": \"\"\r\n" + 
				"    },\r\n" + 
				"    \"entrega\": {\r\n" + 
				"        \"fechaSalidaProductos\": \"\",\r\n" + 
				"        \"horaSalidaProductos\": \"\",\r\n" + 
				"        \"tieneTransportista\": false,\r\n" + 
				"        \"transportista\": {},\r\n" + 
				"        \"direccionEntrega\": {},\r\n" + 
				"        \"condicionesEntrega\": {}\r\n" + 
				"    },\r\n" + 
				"    \"urlAnexos\": \"http://ccc.org.co\",\r\n" + 
				"    \"base64\": \"ASDhoaskdnaosdiADALKSdhapIOJASNdkaJDHasÃ±odashdpasidugABSDka\",\r\n" + 
				"    \"posicionXCufe\": \"1\",\r\n" + 
				"    \"posicionYCufe\": \"1\",\r\n" + 
				"    \"rotacionCufe\": \"0\",\r\n" + 
				"    \"fuenteCufe\": \"6\",\r\n" + 
				"    \"posicionXQr\": \"1\",\r\n" + 
				"    \"posicionYQr\": \"1\",\r\n" + 
				"    \"listaProductos\": [{\r\n" + 
				"            \"numeroLinea\": \"1\",\r\n" + 
				"            \"informacion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"45000\",\r\n" + 
				"            \"idProducto\": \"8094\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"45000\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399001\",\r\n" + 
				"                    \"digitoVerificacion\": \"1\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"2\",\r\n" + 
				"            \"informacion\": \"IMPUESTO DE REGISTRO CON CUANTIA\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"70000\",\r\n" + 
				"            \"idProducto\": \"5900\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"70000\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399029\",\r\n" + 
				"                    \"digitoVerificacion\": \"5\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"3\",\r\n" + 
				"            \"informacion\": \"INTERES MORA NACIONAL CON CUANTIA\",\r\n" + 
				"            \"cantidad\": \"9\",\r\n" + 
				"            \"valorTotal\": \"400\",\r\n" + 
				"            \"idProducto\": \"2082\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"44.444444\",\r\n" + 
				"            \"cantidadReal\": \"9\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399001\",\r\n" + 
				"                    \"digitoVerificacion\": \"1\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"4\",\r\n" + 
				"            \"informacion\": \"ESTAMPILLA PRODESAROLLO\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"700\",\r\n" + 
				"            \"idProducto\": \"5980\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"700\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399029\",\r\n" + 
				"                    \"digitoVerificacion\": \"5\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"5\",\r\n" + 
				"            \"informacion\": \"ESTAMPILLA PRO CULTURA\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"700\",\r\n" + 
				"            \"idProducto\": \"5983\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"700\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399029\",\r\n" + 
				"                    \"digitoVerificacion\": \"5\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"6\",\r\n" + 
				"            \"informacion\": \"ESTAMPILLA PROSEGURIDAD ALIMENTARIA\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"700\",\r\n" + 
				"            \"idProducto\": \"5984\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"700\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399029\",\r\n" + 
				"                    \"digitoVerificacion\": \"5\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"7\",\r\n" + 
				"            \"informacion\": \"ESTAMPILLA PRODESARROLLO UCEVA\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"400\",\r\n" + 
				"            \"idProducto\": \"5986\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"400\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399029\",\r\n" + 
				"                    \"digitoVerificacion\": \"5\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"8\",\r\n" + 
				"            \"informacion\": \"DERECHOS DE MATRICULA\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"0\",\r\n" + 
				"            \"idProducto\": \"10\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"138000\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": true,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399001\",\r\n" + 
				"                    \"digitoVerificacion\": \"1\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": [{\r\n" + 
				"                    \"id\": \"09\",\r\n" + 
				"                    \"esCargo\": false,\r\n" + 
				"                    \"codigo\": \"09\",\r\n" + 
				"                    \"razon\": \"Descuento general\",\r\n" + 
				"                    \"base\": \"138000\",\r\n" + 
				"                    \"porcentaje\": \"100\",\r\n" + 
				"                    \"valor\": \"138000\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"9\",\r\n" + 
				"            \"informacion\": \"SERVICIO ESPECIAL DE CERTIFICACION\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"6100\",\r\n" + 
				"            \"idProducto\": \"2093\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"6100\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399001\",\r\n" + 
				"                    \"digitoVerificacion\": \"1\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }, {\r\n" + 
				"            \"numeroLinea\": \"10\",\r\n" + 
				"            \"informacion\": \"FORMULARIOS MEDIO MAGNETICO\",\r\n" + 
				"            \"cantidad\": \"1\",\r\n" + 
				"            \"valorTotal\": \"6100\",\r\n" + 
				"            \"idProducto\": \"2770\",\r\n" + 
				"            \"codigoPrecio\": \"01\",\r\n" + 
				"            \"valorUnitario\": \"6100\",\r\n" + 
				"            \"cantidadReal\": \"1\",\r\n" + 
				"            \"codigoUnidad\": \"94\",\r\n" + 
				"            \"esMuestraComercial\": false,\r\n" + 
				"            \"item\": {\r\n" + 
				"                \"marca\": \"\",\r\n" + 
				"                \"modelo\": \"\",\r\n" + 
				"                \"codigoArticuloVendedor\": \"\",\r\n" + 
				"                \"codigoExtendidoVendedor\": \"\",\r\n" + 
				"                \"codigoEstandar\": \"999\",\r\n" + 
				"                \"nombreEstandar\": \"\",\r\n" + 
				"                \"descripcion\": \"CONSTITUCION (POR DCMTO PRIVADO)\",\r\n" + 
				"                \"cantidadPaquete\": \"\",\r\n" + 
				"                \"listaCaracteristicas\": [{\r\n" + 
				"                        \"codigo\": \"\",\r\n" + 
				"                        \"valor\": \"\"\r\n" + 
				"                    }\r\n" + 
				"                ],\r\n" + 
				"                \"mandatorio\": {\r\n" + 
				"                    \"tipoIdentificacion\": \"31\",\r\n" + 
				"                    \"identificacion\": \"890399001\",\r\n" + 
				"                    \"digitoVerificacion\": \"1\"\r\n" + 
				"                }\r\n" + 
				"            },\r\n" + 
				"            \"listaImpuestos\": [],\r\n" + 
				"            \"listaCargosDescuentos\": []\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"resolucion\": {\r\n" + 
				"        \"numero\": \"18760000001\",\r\n" + 
				"        \"fechaInicio\": \"2019-01-19\",\r\n" + 
				"        \"fechaFin\": \"2030-01-19\",\r\n" + 
				"        \"numeracion\": {\r\n" + 
				"            \"prefijo\": \"SETT\",\r\n" + 
				"            \"desde\": \"1\",\r\n" + 
				"            \"hasta\": \"5000000\",\r\n" + 
				"            \"fechaInicio\": \"2019-01-19\",\r\n" + 
				"            \"fechaFin\": \"2030-01-19\"\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"}";
		byte[] newPassword = null;
		
		try {
			newPassword = MessageDigest.getInstance("SHA1").digest(password.getBytes("UTF-8"));
			StringBuilder passEnc = new StringBuilder();
			for (int i = 0; i < newPassword.length; ++i) {
				passEnc.append(Integer.toHexString((newPassword[i] & 0xFF) | 0x100).substring(1, 3));
			}
			
			String auth = user + ":" + passEnc.toString();
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
			String authHeaderValue = "Basic " + new String(encodedAuth);
			
			HttpPost request = new HttpPost(urlServicio);
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.addHeader("charset", "UTF-8");
			request.setHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);
			
			StringEntity params = new StringEntity(jsonEnvio.toString(), "UTF-8");
			request.setEntity(params);
	
			RequestConfig config = Utilities.getConfigCon(1000);
			
			CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
			
			//Respuesta
			HttpResponse response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
	
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			String jsonString = "";
			if (Constantes.STATUS_HTTP.intValue() == response.getStatusLine().getStatusCode()) {
				jsonString = EntityUtils.toString(entity);
				System.out.println(jsonString);
			} else {
				jsonString = EntityUtils.toString(entity);
				System.out.println(jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
