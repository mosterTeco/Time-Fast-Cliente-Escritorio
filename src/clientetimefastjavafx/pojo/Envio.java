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
public class Envio {

    private Integer id;
    private String numeroGuia;
    private String calleOrigen;
    private String coloniaOrigen;
    private Integer numeroOrigen;
    private String cpOrigen;
    private String ciudadOrigen;
    private Integer idEstadoOrigen;
    private String estadoOrigen;
    private String calleDestino;
    private String coloniaDestino;
    private Integer numeroDestino;
    private String cpDestino;
    private String ciudadDestino;
    private Integer idEstadoDestino;
    private String estadoDestino;
    private Float costo;
    private Integer idColaborador;
    private String colaborador;
    private Integer idCliente;
    private String cliente;
    private String correo;
    private String telefono;
    private Integer idEstatus;
    private String estatus;

    public Envio() {

    }

    public Envio(Integer id, String numeroGuia, String calleOrigen, String coloniaOrigen, Integer numeroOrigen, String cpOrigen, String ciudadOrigen, Integer idEstadoOrigen, String estadoOrigen, String calleDestino, String coloniaDestino, Integer numeroDestino, String cpDestino, String ciudadDestino, Integer idEstadoDestino, String estadoDestino, Float costo, Integer idColaborador, String colaborador, Integer idCliente, String cliente, String correo, String telefono, Integer idEstatus, String estatus) {
        this.id = id;
        this.numeroGuia = numeroGuia;
        this.calleOrigen = calleOrigen;
        this.coloniaOrigen = coloniaOrigen;
        this.numeroOrigen = numeroOrigen;
        this.cpOrigen = cpOrigen;
        this.ciudadOrigen = ciudadOrigen;
        this.idEstadoOrigen = idEstadoOrigen;
        this.estadoOrigen = estadoOrigen;
        this.calleDestino = calleDestino;
        this.coloniaDestino = coloniaDestino;
        this.numeroDestino = numeroDestino;
        this.cpDestino = cpDestino;
        this.ciudadDestino = ciudadDestino;
        this.idEstadoDestino = idEstadoDestino;
        this.estadoDestino = estadoDestino;
        this.costo = costo;
        this.idColaborador = idColaborador;
        this.colaborador = colaborador;
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.correo = correo;
        this.telefono = telefono;
        this.idEstatus = idEstatus;
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getCalleOrigen() {
        return calleOrigen;
    }

    public void setCalleOrigen(String calleOrigen) {
        this.calleOrigen = calleOrigen;
    }

    public String getColoniaOrigen() {
        return coloniaOrigen;
    }

    public void setColoniaOrigen(String coloniaOrigen) {
        this.coloniaOrigen = coloniaOrigen;
    }

    public Integer getNumeroOrigen() {
        return numeroOrigen;
    }

    public void setNumeroOrigen(Integer numeroOrigen) {
        this.numeroOrigen = numeroOrigen;
    }

    public String getCpOrigen() {
        return cpOrigen;
    }

    public void setCpOrigen(String cpOrigen) {
        this.cpOrigen = cpOrigen;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Integer getIdEstadoOrigen() {
        return idEstadoOrigen;
    }

    public void setIdEstadoOrigen(Integer idEstadoOrigen) {
        this.idEstadoOrigen = idEstadoOrigen;
    }

    public Integer getIdEstadoDestino() {
        return idEstadoDestino;
    }

    public void setIdEstadoDestino(Integer idEstadoDestino) {
        this.idEstadoDestino = idEstadoDestino;
    }

    public String getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(String estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public String getCalleDestino() {
        return calleDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;
    }

    public String getColoniaDestino() {
        return coloniaDestino;
    }

    public void setColoniaDestino(String coloniaDestino) {
        this.coloniaDestino = coloniaDestino;
    }

    public Integer getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(Integer numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public String getCpDestino() {
        return cpDestino;
    }

    public void setCpDestino(String cpDestino) {
        this.cpDestino = cpDestino;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
