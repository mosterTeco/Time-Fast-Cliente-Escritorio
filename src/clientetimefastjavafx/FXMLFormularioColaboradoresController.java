/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioColaboradoresController implements Initializable {

    @FXML
    private ComboBox<String> comboBoxRol;
    @FXML
    private Label labelLicencia;
    @FXML
    private TextField textLicencia;
    @FXML
    private Label labelUnidad;
    @FXML
    private Button buttonSeleccionarUnidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxRol.getItems().addAll("Administrador", "Conductor");

        comboBoxRol.setOnAction(event -> {
            String selectedRole = (String) comboBoxRol.getValue();
            if ("Conductor".equals(selectedRole)) {
                mostrarCamposConductor(true);
            } else {
                mostrarCamposConductor(false);
            }
        });
    }    
    
    private void mostrarCamposConductor(boolean mostrar) {
        labelLicencia.setVisible(mostrar);
        labelLicencia.setManaged(mostrar);
        textLicencia.setVisible(mostrar);
        textLicencia.setManaged(mostrar);
        labelUnidad.setVisible(mostrar);
        labelUnidad.setManaged(mostrar);
        buttonSeleccionarUnidad.setVisible(mostrar);
        buttonSeleccionarUnidad.setManaged(mostrar);
    }

    @FXML
    private void OnClickSeleccionarUnidad(ActionEvent event) {
        try {
            Stage escenarioSeleccionar = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLUnidadesDisponibles.fxml"));
            Scene scene = new Scene(root);
            escenarioSeleccionar.setScene(scene);
            escenarioSeleccionar.setTitle("Seleccionar unidad");
            escenarioSeleccionar.initModality(Modality.APPLICATION_MODAL);
            escenarioSeleccionar.showAndWait();
        } catch (IOException e) {
            
        }
    }
    
}
