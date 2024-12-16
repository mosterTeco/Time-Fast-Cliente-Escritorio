/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Envio;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.RespuestaHTTP;
import clientetimefastjavafx.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author reyes
 */
public class EnvioDAO {
    
    public static List<Envio> obtenerEnvios() {

        List<Envio> envios = null;

        String url = Constantes.URL_WS + "envio/obtenerEnvios";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaEnvio = new TypeToken<List<Envio>>() {
                }.getType();
                envios = gson.fromJson(respuesta.getContenido(), tipoListaEnvio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return envios;
    }
    
    public static Mensaje registrarEnvio(Envio envio) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "envio/crearEnvio";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJSON(url, parametros);

            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }

        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    public static Mensaje editarEnvio(Envio envio) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "envio/actualizarEnvio";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJSON(url, parametros);

            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    
    
}
