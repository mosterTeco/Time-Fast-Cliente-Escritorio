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
public class Posee {
    
    private Integer id;
    private String motivo;
    private String nombreColaborador;
    private String tiempo;
    private Integer idEnvio;
    private Integer idEstatus;

    public Posee() {
        
    }

    public Posee(Integer id, String motivo, String nombreColaborador, String tiempo, Integer idEnvio, Integer idEstatus) {
        this.id = id;
        this.motivo = motivo;
        this.nombreColaborador = nombreColaborador;
        this.tiempo = tiempo;
        this.idEnvio = idEnvio;
        this.idEstatus = idEstatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }
    
}
