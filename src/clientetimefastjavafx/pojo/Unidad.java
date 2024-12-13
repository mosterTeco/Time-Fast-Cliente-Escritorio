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
public class Unidad {

    private Integer id;
    private String nii;
    private String vin;
    private String marca;
    private String modelo;
    private String anio;
    private String estado;
    private String motivo;
    private Integer idTipo;
    private String tipo;
    private Integer idColaborador;
    private String nombreEliminacion;
    private String nombre;
    private String nombreColaborador;

    public Unidad() {

    }

    public Unidad(Integer id, String nii, String vin, String marca, String modelo, String anio, String estado, String motivo, Integer idTipo, String tipo, Integer idColaborador, String nombreEliminacion, String nombre, String nombreColaborador) {
        this.id = id;
        this.nii = nii;
        this.vin = vin;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.estado = estado;
        this.motivo = motivo;
        this.idTipo = idTipo;
        this.tipo = tipo;
        this.idColaborador = idColaborador;
        this.nombreEliminacion = nombreEliminacion;
        this.nombre = nombre;
        this.nombreColaborador = nombreColaborador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNii() {
        return nii;
    }

    public void setNii(String nii) {
        this.nii = nii;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombreEliminacion() {
        return nombreEliminacion;
    }

    public void setNombreEliminacion(String nombreEliminacion) {
        this.nombreEliminacion = nombreEliminacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }

    @Override
    public String toString() {
        return "ID:" + id + " " + modelo + " " + anio + " NII:" + nii;
    }
}
