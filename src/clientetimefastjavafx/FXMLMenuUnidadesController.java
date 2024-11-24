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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLMenuUnidadesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnClickAgregarUnidad(ActionEvent event) {
        try {
            Stage escenarioForm = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLFormularioUnidades.fxml"));
            Scene scene = new Scene(root);
            escenarioForm.setScene(scene);
            escenarioForm.setTitle("Registrar unidad");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException e) {
            
        }
    }

    @FXML
    private void OnClickEliminarUnidad(ActionEvent event) {
        try {
            Stage escenarioFormEliminar = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLEliminarUnidades.fxml"));
            Scene scene = new Scene(root);
            escenarioFormEliminar.setScene(scene);
            escenarioFormEliminar.setTitle("Dar de baja unidad");
            escenarioFormEliminar.initModality(Modality.APPLICATION_MODAL);
            escenarioFormEliminar.showAndWait();
        } catch (IOException e) {
            
        }

    }
    
}
