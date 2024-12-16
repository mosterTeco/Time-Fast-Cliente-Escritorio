/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Estado;
import clientetimefastjavafx.pojo.RespuestaHTTP;
import clientetimefastjavafx.pojo.Rol;
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
public class EstadoDAO {
    
    public static List<Estado> obtenerEstados() {

        List<Estado> estados = null;

        String url = Constantes.URL_WS + "estado/obtenerEstados";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaEstado = new TypeToken<List<Estado>>() {
                }.getType();
                estados = gson.fromJson(respuesta.getContenido(), tipoListaEstado);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return estados;
    }
    
}
