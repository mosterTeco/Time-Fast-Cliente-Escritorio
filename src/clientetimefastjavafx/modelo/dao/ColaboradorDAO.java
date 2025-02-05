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
public class ColaboradorDAO {

    public static List<Colaborador> obtenerColaboradores() {

        List<Colaborador> colaboradores = null;

        String url = Constantes.URL_WS + "colaborador/obtenerColaboradores";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>() {
                }.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }

    public static Mensaje registrarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "colaborador/registrarColaborador";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(colaborador);
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

    public static Mensaje editarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "colaborador/editarColaborador";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(colaborador);
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

    public static Mensaje eliminarColaborador(String numeroPersonal) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "colaborador/eliminarColaborador/" + numeroPersonal;

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

    public static Mensaje editarUnidadColaborador(int id, Integer idUnidad) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "colaborador/editarUnidadColaborador";
        Gson gson = new Gson();

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            parametros.put("idUnidad", idUnidad);

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
    
    public static Mensaje subirFoto(Integer id, byte[] foto) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "colaborador/subirFoto/" + id;
        Gson gson = new Gson();

        try {
            RespuestaHTTP respuesta = ConexionWS.peticionPUTBinary(url, foto);

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
    
    public static Colaborador obtenerFotoColaborador(Integer id) {

        Colaborador colaborador = null;

        String url = Constantes.URL_WS + "colaborador/obtenerFoto/" + id;

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                colaborador = gson.fromJson(respuesta.getContenido(), Colaborador.class);
                System.out.println(colaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaborador;
    }
    
    public static List<Colaborador> obtenerColaboradoresDisp() {

        List<Colaborador> colaboradores = null;

        String url = Constantes.URL_WS + "colaborador/obtenerConductoresAsignados";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>() {
                }.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
}
