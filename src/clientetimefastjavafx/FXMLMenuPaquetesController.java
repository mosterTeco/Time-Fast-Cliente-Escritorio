/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.PaqueteDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Paquete;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLMenuPaquetesController implements Initializable, NotificadorOperacion {
    
    private ObservableList<Paquete> paquetes;

    @FXML
    private TableView<Paquete> tblPaquetes;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colPeso;
    @FXML
    private TableColumn colDimensiones;
    @FXML
    private TableColumn colEnvio;
    @FXML
    private TableColumn colNoGuia;
    @FXML
    private TextField tfBuscarPaquete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        configurarTabla();
        cargarInformacionTabla();
        
        configurarFiltroBusqueda();
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
            escenarioForm.setResizable(false);
            escenarioForm.setTitle("Paquetes");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el formulario", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla() {
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        colDimensiones.setCellValueFactory(new PropertyValueFactory("dimensiones"));
        colEnvio.setCellValueFactory(new PropertyValueFactory("idEnvio"));
        colNoGuia.setCellValueFactory(new PropertyValueFactory("noGuiaEnvio"));
    }
    
    private void cargarInformacionTabla() {
        paquetes = FXCollections.observableArrayList();
        List<Paquete> listaWS = PaqueteDAO.obtenerPaquetes();
        if (listaWS != null) {
            paquetes.addAll(listaWS);
            tblPaquetes.setItems(paquetes);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Por el momento no se puede cargar la informacion de los Paquetes. Intentalo más tarde.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void OnClickEditarPaquete(ActionEvent event) {
        Paquete paquete = tblPaquetes.getSelectionModel().getSelectedItem();
        if (paquete!= null) {
            irFormulario(this, paquete);
        } else {
            Utilidades.mostrarAlertaSimple("Seleccionar unidad", "Para editar debes seleccioar un paquete de la tabla", Alert.AlertType.WARNING);
        }
    }
    
    @Override
    public void notificarOperacion(String tipo, String descripcion) {
        System.out.println("Tipo operacion: " + tipo);
        System.out.println("Descripción del Paquete: " + descripcion);
        cargarInformacionTabla();
    }

    @FXML
    private void OnClickEliminarPaquete(ActionEvent event) {
        Paquete paquete = tblPaquetes.getSelectionModel().getSelectedItem();
        
        if (paquete != null) {
            eliminarPaquete(paquete.getId());
        } else {
            Utilidades.mostrarAlertaSimple("Seleccionar colaborador", "Para eliminar debes seleccioar un colaborador de la tabla", Alert.AlertType.WARNING);
        } 
    }
    
    private void eliminarPaquete(Integer id) {
        Mensaje msj = PaqueteDAO.eliminarPaquete(id);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Eliminacion exitosa", "El paquete con ID: " + id , Alert.AlertType.INFORMATION);
            cargarInformacionTabla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al eliminar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void configurarFiltroBusqueda() {
        FilteredList<Paquete> filteredData = new FilteredList<>(paquetes, p -> true);

        tfBuscarPaquete.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Paquete paquete) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (paquete.getNoGuiaEnvio().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (paquete.getIdEnvio().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } 

                return false; 
            });
        });

        SortedList<Paquete> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblPaquetes.comparatorProperty());
        tblPaquetes.setItems(sortedData);
    }
    
    
}
