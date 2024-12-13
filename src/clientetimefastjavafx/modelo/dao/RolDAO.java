/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.RespuestaHTTP;
import clientetimefastjavafx.pojo.Rol;
import clientetimefastjavafx.pojo.Tipo;
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
public class RolDAO {
    
    public static List<Rol> obtenerRoles() {

        List<Rol> roles = null;

        String url = Constantes.URL_WS + "rol/obtenerRoles";

        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            try {
                Type tipoListaRol = new TypeToken<List<Rol>>() {
                }.getType();
                roles = gson.fromJson(respuesta.getContenido(), tipoListaRol);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return roles;
    }
    
    
}
