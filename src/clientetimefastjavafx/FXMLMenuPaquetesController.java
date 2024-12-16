/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Paquete;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLMenuPaquetesController implements Initializable, NotificadorOperacion {

    @FXML
    private TableView<?> tblPaquetes;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private TableColumn<?, ?> colPeso;
    @FXML
    private TableColumn<?, ?> colDimensiones;
    @FXML
    private TableColumn<?, ?> colEnvio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnClickAgregarPaquete(ActionEvent event) {
        irFormulario(this, null);
    }
    
    private void irFormulario(NotificadorOperacion observador, Paquete paquete) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioPaquetes.fxml"));
            Parent root = loader.load();
            //--
            FXMLFormularioPaquetesController controlador = loader.getController();
            controlador.inicializarValores(observador, paquete);
            //--
            Stage escenarioForm = new Stage();
            Scene escenaFormulario = new Scene(root);
            escenarioForm.setScene(escenaFormulario);
            escenarioForm.setTitle("Paquetes");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el formulario", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacion(String tipo, String descripcion) {
        System.out.println("Tipo operacion: " + tipo);
        System.out.println("Descripción del Paquete: " + descripcion);
        //cargarInformacionTabla();
    }

}
