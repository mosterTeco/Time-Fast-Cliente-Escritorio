/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Paquete;
import clientetimefastjavafx.pojo.RespuestaHTTP;
import clientetimefastjavafx.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author USER
 */
public class PaqueteDAO {
    
    public static List<Paquete> obtenerPaquetes() {

        List<Paquete> paquetes = null;

        String url = Constantes.URL_WS + "paquete/obtenerPaquetes";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaPaquete = new TypeToken<List<Paquete>>() {
                }.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paquetes;
    }
    
    public static List<Paquete> obtenerPaquetesEnvio(Integer idEnvio) {

        List<Paquete> paquetes = null;

        String url = Constantes.URL_WS + "paquete/obtenerPaquetesEnvio/" + idEnvio;

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaPaquete = new TypeToken<List<Paquete>>() {
                }.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paquetes;
    }
    
    public static Mensaje registrarPaquete(Paquete paquete) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "paquete/crearPaquete";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(paquete);
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
    
    public static Mensaje eliminarPaquete(Integer id) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "paquete/eliminarPaquete" + id;

        Gson gson = new Gson();

        try {
            RespuestaHTTP respuesta = ConexionWS.peticionDELETEURL(url, null);

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
    
    public static Mensaje editarCliente(Paquete paquete) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "paquete/actualizarPaquete";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(paquete);
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
