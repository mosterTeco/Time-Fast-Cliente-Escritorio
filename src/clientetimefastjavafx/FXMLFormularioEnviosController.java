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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioEnviosController implements Initializable {

    @FXML
    private ComboBox<?> comboBoxRol;
    @FXML
    private Button buttonSeleccionarUnidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnClickSeleccionarConductor(ActionEvent event) {
        try {
            Stage escenarioSelec = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLConductoresDisponibles.fxml"));
            Scene scene = new Scene(root);
            escenarioSelec.setScene(scene);
            escenarioSelec.setTitle("Seleccionar conudctor");
            escenarioSelec.initModality(Modality.APPLICATION_MODAL);
            escenarioSelec.showAndWait();
        } catch (IOException e) {

        }
    }
    
}
