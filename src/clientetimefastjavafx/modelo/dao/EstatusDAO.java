/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Estado;
import clientetimefastjavafx.pojo.Estatus;
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
public class EstatusDAO {
    
    public static List<Estatus> obtenerEstatus() {

        List<Estatus> estatus = null;

        String url = Constantes.URL_WS + "estatus/obtenerEstatus";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaEstatus = new TypeToken<List<Estatus>>() {
                }.getType();
                estatus = gson.fromJson(respuesta.getContenido(), tipoListaEstatus);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        return estatus;
    }
    
    
}
