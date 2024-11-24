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
public class FXMLMenuClientesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnClickAgregarCliente(ActionEvent event) {
        try {
            Stage escenarioForm = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLFormularioClientes.fxml"));
            Scene scene = new Scene(root);
            escenarioForm.setScene(scene);
            escenarioForm.setTitle("Registrar cliente");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException e) {
            
        }
    }
    
}
