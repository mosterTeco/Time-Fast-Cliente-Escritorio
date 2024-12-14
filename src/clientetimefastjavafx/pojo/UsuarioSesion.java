/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.pojo;

/**
 *
 * @author reyes
 */
public class UsuarioSesion {
    private static UsuarioSesion instancia;
    private String nombreCompleto;
    private String numeroPersonal;

    private UsuarioSesion() {
    }

    public static UsuarioSesion getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioSesion();
        }
        return instancia;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public void cerrarSesion() {
        instancia = null;
    }
}
