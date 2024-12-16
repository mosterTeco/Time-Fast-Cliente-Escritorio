/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Cliente;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Posee;
import clientetimefastjavafx.pojo.RespuestaHTTP;
import clientetimefastjavafx.utilidades.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author reyes
 */
public class PoseeDAO {
    
    public static Mensaje registrarPosee(Posee posee) {
        Mensaje msj = new Mensaje();

        String url = Constantes.URL_WS + "posee/historialEstatus";

        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(posee);
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
}
