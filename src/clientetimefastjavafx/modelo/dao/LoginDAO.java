/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.modelo.dao;

import clientetimefastjavafx.modelo.ConexionWS;
import clientetimefastjavafx.pojo.Login;
import clientetimefastjavafx.pojo.RespuestaHTTP;
import clientetimefastjavafx.utilidades.Constantes;
import java.net.HttpURLConnection;
import com.google.gson.Gson;

/**
 *
 * @author reyes
 */
public class LoginDAO {
    
    public static Login iniciarSesion(String numeroPersonal, String password) {
        Login respuestaLogin = new Login();
        String urlServicio = Constantes.URL_WS + "login/validarCredenciales";
        String parametros = String.format("numeroPersonal=%s&password=%s", numeroPersonal, password);
        RespuestaHTTP respuestaWS = ConexionWS.peticionPOST(urlServicio, parametros);
        if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            System.out.println("JSON:" + respuestaWS.getContenido());
            Gson gson = new Gson();
            respuestaLogin = gson.fromJson(respuestaWS.getContenido(), Login.class);
        } else {
            respuestaLogin.setError(true);
            respuestaLogin.setMensaje("Lo sentimos, hubo un error al procesar la autenticacion, por favor intentalo más tarde");
        }

        return respuestaLogin;
    }

}
