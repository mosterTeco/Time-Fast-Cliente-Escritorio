/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.EnvioDAO;
import clientetimefastjavafx.modelo.dao.PaqueteDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Envio;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Paquete;
import clientetimefastjavafx.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioPaquetesController implements Initializable {
    
    private NotificadorOperacion observador;
    
    private Paquete paqueteEdicion;

    @FXML
    private TextField textPeso;
    @FXML
    private TextArea areaDescripcion;
    @FXML
    private TextField textDimensiones;
    @FXML
    private ComboBox<Envio> comboBoxEnvio;
    
    List<Envio> envios = EnvioDAO.obtenerEnvios();
    
    ObservableList<Envio> enviosDatos = FXCollections.observableArrayList(envios);

    private boolean modoEdicion = false;
    private Envio envio ;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        cargarEnvios();
        
        
        comboBoxEnvio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxEnvio.setStyle("");
            }
        });
    }    
    
    void inicializarValores(NotificadorOperacion observador, Paquete paqueteEdicion) {
        this.observador = observador;
        this.paqueteEdicion = paqueteEdicion;

        if (paqueteEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        } else {

        }
    }

    @FXML
    private void AgregarPaquete(ActionEvent event) {
        String dimensiones = textDimensiones.getText();
        Float peso = Float.parseFloat(textPeso.getText());
        String descripcion = areaDescripcion.getText();
        
        Paquete paquete = new Paquete();
        paquete.setDimensiones(dimensiones);
        paquete.setPeso(peso);
        paquete.setDescripcion(descripcion);
        paquete.setIdEnvio(comboBoxEnvio.getSelectionModel().getSelectedItem().getId());
        
        if (!modoEdicion) {
            guardarDatosPaquete(paquete);
        } else {
            paquete.setId(this.paqueteEdicion.getId());
            editarDatosPaquete(paquete);
        }
    }
    
    private void guardarDatosPaquete(Paquete paquete) {
        Mensaje msj = PaqueteDAO.registrarPaquete(paquete);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro exitoso", "La información del Paquete, fue registrada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Nuevo registro", paquete.getDescripcion());
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarDatosEdicion(){
        textPeso.setText(this.paqueteEdicion.getPeso().toString());
        textDimensiones.setText(this.paqueteEdicion.getDimensiones());
        areaDescripcion.setText(this.paqueteEdicion.getDescripcion());
        
        int posicion = buscarIdEnvio(this.paqueteEdicion.getId());
        comboBoxEnvio.getSelectionModel().select(posicion);
    }
    
    private void editarDatosPaquete(Paquete paquete) {
        Mensaje msj = PaqueteDAO.editarCliente(paquete);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La información del paquete: " + paquete.getId(), Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", paquete.getDescripcion());
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarEnvios() {
        if (envios != null && !envios.isEmpty()) {
            comboBoxEnvio.setItems(enviosDatos);
        } else {
            System.out.println("No se pudieron cargar los envios");
        }
    }
    
    private void cerrarVentana() {
        Stage base = (Stage) textDimensiones.getScene().getWindow();
        base.close();
    }
    
    private int buscarIdEnvio(int id) {
        for (int i = 0; i < enviosDatos.size(); i++) {
            if (enviosDatos.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }
}
