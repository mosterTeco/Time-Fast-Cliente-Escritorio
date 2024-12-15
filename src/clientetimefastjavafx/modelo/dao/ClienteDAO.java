/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Cliente;
import clientetimefastjavafx.pojo.Colaborador;
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
public class ClienteDAO {
    
    public static List<Cliente> obtenerClientes() {

        List<Cliente> clientes = null;

        String url = Constantes.URL_WS + "cliente/obtenerClientes";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaCliente = new TypeToken<List<Cliente>>() {
                }.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoListaCliente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }
    
    public static Mensaje registrarCliente(Cliente cliente) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "cliente/registrarCliente";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(cliente);
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
    
    public static Mensaje editarCliente(Cliente cliente) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "cliente/editarCliente";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(cliente);
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
    
    public static Mensaje eliminarCliente(Integer id) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "cliente/eliminarCliente/" + id;

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
    
}
