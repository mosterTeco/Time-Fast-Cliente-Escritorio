/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.UnidadDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Unidad;
import clientetimefastjavafx.pojo.UsuarioSesion;
import clientetimefastjavafx.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLEliminarUnidadesController implements Initializable {

    private NotificadorOperacion observador;

    private Unidad unidadEdicion;
    @FXML
    private TextField tfVin;
    @FXML
    private TextField tfNii;
    @FXML
    private TextArea tfMotivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarValores(NotificadorOperacion observador, Unidad unidadEdicion) {
        this.observador = observador;
        this.unidadEdicion = unidadEdicion;

        cargarDatosEdicion();
    }

    @FXML
    private void onClickDarDeBajaUnidad(ActionEvent event) {
        String nii = this.unidadEdicion.getNii();
        String vin = this.unidadEdicion.getVin();
        String marca = this.unidadEdicion.getMarca();
        String modelo = this.unidadEdicion.getModelo();
        String anio = this.unidadEdicion.getAnio();
        String estado = "Baja";
        String motivo = tfMotivo.getText();
        int idTipo = this.unidadEdicion.getIdTipo();
        String nombreEdicion = UsuarioSesion.getInstancia().getNombreCompleto();
        
        Unidad unidad = new Unidad();
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setAnio(anio);
        unidad.setVin(vin);
        unidad.setNii(nii);
        unidad.setEstado(estado);
        unidad.setIdTipo(idTipo);
        unidad.setMotivo(motivo);
        unidad.setNombreEliminacion(nombreEdicion);
        
        unidad.setId(this.unidadEdicion.getId());
        editarDatosUnidad(unidad);
        
    }

    private void cargarDatosEdicion() {
        tfVin.setText(this.unidadEdicion.getVin());
        tfNii.setText(this.unidadEdicion.getNii());
        tfMotivo.setText(this.unidadEdicion.getMotivo());
        
        tfVin.setEditable(false);
        tfNii.setEditable(false);
    }
    
    private void editarDatosUnidad(Unidad unidad) {
        Mensaje msj = UnidadDAO.editarUnidad(unidad);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La unidad con NII: " + unidad.getNii() + ", fue dada de baja de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", unidad.getModelo());
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void onClickCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    
    private void cerrarVentana() {
        Stage base = (Stage) tfVin.getScene().getWindow();
        base.close();
    }
}
