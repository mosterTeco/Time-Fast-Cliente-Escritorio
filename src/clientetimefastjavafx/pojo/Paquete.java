/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx.pojo;

/**
 *
 * @author USER
 */
public class Paquete {
    
    private Integer id;
    private String descripcion;
    private Float peso;
    private String dimensiones;
    private Integer idEnvio;
    private String noGuiaEnvio;
    
    public Paquete() {
        
    }

    public Paquete(Integer id, String descripcion, Float peso, String dimensiones, Integer idEnvio, String noGuiaEnvio) {
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.idEnvio = idEnvio;
        this.noGuiaEnvio = noGuiaEnvio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getNoGuiaEnvio() {
        return noGuiaEnvio;
    }

    public void setNoGuiaEnvio(String noGuiaEnvio) {
        this.noGuiaEnvio = noGuiaEnvio;
    }
    
}
