/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.TipoDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Tipo;
import clientetimefastjavafx.pojo.Unidad;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox<Tipo> comboBoxRol;
    @FXML
    private Button buttonSeleccionarUnidad;
    
    List<Tipo> tipos = TipoDAO.obtenerTipos();
    
    ObservableList<Tipo> tiposUnidades = FXCollections.observableArrayList(tipos);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTiposUsuario();
        
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
            comboBoxRol.setItems(tiposUnidades);
        } else {
            System.out.println("No se pudieron cargar los tipos");
        }
    }
    

    @FXML
    private void OnClickSeleccionarConductor(ActionEvent event) {
        try {
            Stage escenarioSeleccionar = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLConductoresDisponibles.fxml"));
            Scene scene = new Scene(root);
            escenarioSeleccionar.setScene(scene);
            escenarioSeleccionar.setTitle("Seleccionar conductor");
            escenarioSeleccionar.initModality(Modality.APPLICATION_MODAL);
            escenarioSeleccionar.showAndWait();
        } catch (IOException e) {

        }
    }
    
    private void cargarDatosEdicion(){
        
    }

    @FXML
    private void OnClickAgregarUnidad(ActionEvent event) {
        
    }

}
