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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reyes
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField tfNumeroPersonal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onClickIrPantallaPrincipal(ActionEvent event) {
        irPantallaPrincipal();
    }

    public void irPantallaPrincipal() {
        try {
            Stage escenarioBase = (Stage) tfNumeroPersonal.getScene().getWindow();
            Parent pantallaPrincipal = FXMLLoader.load(getClass().getResource("FXMLMenuPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(pantallaPrincipal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Pantalla principal");
            escenarioBase.show();
        } catch (Exception e) {
        }
    }

}