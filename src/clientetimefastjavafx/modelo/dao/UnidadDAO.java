/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.RespuestaHTTP;
import clientetimefastjavafx.pojo.Unidad;
import clientetimefastjavafx.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author reyes
 */
public class UnidadDAO {
    
    public static List<Unidad> obtenerUnidades() {

        List<Unidad> unidades = null;

        String url = Constantes.URL_WS + "unidad/obtenerUnidades";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaUnidad = new TypeToken<List<Unidad>>() {
                }.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaUnidad);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    
    public static List<Unidad> obtenerUnidadesDisp() {

        List<Unidad> unidades = null;

        String url = Constantes.URL_WS + "unidad/obtenerUnidadesDisponibles";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaUnidad = new TypeToken<List<Unidad>>() {
                }.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaUnidad);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    
    public static Mensaje registrarUnidad(Unidad unidad) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "unidad/registrar";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(unidad);
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
    
    public static Mensaje editarUnidad(Unidad unidad) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "unidad/editar";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(unidad);
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
    
    public static Mensaje editarEstadoUnidad(int id, String estado) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "unidad/editarEstadoUnidad";
        Gson gson = new Gson();

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            parametros.put("estado", estado);

            String jsonParametros = gson.toJson(parametros);

            RespuestaHTTP respuesta = ConexionWS.peticionPUTJSON(url, jsonParametros);

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
