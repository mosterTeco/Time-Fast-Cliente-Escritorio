/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ColaboradorDAO;
import clientetimefastjavafx.modelo.dao.TipoDAO;
import clientetimefastjavafx.modelo.dao.UnidadDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Tipo;
import clientetimefastjavafx.pojo.Unidad;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioUnidadesController implements Initializable {

    private NotificadorOperacion observador;

    private Unidad unidadEdicion;

    private boolean modoEdicion = false;

    @FXML
    private ComboBox<Tipo> comboBoxTipo;
    @FXML
    private Button buttonSeleccionarUnidad;

    List<Tipo> tipos = TipoDAO.obtenerTipos();

    ObservableList<Tipo> tiposUnidades = FXCollections.observableArrayList(tipos);
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfVin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTiposUsuario();
        
        comboBoxTipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxTipo.setStyle("");
            }
        });
    }

    public void inicializarValores(NotificadorOperacion observador, Unidad unidadEdicion) {
        this.observador = observador;
        this.unidadEdicion = unidadEdicion;

        if (unidadEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        } else {

        }
    }

    private void cargarTiposUsuario() {

        if (tipos != null && !tipos.isEmpty()) {
            comboBoxTipo.setItems(tiposUnidades);
        } else {
            System.out.println("No se pudieron cargar los tipos");
        }
    }

    private void cargarDatosEdicion() {
        tfMarca.setText(this.unidadEdicion.getMarca());
        tfModelo.setText(this.unidadEdicion.getModelo());
        tfAnio.setText(this.unidadEdicion.getAnio());
        tfVin.setText(this.unidadEdicion.getVin());
        
        int posicion = buscarTipo(this.unidadEdicion.getIdTipo());
        comboBoxTipo.getSelectionModel().select(posicion);
        
        tfVin.setEditable(false);
        

    }

    @FXML
    private void OnClickAgregarUnidad(ActionEvent event) {
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        String anio = tfAnio.getText();
        String vin = tfVin.getText();

        if (vin == null || vin.length() < 4) {
            System.out.println("El VIN debe tener al menos 4 caracteres.");
            return; 
        }

        String nii = anio + vin.substring(0, 4);

        Integer idTipo = (comboBoxTipo.getSelectionModel().getSelectedItem() != null)
                ? comboBoxTipo.getSelectionModel().getSelectedItem().getId() : null;

        String estado = "Disponible";
        
        Unidad unidad = new Unidad();
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setAnio(anio);
        unidad.setVin(vin);
        unidad.setNii(nii);
        unidad.setEstado(estado);
        unidad.setIdTipo(idTipo);
        
        
        if (!modoEdicion) {
            guardarDatosUnidad(unidad);
        } else {
            unidad.setId(this.unidadEdicion.getId());
            editarDatosUnidad(unidad);
        }
    }
    
    private void guardarDatosUnidad(Unidad unidad) {
        Mensaje msj = UnidadDAO.registrarUnidad(unidad);
        
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro exitoso", "La unidad con NII: " + unidad.getNii() + ", fue registrada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Nuevo registro", unidad.getNii());
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVentana() {
        Stage base = (Stage) tfMarca.getScene().getWindow();
        base.close();
    }
    
    private int buscarTipo(int idTipo) {
        for (int i = 0; i < tiposUnidades.size(); i++) {
            if (tiposUnidades.get(i).getId() == idTipo) {
                return i;
            }
        }
        return 0;
    }
    
    private void editarDatosUnidad(Unidad unidad) {
        Mensaje msj = UnidadDAO.editarUnidad(unidad);
        
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La unidad con NII: " + unidad.getNii() + ", fue registrada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", unidad.getModelo());
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    
    
    
}
